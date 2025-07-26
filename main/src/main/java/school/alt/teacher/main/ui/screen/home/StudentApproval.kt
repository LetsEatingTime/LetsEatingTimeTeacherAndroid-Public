package school.alt.let.teacher.main.ui.screen.home

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import school.alt.teacher.main.navigation.ScreenNavigate
import school.alt.teacher.main.ui.compose.button.TextButton2
import school.alt.teacher.main.ui.compose.bar.Bar
import school.alt.teacher.main.ui.compose.bar.NavBar
import school.alt.teacher.main.ui.foundation.IcBack
import school.alt.teacher.main.ui.theme.black
import school.alt.teacher.main.ui.theme.line1
import school.alt.teacher.main.ui.theme.line3
import school.alt.teacher.main.ui.theme.main3
import school.alt.teacher.main.ui.theme.pretendard
import school.alt.teacher.main.ui.theme.white
import school.alt.teacher.main.viewmodel.home.NetworkResultState
import school.alt.teacher.main.viewmodel.student.ApprovalListUiState
import school.alt.teacher.main.viewmodel.student.StudentViewModel

@Composable
fun StudentApprovalScreen(
    modifier: Modifier = Modifier,
    studentViewModel: StudentViewModel,
    onMoveScreen: (String) -> Unit,
) {
    val approvalList by studentViewModel.approvalList.observeAsState(listOf())
    val toastMessage by studentViewModel.toastMessage.collectAsState()
    val approvalListState by studentViewModel.approvalListState.collectAsStateWithLifecycle(
        NetworkResultState()
    )
    val approvalListUiState by studentViewModel.approvalListUiState.collectAsState()
    val approvalResultUiState by studentViewModel.approveStudentUiState.collectAsState()
    val context = LocalContext.current
    DisposableEffect(Unit) {
        val scope = CoroutineScope(Dispatchers.Default)
        val job = scope.launch {
//            studentViewModel.getApproveList() {
//                    studentList.value = studentViewModel.appreveList.value.toMutableList()
//            }
        }

        onDispose {
            job.cancel()
        }
    }

//    LaunchedEffect(studentViewModel.appreveList.value ) {
//        studentList.value = studentViewModel.appreveList.value
//    }

    LaunchedEffect(toastMessage) {
        if (toastMessage != "") {
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
            studentViewModel.initToastMessage()
        }
    }


    Box(modifier = Modifier.fillMaxSize()) {
        when (approvalListUiState) {
            is ApprovalListUiState.Success -> {
                LazyColumn(
                    modifier = Modifier.padding(top = 54.dp),
                    verticalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    itemsIndexed((approvalListUiState as ApprovalListUiState.Success).studentList) { index, data ->
                        Bar(
                            paddingValues = PaddingValues(horizontal = 18.dp, vertical = 13.dp),
                            startContent = {
                                Row(
                                    modifier = Modifier,
                                    verticalAlignment = Alignment.Bottom,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = data.name ?: "",
                                        color = black,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        fontFamily = pretendard
                                    )
                                    Text(
                                        text = String.format(
                                            "%d%d%02d",
                                            data.grade,
                                            data.className,
                                            data.classNo
                                        ),
                                        color = line1,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        fontFamily = pretendard
                                    )
                                }
                            },
                            endContext = {
                                Row(
                                    modifier = Modifier,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    TextButton2(
                                        text = "승인",
                                        backgroundColor = main3,
                                        paddingValues = PaddingValues(
                                            horizontal = 20.dp,
                                            vertical = 13.dp
                                        ),
                                        textSize = 16.sp
                                    ) {
                                        studentViewModel.approveUser(data.id ?: "")
                                    }
                                    TextButton2(
                                        text = "거부",
                                        backgroundColor = white,
                                        textColor = line3,
                                        border = BorderStroke(color = line3, width = 1.dp),
                                        paddingValues = PaddingValues(
                                            horizontal = 20.dp,
                                            vertical = 13.dp
                                        ),
                                        textSize = 16.sp
                                    ) {
                                        studentViewModel.unApproveUser(data.id ?: "")
                                    }
                                }

                            }
                        ) {

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
                            onMoveScreen(ScreenNavigate.HOME.name)
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

            is ApprovalListUiState.Error -> {
                if ((approvalListUiState as ApprovalListUiState.Error).message.isNotBlank()) {
                    Toast.makeText(
                        context,
                        (approvalListUiState as ApprovalListUiState.Error).message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

}

@Composable
@Preview(showBackground = true)
fun TestStudentApprovalScreen() {
//    StudentApprovalScreen(modifier = Modifier.padding(horizontal = 18.dp), studentViewModel = StudentViewModel()){}
}