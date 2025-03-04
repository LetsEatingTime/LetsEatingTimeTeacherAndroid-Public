package school.alt.teacher.main.ui.compose.student

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import school.alt.teacher.main.ui.foundation.IcArrowUp
import school.alt.teacher.main.ui.theme.black
import school.alt.teacher.main.ui.theme.line1
import school.alt.teacher.main.ui.theme.main2
import school.alt.teacher.main.ui.theme.pretendard
import school.alt.teacher.main.ui.theme.white
import school.alt.teacher.network.dto.UserDto
import school.alt.teacher.network.dto.UserInfoDto

@Composable
fun StudentList(
    title: String,
    studentData: MutableList<UserDto>,
    contentPaddingValues: PaddingValues = PaddingValues(horizontal = 18.dp, vertical = 20.dp),
    isOpen: MutableState<Boolean>,
    onClick: (UserInfoDto) -> Unit = {},
    backgroundColor: Color = white
) {


    Column {
        Row(
            modifier = Modifier
                .background(color = backgroundColor)
                .clickable { isOpen.value = !isOpen.value }
                .fillMaxWidth()
                .padding(contentPaddingValues),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                color = black,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = pretendard
            )

            IcArrowUp(
                modifier = Modifier.rotate(
                    if (isOpen.value) {
                        0f
                    } else {
                        180f
                    }
                ), contentDescription = "", size = 28.dp
            )
        }
        if (isOpen.value) {
            for (i in 0..<studentData.size) {
                val data = studentData[i].user
                Row(
                    modifier = Modifier
                        .padding(top = 3.dp)
                        .background(color = white)
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp, vertical = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
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
                    Text(
                        modifier = Modifier
                            .clickable {
                                onClick(data)
                            }
                            .padding(5.dp),
                        text = "학생 보기",
                        color = main2,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = pretendard
                    )
                }
                Spacer(modifier = Modifier.padding(top = 3.dp))
            }
        }
    }
}

//@Composable
//@Preview()
//fun StudentTest(){
//
//    val class4 = remember {
//    mutableStateOf(false)
//
//    }
//    val mainScrollState = rememberScrollState()
//
//    Box(modifier = Modifier
//        .background(color = backgroundStrong)
//        .fillMaxSize()){
//
//        Column (
//            modifier = Modifier
//                .verticalScroll(mainScrollState)
//                .fillMaxSize()
//        ) {
//    val stuList =mutableListOf<Student>(
//    Student(name = "teser", studentNumber = "asdf"),
//    Student(name = "teser", studentNumber = "asdf"),
//    Student(name = "teser", studentNumber = "asdf"),
//    Student(name = "teser", studentNumber = "asdf"),
//    Student(name = "teser", studentNumber = "asdf"),
//    Student(name = "teser", studentNumber = "asdf"),
//    Student(name = "teser", studentNumber = "asdf"),
//    Student(name = "teser", studentNumber = "asdf"),
//)
//    StudentList("", stuList, isOpen =  class4)
//            StudentList("", stuList, isOpen =  class4)
//            StudentList("", stuList, isOpen =  class4)
//            StudentList("", stuList, isOpen =  class4)
//        }
//    }
//}
//
//data class Student(
//    val name : String,
//    val studentNumber : String
//)