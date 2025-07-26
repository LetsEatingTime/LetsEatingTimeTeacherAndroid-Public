package school.alt.teacher.main.ui.compose.menu

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Surface
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import school.alt.teacher.main.ui.theme.backgroundStrong
import school.alt.teacher.main.ui.theme.black
import school.alt.teacher.main.ui.theme.pretendard
import school.alt.teacher.main.ui.theme.warning
import school.alt.teacher.main.ui.theme.white

@Composable
fun MenuDayBar(
    modifier: Modifier = Modifier,
    month: Int = 12,
    date: Int = 12,
    breakfastMenu: String = "없음",
    lunchMenu: String = "없음",
    dinnerMenu: String = "없음",
    isSpread: MutableState<Boolean>,
) {
    Column(
        modifier = modifier
            .animateContentSize()
            .fillMaxWidth()
    ) {
        Surface(
            modifier = Modifier
                .clickable (
                    role = Role.Button,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple()
                ){
                    isSpread.value = !isSpread.value
                }
                .background(
                    color = white,
                    shape = RoundedCornerShape(8.dp)
                )
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 13.dp)
        ) {
            Row(
                modifier = modifier
                    .background(
                        color = white,
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${month}월 ${date}일",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = pretendard
                )
            }
        }
        if (isSpread.value) {
            for (i in 0..2) {
                val menu = when (i) {
                    0 -> breakfastMenu
                    1 -> lunchMenu
                    2 -> dinnerMenu
                    else -> breakfastMenu
                }
                Spacer(modifier = Modifier.padding(top = 3.dp))
                MenuBar2(
                    modifier = Modifier.background(white, shape = RoundedCornerShape(8.dp)),
                    menuText = menu,
                    time = i
                )
            }
        }
    }
}


@Composable
@Preview()
fun TestMenuDayBar() {
    val ias = remember { mutableStateOf(false) }
    Column (modifier = Modifier.fillMaxSize().background(warning)){
        MenuDayBar(
            isSpread = ias
        )
    }
}