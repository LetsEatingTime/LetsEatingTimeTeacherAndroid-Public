package school.alt.teacher.main.ui.screen.home

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import school.alt.teacher.main.navigation.ScreenNavigate
import school.alt.teacher.main.ui.compose.button.TextButton2
import school.alt.teacher.main.ui.compose.bar.NavBar
import school.alt.teacher.main.ui.foundation.IcBack
import school.alt.teacher.main.ui.foundation.ImageCharacter
import school.alt.teacher.main.ui.theme.backgroundNormal
import school.alt.teacher.main.ui.theme.black
import school.alt.teacher.main.ui.theme.line3
import school.alt.teacher.main.ui.theme.main1
import school.alt.teacher.main.ui.theme.pretendard

@Composable
fun MenuAddTitleScreen(modifier: Modifier, onMoveScreen: (String) -> Unit) {
    val configuration = LocalConfiguration.current
    val context = LocalContext.current
    val activity = context as Activity
    if (configuration.orientation != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
    Box(
        modifier = Modifier
            .background(color = backgroundNormal)
            .fillMaxSize()
    ) {

        Column(
            Modifier
                .fillMaxSize()
                .padding(bottom = 200.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ImageCharacter(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 50.dp),
                contentScale = ContentScale.FillWidth
            )
            Text(
                text = "메뉴를 추가하기 위해\n식판을 찍어 주세요.",
                color = black,
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = pretendard
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 50.dp)
                .align(Alignment.BottomStart)
        ) {
            TextButton2(
                modifier = Modifier
                    .padding(horizontal = 18.dp)
                    .fillMaxWidth(),
                text = "잔반 스캔하기",
                backgroundColor = main1
            ) {
                onMoveScreen(ScreenNavigate.MEAL_SCAN.name)
            }
        }

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
    }

}

@Composable
@Preview(showBackground = true)
fun TestMenuAddTitleScreen() {
    MenuAddTitleScreen(
        modifier = Modifier.padding(horizontal = 18.dp),
    ) {}
}
