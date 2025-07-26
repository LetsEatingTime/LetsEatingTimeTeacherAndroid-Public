package school.alt.teacher.main.viewmodel.profile

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
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
import school.alt.teacher.main.ui.theme.line1
import school.alt.teacher.main.ui.theme.pretendard
import school.alt.teacher.main.ui.theme.warning
import school.alt.teacher.data.TokenManager
import school.alt.teacher.main.viewmodel.home.NetworkResultState
import school.alt.teacher.network.dto.EditUserRequest
import school.alt.teacher.network.dto.UserDto
import school.alt.teacher.network.dto.UserInfoDto
import school.alt.teacher.network.teacher.TeacherRepository
import school.alt.teacher.network.user.UserRepository
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val teacherRepository: TeacherRepository,
    private val tokenManager: TokenManager,
) : ViewModel() {

    private var _profileInfo = MutableLiveData<UserInfoDto>()
    val profileInfo: LiveData<UserInfoDto> = _profileInfo

    private var _profileState = MutableStateFlow(NetworkResultState())
    val profileState: SharedFlow<NetworkResultState> = _profileState

    private var _userImageUiState = MutableStateFlow<UserImageUiState>(UserImageUiState.Success(""))
    val userImageUiState: StateFlow<UserImageUiState> = _userImageUiState

    private var _userDialogUiState =
        MutableStateFlow<CustomAlertDialogState>(CustomAlertDialogState())
    val userDialogUiState: StateFlow<CustomAlertDialogState> = _userDialogUiState

    private var _userUiState = MutableStateFlow<UserUiState>(
        UserUiState.Success(
            UserDto(
                user = UserInfoDto(),
                mealType = emptyList()
            )
        )
    )

    val userUiState: StateFlow<UserUiState> = _userUiState

    fun initValue() {
        getProfile()
    }

    fun logout(onAction: () -> Unit) {
        viewModelScope.launch {
            _userDialogUiState.emit(
                CustomAlertDialogState(
                    title = "로그아웃 하시겠습니까?",
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
                            text = "로그아웃",
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
                        viewModelScope.launch() {
                            tokenManager.deleteData()
                        }
                        onAction()
                    },
                )
            )
        }
    }

    private fun getUserImage(idx: Int) = viewModelScope.launch {
        launch {
            kotlin.runCatching {
                userRepository.getUserImage(idx)
            }.onSuccess { result ->
                result.onEach {
                    _userImageUiState.emit(UserImageUiState.Success(it.fileName))
                }.launchIn(viewModelScope)

            }.onFailure { e ->
                _userImageUiState.emit(UserImageUiState.Error(e, "이미지를 불러올 수 없습니다."))
            }
        }
    }

    private fun getProfile() = viewModelScope.launch {
        kotlin.runCatching {
            userRepository.getProfile()
        }.onSuccess { result ->
            result.onEach {
                _userUiState.emit(UserUiState.Success(userInfo = it.data))
                getUserImage(it.data.user.idx?.toInt() ?: -1)
            }.launchIn(viewModelScope)
        }.onFailure { e ->
            _userUiState.emit(UserUiState.Error(e, "서버에러"))
        }
    }

    fun withdrawDialog(onAction: () -> Unit) = viewModelScope.launch {
        _userDialogUiState.emit(
            CustomAlertDialogState(
                title = "회원탈퇴를 진행하시겠습니까?",
                description = "회원탈퇴를 기준으로 30일 후 계정이 삭제됨니다.",
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
                    viewModelScope.launch(Dispatchers.IO) {
                        tokenManager.deleteData()
                        withdraw {
                            onAction()
                        }
                    }
                },
            )
        )
    }

    private suspend fun withdraw(onAction: () -> Unit) {
        kotlin.runCatching {
            userRepository.withdraw()
        }.onSuccess { result ->
            withContext(Dispatchers.Main) {
                onAction()
            }
        }.onFailure { e ->
        }
    }

    fun modifyUser(userdata: EditUserRequest, onAction: () -> Unit) {
        viewModelScope.launch {
            kotlin.runCatching { teacherRepository.editStudent(userdata) }
                .onFailure { e ->
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        if (e is HttpException) {
                            Log.e("Login", "HttpException occurred")
                            val errorBody = e.response.body
                            Log.e("Login", "Error body: $errorBody")
                        }
                    }
                }
        }
    }

    private fun resetDialogState() {
        viewModelScope.launch {
            _userDialogUiState.emit(
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
}

sealed class UserImageUiState {
    data class Success(val imageUrl: String) : UserImageUiState()
    data class Error(val exception: Throwable, var message: String = "") : UserImageUiState()
}

sealed class UserUiState {
    data class Success(val userInfo: UserDto) : UserUiState()
    data class Error(val exception: Throwable, var message: String = "") : UserUiState()
}

sealed class WithdrawUiState {
    data class Success(val message: String) : WithdrawUiState()
    data class Error(val exception: Throwable, var message: String = "") : WithdrawUiState()
}

data class CustomAlertDialogState(
    val title: String = "",
    val description: String = "",
    val negativeComment: @Composable () -> Unit = {},
    val positiveComment: @Composable () -> Unit = {},
    val onClickNegative: () -> Unit = {},
    val onClickPositive: () -> Unit = {},
)