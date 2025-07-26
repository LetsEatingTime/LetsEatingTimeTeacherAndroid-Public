package school.alt.teacher.main.ui.compose.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import school.alt.teacher.main.ui.foundation.IcBreakfast
import school.alt.teacher.main.ui.foundation.IcScan
import school.alt.teacher.main.ui.theme.disable
import school.alt.teacher.main.ui.theme.main2
import school.alt.teacher.main.ui.theme.pretendard
import school.alt.teacher.main.ui.theme.white


@Composable
fun TextButton1(
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit = {},
    text: String,
    textColor: Color = white,
    textSize: TextUnit = 16.sp,
    enabled: Boolean = true,
    paddingValues: PaddingValues = PaddingValues(vertical = 13.dp),
    backgroundColor: Color,
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = backgroundColor,
        contentColor = white,
        disabledContainerColor = disable,
        disabledContentColor = white
    ),
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        colors = buttonColors,
        onClick = { onClick() },
        enabled = enabled,
        contentPadding = paddingValues,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 30.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            icon()
            Spacer(modifier = Modifier.padding(top = 5.dp))
            Text(
                text = text,
                modifier = Modifier,
                color = textColor,
                fontSize = textSize,
                fontFamily = pretendard,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun TextButton2(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = white,
    textSize: TextUnit = 18.sp,
    enabled: Boolean = true,
    backgroundColor: Color,
    border: BorderStroke = BorderStroke(0.dp, Color.Transparent),
    paddingValues: PaddingValues = PaddingValues(vertical = 13.dp),
    cornerRadius: Dp = 10.dp,
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = backgroundColor,
        contentColor = white,
        disabledContainerColor = disable,
        disabledContentColor = textColor
    ),
    onAction: () -> Unit,
) {
    Button(
        modifier = modifier,
        onClick = { onAction() },
        colors = buttonColors,
        enabled = enabled,
        contentPadding = paddingValues,
        shape = RoundedCornerShape(cornerRadius),
        border = border,
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 4.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp
        )
    ) {
        Text(
            modifier = Modifier,
            text = text,
            color = textColor,
            fontSize = textSize,
            fontFamily = pretendard,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun CustomIconButton(
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit = {},
    iconButtonColors: IconButtonColors = IconButtonDefaults.filledTonalIconButtonColors(
        containerColor = main2,
        contentColor = white,
        disabledContainerColor = disable,
        disabledContentColor = white
    ),
    onClick: () -> Unit = {},
) {
    FilledIconButton(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(main2),
        colors = iconButtonColors,
        onClick = { onClick() }) {
        icon()
    }
}

@Preview(showBackground = true)
@Composable
fun TestButton() {
    Column(modifier = Modifier.fillMaxSize()) {
        TextButton1(
            text = "test",
            backgroundColor = white
        ) { }

        TextButton1(
            text = "test2",
            backgroundColor = main2,
            icon = { IcScan(contentDescription = "") }
        ) { }
        TextButton2(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp),
            text = "메뉴 추가",
            backgroundColor = main2,
        ) { }
        Spacer(modifier = Modifier.padding(top = 18.dp))

        TextButton2(
            modifier = Modifier
                .fillMaxWidth(),
            text = "메뉴 추가",
            backgroundColor = main2,
        ) { }
        TextButton2(backgroundColor = main2, text = "aaa", enabled = false) {}

        CustomIconButton(
            icon = { IcBreakfast(contentDescription = "") }
        ) { }
    }
}