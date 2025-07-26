package school.alt.let.teacher.main.ui.screen.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import school.alt.teacher.main.navigation.ScreenNavigate
import school.alt.teacher.main.ui.compose.dialog.LetAlertDialog
import school.alt.teacher.main.ui.compose.bar.Bar
import school.alt.teacher.main.ui.compose.chips.CategoryTag
import school.alt.teacher.main.ui.compose.menu.MenuBar1
import school.alt.teacher.main.ui.compose.bar.NavBar
import school.alt.teacher.main.ui.foundation.IcBack
import school.alt.teacher.main.ui.foundation.IcEnter
import school.alt.teacher.main.ui.foundation.ImageMedal
import school.alt.teacher.main.ui.theme.backgroundNormal
import school.alt.teacher.main.ui.theme.backgroundStrong
import school.alt.teacher.main.ui.theme.black
import school.alt.teacher.main.ui.theme.line1
import school.alt.teacher.main.ui.theme.line3
import school.alt.teacher.main.ui.theme.main1
import school.alt.teacher.main.ui.theme.pretendard
import school.alt.teacher.main.ui.theme.warning
import school.alt.teacher.main.ui.theme.white
import school.alt.teacher.main.viewmodel.student.StudentImageUrlUiState
import school.alt.teacher.main.viewmodel.student.StudentViewModel

