
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.first
import school.alt.teacher.main.navigation.ScreenNavigate
import school.alt.teacher.main.ui.compose.button.TextButton2
import school.alt.teacher.main.ui.compose.textfield.GrayTextField
import school.alt.teacher.main.ui.foundation.ImageCharacter
import school.alt.teacher.main.ui.foundation.ImageLogo
import school.alt.teacher.main.ui.theme.black
import school.alt.teacher.main.ui.theme.line1
import school.alt.teacher.main.ui.theme.main2
import school.alt.teacher.main.ui.theme.pretendard
import school.alt.teacher.main.ui.theme.white
import school.alt.teacher.main.viewmodel.login.LoginUiState
import school.alt.teacher.main.viewmodel.login.LoginViewModel

@Composable
fun Login(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel,
    onMoveScreen: (String) -> Unit = {}
) {

    val context = LocalContext.current

    var idText by rememberSaveable {
        mutableStateOf("")
    }

    var passwordText by rememberSaveable {
        mutableStateOf("")
    }

    val maintain = remember {
        mutableStateOf(false)
    }

    //앱이 시작할 때 로그인 유지가 되어있는지 확인하는 코드
    LaunchedEffect(key1 = true) {
        viewModel.getAutoLogin().collect() {
            maintain.value = it

            if (it && viewModel.getAccessToken().first().isNotBlank()) {
                onMoveScreen(ScreenNavigate.HOME.name)
            }
        }
    }

    val loginUiState by viewModel.loginUiState.collectAsState()

    LaunchedEffect(key1 = loginUiState) {
        when (loginUiState) {
            is LoginUiState.Error -> {
                Toast.makeText(context, (loginUiState as LoginUiState.Error).message, Toast.LENGTH_SHORT).show()
            }

            is LoginUiState.Success -> {
                if ((loginUiState as LoginUiState.Success).isLogin) {
                    onMoveScreen(ScreenNavigate.HOME.name)
                    (loginUiState as LoginUiState.Success).isLogin = false
                }
            }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(white)
    ) {
        ImageCharacter(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            contentScale = ContentScale.FillWidth
        )
        Column(
            modifier = modifier.align(Alignment.TopCenter),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(top = 98.dp))
            ImageLogo(
                modifier = Modifier
                    .padding(horizontal = 25.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(top = 30.dp))
            GrayTextField(
                text = idText,
                label = "아이디를 입력해주세요",
                keyboardType = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true,
                onValueChange = { idText = it }
            )
            Spacer(modifier = Modifier.padding(top = 18.dp))
            GrayTextField(
                text = passwordText,
                label = "비밀번호를 입력해주세요",
                keyboardType = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                onValueChange = { passwordText = it }
            )
            Spacer(modifier = Modifier.padding(top = 18.dp))
            TextButton2(
                modifier = Modifier.fillMaxWidth(),
                text = "로그인",
                enabled = idText.isNotBlank() && passwordText.isNotBlank(),
                backgroundColor = main2
            ) {
                viewModel.setId(inputId = idText)
                viewModel.setPassword(inputPassword = passwordText)
                viewModel.login()
                viewModel.setAutoLogin(maintain.value)

            }
            Row(
                modifier = Modifier.align(Alignment.Start),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    modifier = Modifier,
                    checked = maintain.value,
                    colors = CheckboxDefaults.colors(
                        checkedColor = main2,
                        uncheckedColor = black,
                        checkmarkColor = white
                    ),
                    onCheckedChange = {
                        maintain.value = it
                    })
                Text(
                    text = "로그인 상태 유지",
                    fontSize = 12.sp,
                    fontFamily = pretendard,
                    fontWeight = FontWeight.Normal
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier
                        .padding(9.dp)
                        .clickable {
                            onMoveScreen(ScreenNavigate.CHANGE_PASSWORD.name)
                        },
                    text = "비밀번호 변경", fontSize = 14.sp,
                    fontFamily = pretendard,
                    color = black,
                    fontWeight = FontWeight.Normal
                )
                Box(
                    modifier = Modifier
                        .background(color = line1)
                        .padding(top = 13.dp, start = 1.dp)
                )
                Text(
                    modifier = Modifier
                        .padding(9.dp)
                        .clickable {
                            onMoveScreen(ScreenNavigate.SIGNUP1.name)
                        },
                    text = "회원가입",
                    fontSize = 14.sp,
                    color = black,
                    fontFamily = pretendard,
                    fontWeight = FontWeight.Normal
                )
            }
        }

    }
}