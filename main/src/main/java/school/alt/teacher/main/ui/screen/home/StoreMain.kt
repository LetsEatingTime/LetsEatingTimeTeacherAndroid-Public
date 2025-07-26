package school.alt.let.teacher.main.ui.screen.home

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.IconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import school.alt.teacher.main.ui.compose.button.TextButton2
import school.alt.teacher.main.ui.compose.bar.Bar
import school.alt.teacher.main.ui.compose.bar.NavBar
import school.alt.teacher.main.ui.foundation.IcBack
import school.alt.teacher.main.ui.theme.backgroundStrong
import school.alt.teacher.main.ui.theme.black
import school.alt.teacher.main.ui.theme.line1
import school.alt.teacher.main.ui.theme.line2
import school.alt.teacher.main.ui.theme.line3
import school.alt.teacher.main.ui.theme.main1
import school.alt.teacher.main.ui.theme.main3
import school.alt.teacher.main.ui.theme.pretendard
import school.alt.teacher.main.ui.theme.white
import school.alt.teacher.main.viewmodel.store.ProductImageUiState
import school.alt.teacher.main.viewmodel.store.StoreViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun StoreMainScreen(
    modifier: Modifier = Modifier,
    storeViewModel: StoreViewModel,
    onMoveScreen: () -> Unit
) {

    val productionImageList by storeViewModel.productImageList.collectAsState()


    DisposableEffect(Unit) {
        val scope = CoroutineScope(Dispatchers.Default)
        val job = scope.launch {
        }

        onDispose {
            job.cancel()
        }
    }

    Box(
        modifier = Modifier
            .background(color = backgroundStrong)
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
            when (productionImageList) {
                is ProductImageUiState.Success -> {
                    if (!(productionImageList as ProductImageUiState.Success).products[storeViewModel.product.value?.image]?.fileName.isNullOrBlank()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            AsyncImage(
                                modifier = Modifier
                                    .background(color = line2)
                                    .height(230.dp)
                                    .fillMaxWidth(),
                                model = (productionImageList as ProductImageUiState.Success).products[storeViewModel.product.value?.image]?.fileName,
                                contentDescription = ""
                            )
                        }
                    }
                }

                is ProductImageUiState.Error -> {
                    ""
                }
            }
            Column(
                modifier = Modifier
                    .background(white)
                    .padding(horizontal = 17.dp, vertical = 13.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = storeViewModel.product.value?.productName.toString(),
                    color = line1,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = pretendard
                )
                Spacer(modifier = Modifier.padding(top = 3.dp))
                Row() {
                    Text(
                        text = storeViewModel.product.value?.price.toString(),
                        color = black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = pretendard
                    )
                    Text(
                        text = "P",
                        color = main1,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = pretendard
                    )
                }
                Spacer(modifier = Modifier.padding(top = 8.dp))

                HorizontalDivider(modifier = Modifier.background(color = line2))

                Spacer(modifier = Modifier.padding(top = 8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "재고",
                        color = line1,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = pretendard
                    )
                    Text(
                        text = "${storeViewModel.product.value?.stock}개",
                        color = black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = pretendard
                    )
                }

                Spacer(modifier = Modifier.padding(top = 8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "등록일",
                        color = line1,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = pretendard
                    )
                    Text(
                        text = formatDateToYYMMDD(
                            storeViewModel.product.value?.grantTime ?: "2014-12-12 07:07:07"
                        ),
                        color = black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = pretendard
                    )
                }
            }
            Spacer(modifier = Modifier.padding(top = 3.dp))
            Text(
                modifier = Modifier
                    .background(white)
                    .fillMaxWidth()
                    .padding(vertical = 13.dp, horizontal = 17.dp),
                textAlign = TextAlign.Start,
                text = "신청자",
                color = black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = pretendard
            )
            val test = listOf("tttt", "tttt", "tttt", "tttt", "tttt")


            LazyColumn(
                modifier = Modifier.padding(top = 6.dp),
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                itemsIndexed(test) { index, data ->
                    Bar(
                        paddingValues = PaddingValues(horizontal = 18.dp, vertical = 13.dp),
                        startContent = {
                            Row(
                                modifier = Modifier,
                                verticalAlignment = Alignment.Bottom,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = data ?: "",
                                    color = black,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Medium,
                                    fontFamily = pretendard
                                )
                                Text(
                                    text = "",
                                    color = line1,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    fontFamily = pretendard
                                )
                            }
                        },
                        endContext = {
                            Row(
                                modifier = Modifier,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                TextButton2(
                                    text = "승인",
                                    backgroundColor = main3,
                                    paddingValues = PaddingValues(),
                                    textSize = 16.sp
                                ) {

                                }
                            }

                        }
                    ) {

                    }
                }
            }
        }
    }
}

fun formatDateToYYMMDD(dateString: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val outputFormat = SimpleDateFormat("yy.MM.dd", Locale.getDefault())

    val date = inputFormat.parse(dateString)
    return outputFormat.format(date as Date) // 형식화된 문자열 반환
}