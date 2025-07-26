package school.alt.teacher.main.ui.compose.shimmer

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import school.alt.teacher.main.ui.theme.line1
import school.alt.teacher.main.ui.theme.white


@Composable
fun ShimmerProduction(brush: Brush) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(color = white)
            .wrapContentSize()
            .padding(horizontal = 16.dp, vertical = 13.dp), horizontalAlignment = Alignment.Start
    ) {
        Spacer(
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(brush)
        )
        Spacer(modifier = Modifier.size(4.dp))
        Column(verticalArrangement = Arrangement.Center) {
            Spacer(
                modifier = Modifier
                    .height(12.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .width(60.dp)
                    .background(brush)
            )

            Spacer(modifier = Modifier.height(4.dp)) //creates an empty space between
            Spacer(
                modifier = Modifier
                    .height(12.dp)
                    .width(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth(fraction = 0.7f)
                    .background(brush)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun TestShimmer() {
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
        ),
        label = ""
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(
            x = translateAnim.value,
            y = translateAnim.value
        )
    )

    Column(
        modifier = Modifier
            .background(color = line1)
            .fillMaxSize()
            .padding(horizontal = 27.dp)
    ) {
        ShimmerProduction(brush)
        Spacer(modifier = Modifier.padding(top = 10.dp))
        ShimmerMenu1(brush)
        Spacer(modifier = Modifier.padding(top = 10.dp))
        Menu2Item(brush)
        Spacer(modifier = Modifier.padding(top = 10.dp))
        ShimmerMenu2(brush = brush)
    }
}