package school.alt.teacher.main.ui.compose.progressbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import school.alt.teacher.main.ui.theme.line3
import school.alt.teacher.main.ui.theme.white

@Composable
fun CustomProgressBar(progress: Float) {
    // 기본 배경 바의 색상 (진한 색)
    val backgroundColor = line3
    // 진행 중인 바의 색상 (흰색)
    val progressColor = white

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(30.dp))
            .background(color = backgroundColor)
            .padding(2.dp)
    ) {
        LinearProgressIndicator(
            progress = {
                progress // 진행 상태, 0.0 ~ 1.0 사이의 값
            },
            modifier = Modifier
                .fillMaxWidth() // 너비를 맞춤
                .clip(RoundedCornerShape(30.dp))
                .background(backgroundColor)
                .height(4.dp), // 높이 맞춤
            color = progressColor, // 진행 중인 바의 색상
            trackColor = backgroundColor, // 기본 배경 바의 색상
        )
    }
}