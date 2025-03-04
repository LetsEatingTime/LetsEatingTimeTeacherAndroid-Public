package school.alt.teacher.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import school.alt.teacher.main.viewmodel.scan.ScanViewModel
import school.alt.teacher.main.navigation.LetTeacherApp
import school.alt.teacher.main.ui.theme.LetTeacherTheme
import school.alt.teacher.main.ui.theme.white
import school.alt.teacher.main.viewmodel.change_password.ChangePasswordViewModel
import school.alt.teacher.main.viewmodel.home.HomeViewModel
import school.alt.teacher.main.viewmodel.login.LoginViewModel
import school.alt.teacher.main.viewmodel.menu.MenuViewModel
import school.alt.teacher.main.viewmodel.profile.ProfileViewModel
import school.alt.teacher.main.viewmodel.sign_up.SignUpViewModel
import school.alt.teacher.main.viewmodel.store.StoreViewModel
import school.alt.teacher.main.viewmodel.student.StudentViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val loginViewModel: LoginViewModel by viewModels()
            val singInViewModel: SignUpViewModel by viewModels()
            val homeViewModel: HomeViewModel by viewModels()
            val storeViewModel: StoreViewModel by viewModels()
            val profileViewModel: ProfileViewModel by viewModels()
            val studentViewModel: StudentViewModel by viewModels()
            val changePasswordViewModel: ChangePasswordViewModel by viewModels()
            val scanViewModel: ScanViewModel by viewModels()
            val menuViewModel: MenuViewModel by viewModels()
            val accessToken = loginViewModel.getAccessToken().collectAsState("")

            LaunchedEffect(key1 = accessToken.value) {
                if (accessToken.value != "") {
                    studentViewModel.initValue()
                    profileViewModel.initValue()
                }
            }



            LetTeacherTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Main(
                        innerPadding = innerPadding,
                        loginViewModel = loginViewModel,
                        singInViewModel = singInViewModel,
                        homeViewModel = homeViewModel,
                        storeViewModel = storeViewModel,
                        profileViewModel = profileViewModel,
                        studentViewModel = studentViewModel,
                        changePasswordViewModel = changePasswordViewModel,
                        scanViewModel = scanViewModel,
                        menuViewModel = menuViewModel,
                    )
                }
            }
        }
    }
}

@Composable
fun Main(
    innerPadding: PaddingValues,
    loginViewModel: LoginViewModel,
    singInViewModel: SignUpViewModel,
    homeViewModel: HomeViewModel,
    storeViewModel: StoreViewModel,
    profileViewModel: ProfileViewModel,
    studentViewModel: StudentViewModel,
    changePasswordViewModel: ChangePasswordViewModel,
    scanViewModel: ScanViewModel,
    menuViewModel: MenuViewModel,

    ) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(white),
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding(),  // 상태바와 네비게이션 바 패딩 적용
            color = white
        ) {
            LetTeacherApp(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 28.dp),
                loginViewModel = loginViewModel,
                singInViewModel = singInViewModel,
                homeViewModel = homeViewModel,
                storeViewModel = storeViewModel,
                profileViewModel = profileViewModel,
                studentViewModel = studentViewModel,
                changePasswordViewModel = changePasswordViewModel,
                scanViewModel = scanViewModel,
                menuViewModel = menuViewModel,
            )
        }
    }
}
