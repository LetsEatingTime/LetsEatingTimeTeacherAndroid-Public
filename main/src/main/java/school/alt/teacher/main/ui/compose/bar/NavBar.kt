package school.alt.teacher.main.ui.compose.bar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import school.alt.teacher.main.ui.theme.line2
import school.alt.teacher.main.ui.theme.white

@Composable
fun NavBar(
    modifier: Modifier = Modifier,
    startContent: @Composable (modifier: Modifier) -> Unit = {},
    centerContext: @Composable (modifier: Modifier) -> Unit = {},
    endContext: @Composable (modifier: Modifier) -> Unit = { },
    isClickable: Boolean = false, // 클릭이 가능한지
    backgroundColor: Color = white, // 배경 색상
    padding: PaddingValues = PaddingValues(0.dp, 0.dp),
    isUnderLine: Boolean = false,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .background(
                color = if (isUnderLine) {
                    line2
                } else {
                    Color.Transparent
                }
            )
            .fillMaxWidth()
            .padding(bottom = 1.dp)
    ) {
        Box(
            modifier = modifier
                .clickable(enabled = isClickable) { onClick() }
                .fillMaxWidth()
                .background(backgroundColor)
                .padding(padding)
        ) {
            startContent(Modifier.align(alignment = Alignment.CenterStart))
            centerContext(Modifier.align(alignment = Alignment.Center))
            endContext(Modifier.align(alignment = Alignment.CenterEnd))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Test() {
    Column(Modifier.fillMaxSize()) {
        NavBar(
            startContent = { Text(modifier = it, text = "ttt") },
            centerContext = { Text(modifier = it, text = "10101") },
            endContext = { Text(modifier = it, text = "10101") },
            isClickable = true
        ) {

        }
    }
}