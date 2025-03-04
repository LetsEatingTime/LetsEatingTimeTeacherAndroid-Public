package school.alt.teacher.main.ui.screen.auth.changepassword

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import school.alt.teacher.main.navigation.ScreenNavigate
import school.alt.teacher.main.ui.compose.button.Button2
import school.alt.teacher.main.ui.compose.navBar.NavBar
import school.alt.teacher.main.ui.compose.textfild.GrayTextField
import school.alt.teacher.main.ui.foundation.IcBack
import school.alt.teacher.main.ui.foundation.ImageCharacter
import school.alt.teacher.main.ui.theme.line3
import school.alt.teacher.main.ui.theme.main2
import school.alt.teacher.main.ui.theme.pretendard
import school.alt.teacher.main.ui.theme.white
import school.alt.teacher.main.viewmodel.change_password.ChangePasswordViewModel
import school.alt.teacher.network.dto.PasswordChangeRequest

@Composable
fun ChangePasswordScreen(
    modifier: Modifier = Modifier,
    changePasswordViewModel: ChangePasswordViewModel,
    onMoveScreen: (String) -> Unit
) {
    val idText = rememberSaveable {
        mutableStateOf("")
    }

    val newPasswordText = rememberSaveable {
        mutableStateOf("")
    }
    val currentPasswordText = rememberSaveable {
        mutableStateOf("")
    }

    val context = LocalContext.current

    LaunchedEffect(changePasswordViewModel.toastMessage.value) {
        if (changePasswordViewModel.toastMessage.value != "") {
            Toast.makeText(context, changePasswordViewModel.toastMessage.value, Toast.LENGTH_SHORT)
                .show()
            changePasswordViewModel.initToastMessage()
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = white)) {
        Box(
            modifier = modifier.fillMaxSize(),
        ) {
            Column(Modifier.align(Alignment.BottomCenter)) {

                ImageCharacter(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentScale = ContentScale.FillWidth
                )
                Spacer(modifier = Modifier.padding(top = 18.dp))
                Spacer(modifier = Modifier.padding(top = 9.dp))


                Button2(
                    modifier = Modifier.fillMaxWidth(),
                    text = "비밀번호 변경",
                    enabled =
                    idText.value.isNotBlank() && currentPasswordText.value.isNotBlank()
                            && newPasswordText.value.isNotBlank()
//                                .contains(Pattern.passwordRegex) // TODO : 나중에 정규식 적용
                    ,
                    backgroundColor = main2
                ) {
                    changePasswordViewModel.setId(inputId = idText.value)
                    changePasswordViewModel.setNewPassword(inputNewPassword = currentPasswordText.value)
                    changePasswordViewModel.changePassword(
                        PasswordChangeRequest(
                            id = idText.value,
                            password = currentPasswordText.value,
                            newPassword = newPasswordText.value
                        )
                    ) {
                        onMoveScreen(ScreenNavigate.LOGIN.name)
                    }
                }
                Spacer(modifier = Modifier.padding(top = 50.dp))
            }
            Column(Modifier.align(Alignment.TopCenter)) {

                Spacer(modifier = Modifier.padding(top = 85.dp))
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = "비밀번호 변경",
                    fontSize = 16.sp,
                    fontFamily = pretendard,
                    fontWeight = FontWeight.SemiBold
                )
                GrayTextField(
                    text = idText,
                    label = "현재 아이디",
                    keyboardType = KeyboardOptions(keyboardType = KeyboardType.Text),
                    singleLine = true
                )
                Spacer(modifier = Modifier.padding(top = 18.dp))
                GrayTextField(
                    text = currentPasswordText,
                    label = "현재 비밀번호",
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardType = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true
                )
                Spacer(modifier = Modifier.padding(top = 18.dp))
                GrayTextField(
                    text = newPasswordText,
                    label = "변경할 비밀번호",
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardType = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true
                )
            }
        }
        Column(
            Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        ) {
            NavBar(
                modifier = Modifier.fillMaxWidth(),
                padding = PaddingValues(3.dp),
                startContent = {
                    IconButton(modifier = it, onClick = {
                        onMoveScreen(ScreenNavigate.LOGIN.name)
                        changePasswordViewModel.initValues()
                    }) {
                        IcBack(
                            contentDescription = "",
                            tint = line3
                        )
                    }
                },
                centerContext = {
                    Text(
                        modifier = it,
                        text = "Let’sEatingTime",
                        fontSize = 16.sp,
                        fontFamily = pretendard,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                isClickable = false
            )
        }
    }

}
