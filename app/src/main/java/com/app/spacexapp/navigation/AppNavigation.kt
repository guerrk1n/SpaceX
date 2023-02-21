package com.app.spacexapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.app.feature.launchpad.detail.LaunchpadDetailScreen
import com.app.feature.rocket.detail.RocketDetailScreen
import com.app.spacexapp.ui.MainScreen

internal sealed class Screen(val route: String) {
    object MainTabs : Screen("mainTabs")
}

private sealed class LeafScreen(val route: String) {

    fun createRoute(root: Screen) = "${root.route}/$route"

    object MainTabs : LeafScreen("mainTabs")
    object RocketDetails : LeafScreen("rocketDetails/{rocketId}") {
        fun createRoute(root: Screen, rocketId: String) = "${root.route}/rocketDetails/$rocketId"
    }

    object LaunchpadDetails : LeafScreen("launchpadDetails/{launchpadId}") {
        fun createRoute(root: Screen, launchpadId: String) = "${root.route}/launchpadDetails/$launchpadId"
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
        addRocketDetail(navController, root)
        addLaunchpadDetail(navController, root)
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
            },
            openLaunchpadDetail = { launchpadId ->
                navController.navigate(LeafScreen.LaunchpadDetails.createRoute(root, launchpadId))
            }
        )
    }
}

private fun NavGraphBuilder.addRocketDetail(navController: NavHostController, root: Screen) {
    composable(
        route = LeafScreen.RocketDetails.createRoute(root),
        arguments = listOf(
            navArgument("rocketId") { type = NavType.StringType }) // TODO replace with constant
    ) {
        RocketDetailScreen(
            navigateUp = navController::navigateUp
        )
    }
}

private fun NavGraphBuilder.addLaunchpadDetail(navController: NavHostController, root: Screen) {
    composable(
        route = LeafScreen.LaunchpadDetails.createRoute(root),
        arguments = listOf(
            navArgument("launchpadId") { type = NavType.StringType }) // TODO replace with constant
    ) {
        LaunchpadDetailScreen(
            navigateUp = navController::navigateUp
        )
    }
}
