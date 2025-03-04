package school.alt.teacher.main.ui.foundation

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import school.alt.teacher.main.R

@Composable
fun ImageLogo(
    modifier: Modifier =Modifier,
    contentDescription: String = "",
    contentScale: ContentScale = ContentScale.Fit) {
    Image(
        modifier = modifier,
        painter = painterResource(R.drawable.let_teacher_logo),
        contentDescription = contentDescription,
        contentScale = contentScale
    )
}

@Composable
fun ImageMedal(
    modifier: Modifier = Modifier,
    contentDescription: String = "",
    contentScale: ContentScale = ContentScale.Fit,
) {
    Image(
        modifier = modifier,
        painter = painterResource(R.drawable.img_medal),
        contentDescription = contentDescription,
        contentScale = contentScale
    )
}

@Composable
fun ImageCharacter(
    modifier: Modifier = Modifier,
    contentDescription: String = "",
    contentScale: ContentScale = ContentScale.Fit) {
    Image(
        modifier = modifier,
        painter = painterResource(R.drawable.img),
        contentDescription = contentDescription,
        contentScale = contentScale
    )
}