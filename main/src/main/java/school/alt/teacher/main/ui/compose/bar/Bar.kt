package school.alt.teacher.main.ui.compose.bar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import school.alt.teacher.main.ui.foundation.IcEnter
import school.alt.teacher.main.ui.theme.white

@Composable
fun Bar(
    modifier: Modifier = Modifier,
    startContent: @Composable (Modifier) -> Unit = {},
    endContext: @Composable (Modifier) -> Unit = {},
    isClickable: Boolean = false,
    backgroundColor: Color = white,
    paddingValues: PaddingValues = PaddingValues(horizontal = 18.dp, vertical = 20.dp),
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .clickable(enabled = isClickable) { onClick() }
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(paddingValues), 
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        startContent(Modifier)
        endContext(Modifier)
    }
}

@Preview(showBackground = true)
@Composable
fun BarTest() {
    Column {
        Bar(
            modifier = Modifier,
            startContent = { Text(text = "ttt") },
            endContext = { IcEnter(contentDescription = "") },
            isClickable = true
        ) { }
    }
}