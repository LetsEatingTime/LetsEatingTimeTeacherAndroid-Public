package school.alt.teacher.main.ui.foundation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import school.alt.teacher.main.ui.theme.black
import school.alt.teacher.main.ui.theme.white
import school.alt.teacher.main.R

@Composable
fun IcAddPeople(
    modifier: Modifier = Modifier,
    contentDescription: String?,
    tint: Color = black,
    size: Dp = 32.dp
) {
    Icon(
        modifier = modifier.size(size),
        painter = painterResource(id = R.drawable.ic_addpeople),
        contentDescription = contentDescription,
        tint = tint
    )
}

@Composable
fun IcAddPhoto(
    modifier: Modifier = Modifier,
    contentDescription: String?,
    tint: Color = black,
    size: Dp = 32.dp
) {
    Icon(
        modifier = modifier.size(size),
        painter = painterResource(id = R.drawable.ic_add_photo),
        contentDescription = contentDescription,
        tint = tint
    )
}

@Composable
fun IcGallery(
    modifier: Modifier = Modifier,
    contentDescription: String?,
    tint: Color = black,
    size: Dp = 32.dp
) {
    Icon(
        modifier = modifier.size(size),
        painter = painterResource(id = R.drawable.ic_gallery),
        contentDescription = contentDescription,
        tint = tint
    )
}

@Composable
fun IcAdd(
    modifier: Modifier = Modifier,
    contentDescription: String?,
    tint: Color = black,
    size: Dp = 32.dp
) {
    Icon(
        modifier = modifier.size(size),
        painter = painterResource(id = R.drawable.ic_add),
        contentDescription = contentDescription,
        tint = tint
    )
}

@Composable
fun IcArrowUp(
    modifier: Modifier = Modifier,
    contentDescription: String?,
    tint: Color = black,
    size: Dp = 32.dp
) {
    Icon(
        modifier = modifier.size(size),
        painter = painterResource(id = R.drawable.ic_arrow_up),
        contentDescription = contentDescription,
        tint = tint
    )
}

@Composable
fun IcBack(
    modifier: Modifier = Modifier,
    contentDescription: String?,
    tint: Color = black,
    size: Dp = 32.dp
) {
    Icon(
        modifier = modifier.size(size),
        painter = painterResource(id = R.drawable.ic_back),
        contentDescription = contentDescription,
        tint = tint
    )
}

@Composable
fun IcDelete(
    modifier: Modifier = Modifier,
    contentDescription: String?,
    tint: Color = black,
    size: Dp = 32.dp
) {
    Icon(
        modifier = modifier.size(size),
        painter = painterResource(id = R.drawable.ic_delete),
        contentDescription = contentDescription,
        tint = tint
    )
}

@Composable
fun IcEnter(
    modifier: Modifier = Modifier,
    contentDescription: String?,
    tint: Color = black,
    size: Dp = 32.dp
) {
    Icon(
        modifier = modifier.size(size),
        painter = painterResource(id = R.drawable.ic_enter),
        contentDescription = contentDescription,
        tint = tint
    )
}

@Composable
fun IcShadowMore(
    modifier: Modifier = Modifier,
    contentDescription: String?,
    tint: Color = white,
    size: Dp = 32.dp
) {
    Box(modifier = modifier.wrapContentSize()) {
        Icon(
            painter = painterResource(id = R.drawable.ic_more),
            contentDescription = null,
            Modifier
                .size(size)
                .offset(x = (0).dp, y = (1).dp)
                .blur(1.dp),
            tint = Color.Black,
        )
        Icon(
            modifier = Modifier.size(size),
            painter = painterResource(id = R.drawable.ic_more),
            contentDescription = contentDescription,
            tint = tint
        )
    }
}

@Composable
fun IcMore(
    modifier: Modifier = Modifier,
    contentDescription: String?,
    tint: Color = white,
    size: Dp = 32.dp
) {
    Icon(
        modifier = modifier.size(size),
        painter = painterResource(id = R.drawable.ic_more),
        contentDescription = contentDescription,
        tint = tint
    )
}

@Composable
fun IcBreakfast(
    modifier: Modifier = Modifier,
    contentDescription: String?,
    size: Dp = 32.dp
) {
    Icon(
        modifier = modifier.size(size),
        painter = painterResource(id = R.drawable.ic_breakfast),
        contentDescription = contentDescription
    )
}

@Composable
fun IcLunch(
    modifier: Modifier = Modifier,
    contentDescription: String?,
    size: Dp = 32.dp
) {
    Icon(
        modifier = modifier.size(size),
        painter = painterResource(id = R.drawable.ic_lunch),
        contentDescription = contentDescription
    )
}

@Composable
fun IcDinner(
    modifier: Modifier = Modifier,
    contentDescription: String?,
    size: Dp = 32.dp
) {
    Icon(
        modifier = modifier.size(size),
        painter = painterResource(id = R.drawable.ic_dinner),
        contentDescription = contentDescription
    )
}

@Composable
fun ImgBreakfast(
    modifier: Modifier = Modifier,
    contentDescription: String?,
    size: Dp = 32.dp
) {
    Image(
        modifier = modifier.size(size),
        painter = painterResource(id = R.drawable.img_breakfast),
        contentDescription = contentDescription
    )
}

@Composable
fun ImgLunch(
    modifier: Modifier = Modifier,
    contentDescription: String?,
    size: Dp = 32.dp
) {
    Image(
        modifier = modifier.size(size),
        painter = painterResource(id = R.drawable.img_lunch),
        contentDescription = contentDescription
    )
}

@Composable
fun ImgDinner(
    modifier: Modifier = Modifier,
    contentDescription: String?,
    size: Dp = 32.dp
) {
    Image(
        modifier = modifier.size(size),
        painter = painterResource(id = R.drawable.img_dinner),
        contentDescription = contentDescription
    )
}

@Composable
fun IcSearch(
    modifier: Modifier = Modifier,
    contentDescription: String?,
    tint: Color = black,
    size: Dp = 32.dp
) {
    Icon(
        modifier = modifier.size(size),
        painter = painterResource(id = R.drawable.ic_search),
        contentDescription = contentDescription,
        tint = tint
    )
}

@Composable
fun IcHome(
    modifier: Modifier = Modifier,
    contentDescription: String?,
    tint: Color = black,
    size: Dp = 32.dp
) {
    Icon(
        modifier = modifier.size(size),
        painter = painterResource(id = R.drawable.ic_hoem),
        contentDescription = contentDescription,
        tint = tint
    )
}

@Composable
fun IcScan(
    modifier: Modifier = Modifier,
    contentDescription: String?,
    tint: Color = black,
    size: Dp = 32.dp
) {
    Icon(
        modifier = modifier.size(size),
        painter = painterResource(id = R.drawable.ic_scan),
        contentDescription = contentDescription,
        tint = tint
    )
}

@Composable
fun IcProfile(
    modifier: Modifier = Modifier,
    contentDescription: String?,
    tint: Color = black,
    size: Dp = 32.dp
) {
    Icon(
        modifier = modifier.size(size),
        painter = painterResource(id = R.drawable.ic_profile),
        contentDescription = contentDescription,
        tint = tint
    )
}

@Composable
fun IcGroup(
    modifier: Modifier = Modifier,
    contentDescription: String?,
    tint: Color = black,
    size: Dp = 32.dp
) {
    Icon(
        modifier = modifier.size(size),
        painter = painterResource(id = R.drawable.ic_group),
        contentDescription = contentDescription,
        tint = tint
    )
}