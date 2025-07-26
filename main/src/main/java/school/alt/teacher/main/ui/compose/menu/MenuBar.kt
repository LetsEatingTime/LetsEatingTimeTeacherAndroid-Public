package school.alt.teacher.main.ui.compose.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import school.alt.teacher.main.ui.compose.toggle.Toggle
import school.alt.teacher.main.ui.compose.toggle.ToggleSet
import school.alt.teacher.main.ui.foundation.ImgBreakfast
import school.alt.teacher.main.ui.foundation.ImgDinner
import school.alt.teacher.main.ui.foundation.ImgLunch
import school.alt.teacher.main.ui.theme.black
import school.alt.teacher.main.ui.theme.line2
import school.alt.teacher.main.ui.theme.main3
import school.alt.teacher.main.ui.theme.white

//menu들을 하나의 문장으로 전처리 후 menu에 넣어주세요. (전처리 코드는 LET학생앱을 참고 ex) ["카레","딸기 라떼"] -> "카레, 딸기 라떼"
//time은 아이콘을 보여주기 위함입니다. 시간에 맞게 값을 넣어주세요
// | 아침 : 0 | 점심 : 1 | 저녁 | 2 |
@Composable
fun MenuBar2(
    modifier: Modifier = Modifier,
    time: Int = 0,
    menuText: String = "없음",
    paddingValues: PaddingValues = PaddingValues(horizontal = 27.dp)
) {
    Surface(
        modifier = modifier
            .padding(paddingValues)
            .clip(RoundedCornerShape(8.dp))
            .background(white)
            .padding(vertical = 13.dp, horizontal = 16.dp)
            .fillMaxWidth()
            .wrapContentHeight(),

        ) {
        Row(
            modifier = Modifier.background(white),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            when (time) {
                0 -> {
                    VerticalMenuIcon(text = "아침") {
                        ImgBreakfast(contentDescription = "")
                    }
                }

                1 -> {
                    VerticalMenuIcon(text = "점심") {
                        ImgLunch(contentDescription = "")
                    }
                }

                2 -> {
                    VerticalMenuIcon(text = "저녁") {
                        ImgDinner(contentDescription = "")
                    }
                }

                else -> {
                    VerticalMenuIcon(text = "아침") {
                        ImgBreakfast(contentDescription = "")
                    }
                }
            }

            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = menuText,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = black,
                softWrap = true
            )
        }
    }
}

@Composable
fun MenuBar1(
    modifier: Modifier = Modifier,
    lunchMenu: String = "없음",
    dinnerMenu: String = "없음",
    breakfastMenu: String = "없음",
    isShowToggle: Boolean = false,
    titleText: String? = null,
    toggleSelect: Int = 0,
    toggleLength: Int = if (isShowToggle) 3 else 0,
    paddingValues: PaddingValues = PaddingValues(horizontal = 27.dp)
) {
    Surface(
        modifier = modifier
            .padding(paddingValues)
            .clip(RoundedCornerShape(8.dp))
            .background(white)
            .padding(13.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        Column(
            modifier = Modifier.background(white),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (titleText != null) {
                Text(
                    modifier = Modifier
                        .background(white)
                        .align(Alignment.Start)
                        .padding(top = 3.dp, start = 8.dp),
                    text = titleText,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = black,
                )
                Spacer(modifier = Modifier.padding(top = 10.dp))
            }
            Row(
                modifier = Modifier
                    .background(white)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    HorizontalMenuIcon(
                        icon = { ImgBreakfast(contentDescription = "", size = 28.dp) },
                        text = "아침"
                    )
                    Spacer(modifier = Modifier.padding(top = 13.dp))
                    Text(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        text = breakfastMenu,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = black,
                        softWrap = true
                    )
                }
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    HorizontalMenuIcon(
                        icon = { ImgLunch(contentDescription = "", size = 28.dp) },
                        text = "점심"
                    )
                    Spacer(modifier = Modifier.padding(top = 13.dp))
                    Text(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        text = lunchMenu,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = black,
                        softWrap = true
                    )
                }
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    HorizontalMenuIcon(
                        icon = { ImgDinner(contentDescription = "", size = 28.dp) },
                        text = "저녁"
                    )
                    Spacer(modifier = Modifier.padding(top = 13.dp))
                    Text(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        text = dinnerMenu,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = black,
                        softWrap = true
                    )
                }
            }
            Spacer(modifier = Modifier.padding(top = 13.dp))
            if (isShowToggle) {
                ToggleSet(
                    selected = toggleSelect,
                    setLength = toggleLength
                ) { isSelect ->
                    Toggle(
                        modifier = Modifier
                            .size(5.dp)
                            .clip(CircleShape),
                        selectTint = main3,
                        unSelectTint = line2,
                        isSelect = isSelect
                    )
                }
            }

        }
    }
}

@Preview(showBackground = true, backgroundColor = 929292)
@Composable
fun Menu() {
    Column {
        MenuBar2()
        MenuBar1()
        MenuBar1()
    }
}