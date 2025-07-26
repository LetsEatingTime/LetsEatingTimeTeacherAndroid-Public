package school.alt.teacher.main.ui.screen.home

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import school.alt.teacher.main.navigation.ScreenNavigate
import school.alt.teacher.main.ui.compose.dialog.LetAlertDialog
import school.alt.teacher.main.ui.compose.bar.Bar
import school.alt.teacher.main.ui.foundation.IcEnter
import school.alt.teacher.main.ui.theme.backgroundNormal
import school.alt.teacher.main.ui.theme.backgroundStrong
import school.alt.teacher.main.ui.theme.black
import school.alt.teacher.main.ui.theme.line1
import school.alt.teacher.main.ui.theme.pretendard
import school.alt.teacher.main.ui.theme.warning
import school.alt.teacher.main.viewmodel.home.NetworkResultState
import school.alt.teacher.main.viewmodel.profile.ProfileViewModel
import school.alt.teacher.main.viewmodel.profile.UserImageUiState
import school.alt.teacher.main.viewmodel.profile.UserUiState
import school.alt.teacher.network.dto.UserInfoDto

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    profileViewModel: ProfileViewModel,
    onMoveScreen: (String) -> Unit
) {
    val context = LocalContext.current

    val productionList by profileViewModel.profileInfo.observeAsState(UserInfoDto())
    val productionState by profileViewModel.profileState.collectAsStateWithLifecycle(
        NetworkResultState()
    )

    val profileUrlImageUiState by profileViewModel.userImageUiState.collectAsState()
    val profileUiState by profileViewModel.userUiState.collectAsState()
    val profileDialogUiState by profileViewModel.userDialogUiState.collectAsState()

    val name = remember {
        mutableStateOf(profileViewModel.profileInfo.value?.name ?: "")
    }


    DisposableEffect(Unit) {
        val scope = CoroutineScope(Dispatchers.Default)
        val job = scope.launch {
        }

        onDispose {
            job.cancel()
        }
    }


    val packageInfo = context.packageManager?.getPackageInfo(context.packageName, 0)
    val versionName = packageInfo?.versionName // 버전 이름 (예: "1.0")
    val mainScrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .background(color = backgroundStrong)
            .fillMaxSize()
            .verticalScroll(mainScrollState),
        verticalArrangement = Arrangement.Top

    ) {
        Column(
            Modifier
                .background(color = backgroundNormal)
                .fillMaxWidth()
                .padding(top = 80.dp, bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(width = 150.dp, height = 200.dp)
                    .background(color = line1),
                model = when (profileUrlImageUiState) {
                    is UserImageUiState.Success -> {
                        (profileUrlImageUiState as UserImageUiState.Success).imageUrl
                    }

                    is UserImageUiState.Error -> {
                        ""
                    }
                },
                contentDescription = ""
            )
            Spacer(modifier = Modifier.padding(top = 16.dp))
            when (profileUiState) {
                is UserUiState.Success -> {
                    val userInfo = (profileUiState as UserUiState.Success).userInfo.user
                    Text(
                        text = "안녕하세요, ${userInfo.name}님",
                        color = black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = pretendard
                    )
                    Spacer(modifier = Modifier.padding(top = 4.dp))
                    Text(
                        text = "선생님",
                        color = line1,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = pretendard
                    )
                }

                is UserUiState.Error -> {
                    Text(
                        text = "",
                        color = black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = pretendard
                    )
                    Spacer(modifier = Modifier.padding(top = 4.dp))
                    Text(
                        text = "",
                        color = line1,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = pretendard
                    )
                }
            }
        }
        Spacer(modifier = Modifier.padding(top = 16.dp))
        Bar(
            startContent = {
                Text(
                    text = "프로필 편집하기",
                    color = black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = pretendard
                )
            },
            endContext = { IcEnter(contentDescription = "", size = 26.dp) },
            paddingValues = PaddingValues(vertical = 10.dp, horizontal = 18.dp),
            isClickable = true
        ) {
            onMoveScreen(ScreenNavigate.PROFILE_MODIFY.name)
        }
        Spacer(modifier = Modifier.padding(top = 16.dp))
        Bar(
            startContent = {
                Text(
                    text = "개인정보 처리 방침",
                    color = black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = pretendard
                )
            },
            endContext = { IcEnter(contentDescription = "", size = 26.dp) },
            isClickable = true,
            paddingValues = PaddingValues(vertical = 10.dp, horizontal = 18.dp)
        ) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://encouraging-wok-aa3.notion.site/140b28899b6c804d9afcc5de19c288f0?pvs=4"))
            context.startActivity(intent)
        }
        Spacer(modifier = Modifier.padding(top = 16.dp))
        Bar(
            isClickable = false,
            startContent = {
                Text(
                    text = "앱 버전",
                    color = black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = pretendard
                )
            },
            endContext = {
                Text(
                    text = "v$versionName",
                    color = line1,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = pretendard
                )
            },
            paddingValues = PaddingValues(vertical = 13.dp, horizontal = 18.dp)
        ) { }

        Spacer(modifier = Modifier.padding(top = 16.dp))
        Bar(
            startContent = {
                Text(
                    text = "로그아웃",
                    color = warning,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = pretendard
                )
            },
            isClickable = true,
            endContext = { IcEnter(contentDescription = "", size = 26.dp) },
            paddingValues = PaddingValues(vertical = 10.dp, horizontal = 18.dp)
        ) {
            profileViewModel.logout(){
                onMoveScreen(ScreenNavigate.LOGIN.name)
            }
        }
        Bar(
            startContent = {
                Text(
                    text = "회원탈퇴",
                    color = warning,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = pretendard
                )
            },
            endContext = { IcEnter(contentDescription = "", size = 26.dp) },
            paddingValues = PaddingValues(vertical = 10.dp, horizontal = 18.dp),
            isClickable = true
        ) {
            profileViewModel.withdrawDialog(){
                onMoveScreen(ScreenNavigate.LOGIN.name)
            }
        }
    }
    if (profileDialogUiState.title != "") {
        LetAlertDialog(
            title = profileDialogUiState.title,
            subTitle = "",
            description = profileDialogUiState.description,
            setNegativeText = {
                profileDialogUiState.negativeComment()
            },
            setPositiveText = {
                profileDialogUiState.positiveComment()
            },
            onClickNegative = profileDialogUiState.onClickNegative,
            onClickPositive = profileDialogUiState.onClickPositive
        )
    }
}

@Composable
@Preview(showBackground = true)
fun ProfileTest() {
//    ProfileScreen(
//        modifier = Modifier.padding(horizontal = 29.dp),
//        profileViewModel = ProfileViewModel()
//    ){}
}
