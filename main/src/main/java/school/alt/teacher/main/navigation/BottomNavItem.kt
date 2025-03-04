package school.alt.teacher.main.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import school.alt.teacher.main.R

sealed class BottomNavItem(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val screensRoute: String
) {
    data object Announce :
        BottomNavItem(R.string.announce, R.drawable.ic_group, ScreenNavigate.STUDENT.name)

    data object Home : BottomNavItem(R.string.home, R.drawable.ic_hoem, ScreenNavigate.HOME.name)
    data object Profile :
        BottomNavItem(R.string.profile, R.drawable.ic_profile, ScreenNavigate.PROFILE.name)

}
