package school.alt.teacher.main.ui.screen.auth.signup

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import school.alt.teacher.main.navigation.ScreenNavigate
import school.alt.teacher.main.ui.compose.button.TextButton2
import school.alt.teacher.main.ui.compose.bar.NavBar
import school.alt.teacher.main.ui.compose.progressbar.CustomProgressBar
import school.alt.teacher.main.ui.compose.textfield.GrayTextField
import school.alt.teacher.main.ui.foundation.IcBack
import school.alt.teacher.main.ui.foundation.ImageCharacter
import school.alt.teacher.main.ui.theme.line3
import school.alt.teacher.main.ui.theme.main2
import school.alt.teacher.main.ui.theme.pretendard
import school.alt.teacher.main.ui.theme.white
import school.alt.teacher.main.viewmodel.sign_up.SignUpViewModel

@Composable
fun SignUp1Screen(
    modifier: Modifier,
    signUpViewModel: SignUpViewModel,
    onMoveScreen: (String) -> Unit = {}
) {
    var idText by rememberSaveable {
        mutableStateOf("")
    }

    var passwordText by rememberSaveable {
        mutableStateOf("")
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
                Text(
                    modifier = Modifier
                        .padding(end = 3.dp)
                        .align(Alignment.End),
                    text = "1/2",
                    fontSize = 11.sp,
                    fontFamily = pretendard,
                    fontWeight = FontWeight.Normal
                )
                CustomProgressBar(0.5f)
                Spacer(modifier = Modifier.padding(top = 9.dp))


                TextButton2(
                    modifier = Modifier.fillMaxWidth(),
                    text = "다음으로",
                    enabled = idText.isNotBlank() && passwordText.isNotBlank(),
                    backgroundColor = main2
                ) {
                    signUpViewModel.setId(inputId = idText)
                    signUpViewModel.setPassword(inputPassword = passwordText)
                    onMoveScreen(ScreenNavigate.SIGNUP2.name)
                }
                Spacer(modifier = Modifier.padding(top = 50.dp))
            }
            Column(Modifier.align(Alignment.TopCenter)) {

                Spacer(modifier = Modifier.padding(top = 85.dp))
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = "아이디",
                    fontSize = 16.sp,
                    fontFamily = pretendard,
                    fontWeight = FontWeight.SemiBold
                )
                GrayTextField(
                    text = idText,
                    label = "아이디를 입력해주세요",
                    keyboardType = KeyboardOptions(keyboardType = KeyboardType.Text),
                    singleLine = true,
                    onValueChange = { idText = it }
                )
                Spacer(modifier = Modifier.padding(top = 18.dp))
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = "비밀번호",
                    fontSize = 16.sp,
                    fontFamily = pretendard,
                    fontWeight = FontWeight.SemiBold
                )
                GrayTextField(
                    text = passwordText,
                    label = "비밀번호를 입력해주세요",
                    keyboardType = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                    onValueChange = { passwordText = it }
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
                        signUpViewModel.initValues()
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