@Composable
fun StudentProfileScreen(
    modifier: Modifier = Modifier,
    studentViewModel: StudentViewModel,
    onMoveScreen: (String?) -> Unit
) {
    val context = LocalContext.current
    val profileImageUrl = remember { mutableStateOf("") }
    val name = remember { mutableStateOf(studentViewModel.student.value!!.name) }
    val studentImageUiState by studentViewModel.studentImageUiState.collectAsState()
    val studentDialogUiState by studentViewModel.studentDialogUiState.collectAsState()
    val words = listOf("난류", "우유", "땅콩", "밀", "메밀", "복숭아", "닭고기")
    val mainScrollState = rememberScrollState()

    LaunchedEffect(studentViewModel.student.value) {
        name.value = studentViewModel.student.value!!.name
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .background(color = backgroundStrong)
                .verticalScroll(mainScrollState)
                .fillMaxSize()
                .padding(bottom = 100.dp),
            verticalArrangement = Arrangement.Top

        ) {
            Column(
                Modifier
                    .background(color = backgroundNormal)
                    .fillMaxWidth()
                    .padding(top = 80.dp, bottom = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(width = 150.dp, height = 200.dp)
                        .background(color = line1),
                    model = when (studentImageUiState) {

                        is StudentImageUrlUiState.Success -> {
                            (studentImageUiState as StudentImageUrlUiState.Success).studentUrl.fileName
                        }

                        is StudentImageUrlUiState.Error -> {
                            ""
                        }
                    }, contentDescription = ""
                )
                Spacer(modifier = Modifier.padding(top = 16.dp))
                Text(
                    text = name.value ?: "",
                    color = black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = pretendard
                )
                Spacer(modifier = Modifier.padding(top = 4.dp))
                Text(
                    text = "${studentViewModel.student.value?.grade ?: -1}학년 ${studentViewModel.student.value?.classNo ?: -1}반 ${studentViewModel.student.value?.classNo ?: -1}번", //String.format("%d%d%02d", data.grade, data.className, data.classNo)
                    color = line1,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = pretendard
                )
            }
            Spacer(modifier = Modifier.padding(top = 16.dp))
            Row(
                modifier = Modifier
                    .background(white)
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp, vertical = 13.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "학생 소유 포인트",
                    color = black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = pretendard
                )
                Row() {
                    Text(
                        text = studentViewModel.student.value!!.point.toString(),
                        color = black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = pretendard
                    )
                    Text(
                        text = "P",
                        color = main1,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = pretendard
                    )
                }
            }
            Spacer(modifier = Modifier.padding(top = 16.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = white)
                    .padding(horizontal = 18.dp, vertical = 13.dp)
            ) {
                Text(
                    text = "가장 잔반이 적은 식단",
                    color = black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = pretendard
                )
                Spacer(modifier = Modifier.padding(top = 13.dp))
                Row {
                    ImageMedal(
                        modifier = Modifier.size(28.dp),
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.padding(start = 8.dp))
                    Text(
                        text = "가장 잔반이 적은 식단",
                        color = black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = pretendard
                    )
                }
            }
            Spacer(modifier = Modifier.padding(top = 16.dp))
            Column(
                Modifier
                    .background(white)
                    .fillMaxWidth()
                    .padding(top = 13.dp)
            ) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 2.dp, end = 1.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "월 평균 잔반 Zero 비율",
                        color = black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = pretendard
                    )
                }
                Spacer(modifier = Modifier.padding(top = 13.dp))
                MenuBar1(
                    breakfastMenu = "87%",
                    lunchMenu = "89%",
                    dinnerMenu = "76%"
                )
            }
            Spacer(modifier = Modifier.padding(top = 16.dp))
            if (!studentViewModel.student.value?.allergy.isNullOrEmpty()) {
                Column(
                    Modifier
                        .background(white)
                        .padding(vertical = 13.dp)
                ) {
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(start = 2.dp, end = 1.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "알레르기 음식",
                            color = black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = pretendard
                        )
                    }
                    Spacer(modifier = Modifier.padding(top = 13.dp))

                    ChipVerticalGrid(
                        spacing = 7.dp,
                        modifier = modifier
                    ) {
                        words.forEach { word ->
                            val isSelected = remember {
                                mutableStateOf(false)
                            }
                            CategoryTag(isBorder = isSelected, text = word, enabled = false)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.padding(top = 16.dp))
            Bar(
                startContent = {
                    Text(
                        text = "프로필 편집하기",
                        color = black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = pretendard
                    )
                },
                endContext = { IcEnter(contentDescription = "", size = 28.dp) },
                isClickable = true,
                paddingValues = PaddingValues(vertical = 13.dp, horizontal = 18.dp),
            ) {
                onMoveScreen(ScreenNavigate.STUDENT_PROFILE_MODIFY.name)
            }
            Bar(
                startContent = {
                    Text(
                        text = "회원탈퇴",
                        color = warning,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = pretendard
                    )
                },
                isClickable = true,
                paddingValues = PaddingValues(vertical = 13.dp, horizontal = 18.dp)
            ) {
                studentViewModel.showDeleteStudent(studentViewModel.student.value?.id ?: "") {
                    onMoveScreen(null)
                }
            }
        }
        NavBar(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            padding = PaddingValues(3.dp),
            startContent = {
                IconButton(modifier = it, onClick = {
                    onMoveScreen(ScreenNavigate.STUDENT.name)
                }) {
                    IcBack(
                        contentDescription = "",
                        tint = line3
                    )
                }
            },
            isClickable = false,
            isUnderLine = true
        )
    }
    if (studentDialogUiState.title != "") {
        LetAlertDialog(
            title = studentDialogUiState.title,
            subTitle = "",
            description = studentDialogUiState.description,
            setNegativeText = {
                studentDialogUiState.negativeComment()
            },
            setPositiveText = {
                studentDialogUiState.positiveComment()
            },
            onClickNegative = studentDialogUiState.onClickNegative,
            onClickPositive = studentDialogUiState.onClickPositive
        )
    }
}

//@Composable
//@Preview(showBackground = true)
//fun TestStudentProfileScreen(){
////    StudentProfileScreen(modifier = Modifier.padding(horizontal = 18.dp), studentViewModel = StudentViewModel()){}
//}


@Composable
fun ChipVerticalGrid(
    modifier: Modifier = Modifier,
    spacing: Dp,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = Modifier.padding(horizontal = 18.dp)
    ) { measurables, constraints ->
        var currentRow = 0
        var currentOrigin = IntOffset.Zero
        val spacingValue = spacing.toPx().toInt()
        val placeables = measurables.map { measurable ->
            val placeable = measurable.measure(constraints)

            if (currentOrigin.x > 0f && currentOrigin.x + placeable.width > constraints.maxWidth) {
                currentRow += 1
                currentOrigin =
                    currentOrigin.copy(x = 0, y = currentOrigin.y + placeable.height + spacingValue)
            }

            placeable to currentOrigin.also {
                currentOrigin = it.copy(x = it.x + placeable.width + spacingValue)
            }
        }

        layout(
            width = constraints.maxWidth,
            height = placeables.lastOrNull()?.run { first.height + second.y } ?: 0
        ) {
            placeables.forEach {
                val (placeable, origin) = it
                placeable.place(origin.x, origin.y)
            }
        }
    }
}