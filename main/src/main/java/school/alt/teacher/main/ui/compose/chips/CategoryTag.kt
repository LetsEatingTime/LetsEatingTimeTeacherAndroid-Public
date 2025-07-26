package school.alt.teacher.main.ui.compose.chips

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import school.alt.teacher.main.ui.theme.disable
import school.alt.teacher.main.ui.theme.main2
import school.alt.teacher.main.ui.theme.pretendard
import school.alt.teacher.main.ui.theme.white

@Composable
fun CategoryTag(
    modifier: Modifier = Modifier,
    text: String,
    isBorder: MutableState<Boolean> = mutableStateOf(true),
    enabled: Boolean = true,
    onClick: () -> Unit = {},
) {


    val borderColor: BorderStroke = if (isBorder.value) {
        BorderStroke(1.dp, main2)
    } else {
        BorderStroke(1.dp, disable)
    }

    val cardColors = CardDefaults.outlinedCardColors(
        containerColor = if (isBorder.value) {
            main2
        } else {
            white
        },
        contentColor = if (isBorder.value) {
            white
        } else {
            disable
        },
        disabledContainerColor = if (isBorder.value) {
            main2
        } else {
            white
        },
        disabledContentColor = if (isBorder.value) {
            white
        } else {
            disable
        }
    )




    OutlinedCard(modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = cardColors,
        border = borderColor,
        enabled = enabled,
        onClick = {
            isBorder.value = isBorder.value.not()
            onClick()
        }) {
        Text(
            modifier = modifier
                .padding(horizontal = 14.dp, vertical = 6.dp),
            text = text,
            fontSize = 16.sp,
            fontFamily = pretendard,
            fontWeight = FontWeight.Medium
        )

    }


}

@Composable
fun SingleCategoryTag(
    modifier: Modifier = Modifier,
    text: String,
    isBorder: Boolean = true,
    enabled: Boolean = true,
    onClick: () -> Unit = {},
) {


    val borderColor: BorderStroke = if (isBorder) {
        BorderStroke(1.dp, main2)
    } else {
        BorderStroke(1.dp, disable)
    }

    val cardColors = CardDefaults.outlinedCardColors(
        containerColor = if (isBorder) {
            main2
        } else {
            white
        },
        contentColor = if (isBorder) {
            white
        } else {
            disable
        },
        disabledContainerColor = if (isBorder) {
            main2
        } else {
            white
        },
        disabledContentColor = if (isBorder) {
            white
        } else {
            disable
        }
    )




    OutlinedCard(modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = cardColors,
        border = borderColor,
        enabled = enabled,
        onClick = {
            onClick()
        }) {
        Text(
            modifier = modifier
                .padding(horizontal = 14.dp, vertical = 6.dp),
            text = text,
            fontSize = 16.sp,
            fontFamily = pretendard,
            fontWeight = FontWeight.Medium
        )

    }


}


@Composable
@Preview(showBackground = true)
fun CategoryTest() {
    val test = remember { mutableStateOf(false) }
    CategoryTag(text = "a", isBorder = test, enabled = false)
}