package school.alt.teacher.main.ui.compose.shimmer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import school.alt.teacher.main.ui.theme.white


@Composable
fun ShimmerMenu2(brush: Brush) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(white)
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 13.dp, vertical = 13.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Menu2Item(brush)
        Menu2Item(brush)
        Menu2Item(brush)
    }
}

@Composable
fun Menu2Item(brush: Brush) {
    Column(modifier = Modifier.size(width = 82.dp, height = 54.dp)) {
        Row {
            Spacer(
                modifier = Modifier
                    .size(25.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(brush)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Spacer(
                modifier = Modifier
                    .width(53.dp)
                    .height(25.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(brush)
            )
        }
        Spacer(modifier = Modifier.height(7.dp))
        Spacer(
            modifier = Modifier
                .height(25.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(brush)
        )
    }
}