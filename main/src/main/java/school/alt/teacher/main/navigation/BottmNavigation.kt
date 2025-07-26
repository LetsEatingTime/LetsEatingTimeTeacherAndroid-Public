package school.alt.teacher.main.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import school.alt.teacher.main.ui.theme.line1
import school.alt.teacher.main.ui.theme.main2

@Composable
fun BottomNavigation(
    modifier: Modifier = Modifier,
    containerColor: Color,
    contentColor: Color,
    indicatorColor: Color,
    navController: NavHostController,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val items = listOf(
        BottomNavItem.Announce,
        BottomNavItem.Home,
        BottomNavItem.Profile
    )

    AnimatedVisibility(
        visible = items.map { it.screensRoute }.contains(currentRoute),
        // 나중에 사라지는거, 나타나는 애니메이션 수정
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        NavigationBar(
            modifier = modifier,
            containerColor = containerColor,
            contentColor = contentColor,
            tonalElevation = 0.dp,
        ) {
            items.forEach { item ->
                NavigationBarItem(
                    selected = currentRoute == item.screensRoute,
                    onClick = {
                        navController.navigate(item.screensRoute) {
                            navController.graph.startDestinationRoute?.let {
                                popUpTo(it) { saveState = true }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    label = {
                        Text(text = stringResource(id = item.title), fontSize = 11.sp)
                    },
                    colors = NavigationBarItemColors(
                        selectedIndicatorColor = indicatorColor,
                        disabledIconColor = line1,
                        disabledTextColor = line1,
                        unselectedTextColor = line1,
                        selectedTextColor = main2,
                        unselectedIconColor = line1,
                        selectedIconColor = main2
                    ),
                    icon = {
                        Column(
                            modifier = Modifier,
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = stringResource(id = item.title),
                                modifier = Modifier.size(32.dp),
                            )
                        }


                    },
                    alwaysShowLabel = false
                )
            }
        }
    }
}
