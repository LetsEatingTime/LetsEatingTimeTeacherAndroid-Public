package school.alt.teacher.main.viewmodel.student

import android.net.Uri
import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import school.alt.teacher.main.ui.theme.line1
import school.alt.teacher.main.ui.theme.pretendard
import school.alt.teacher.main.ui.theme.warning
import school.alt.teacher.main.viewmodel.home.NetworkResultState
import school.alt.teacher.network.dto.EditUserRequest
import school.alt.teacher.network.dto.ImageResponse
import school.alt.teacher.network.dto.UserDto
import school.alt.teacher.network.dto.UserInfoDto
import school.alt.teacher.network.teacher.TeacherRepository
import school.alt.teacher.network.user.UserRepository
import javax.inject.Inject

@HiltViewModel
class StudentViewModel @Inject constructor(
    private val teacherRepository: TeacherRepository,
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _students1 = mutableStateOf(
        mutableListOf<MutableList<UserDto>>(
        )
    )

    private val _student = mutableStateOf<UserInfoDto?>(null)
    val student: State<UserInfoDto?> = _student

    private val _toastMessage = MutableStateFlow<String>("")
    val toastMessage: StateFlow<String> = _toastMessage

    private var _approvalListUiState =
        MutableStateFlow<ApprovalListUiState>(ApprovalListUiState.Success(emptyList()))
    val approvalListUiState: StateFlow<ApprovalListUiState> = _approvalListUiState

    private var _approveStudentUiState =
        MutableStateFlow<ApproveStudentUiState>(ApproveStudentUiState.Success())
    val approveStudentUiState: StateFlow<ApproveStudentUiState> = _approveStudentUiState

    private var _userListUiState = MutableStateFlow<UserListUiState>(
        UserListUiState.Success(
            mutableMapOf()
        )
    )
    val userListUiState: StateFlow<UserListUiState> = _userListUiState

    private var _userUiState =
        MutableStateFlow<StudentUiState>(StudentUiState.Success(UserInfoDto()))
    val userUiState: StateFlow<StudentUiState> = _userUiState

    private var _studentImageUiState = MutableStateFlow<StudentImageUrlUiState>(
        StudentImageUrlUiState.Success(
            ImageResponse()
        )
    )
    val studentImageUiState: StateFlow<StudentImageUrlUiState> = _studentImageUiState

    fun initToastMessage() {
        _toastMessage.value = ""
    }

    private var _studentList = MutableLiveData<MutableList<MutableList<UserDto>>>()
    val studentList: LiveData<MutableList<MutableList<UserDto>>> = _studentList

    private var _studentListState = MutableStateFlow(NetworkResultState())
    val studentListState: SharedFlow<NetworkResultState> = _studentListState

    private var _approvalList = MutableLiveData<List<UserInfoDto>>()
    val approvalList: LiveData<List<UserInfoDto>> = _approvalList

    private var _approvalListState = MutableStateFlow(NetworkResultState())
    val approvalListState: SharedFlow<NetworkResultState> = _approvalListState

    private val _imageUri = mutableStateOf<Uri>("".toUri())
    val imageUri: State<Uri> = _imageUri
    fun setUri(inputValue: Uri) {
        _imageUri.value = inputValue
    }

    private var _studentDialogUiState =
        MutableStateFlow<CustomAlertDialogState>(CustomAlertDialogState())
    val studentDialogUiState: StateFlow<CustomAlertDialogState> = _studentDialogUiState

    fun setUserData(userInfo: UserInfoDto) {
        _student.value = userInfo
    }

    fun initValue() {
        getStudentList()
        getApprovalList()
    }

    fun getStudentList() = viewModelScope.launch {
        kotlin.runCatching {
            teacherRepository.getUsers()
        }.onSuccess { data ->
            _studentListState.emit(NetworkResultState("", true))
            data.onEach { responseDto ->
                // 조회 요령 list[학년][반]
                val userMap: MutableMap<Int, MutableList<MutableList<UserDto>>> = mutableMapOf(
                    0 to mutableListOf(
                        mutableListOf(),
                        mutableListOf(),
                        mutableListOf(),
                        mutableListOf()
                    ),    // 전체
                    1 to mutableListOf(
                        mutableListOf(),
                        mutableListOf(),
                        mutableListOf(),
                        mutableListOf()
                    ),    // 1학년
                    2 to mutableListOf(
                        mutableListOf(),
                        mutableListOf(),
                        mutableListOf(),
                        mutableListOf()
                    ),    // 2학년
                    3 to mutableListOf(
                        mutableListOf(),
                        mutableListOf(),
                        mutableListOf(),
                        mutableListOf()
                    ),    // 3학년
                )
                val userList = mutableListOf<MutableList<UserDto>>(
                    mutableListOf<UserDto>(), // 전체
                    mutableListOf<UserDto>(), // 1학년
                    mutableListOf<UserDto>(), // 2학년
                    mutableListOf<UserDto>(), // 3학년
                )
                responseDto.data.forEach {
                    if (it.user.userType == "S") { // 학생일떄만 추가
                        if (it.user.className <= 4) {
                            if (it.user.className != 0) {
                                userMap[it.user.grade]?.get(it.user.className - 1)?.add(it)
                                userMap[0]?.get(it.user.className - 1)?.add(it)
                            } else {
                                userMap[0]?.get(it.user.className)?.add(it)
                                userMap[it.user.grade]?.get(it.user.className - 1)?.add(it)
                            }
                        }
                    }
                }
                _userListUiState.emit(UserListUiState.Success(userMap))
                _studentList.value = userList
            }.launchIn(viewModelScope)
        }.onFailure { e ->
            if (e is HttpException) {
                if (e.response.code == 500) {
                    _userListUiState.emit(UserListUiState.Error(e, message = "서버 에러"))
                }
            }
            _toastMessage.value = "서버 에러"
            _studentListState.emit(NetworkResultState(e.message.toString(), false))
        }
    }

    fun getApprovalList() = viewModelScope.launch {
        kotlin.runCatching {
            teacherRepository.appreve(0, 100)
        }.onSuccess { result ->
            result.onEach {
                _approvalListUiState.emit(ApprovalListUiState.Success(it.data))
            }.launchIn(viewModelScope)
        }.onFailure { e ->
            _toastMessage.value = "서버 에러"
            if (e is HttpException) {
                if (e.response.code == 500) {
                    _approvalListUiState.emit(ApprovalListUiState.Error(e, "서버에러"))
                }
            }
            _studentListState.emit(NetworkResultState(e.message.toString(), false))
        }
    }

    fun getStudentImageUrl(studentIdx: Int) = viewModelScope.launch {
        kotlin.runCatching {
            userRepository.getUserImage(idx = studentIdx)
        }.onSuccess { result ->
            result.onEach {
                _studentImageUiState.emit(StudentImageUrlUiState.Success(it))
            }.launchIn(viewModelScope)
        }.onFailure { e ->
            var message = ""
            if (e is HttpException) {
                message = if (e.response.code == 500) {
                    "서버에 문제가 발생하였습니다."
                } else {
                    "학생 이미지를 불러오는데 실패했습니다."
                }
            }
            _studentImageUiState.emit(StudentImageUrlUiState.Error(e, message))

        }
    }

    fun getUsers(onAction: (Boolean) -> Unit) {
        viewModelScope.launch {
            kotlin.runCatching {
                teacherRepository.getUsers()
            }.onSuccess { data ->
                val result1 = mutableListOf<MutableList<UserDto>>(
                )
//                data.data.forEach {
//                    if(it.user.userType == "S"){
//                        if (it.user.grade != 0){
//                            result1[it.user.className-1].add(it)
//                        }
//                        else{
//                            result1[it.user.className].add(it)
//                        }
//                    }
//                }
                _students1.value = result1
                onAction(true)
            }.onFailure { e ->
                e.printStackTrace()
                Log.e("Student", "Error message: ${e.message}")
                withContext(Dispatchers.Main) {
                    if (e is HttpException) {
                        Log.e("Student", "HttpException occurred")
                        val errorBody = e.response.body
                        Log.e("Student", "Error body: $errorBody")
                    }
                }
            }
        }
    }

    fun uploadStudentImage(id: String, image: MultipartBody.Part) = viewModelScope.launch {
        runCatching {
            teacherRepository.uploadPhoto(
                image = image,
                id = id.toRequestBody("text/plain".toMediaTypeOrNull()),
                table = "user".toRequestBody("text/plain".toMediaTypeOrNull())
            )
        }.onSuccess {

        }.onFailure { e ->
            e.printStackTrace()
        }
    }

    fun modifyStudent(userdata: EditUserRequest, onAction: () -> Unit) {
        viewModelScope.launch {
            kotlin.runCatching { teacherRepository.editStudent(userdata) }
                .onSuccess { result ->
                    withContext(Dispatchers.Main) {
                        onAction()
                    }
                }
                .onFailure { e ->
                    e.printStackTrace()
                    Log.e("Student", "Error message: ${e.message}")
                    withContext(Dispatchers.Main) {
                        if (e is HttpException) {
                            Log.e("Student", "HttpException occurred")
                            val errorBody = e.response.body
                            Log.e("Student", "Error body: $errorBody")
                        }
                    }
                }
        }
    }

    fun showDeleteStudent(id: String, onAction: () -> Unit) =
        viewModelScope.launch {
            _studentDialogUiState.emit(
                CustomAlertDialogState(
                    title = "학생을 회원탈퇴 하시겠습니까?",
                    description = "",
                    negativeComment = {
                        Text(
                            text = "취소",
                            color = line1,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = pretendard
                        )
                    },
                    positiveComment = {
                        Text(
                            text = "회원탈퇴",
                            color = warning,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = pretendard
                        )
                    },
                    onClickNegative = {
                        resetDialogState()
                    },
                    onClickPositive = {
                        deleteStudent(id) {
                            onAction()
                        }
                        resetDialogState()
                    },
                )
            )
        }

    private fun deleteStudent(id: String, onAction: () -> Unit) {
        viewModelScope.launch {
            kotlin.runCatching { teacherRepository.deleteUser(id) }
                .onSuccess {
                    _toastMessage.value = "성공적으로 학생이 탈퇴되었습니다."
                    withContext(Dispatchers.Main) {
                        onAction()
                    }
                }.onFailure { e ->
                    withContext(Dispatchers.Main) {
                        if (e is HttpException) {
                            Log.e("Student", "HttpException occurred")
                            val errorBody = e.response.body
                            Log.e("Student", "Error body: $errorBody")
                        }
                    }
                    _toastMessage.value = "서버 에러"
                }
        }
    }

    fun unApproveUser(id: String) {
        viewModelScope.launch {
            kotlin.runCatching { teacherRepository.deleteUser(id) }
                .onSuccess {
                    _approveStudentUiState.emit(
                        ApproveStudentUiState.Success(
                            message = "성공적으로 학생이 거부되었습니다.",
                            isApprovalStudent = true
                        )
                    )
                    _toastMessage.emit("성공적으로 학생이 거부되었습니다.")
                    getApprovalList()
                }.onFailure { e ->
                    withContext(Dispatchers.Main) {
                        if (e is HttpException) {
                            Log.e("Student", "HttpException occurred")
                            val errorBody = e.response.body
                            Log.e("Student", "Error body: $errorBody")
                        }
                    }
                    _approveStudentUiState.emit(
                        ApproveStudentUiState.Error(
                            exception = e,
                            message = "서버 에러",
                        )
                    )
                    _toastMessage.emit("서버 에러")
                }
        }
    }

    fun approveUser(id: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                teacherRepository.appreveUser(id)
            }.onSuccess { result ->
                _approveStudentUiState.emit(
                    ApproveStudentUiState.Success(
                        message = "성공적으로 학생이 승인되었습니다.",
                        isApprovalStudent = true
                    )
                )
                _toastMessage.emit("성공적으로 학생이 승인되었습니다.")
                getApprovalList()
            }.onFailure { e ->
                withContext(Dispatchers.Main) {
                    if (e is HttpException) {
                        Log.e("Student", "HttpException occurred")
                        val errorBody = e.response.body
                        Log.e("Student", "Error body: $errorBody")
                    }
                    _approveStudentUiState.emit(
                        ApproveStudentUiState.Error(
                            exception = e,
                            message = "승인실패",
                        )
                    )
                    _toastMessage.emit("승인실패")
                }
            }
        }
    }

    private fun resetDialogState() {
        viewModelScope.launch {
            _studentDialogUiState.emit(
                CustomAlertDialogState(
                    title = "",
                    description = "",
                    onClickNegative = {
                        resetDialogState()
                    },
                    onClickPositive = {
                        resetDialogState()
                    }
                )
            )
        }
    }

    fun searchStudent(keyword: String) {
        if (keyword != "") {
            // 조회 요령 list[학년][반]
            val newSearchMap: MutableMap<Int, MutableList<MutableList<UserDto>>> = mutableMapOf(
                // 전체
                0 to mutableListOf(
                    mutableListOf(),
                    mutableListOf(),
                    mutableListOf(),
                    mutableListOf()
                ),
                // 1학년
                1 to mutableListOf(
                    mutableListOf(),
                    mutableListOf(),
                    mutableListOf(),
                    mutableListOf()
                ),
                // 2학년
                2 to mutableListOf(
                    mutableListOf(),
                    mutableListOf(),
                    mutableListOf(),
                    mutableListOf()
                ),
                // 3학년
                3 to mutableListOf(
                    mutableListOf(),
                    mutableListOf(),
                    mutableListOf(),
                    mutableListOf()
                ),
            )
            when (_userListUiState.value) {
                is UserListUiState.Success -> {
                    val students = (_userListUiState.value as UserListUiState.Success).studentList
                    when (keyword) {
                        "이름만 입력 했을 때 ex) 엄성민 or 엄 or 성 or 엄성" -> {
                            getStudentsByName(
                                students = students,
                                newSearchMap = newSearchMap,
                                keyword = keyword
                            )
                        }

                        "숫자만 입력 했을 때 ex) 1212" -> getStudentsByNumber(students)
                    }
                }

                is UserListUiState.Error -> {

                }
            }

            _userListUiState.onEach {
                _userListUiState.emit(UserListUiState.Success(newSearchMap))
            }.launchIn(viewModelScope)

        } else {
            getStudentList()
        }
    }

    private fun getStudentsByNumber(
        students: MutableMap<Int, MutableList<MutableList<UserDto>>>,
    ): MutableMap<Int, MutableList<MutableList<UserDto>>> {
        students.forEach { key, value ->
            value.forEach {
                it.forEach {
                    val data = it.user
                    val studentNumber = "%d%d%02d".format(
                        data.grade,
                        data.className,
                        data.classNo
                    )
                }
            }
        }
        return students
    }

    private fun getStudentsByName(
        students: MutableMap<Int, MutableList<MutableList<UserDto>>>,
        newSearchMap: MutableMap<Int, MutableList<MutableList<UserDto>>>,
        keyword: String
    ): MutableMap<Int, MutableList<MutableList<UserDto>>> {
        students.forEach { key, value ->
            value.forEach { user ->
                user.forEach {
                    if (it.user.name?.contains(keyword) == true) {
                        if (it.user.className != 0) {
                            newSearchMap[it.user.grade]?.get(it.user.className - 1)
                                ?.add(it)
                            newSearchMap[0]?.get(it.user.className - 1)
                                ?.add(it)
                        } else {
                            newSearchMap[0]?.get(it.user.className)
                                ?.add(it)
                            newSearchMap[it.user.grade]?.get(it.user.className - 1)
                                ?.add(it)
                        }
                    }
                }
            }
        }
        return newSearchMap
    }
}


