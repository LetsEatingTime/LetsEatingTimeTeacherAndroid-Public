package school.alt.teacher.main.ui.compose.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import school.alt.teacher.main.ui.theme.main2
import school.alt.teacher.main.ui.theme.pretendard
import school.alt.teacher.main.ui.theme.white

@Composable
fun VerticalMenuIcon(
    modifier: Modifier = Modifier,
    text: String = "없음",
    icon: @Composable () -> Unit,
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        icon()
        Spacer(modifier = Modifier.padding(top = 3.dp))
        Surface(
            Modifier
                .clip(RoundedCornerShape(30.dp))
                .background(main2)
                .padding(vertical = 3.dp, horizontal = 16.dp)
        ) {
            Text(
                modifier = Modifier.background(main2),
                text = text,
                color = white,
                fontSize = 12.sp,
                fontFamily = pretendard,
                fontWeight = FontWeight.SemiBold
            )
        }

    }
}


@Composable
fun HorizontalMenuIcon(
    modifier: Modifier = Modifier,
    text: String = "없음",
    icon: @Composable () -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon()
        Spacer(modifier = Modifier.padding(start = 3.dp))
        Surface(
            Modifier
                .clip(RoundedCornerShape(30.dp))
                .background(main2)
                .padding(vertical = 3.dp, horizontal = 16.dp)
        ) {
            Text(
                modifier = Modifier.background(main2),
                text = text,
                color = white,
                fontSize = 12.sp,
                fontFamily = pretendard,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

