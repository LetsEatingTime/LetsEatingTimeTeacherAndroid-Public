package school.alt.teacher.main.ui.screen.home

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import school.alt.teacher.main.navigation.ScreenNavigate
import school.alt.teacher.main.ui.theme.black
import school.alt.teacher.main.ui.theme.line3
import school.alt.teacher.main.ui.theme.pretendard
import school.alt.teacher.main.ui.theme.white
import school.alt.teacher.main.viewmodel.scan.ScanViewModel

@Composable
fun MenuResultScreen(
    modifier: Modifier,
    scanViewModel: ScanViewModel,
    onMoveScreen: (String) -> Unit
) {
    val imageData = remember {
        mutableStateOf(scanViewModel.imageUri.value)
    }
    val configuration = LocalConfiguration.current
    val context = LocalContext.current
    val activity = context as Activity
    if (configuration.orientation != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    Box(
        modifier = Modifier
            .background(black)
            .fillMaxSize(),

        ) {
        AsyncImage(
            modifier = Modifier
                .padding(bottom = 75.dp)
                .background(line3)
                .align(Alignment.Center)
                .fillMaxWidth()
                .height(360.dp),
            model = imageData.value,
            contentDescription = "",
            contentScale = ContentScale.FillBounds
        )
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 35.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                modifier = Modifier
                    .clickable {
                        onMoveScreen(ScreenNavigate.MEAL_SCAN.name)
                    }
                    .padding(15.dp),
                text = "다시찍기",
                color = white,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = pretendard
            )
            Text(
                modifier = Modifier
                    .clickable {
                        onMoveScreen(ScreenNavigate.HOME.name)
                    }
                    .padding(15.dp),
                text = "사용하기",
                color = white,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = pretendard
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestMenuResultScreen() {
    MenuResultScreen(
        Modifier,
        ScanViewModel()
    ) {
    }
}