package school.alt.teacher.main.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import school.alt.teacher.main.navigation.ScreenNavigate
import school.alt.teacher.main.ui.compose.menu.MenuDayBar
import school.alt.teacher.main.ui.compose.navBar.NavBar
import school.alt.teacher.main.ui.foundation.IcBack
import school.alt.teacher.main.ui.theme.backgroundStrong
import school.alt.teacher.main.ui.theme.line3
import school.alt.teacher.main.viewmodel.menu.MealListUiState
import school.alt.teacher.main.viewmodel.menu.MenuViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun ShowMenuScreen(
    modifier: Modifier,
    menuViewModel: MenuViewModel,
    onMoveScreen: (String) -> Unit
) {

//    val meals = remember {
//        mutableStateOf(
//            listOf(
//                listOf<String>("급식이 없습니다.", "급식이 없습니다.", "급식이 없습니다."),
//                listOf<String>("급식이 없습니다.", "급식이 없습니다.", "급식이 없습니다."),
//                listOf<String>("급식이 없습니다.", "급식이 없습니다.", "급식이 없습니다.")
//            )
//        )
//    }

    val mealListUiState by menuViewModel.mealListUiState.collectAsState()
    val mealScrollState = rememberScrollState()

    DisposableEffect(Unit) {
        val scope = CoroutineScope(Dispatchers.Default)
        val job = scope.launch {
//            menuViewModel.getMeals() {
//                meals.value = menuViewModel.meals.value
//            }
        }

        onDispose {
            job.cancel()
        }
    }

//    LaunchedEffect(menuViewModel.meals.value) {
//        meals.value = menuViewModel.meals.value
//    }

    val menu1 = ""
    val menu2 = ""
    val menu3 = ""

    Box(
        modifier = Modifier
            .background(color = backgroundStrong)
            .fillMaxSize()
    ) {
        NavBar(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            padding = PaddingValues(3.dp),
            startContent = {
                IconButton(modifier = it, onClick = {
                    onMoveScreen(ScreenNavigate.HOME.name)
                }) {
                    IcBack(
                        contentDescription = "",
                        tint = line3
                    )
                }
            },
            isClickable = false,
            isUnderLine = true
        )
        Column(
            modifier
                .padding(top = 54.dp)
                .padding(top = 16.dp)
                .fillMaxWidth()
                .verticalScroll(mealScrollState)
        ) {

            when (mealListUiState) {
                is MealListUiState.Success -> {
                    val mealList = (mealListUiState as MealListUiState.Success).meals
                    val now = LocalDateTime.now()
                    for (i in mealList.indices) {
                        val isSpread = remember {
                            mutableStateOf(i == 0)
                        }
                        val day = now.plusDays(i.toLong())
                            .format(
                                DateTimeFormatter.ofPattern("MMdd")
                            )
                        MenuDayBar(
                            modifier = Modifier,
                            isSpread = isSpread,
                            breakfastMenu = mealList[i][0],
                            dinnerMenu = mealList[i][1],
                            lunchMenu = mealList[i][2],
                            month = day.substring(0, 2).toInt(),
                            date = day.substring(2, 4).toInt(),
                        )
                        Spacer(modifier = Modifier.padding(top = 3.dp))
                    }
                }

                is MealListUiState.Error -> {

                }
            }
        }
    }
}

//@Composable
//@Preview(showBackground = true)
//fun TestShowMenu(){
//    ShowMenuScreen(modifier = Modifier.padding(horizontal = 1