package school.alt.teacher.main.ui.compose.student

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import school.alt.teacher.main.ui.foundation.IcArrowUp
import school.alt.teacher.main.ui.theme.backgroundStrong
import school.alt.teacher.main.ui.theme.black
import school.alt.teacher.main.ui.theme.line1
import school.alt.teacher.main.ui.theme.main2
import school.alt.teacher.main.ui.theme.pretendard
import school.alt.teacher.main.ui.theme.white
import school.alt.teacher.network.dto.UserDto
import school.alt.teacher.network.dto.UserInfoDto

@Composable
fun StudentList(
    modifier: Modifier = Modifier,
    title: String,
    students: MutableList<UserDto>,
    contentPadding: PaddingValues = PaddingValues(horizontal = 18.dp, vertical = 20.dp),
    isOpen: Boolean,
    onValueChange: (Boolean) -> Unit,
    onClick: (UserInfoDto) -> Unit = {},
    backgroundColor: Color = white
) {
    Column (
        modifier = modifier.animateContentSize()
    ){
        Row(
            modifier = Modifier
                .background(color = backgroundColor)
                .clickable { onValueChange(!isOpen) }
                .fillMaxWidth()
                .padding(contentPadding),
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
                    if (isOpen) {
                        0f
                    } else {
                        180f
                    }
                ), contentDescription = "", size = 28.dp
            )
        }
        if (isOpen) {
            for (i in 0..<students.size) {
                val data = students[i].user
                val studentNumber = "%d%d%02d".format(data.grade, data.className, data.classNo)
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
                            text = studentNumber,
                            color = line1,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = pretendard
                        )
                    }
                    Text(
                        modifier = Modifier
                            .clickable (
                                role = Role.Button,
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple()
                            ){
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

@Composable
@Preview()
fun StudentTest(){

    var class4 by remember {
    mutableStateOf(false)

    }
    val mainScrollState = rememberScrollState()

    Box(modifier = Modifier
        .background(color = backgroundStrong)
        .fillMaxSize()){

        Column (
            modifier = Modifier
                .verticalScroll(mainScrollState)
                .fillMaxSize()
        ) {
    val stuList =mutableListOf<UserDto>(
        UserDto(mealType =  emptyList(),
            user = UserInfoDto())
)
    StudentList(
        modifier = Modifier,
        title = "1",
        students = stuList,
        isOpen =  class4,
        onValueChange = { class4 = it }
    )
            StudentList(
                modifier = Modifier,
                title = "1",
                students = stuList,
                isOpen =  class4,
                onValueChange = { class4 = it }
            )
            StudentList(
                modifier = Modifier,
                title = "2",
                students = stuList,
                isOpen =  class4,
                onValueChange = { class4 = it }
            )
            StudentList(
                modifier = Modifier,
                title = "3",
                students = stuList,
                isOpen =  class4,
                onValueChange = { class4 = it }
            )
        }
    }
}

data class Student(
    val name : String,
    val studentNumber : String
)