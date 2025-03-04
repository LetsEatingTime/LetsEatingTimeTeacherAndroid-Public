package school.alt.let.teacher.main.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import school.alt.teacher.main.navigation.ScreenNavigate
import school.alt.teacher.main.ui.compose.button.CustomIcon
import school.alt.teacher.main.ui.compose.item.SingleChipGroup
import school.alt.teacher.main.ui.compose.student.StudentList
import school.alt.teacher.main.ui.compose.textfild.CustomOutlineTextField
import school.alt.teacher.main.ui.foundation.IcSearch
import school.alt.teacher.main.ui.theme.backgroundStrong
import school.alt.teacher.main.ui.theme.line2
import school.alt.teacher.main.ui.theme.white
import school.alt.teacher.main.viewmodel.home.NetworkResultState
import school.alt.teacher.main.viewmodel.student.StudentViewModel
import school.alt.teacher.main.viewmodel.student.UserListUiState
import school.alt.teacher.network.dto.UserDto

@Composable
fun StudentSearchScreen(
    modifier: Modifier,
    studentViewModel: StudentViewModel,
    onMoveScreen: (String) -> Unit
) {

    val userListUiState by studentViewModel.userListUiState.collectAsState()

    val studentListState by studentViewModel.studentListState.collectAsStateWithLifecycle(
        NetworkResultState()
    )

    val studentUiState by studentViewModel.userUiState.collectAsState()

    val selectIndex = remember {
        mutableIntStateOf(0)
    }

    val tagList = remember {
        mutableListOf("전체", "1학년", "2학년", "3학년")
    }

    val searchText = rememberSaveable {
        mutableStateOf("")
    }

    val class1 = rememberSaveable {
        mutableStateOf(false)
    }
    val class2 = rememberSaveable {
        mutableStateOf(false)
    }
    val class3 = rememberSaveable {
        mutableStateOf(false)
    }
    val class4 = rememberSaveable {
        mutableStateOf(false)
    }


//    LaunchedEffect(key1 = studentViewModel.students1.value) {
//        stu1List = mutableStateListOf()
//        stu1List.addAll(studentViewModel.students1.value)
//    }


    val mainScrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .background(color = backgroundStrong)
            .fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .verticalScroll(mainScrollState)
                .fillMaxSize()
                .padding(top = 140.dp)
        ) {

            when (userListUiState) {
                is UserListUiState.Success -> {
                    val studentList = (userListUiState as UserListUiState.Success).studentList
                    if (studentList[selectIndex.intValue]?.get(0)?.size != 0) {
                        StudentList(
                            title = "1반",
                            studentData = studentList[selectIndex.intValue]?.get(0)
                                ?: mutableListOf<UserDto>(),
                            isOpen = class1,
                            onClick = { userData ->
                                studentViewModel.setUserData(userData)
                                if(userData.image != null) {
                                    studentViewModel.getStudentImageUrl(userData.image!!.toInt())
                                }
                                onMoveScreen(ScreenNavigate.STUDENT_PROFILE.name)
                            })
                    }

                    if (studentList[selectIndex.intValue]?.get(1)?.size != 0) {
                        StudentList(title = "2반",
                            studentData = studentList[selectIndex.intValue]?.get(1)
                                ?: mutableListOf<UserDto>(),
                            isOpen = class2,
                            onClick = { userData ->
                                studentViewModel.setUserData(userData)
                                if(userData.image != null) {
                                    studentViewModel.getStudentImageUrl(userData.image!!.toInt())
                                }
                                onMoveScreen(ScreenNavigate.STUDENT_PROFILE.name)
                            })
                    }
                    if (studentList[selectIndex.intValue]?.get(2)?.size != 0) {
                        StudentList(
                            title = "3반",
                            studentData = studentList[selectIndex.intValue]?.get(2)
                                ?: mutableListOf<UserDto>(),
                            isOpen = class3,
                            onClick = { userData ->
                                studentViewModel.setUserData(userData)
                                if(userData.image != null) {
                                    studentViewModel.getStudentImageUrl(userData.image!!.toInt())
                                }
                                onMoveScreen(ScreenNavigate.STUDENT_PROFILE.name)
                            })
                    }
                    if (studentList[selectIndex.intValue]?.get(3)?.size != 0) {
                        StudentList(
                            title = "4반",
                            studentData = studentList[selectIndex.intValue]?.get(3)
                                ?: mutableListOf<UserDto>(),
                            isOpen = class4,
                            onClick = { userData ->
                                studentViewModel.setUserData(userData)
                                if(userData.image != null) {
                                    studentViewModel.getStudentImageUrl(userData.image!!.toInt())
                                }
                                onMoveScreen(ScreenNavigate.STUDENT_PROFILE.name)
                            })
                    }
                }

                is UserListUiState.Error -> {

                }
            }
        }
        Column {
            Row(
                modifier = Modifier
                    .background(color = white)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 18.dp, vertical = 13.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CustomOutlineTextField(
                    modifier = Modifier
                        .defaultMinSize(minHeight = 0.dp, minWidth = 0.dp)
                        .width(285.dp),
                    text = searchText,
                    label = "이름으로 학생을 찾아봐요 (예: 박하은)",
                    singleLine = true
                )
                CustomIcon(
                    modifier = Modifier
                        .clickable {
                            studentViewModel.searchStudent(searchText.value)
                        }
                        .padding(vertical = 8.dp)
                        .size(54.dp),
                    icon = { IcSearch(contentDescription = "", tint = white, size = 28.dp) },
                )
            }
            Column(
                modifier = Modifier
                    .background(line2)
                    .padding(top = 1.dp)
            ) {
                Box(
                    modifier = Modifier
                        .background(color = white)
                        .fillMaxWidth()
                ) {
                    SingleChipGroup(chipList = tagList, selectIndex = selectIndex) {
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestStudentSearchScreen() {
//    StudentSearchScreen(
//        modifier = Modifier.padding(horizontal = 18.dp),
//        studentViewModel = StudentViewModel()
//    ){}
}
