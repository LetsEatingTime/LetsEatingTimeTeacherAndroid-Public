package school.alt.teacher.main.navigation

import Login
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import school.alt.let.teacher.main.ui.screen.auth.signup.SignUp2Screen
import school.alt.let.teacher.main.ui.screen.home.StoreMainScreen
import school.alt.let.teacher.main.ui.screen.home.StudentApprovalScreen
import school.alt.let.teacher.main.ui.screen.home.StudentProfileModifyScreen
import school.alt.let.teacher.main.ui.screen.home.StudentProfileScreen
import school.alt.let.teacher.main.ui.screen.home.StudentSearchScreen
import school.alt.teacher.main.ui.screen.auth.changepassword.ChangePasswordScreen
import school.alt.teacher.main.ui.screen.auth.signup.SignUp1Screen
import school.alt.teacher.main.ui.screen.home.HomeScreen
import school.alt.teacher.main.ui.screen.home.MealScan
import school.alt.teacher.main.ui.screen.home.MenuAddTitleScreen
import school.alt.teacher.main.ui.screen.home.MenuResultScreen
import school.alt.teacher.main.ui.screen.home.ProfileModifyScreen
import school.alt.teacher.main.ui.screen.home.ProfileScreen
import school.alt.teacher.main.ui.screen.home.ShowMenuScreen
import school.alt.teacher.main.ui.screen.home.StoreScreen
import school.alt.teacher.main.ui.theme.main2
import school.alt.teacher.main.ui.theme.white
import school.alt.teacher.main.viewmodel.change_password.ChangePasswordViewModel
import school.alt.teacher.main.viewmodel.home.HomeViewModel
import school.alt.teacher.main.viewmodel.login.LoginViewModel
import school.alt.teacher.main.viewmodel.menu.MenuViewModel
import school.alt.teacher.main.viewmodel.profile.ProfileViewModel
import school.alt.teacher.main.viewmodel.scan.ScanViewModel
import school.alt.teacher.main.viewmodel.sign_up.SignUpViewModel
import school.alt.teacher.main.viewmodel.store.StoreViewModel
import school.alt.teacher.main.viewmodel.student.StudentViewModel

enum class ScreenNavigate {
    LOGIN,
    SIGNUP1,
    SIGNUP2,
    CHANGE_PASSWORD,
    HOME,
    MEAL_SCAN,
    MENU_ADD_TITLE,
    MENU_RESULT,
    SHOW_MENU,
    STORE,
    STORE_MAIN,
    STUDENT,
    STUDENT_APPROVAL,
    STUDENT_PROFILE,
    STUDENT_PROFILE_MODIFY,
    PROFILE,
    PROFILE_MODIFY
}

