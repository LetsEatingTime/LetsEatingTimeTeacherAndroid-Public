package school.alt.teacher.main.ui.compose.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import school.alt.teacher.main.ui.theme.pretendard
import school.alt.teacher.main.ui.theme.white

@Composable
fun LetAlertDialog(
    title: String? = null,
    subTitle: String? = null,
    description: String? = null,
    setNegativeText: @Composable () -> Unit,
    setPositiveText: @Composable () -> Unit,
    onClickNegative: () -> Unit,
    onClickPositive: () -> Unit,
    onDismissRequest: () -> Unit = {},
) {
    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        )
    ) {
        Card(
            shape = RoundedCornerShape(20.dp),
        ) {
            Column(
                modifier = Modifier
                    .width(300.dp)
                    .wrapContentHeight()
                    .background(
                        color = Color.White,
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(40.dp))
                if (!title.isNullOrBlank()) {
                    Text(
                        text = title,
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontFamily = pretendard,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                if (!subTitle.isNullOrBlank()) {
                    Text(
                        text = subTitle,
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontFamily = pretendard,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                if (!description.isNullOrBlank()) {
                    Text(
                        text = description,
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            color = Color.LightGray,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal
                        )
                    )
                }
                Spacer(modifier = Modifier.height(40.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(color = Color.LightGray)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min)
                ) {
                    Button(
                        onClick = { onClickNegative() },
                        shape = RectangleShape,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White, // 버튼 배경색상
                            contentColor = Color.Black, // 버튼 텍스트 색상
                            disabledContainerColor = Color.Gray, // 버튼 비활성화 배경 색상
                            disabledContentColor = Color.White, // 버튼 비활성화 텍스트 색상
                        )
                    ) { setNegativeText() }

                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp)
                            .background(color = Color.LightGray)
                    )

                    Button(
                        onClick = { onClickPositive() },
                        shape = RectangleShape,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Red,
                            disabledContainerColor = Color.Gray,
                            disabledContentColor = Color.White,
                        ),
                    ) { setPositiveText() }
                }
            }
        }
    }
}


@Composable
@Preview()
fun TestCustomAlertDialog() {
    val isShowDialog = remember {
        mutableStateOf<Boolean>(false)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = white)
    ) {
        Button(onClick = {
            isShowDialog.value = !isShowDialog.value
        }) {

        }

        if (isShowDialog.value) {
            LetAlertDialog(
                title = null,
                subTitle = "상품을 수정하거나 삭제할 수 있습니다.",
                description = null,
                setNegativeText = {
                    Text(
                        text = "취소",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal
                        )
                    )
                },
                setPositiveText = {
                    Text(
                        text = "삭제",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                onClickNegative = {
                    isShowDialog.value = false
                },
                onClickPositive = {
                    isShowDialog.value = false
                }
            )
        }
    }
}