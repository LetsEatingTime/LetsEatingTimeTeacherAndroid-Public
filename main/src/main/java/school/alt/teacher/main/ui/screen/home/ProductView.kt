package school.alt.teacher.main.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import school.alt.teacher.main.ui.compose.bar.NavBar
import school.alt.teacher.main.ui.foundation.IcBack
import school.alt.teacher.main.ui.theme.black
import school.alt.teacher.main.ui.theme.line2
import school.alt.teacher.main.ui.theme.line3
import school.alt.teacher.main.ui.theme.main1
import school.alt.teacher.main.ui.theme.pretendard
import school.alt.teacher.main.ui.theme.white
import school.alt.teacher.main.viewmodel.store.StoreViewModel

@Composable
fun ProductScreen(
    modifier: Modifier = Modifier,
    storeViewModel: StoreViewModel,
    onMoveScreen: () -> Unit
) {
    val isImageSelect = remember {
        mutableStateOf(true)
    }

    val prizeNameText = remember {
        mutableStateOf("")
    }

    val prizeCostText = remember {
        mutableStateOf("")
    }

    val imageData = remember {
        mutableStateOf("")
    }
    Box(
        modifier = Modifier
            .background(color = white)
            .fillMaxSize()
    ) {
        Column {
            NavBar(
                modifier = Modifier.fillMaxWidth(),
                padding = PaddingValues(3.dp),
                startContent = {
                    IconButton(modifier = it, onClick = {
                        onMoveScreen()
                    }) {
                        IcBack(
                            contentDescription = "",
                            tint = line3
                        )
                    }
                },
                isClickable = false
            )
            Box(modifier = Modifier.fillMaxWidth()) {
                AsyncImage(
                    modifier = Modifier
                        .background(color = line2)
                        .height(230.dp)
                        .fillMaxWidth(),
                    model = imageData.value,
                    contentDescription = ""
                )
            }
            Column(
                Modifier
                    .padding(horizontal = 27.dp, vertical = 36.dp)
                    .padding(bottom = 26.dp)
                    .fillMaxWidth(),
            ) {
                Text(
                    text = "a",
                    color = black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = pretendard
                )
                Spacer(modifier = Modifier.padding(top = 13.dp))
                Row() {
                    Text(
                        text = "0",
                        color = black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = pretendard
                    )
                    Text(
                        text = "P",
                        color = main1,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = pretendard
                    )
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun ProductScreen() {
//    ProductScreen(
//        modifier = Modifier.padding(horizontal = 18.dp),
////        storeViewModel = StoreViewModel()
//    ){}
//}