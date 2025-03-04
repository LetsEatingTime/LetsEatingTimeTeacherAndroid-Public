package school.alt.teacher.main.ui.compose.textfild

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import school.alt.teacher.main.ui.theme.line1
import school.alt.teacher.main.ui.theme.main2
import school.alt.teacher.main.ui.theme.pretendard
import school.alt.teacher.main.ui.theme.textFileBackground
import school.alt.teacher.main.ui.theme.warning

@Composable
fun GrayTextField(
    modifier: Modifier = Modifier,
    text: MutableState<String>,
    keyboardType: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
    label: String = "",
    singleLine: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    textSize: Int = 16,
    isError: Boolean = false,
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
    ),
) {
    val isFocused = remember { mutableStateOf(false) }
    OutlinedTextFieldBackground(textFileBackground) {
        OutlinedTextField(
            modifier = modifier
                .onFocusChanged {
                    isFocused.value = it.isFocused
                }
                .fillMaxWidth(),
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardType,
            value = text.value,
            label = {
                if (isFocused.value || text.value.isNotEmpty()) {
                    Text("")
                } else {
                    Text(
                        modifier = Modifier,
                        text = label,
                        fontSize = textSize.sp,
                        fontFamily = pretendard,
                        fontWeight = FontWeight.Normal
                    )
                }
            },
            supportingText = {
                if (isError) {
                    Text(
                        modifier = Modifier,
                        text = label,
                        fontSize = 12.sp,
                        fontFamily = pretendard,
                        fontWeight = FontWeight.Medium,
                        color = warning
                    )
                }
            },
            colors = color,
            singleLine = singleLine,
            onValueChange = { inputText ->
                text.value = inputText
            }
        )
    }
}

@Composable
fun OutlinedTextFieldBackground(
    color: Color,
    content: @Composable () -> Unit,
) {
    // This box just wraps the background and the OutlinedTextField
    Box {
        // This box works as background
        Box(
            modifier = Modifier
                .matchParentSize()
                .padding(top = 8.dp) // adding some space to the label
                .background(
                    color,
                    // rounded corner to match with the OutlinedTextField
                    shape = RoundedCornerShape(10.dp)
                )
        )
        // OutlineTextField will be the content...
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun Test() {
    val test = remember { mutableStateOf("") }
    Column (modifier = Modifier.fillMaxSize()){
        GrayTextField(text = test, label = "test")
    }

}

