package school.alt.teacher.main.ui.compose.textfield

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import school.alt.teacher.main.ui.theme.line1
import school.alt.teacher.main.ui.theme.main2
import school.alt.teacher.main.ui.theme.pretendard
import school.alt.teacher.main.ui.theme.warning
import school.alt.teacher.main.ui.theme.white

@Composable
fun CustomOutlineTextField(
    modifier: Modifier = Modifier,
    text: String,
    keyboardType: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    label: String = "",
    singleLine: Boolean = false,
    color: TextFieldColors = TextFieldDefaults.colors(
        unfocusedContainerColor = white,
        unfocusedIndicatorColor = line1,
        unfocusedLabelColor = line1,
        focusedContainerColor = white,
        focusedIndicatorColor = main2,
        focusedLabelColor = main2,
        errorIndicatorColor = warning,
        errorLabelColor = warning
    ),
    onValueChange: (String) -> Unit,
) {
    val isFocused = remember { mutableStateOf(false) }
    OutlinedTextField(
        modifier = modifier
            .onFocusChanged { isFocused.value = it.isFocused }
            .fillMaxWidth(),
        keyboardOptions = keyboardType,
        value = text,
        label = {
            if (isFocused.value || text.isNotEmpty()) {
                Text("")
            } else {
                Text(
                    modifier = Modifier,
                    text = label,
                    fontSize = 16.sp,
                    fontFamily = pretendard,
                    fontWeight = FontWeight.Normal
                )
            }
        },
        singleLine = singleLine,
        colors = color,
        onValueChange = { onValueChange(it) },
        shape = RoundedCornerShape(8.dp)
    )
}