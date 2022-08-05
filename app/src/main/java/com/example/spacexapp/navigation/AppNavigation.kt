package com.example.spacexapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.spacexapp.ui.main.MainScreen
import com.example.spacexapp.ui.screens.details.rocket.RocketDetailScreen

internal sealed class Screen(val route: String) {
    object MainTabs : Screen("mainTabs")
}

private sealed class LeafScreen(val route: String) {

    fun createRoute(root: Screen) = "${root.route}/$route"

    object MainTabs : LeafScreen("mainTabs")
    object RocketDetails : LeafScreen("rocketDetails/{rocketId}") {
        fun createRoute(root: Screen, rocketId: String) = "${root.route}/rocketDetails/$rocketId"
    }

}

@Composable
internal fun AppNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.MainTabs.route,
        modifier = modifier,
    ) {
        addMainTabsTopLevel(navController, Screen.MainTabs)
    }
}

private fun NavGraphBuilder.addMainTabsTopLevel(
    navController: NavHostController,
    root: Screen,
) {
    navigation(
        route = Screen.MainTabs.route,
        startDestination = LeafScreen.MainTabs.createRoute(Screen.MainTabs)
    ) {
        addMainTabs(navController, root)
        addDetail(navController, root)
    }
}

private fun NavGraphBuilder.addMainTabs(
    navController: NavHostController,
    root: Screen,
) {
    composable(route = LeafScreen.MainTabs.createRoute(root)) {
        MainScreen(
            openRocketDetail = { rocketId ->
                navController.navigate(LeafScreen.RocketDetails.createRoute(root, rocketId))
            }
        )
    }
}

private fun NavGraphBuilder.addDetail(navController: NavHostController, root: Screen) {
    composable(
        route = LeafScreen.RocketDetails.createRoute(root),
        arguments = listOf(
            navArgument("rocketId") {
                type = NavType.StringType
            })
    ) {
        val rocketId = it.arguments?.getString("rocketId")!!
        RocketDetailScreen(
            navigateUp = navController::navigateUp,
            rocketId = rocketId
        )
    }
}
