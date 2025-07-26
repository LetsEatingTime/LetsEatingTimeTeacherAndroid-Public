package school.alt.teacher.main.ui.compose.menu

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import school.alt.teacher.main.ui.compose.shimmer.ShimmerMenu1
import school.alt.teacher.main.viewmodel.home.MealUiState

@Composable
fun CustomMenu1ViewPager(
    pagerState: PagerState,
    isShowToggle: Boolean = false,
    menuList: List<List<String>>
) {
    HorizontalPager(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        state = pagerState
    ) {
        val titleText = when (pagerState.currentPage) {
            0 -> "전체"
            1 -> "1학년"
            2 -> "2학년"
            3 -> "3학년"
            else -> "???"

        }
        MenuBar1(
            breakfastMenu = menuList[pagerState.currentPage][0],
            lunchMenu = menuList[pagerState.currentPage][1],
            dinnerMenu = menuList[pagerState.currentPage][2],
            isShowToggle = isShowToggle,
            toggleSelect = pagerState.currentPage,
            toggleLength = pagerState.pageCount,
            titleText = titleText
        )
    }
}

@Composable
fun CustomMenu2ViewPager(
    pagerState: PagerState,
    menuState: MealUiState,
) {
    HorizontalPager(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        state = pagerState
    ) {

        val shimmerColors = listOf(
            Color.LightGray.copy(alpha = 0.6f),
            Color.LightGray.copy(alpha = 0.2f),
            Color.LightGray.copy(alpha = 0.6f),
        )

        val transition = rememberInfiniteTransition(label = "")
        val translateAnim = transition.animateFloat(
            initialValue = 0f,
            targetValue = 1000f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 1000,
                    easing = FastOutSlowInEasing
                ),
                repeatMode = RepeatMode.Reverse
            ), label = ""
        )

        val brush = Brush.linearGradient(
            colors = shimmerColors,
            start = Offset.Zero,
            end = Offset(x = translateAnim.value, y = translateAnim.value)
        )
        when (menuState) {
            is MealUiState.Success -> {
                MenuBar2(
                    time = pagerState.currentPage,
                    menuText = (menuState as MealUiState.Success).meals[pagerState.currentPage]
                )
            }

            is MealUiState.Error -> {
                ShimmerMenu1(brush)

            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun MenuViewPagerTest() {
    val menuPagerState = rememberPagerState {
        3 // 총 페이지 수 설정
    }
    val pagerState = rememberPagerState {
        10 // 총 페이지 수 설정
    }


    Column(modifier = Modifier.fillMaxSize()) {
        CustomMenu1ViewPager(
            pagerState, menuList = listOf(
                listOf("", "", ""),
                listOf("", "", ""),
                listOf("", "", ""),
                listOf("", "", "")
            )
        )
    }
}