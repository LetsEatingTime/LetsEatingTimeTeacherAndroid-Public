package school.alt.teacher.main.viewmodel.home

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import school.alt.teacher.main.ui.theme.line1
import school.alt.teacher.main.ui.theme.pretendard
import school.alt.teacher.main.ui.theme.warning
import school.alt.teacher.main.viewmodel.profile.CustomAlertDialogState
import school.alt.teacher.network.open.OpenRepository
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val openRepository: OpenRepository,
) : ViewModel() {
    private val date: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
    private val _favoriteFood = mutableStateOf(listOf<String>())
    private val _dislikedFood = mutableStateOf(listOf<String>())
    private val _zeroRateAvg = mutableStateOf(listOf(listOf<String>()))
    private val _mealMenu = mutableStateOf(listOf("정보가 없습니다", "정보가 없습니다", "정보가 없습니다"))

    val favoriteFood: State<List<String>> = _favoriteFood
    val dislikedFood: State<List<String>> = _dislikedFood
    val zeroRateAvg: State<List<List<String>>> = _zeroRateAvg
    val mealMenu: State<List<String>> = _mealMenu

    private var _mealUiState = MutableStateFlow<MealUiState>(MealUiState.Success(emptyList()))
    val mealUiState: StateFlow<MealUiState> = _mealUiState

    private var _leftoversDataUiState = MutableStateFlow<LeftoversDataState>(
        LeftoversDataState.Success(
            foods = emptyList()
        )
    )
    val leftoversDataUiState: StateFlow<LeftoversDataState> = _leftoversDataUiState

    private var _mealState = MutableStateFlow(NetworkResultState())
    val mealState: SharedFlow<NetworkResultState> = _mealState

    private var _userDialogUiState =
        MutableStateFlow<CustomAlertDialogState>(CustomAlertDialogState())
    val userDialogUiState: StateFlow<CustomAlertDialogState> = _userDialogUiState

    private var _meal = MutableLiveData<List<String>>()
    val meal: LiveData<List<String>> = _meal


    init {
        test()
//        viewModelScope.launch {
//            openRepository.getMeal(date).collect { result ->
//                val response = result.data
//                val menu = listOf(
//                    response.breakfast?.menu?.joinToString(", ") ?: "급식이 없습니다.",
//                    response.lunch?.menu?.joinToString(", ") ?: "급식이 없습니다.",
//                    response.dinner?.menu?.joinToString(", ") ?: "급식이 없습니다."
//                )
//                _mealUiState.value = MealUiState.Success(menu)
//            }
//        }
    }

    fun test() = viewModelScope.launch {
        kotlin.runCatching {
            openRepository.getMeal(date)
        }.onSuccess { result ->
            result.onEach {
                val menu = listOf(
                    it.data.breakfast?.menu?.joinToString(", ") ?: "급식이 없습니다.",
                    it.data.lunch?.menu?.joinToString(", ") ?: "급식이 없습니다.",
                    it.data.dinner?.menu?.joinToString(", ") ?: "급식이 없습니다."
                )
                _mealUiState.emit(MealUiState.Success(menu))
            }.launchIn(viewModelScope)
        }.onFailure { e ->
            _mealUiState.emit(MealUiState.Error(e))
        }
    }

    fun showProductDialog(onModify: () -> Unit) = viewModelScope.launch {
        _userDialogUiState.emit(
            CustomAlertDialogState(
                title = "상품을 수정하거나 삭제할 수 있습니다.",
                description = "",
                negativeComment = {
                    Text(
                        text = "삭제",
                        color = warning,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = pretendard
                    )
                },
                positiveComment = {
                    Text(
                        text = "수정",
                        color = line1,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = pretendard
                    )
                },
                onClickNegative = {
                    resetDialogState()
                },
                onClickPositive = {
                    onModify()
                    resetDialogState()
                },
            )
        )
    }

    fun initData() {
        viewModelScope.launch(Dispatchers.IO) {
//            openRepository.getMeal(date).collect() { result ->
//                    val response = result.data
//                    val menu = listOf(
//                        response.breakfast?.menu?.joinToString(", ") ?: "급식이 없습니다.",
//                        response.lunch?.menu?.joinToString(", ") ?: "급식이 없습니다.",
//                        response.dinner?.menu?.joinToString(", ") ?: "급식이 없습니다."
//                    )
//                    _mealUiState.value = MealUiState.Success(menu)
//            }
//                    .onFailure { e ->
//                e.printStackTrace()
//                Log.e("Open", "Error message: ${e.message}")
//                withContext(Dispatchers.Main) {
//                    if (e is HttpException) {
//                        Log.e("Open", "HttpException occurred")
//                        val errorBody = e.response.body
//                        Log.e("Open", "Error body: $errorBody")
//                    }
//                }
//                _mealMenu.value = listOf("정보가 없습니다", "정보가 없습니다", "정보가 없습니다")
//                result(false)
//            }
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

     fun getLeftoversData() = viewModelScope.launch(Dispatchers.IO){
        kotlin.runCatching {
            openRepository.getLeftoversData()
        }.onSuccess { result ->
            result.onEach {
                _leftoversDataUiState.emit(
                    LeftoversDataState.Success(
                        foods = it.data
                    )
                )
            }.launchIn(viewModelScope)
        }.onFailure { e ->
            e.printStackTrace()
            _leftoversDataUiState.emit(LeftoversDataState.Error(e))
        }
    }
}

sealed class MealUiState {
    data class Success(val meals: List<String>) : MealUiState()
    data class Error(val exception: Throwable) : MealUiState()
}

data class NetworkResultState(
    val error: String = "",
    val isSuccess: Boolean = false
)

sealed class LeftoversDataState {
    data class Success(val foods: List<String?>) : LeftoversDataState()
    data class Error(val exception: Throwable) : LeftoversDataState()
}