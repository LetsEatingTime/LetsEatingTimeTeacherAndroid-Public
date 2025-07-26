package school.alt.let.teacher.main.ui.screen.home


import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import school.alt.teacher.main.navigation.ScreenNavigate
import school.alt.teacher.main.ui.compose.button.TextButton2
import school.alt.teacher.main.ui.compose.chips.CategoryTag
import school.alt.teacher.main.ui.compose.textfield.TextFieldBorder
import school.alt.teacher.main.ui.theme.backgroundNormal
import school.alt.teacher.main.ui.theme.backgroundStrong
import school.alt.teacher.main.ui.theme.black
import school.alt.teacher.main.ui.theme.line1
import school.alt.teacher.main.ui.theme.line3
import school.alt.teacher.main.ui.theme.main2
import school.alt.teacher.main.ui.theme.main3
import school.alt.teacher.main.ui.theme.pretendard
import school.alt.teacher.main.ui.theme.warning
import school.alt.teacher.main.ui.theme.white
import school.alt.teacher.main.viewmodel.student.StudentViewModel
import school.alt.teacher.network.dto.EditUserRequest
import java.io.File
import java.io.FileOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentProfileModifyScreen(
    modifier: Modifier = Modifier,
    studentViewModel: StudentViewModel,
    onMoveScreen: (String) -> Unit
) {

    var nameText by rememberSaveable {
        mutableStateOf(studentViewModel.student.value?.name ?: "")
    }

    var isImageChanged by remember {
        mutableStateOf(false)
    }

    var numberText by rememberSaveable {
        mutableStateOf(studentViewModel.student.value!!.classNo.toString())
    }
    var classText by rememberSaveable {
        mutableStateOf(studentViewModel.student.value!!.className.toString())
    }
    var gradeText by rememberSaveable {
        mutableStateOf(studentViewModel.student.value!!.grade.toString())
    }

    val showBottomSheet = remember { mutableStateOf(false) }

    val profileUrl = rememberSaveable {
        mutableStateOf("")
    }

    val context = LocalContext.current

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val getImage = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            if (uri != null) {
                isImageChanged = true
                studentViewModel.setUri(uri)
            }
        }
    )

    LaunchedEffect(studentViewModel.imageUri.value) {
        profileUrl.value = studentViewModel.imageUri.value.toString()
    }

    val allergy = rememberSaveable {
        mutableMapOf(
            "난류" to mutableStateOf(false),
            "우유" to mutableStateOf(false),
            "돼지고기" to mutableStateOf(false),
            "메밀" to mutableStateOf(false),
            "대두" to mutableStateOf(false),
            "게" to mutableStateOf(false),
            "밀" to mutableStateOf(false),
            "고등어" to mutableStateOf(false),
            "새우" to mutableStateOf(false),
            "땅콩" to mutableStateOf(false),
            "복숭아" to mutableStateOf(false),
            "토마토" to mutableStateOf(false),
            "아황산류" to mutableStateOf(false),
            "호두" to mutableStateOf(false),
            "닭고기" to mutableStateOf(false),
            "쇠고기" to mutableStateOf(false),
            "오징어" to mutableStateOf(false),
            "잣" to mutableStateOf(false),
            "조개류(굴, 전복, 홍합 포함)" to mutableStateOf(false)
        )
    }

    val mainScrollState = rememberScrollState()
    Box(
        modifier = Modifier
            .background(color = backgroundStrong)
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(mainScrollState)
                .padding(bottom = 130.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Column(
                modifier = Modifier
                    .background(color = backgroundNormal)
                    .fillMaxWidth()
                    .padding(vertical = 32.dp, horizontal = 18.dp),
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "프로필 이미지",
                    color = black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = pretendard
                )
                Spacer(modifier = Modifier.padding(top = 13.dp))
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .size(size = 85.dp)
                            .clip(CircleShape)
                            .background(color = line1),
                        model = profileUrl.value, contentDescription = ""
                    )
                    Spacer(modifier = Modifier.padding(start = 10.dp))
                    Text(
                        modifier = Modifier
                            .clickable {
                                getImage.launch("image/*")
                            }
                            .padding(17.dp),
                        text = "프로필 이미지 변경하기",
                        color = main3,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = pretendard
                    )
                }
            }
            Spacer(modifier = Modifier.padding(top = 16.dp))
            TextFieldBorder(
                text = nameText,
                borderTitle = "이름",
                label = "이름을 입력 해 주세요",
                onValueChange = { nameText = it }
            )
            Spacer(modifier = Modifier.padding(top = 16.dp))
            TextFieldBorder(
                text = gradeText,
                borderTitle = "학년",
                label = "학년을 입력 해 주세요",
                onValueChange = { gradeText = it }
            )
            Spacer(modifier = Modifier.padding(top = 16.dp))
            TextFieldBorder(
                text = classText,
                borderTitle = "반",
                label = "반을 입력 해 주세요",
                onValueChange = { classText = it }
            )
            Spacer(modifier = Modifier.padding(top = 16.dp))
            TextFieldBorder(
                text = numberText,
                borderTitle = "번호",
                label = "번호을 입력 해 주세요",
                onValueChange = { numberText = it }
            )
            Spacer(modifier = Modifier.padding(top = 16.dp))
            Column(
                Modifier
                    .background(white)
                    .fillMaxWidth()
                    .padding(vertical = 13.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "알레르기 음식",
                        color = black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = pretendard
                    )
                    Text(
                        modifier = Modifier.clickable {
                            showBottomSheet.value = true
                        },
                        text = "수정하기",
                        color = line1,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = pretendard
                    )
                }
                Spacer(modifier = Modifier.padding(top = 13.dp))

                ChipVerticalGrid(
                    spacing = 7.dp,
                    modifier = Modifier
                ) {
                    allergy.forEach { word ->
                        val isSelected = remember {
                            word.value
                        }
                        CategoryTag(isBorder = isSelected, text = word.key, enabled = false)
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .padding(horizontal = 18.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 50.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton2(
                text = "수정 취소",
                backgroundColor = warning,
                textSize = 16.sp,
                paddingValues = PaddingValues(horizontal = 55.dp, vertical = 13.dp),
            ) {
                onMoveScreen(ScreenNavigate.STUDENT_PROFILE.name)
            }
            TextButton2(
                text = "수정 완료",
                backgroundColor = line3,
                textSize = 16.sp,
                paddingValues = PaddingValues(horizontal = 55.dp, vertical = 13.dp),
            ) {
                studentViewModel.modifyStudent(
                    EditUserRequest(
                        idx = studentViewModel.student.value?.idx?.toInt(),
                        id = studentViewModel.student.value?.id ?: "",
                        className = classText.toInt(),
                        classNo = numberText.toInt(),
                        grade = gradeText.toInt(),
                        name = nameText,
                        allergy = allergy.filterValues { it.value }.keys.toList()
                    )
                ) {
                    if(isImageChanged){
                        val image = prepareFilePart(profileUrl.value.toUri(), context)
                        if (image != null) {
                            studentViewModel.uploadStudentImage(
                                id = studentViewModel.student.value?.id ?: "",
                                image = image
                            )
                        }
                    }

                    studentViewModel.getStudentList()
                    onMoveScreen(ScreenNavigate.STUDENT_PROFILE.name)
                }
            }
        }
        if (showBottomSheet.value) {
            ModalBottomSheet(
                modifier = Modifier,
                contentColor = white,
                onDismissRequest = {
                    showBottomSheet.value = false
                },
                sheetState = sheetState
            ) {
                Box(
                    modifier = Modifier
                        .height(600.dp)
                        .padding(bottom = 50.dp)
                ) {
                    Column(
                        Modifier
                            .padding(vertical = 13.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 18.dp)
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
                            modifier = Modifier
                        ) {
                            allergy.forEach { word ->
                                val isSelected = remember {
                                    word.value
                                }
                                CategoryTag(isBorder = isSelected, text = word.key, enabled = true)
                            }
                        }
                    }
                    TextButton2(
                        modifier = Modifier
                            .background(color = white)
                            .padding(horizontal = 18.dp)
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter), backgroundColor = main2, text = "수정 완료"
                    ) {
                        showBottomSheet.value = false
                    }
                }
            }
        }
    }
}

private fun prepareFilePart(uri: Uri, context : Context): MultipartBody.Part? {
    val file = getFileFromUri(uri = uri, context = context)
    return file?.let {
        val requestFile = it.asRequestBody("image/*".toMediaTypeOrNull())
        MultipartBody.Part.createFormData("image", it.name, requestFile)
    }
}

private fun getFileFromUri(uri: Uri, context : Context): File? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri)
        val file = File(context.cacheDir, "temp_file")
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()
        file
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
