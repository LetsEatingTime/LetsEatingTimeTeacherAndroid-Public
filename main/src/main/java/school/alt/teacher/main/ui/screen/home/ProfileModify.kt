package school.alt.teacher.main.ui.screen.home

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import school.alt.teacher.main.navigation.ScreenNavigate
import school.alt.teacher.main.ui.compose.button.TextButton2
import school.alt.teacher.main.ui.compose.textfield.TextFieldBorder
import school.alt.teacher.main.ui.theme.backgroundNormal
import school.alt.teacher.main.ui.theme.backgroundStrong
import school.alt.teacher.main.ui.theme.black
import school.alt.teacher.main.ui.theme.line1
import school.alt.teacher.main.ui.theme.line3
import school.alt.teacher.main.ui.theme.main3
import school.alt.teacher.main.ui.theme.pretendard
import school.alt.teacher.main.ui.theme.warning
import school.alt.teacher.main.viewmodel.profile.ProfileViewModel
import school.alt.teacher.main.viewmodel.profile.UserImageUiState
import school.alt.teacher.main.viewmodel.profile.UserUiState
import school.alt.teacher.network.dto.EditUserRequest

@Composable
fun ProfileModifyScreen(
    modifier: Modifier = Modifier,
    profileViewModel: ProfileViewModel,
    onMoveScreen: (String) -> Unit
) {
    val userUiState by profileViewModel.userUiState.collectAsState()
    val userImageUrlUiState by profileViewModel.userImageUiState.collectAsState()

    val profileUrl: MutableState<String> = rememberSaveable {
        mutableStateOf(
            when (userImageUrlUiState) {
                is UserImageUiState.Success -> {
                    (userImageUrlUiState as UserImageUiState.Success).imageUrl
                }

                is UserImageUiState.Error -> {
                    ""
                }
            }
        )
    }

    val getImage = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            if (uri != null) {
                profileUrl.value = uri.toString()
            }
        }
    )

    var nameText by rememberSaveable {
        mutableStateOf(
            when (userUiState) {
                is UserUiState.Success -> {
                    (userUiState as UserUiState.Success).userInfo.user.name
                }

                is UserUiState.Error -> {
                    ""
                }
            } ?: ""
        )
    }

    Box(
        modifier = Modifier
            .background(color = backgroundStrong)
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
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
                label = "이름을 입력 해 주세요.",
                onValueChange = { nameText = it }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 50.dp)
                .padding(horizontal = 18.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton2(
                text = "수정 취소",
                backgroundColor = warning,
                textSize = 16.sp,
                paddingValues = PaddingValues(horizontal = 55.dp, vertical = 13.dp),
            ) {
                onMoveScreen(ScreenNavigate.PROFILE.name)
            }
            TextButton2(
                text = "수정 완료",
                backgroundColor = line3,
                textSize = 16.sp,
                paddingValues = PaddingValues(horizontal = 55.dp, vertical = 13.dp),
            ) {
                if (nameText.isNotBlank()) {
                    profileViewModel.modifyUser(
                        EditUserRequest(
                            name = nameText,
                            id = when (userUiState) {
                                is UserUiState.Success -> {
                                    (userUiState as UserUiState.Success).userInfo.user.id
                                }

                                is UserUiState.Error -> {
                                    ""
                                }
                            },
                            idx = null
                        )
                    ) {}
                }
                onMoveScreen(ScreenNavigate.PROFILE.name)
            }
        }
    }
}