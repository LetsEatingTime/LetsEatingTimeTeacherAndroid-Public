package school.alt.teacher.main.ui.compose.prize

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import school.alt.teacher.main.R
import school.alt.teacher.main.ui.foundation.IcShadowMore
import school.alt.teacher.main.ui.foundation.ImgDinner
import school.alt.teacher.main.ui.theme.black
import school.alt.teacher.main.ui.theme.line1
import school.alt.teacher.main.ui.theme.main1
import school.alt.teacher.main.ui.theme.pretendard
import school.alt.teacher.main.ui.theme.white

@Composable
fun PrizeItem(
    modifier: Modifier = Modifier,
    prizeTitle: String = "N/A",
    prizePrice: String = "0",
    prizeUrl: String? = null,
    clickMore : () -> Unit
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(white)
            .padding(start = 16.dp, bottom = 13.dp)
    ) {
        Column(
            modifier = Modifier,
        ) {
            Box(modifier = Modifier) {
                if(prizeUrl.isNullOrEmpty()) {
                    AsyncImage(
                        modifier = Modifier.size(100.dp),
                        model = prizeUrl,
                        contentDescription = "",
                    )
                }else{
                    ImgDinner(
                        modifier = Modifier.size(100.dp),
                        contentDescription = "",
                    )
                }
                IcShadowMore(
                    size = 25.dp,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .clickable {
                            clickMore()
                        },
                    contentDescription = "",
                    tint = white
                )

            }
            Spacer(modifier = Modifier.padding(top = 4.dp))
            Text(
                text = prizeTitle,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = pretendard,
                color = line1
            )
            Text(text = buildAnnotatedString {
                pushStyle(
                    style = SpanStyle(
                        fontFamily = pretendard,
                        fontWeight = FontWeight.Medium,
                        color = black,
                        fontSize = 12.sp
                    )
                )
                append(
                    if (prizePrice != "0" && prizePrice.isNotEmpty()) {
                        prizePrice
                    } else {
                        "무료"
                    }
                )
                pop()
                pushStyle(
                    style = SpanStyle(
                        fontFamily = pretendard,
                        fontWeight = FontWeight.Bold,
                        color = main1,
                        fontSize = 12.sp
                    )
                )
                if (prizePrice != "0" && prizePrice.isNotEmpty()) {
                    append("P")
                }
                pop()
            })
        }
    }
}

@Composable
@Preview(showBackground = true)
fun TestPrize() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.DarkGray)
    ) {
        PrizeItem() {}
    }
}

