package school.alt.let.teacher.main.ui.screen.auth.signup

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
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
import school.alt.teacher.network.dto.SignUpRequestDto

@Composable
fun SignUp2Screen(
    modifier: Modifier,
    signUpViewModel: SignUpViewModel,
    onMoveScreen: (String) -> Unit
) {

    var nameText by rememberSaveable {
        mutableStateOf("")
    }
    val context = LocalContext.current

    LaunchedEffect(signUpViewModel.toastMessage.value) {
        if (signUpViewModel.toastMessage.value != "") {
            Toast.makeText(context, signUpViewModel.toastMessage.value, Toast.LENGTH_SHORT).show()
            signUpViewModel.initToastMessage()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = white)
    ) {
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
                    text = "2/2",
                    fontSize = 11.sp,
                    fontFamily = pretendard,
                    fontWeight = FontWeight.Normal
                )
                CustomProgressBar(1f)
                Spacer(modifier = Modifier.padding(top = 9.dp))


                TextButton2(
                    modifier = Modifier.fillMaxWidth(),
                    text = "회원가입 하기",
                    enabled = nameText.isNotBlank(),
                    backgroundColor = main2
                ) {
                    signUpViewModel.setName(inputName = nameText)
                    signUpViewModel.signUp(
                        SignUpRequestDto(
                            id = signUpViewModel.id.value,
                            password = signUpViewModel.password.value,
                            name = signUpViewModel.name.value
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
                    text = "이름",
                    fontSize = 16.sp,
                    fontFamily = pretendard,
                    fontWeight = FontWeight.SemiBold
                )
                GrayTextField(
                    text = nameText,
                    label = "이름을 입력해주세요",
                    keyboardType = KeyboardOptions(keyboardType = KeyboardType.Text),
                    singleLine = true,
                    onValueChange = { nameText = it }
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
                        onMoveScreen(ScreenNavigate.SIGNUP1.name)
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