sealed class ApprovalListUiState {
    data class Success(val studentList: List<UserInfoDto>) : ApprovalListUiState()
    data class Error(val exception: Throwable, var message: String = "") : ApprovalListUiState()
}

sealed class UserListUiState {
    data class Success(val studentList: MutableMap<Int, MutableList<MutableList<UserDto>>>) :
        UserListUiState()

    data class Error(val exception: Throwable, var message: String = "") : UserListUiState()
}

sealed class StudentUiState {
    data class Success(val studentInfo: UserInfoDto) : StudentUiState()
    data class Error(val exception: Throwable, var message: String = "") : StudentUiState()
}

sealed class StudentImageUrlUiState {
    data class Success(val studentUrl: ImageResponse) : StudentImageUrlUiState()
    data class Error(val exception: Throwable, var message: String = "") : StudentImageUrlUiState()
}

sealed class ApproveStudentUiState {
    data class Success(val message: String = "", val isApprovalStudent: Boolean = false) :
        ApproveStudentUiState()

    data class Error(val exception: Throwable, var message: String = "") : ApproveStudentUiState()
}

data class CustomAlertDialogState(
    val title: String = "",
    val description: String = "",
    val negativeComment: @Composable () -> Unit = {},
    val positiveComment: @Composable () -> Unit = {},
    val onClickNegative: () -> Unit = {},
    val onClickPositive: () -> Unit = {},
)