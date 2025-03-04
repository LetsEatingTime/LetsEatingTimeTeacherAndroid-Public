package school.alt.teacher.main.ui.compose.textfild

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import school.alt.teacher.main.ui.theme.backgroundNormal
import school.alt.teacher.main.ui.theme.black
import school.alt.teacher.main.ui.theme.line1
import school.alt.teacher.main.ui.theme.main2
import school.alt.teacher.main.ui.theme.pretendard
import school.alt.teacher.main.ui.theme.textFileBackground
import school.alt.teacher.main.ui.theme.warning

@Composable
fun TextBorder(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(horizontal = 17.dp, vertical = 13.dp),
    titleText : String = "",
    text: MutableState<String>,
    keyboardType: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    label : String = "",
    singleLine : Boolean = false,
    color: TextFieldColors = TextFieldDefaults.colors(
        unfocusedContainerColor = textFileBackground,
        unfocusedIndicatorColor = textFileBackground,
        unfocusedLabelColor = line1,
        focusedContainerColor = textFileBackground,
        focusedIndicatorColor = textFileBackground,
        focusedLabelColor = main2,
        errorIndicatorColor = warning,
        errorLabelColor = warning,
        disabledLabelColor = textFileBackground,
        cursorColor = main2
    )
) {
    Column(modifier = modifier
        .background(backgroundNormal)
        .fillMaxWidth()
        .padding(paddingValues = paddingValues)){
        Text(
            text = titleText,
            color = black,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = pretendard
        )
        Spacer(modifier = Modifier.padding(top = 10.dp))
        Box(modifier = Modifier.padding(horizontal = 9.dp)){
            GrayTextField(text = text, label = label, keyboardType = keyboardType , singleLine = singleLine, color = color)
        }
    }
}