package school.alt.teacher.main.ui.compose.toggle

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import school.alt.teacher.main.ui.theme.backgroundNormal
import school.alt.teacher.main.ui.theme.backgroundStrong
import school.alt.teacher.main.ui.theme.main3

@Composable
fun ToggleSet(
    setLength: Int = 3,
    selected: Int = 0,
    betweenPadding: Dp = 6.dp,
    item: @Composable (Boolean) -> Unit,
) {
    Row(horizontalArrangement = Arrangement.spacedBy(betweenPadding)) {
        repeat(setLength) { index ->
            item(selected == index)
        }
    }
}

@Composable
fun Toggle(
    modifier: Modifier = Modifier,
    selectTint: Color = main3,
    unSelectTint: Color = backgroundNormal,
    isSelect: Boolean = false,
) {
    var tint = unSelectTint
    if (isSelect) {
        tint = selectTint
    }
    Box(
        modifier = modifier
            .background(color = tint)
    )
}

@Preview(showBackground = true)
@Composable
fun Thread() {
    Column {
        Toggle(
            modifier = Modifier
                .size(5.dp)
                .clip(CircleShape),
            selectTint = main3,
            unSelectTint = backgroundStrong,
            isSelect = true
        )
        ToggleSet { isSelect ->
            Toggle(
                modifier = Modifier
                    .size(5.dp)
                    .clip(CircleShape),
                selectTint = main3,
                unSelectTint = backgroundStrong,
                isSelect = isSelect
            )
        }
    }
}