@Composable
fun LetTeacherApp(
    modifier: Modifier,
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
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = ScreenNavigate.valueOf(
        backStackEntry?.destination?.route ?: ScreenNavigate.LOGIN.name
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigation(
                containerColor = white,
                contentColor = main2,
                indicatorColor = Color.Transparent,
                navController = navController
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(
                navController = navController,
                startDestination = ScreenNavigate.LOGIN.name,
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                composable(
                    route = ScreenNavigate.LOGIN.name,
                ) {
                    Login(
                        modifier = modifier,
                        viewModel = loginViewModel,
                        onMoveScreen = { destination ->
                            if (destination == ScreenNavigate.HOME.name) {
                                navController.navigate(destination) {
                                    popUpTo(ScreenNavigate.LOGIN.name) {
                                        inclusive = true
                                    }
                                }
                            } else {
                                move(destination = destination, navController = navController)
                            }
                        }
                    )
                }
                composable(
                    route = ScreenNavigate.SIGNUP1.name
                ) {
                    SignUp1Screen(
                        modifier = modifier,
                        signUpViewModel = singInViewModel,
                        onMoveScreen = { destination ->
                            if (destination == ScreenNavigate.LOGIN.name) {
                                navController.navigate(destination) {
                                    popUpTo(ScreenNavigate.SIGNUP1.name) {
                                        inclusive = true
                                    }
                                }
                            } else {
                                move(destination = destination, navController = navController)
                            }
                        })
                }
                composable(
                    route = ScreenNavigate.SIGNUP2.name,

                    ) {
                    SignUp2Screen(
                        modifier = modifier,
                        signUpViewModel = singInViewModel,
                        onMoveScreen = { destination ->
                            if (destination == ScreenNavigate.LOGIN.name) {
                                navController.navigate(destination) {
                                    popUpTo(ScreenNavigate.SIGNUP2.name) {
                                        inclusive = true
                                    }
                                }
                            } else {
                                move(destination = destination, navController = navController)
                            }
                        })
                }

                composable(
                    route = ScreenNavigate.CHANGE_PASSWORD.name
                ) {
                    ChangePasswordScreen(
                        modifier = modifier,
                        changePasswordViewModel = changePasswordViewModel,
                        onMoveScreen = { destination ->
                            move(destination = destination, navController = navController)
                        })
                }
                composable(
                    route = ScreenNavigate.HOME.name,
                ) {
                    HomeScreen(
                        modifier = modifier,
                        homeViewModel = homeViewModel,
                        storeViewModel = storeViewModel,
                        studentViewModel = studentViewModel,
                        onMoveScreen = { destination ->
                            move(destination = destination, navController = navController)
                        })
                }

                composable(
                    route = ScreenNavigate.MEAL_SCAN.name
                ) {
                    MealScan(scanViewModel = scanViewModel, onMoveScreen = { destination ->
                        move(destination = destination, navController = navController)
                    })
                }

                composable(
                    route = ScreenNavigate.MENU_ADD_TITLE.name
                ) {
                    MenuAddTitleScreen(modifier = modifier, onMoveScreen = { destination ->
                        move(destination = destination, navController = navController)
                    })
                }

                composable(
                    route = ScreenNavigate.MENU_RESULT.name
                ) {
                    MenuResultScreen(
                        modifier = modifier,
                        scanViewModel = scanViewModel,
                        onMoveScreen = { destination ->
                            move(destination = destination, navController = navController)
                        })
                }
                composable(
                    route = ScreenNavigate.PROFILE.name
                ) {
                    ProfileScreen(
                        modifier = modifier,
                        profileViewModel = profileViewModel,
                        onMoveScreen = { destination ->
                            if (destination == ScreenNavigate.LOGIN.name) {
                                navController.navigate(destination) {
                                    popUpTo(ScreenNavigate.PROFILE.name) {
                                        inclusive = true
                                    }
                                }
                            } else {
                                move(destination = destination, navController = navController)
                            }
                        })
                }

                composable(
                    route = ScreenNavigate.PROFILE_MODIFY.name
                ) {
                    ProfileModifyScreen(
                        modifier = modifier,
                        profileViewModel = profileViewModel,
                        onMoveScreen = { destination ->
                            move(destination = destination, navController = navController)
                        })
                }
                composable(
                    route = ScreenNavigate.SHOW_MENU.name
                ) {
                    ShowMenuScreen(
                        modifier = modifier,
                        menuViewModel = menuViewModel,
                        onMoveScreen = { destination ->
                            move(destination = destination, navController = navController)
                        })
                }

                composable(
                    route = ScreenNavigate.STORE.name
                ) {
                    StoreScreen(
                        modifier = modifier,
                        storeViewModel = storeViewModel,
                        onMoveScreen = {
                            navController.popBackStack()
                        })
                }

                composable(
                    route = ScreenNavigate.STORE_MAIN.name
                ) {
                    StoreMainScreen(
                        modifier = modifier,
                        storeViewModel = storeViewModel,
                        onMoveScreen = {
                            navController.popBackStack()
                        })
                }

                composable(
                    route = ScreenNavigate.STUDENT_PROFILE.name
                ) {
                    StudentProfileScreen(
                        modifier = modifier,
                        studentViewModel = studentViewModel,
                        onMoveScreen = { destination ->
                            if (destination == null) {
                                navController.navigate(ScreenNavigate.STUDENT.name) {
                                    popUpTo(ScreenNavigate.STUDENT_PROFILE.name) {
                                        inclusive = true
                                    }
                                }

                            } else {
                                move(destination = destination, navController = navController)
                            }
                        })
                }

                composable(
                    route = ScreenNavigate.STUDENT_PROFILE_MODIFY.name
                ) {
                    StudentProfileModifyScreen(
                        modifier = modifier,
                        studentViewModel = studentViewModel,
                        onMoveScreen = { destination ->
                            navController.popBackStack()
                        })
                }

                composable(
                    route = ScreenNavigate.STUDENT_APPROVAL.name
                ) {
                    StudentApprovalScreen(
                        modifier = modifier,
                        studentViewModel = studentViewModel,
                        onMoveScreen = { destination ->
                            move(destination = destination, navController = navController)
                        })
                }

                composable(
                    route = ScreenNavigate.STUDENT.name
                ) {
                    StudentSearchScreen(
                        modifier = modifier,
                        studentViewModel = studentViewModel,
                        onMoveScreen = { destination ->
                            move(destination = destination, navController = navController)
                        })
                }
            }
        }
    }
}

fun move(destination: String, navController: NavHostController) {
    navController.navigate(destination)
}
