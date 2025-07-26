package school.alt.teacher.main.viewmodel.menu

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import school.alt.teacher.network.open.OpenRepository
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val openRepository: OpenRepository
) : ViewModel() {

    private val _meals = mutableStateOf(listOf(listOf("", "", ""), listOf("", "", "")))
    val meals: State<List<List<String>>> = _meals

    private var _mealListUiState =
        MutableStateFlow<MealListUiState>(MealListUiState.Success(emptyList()))
    val mealListUiState: StateFlow<MealListUiState> = _mealListUiState

    init {
        getMeals()
    }

    private fun getMeals() = viewModelScope.launch {
        val now = LocalDateTime.now()
        _mealListUiState.onEach {
            kotlin.runCatching {
                val menusList = mutableListOf<List<String>>()
                for (i in 0..7) {
                    runBlocking {
                        val meal = async {
                            val mealList = mutableListOf<String>()
                            runCatching {
                                openRepository.getMeal(
                                    now.plusDays(i.toLong())
                                        .format(
                                            DateTimeFormatter.ofPattern("yyyyMMdd")
                                        )
                                )
                            }.onSuccess { result ->
                                val response = result.first().data
                                mealList.add(
                                    response.breakfast?.menu?.joinToString(", ") ?: "급식이 없습니다."
                                )
                                mealList.add(
                                    response.lunch?.menu?.joinToString(", ") ?: "급식이 없습니다."
                                )
                                mealList.add(
                                    response.dinner?.menu?.joinToString(", ") ?: "급식이 없습니다."
                                )
                            }.onFailure { e ->
                                e.printStackTrace()
                                Log.e("Open", "Error message: ${e.message}")
                                withContext(Dispatchers.Main) {
                                    if (e is HttpException) {
                                        Log.e("Open", "HttpException occurred")
                                        val errorBody = e.response.body
                                        Log.e("Open", "Error body: $errorBody")
                                    }
                                }
                                mealList.add("급식이 없습니다.")
                                mealList.add("급식이 없습니다.")
                                mealList.add("급식이 없습니다.")
                            }
                            mealList
                        }
                        menusList.add(meal.await())
                    }
                }
                menusList
            }.onSuccess {
                _mealListUiState.emit(MealListUiState.Success(it))
            }.onFailure { e ->
                _mealListUiState.emit(MealListUiState.Error(e))
            }
        }.launchIn(viewModelScope)
    }
}

sealed class MealListUiState {
    data class Success(val meals: List<List<String>>) : MealListUiState()
    data class Error(val exception: Throwable) : MealListUiState()
}
