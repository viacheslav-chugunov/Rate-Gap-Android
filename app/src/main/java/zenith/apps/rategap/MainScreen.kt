package zenith.apps.rategap

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import zenith.apps.pick_currency.screen.PickCurrencyScreen
import zenith.apps.rate.screen.RateScreen
import zenith.apps.splash.screen.SplashScreen

@Composable
fun MainScreen(
    hideSplash: () -> Unit
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = SplashDestination) {
        composable<SplashDestination> {
            SplashScreen(
                hideSplash = hideSplash,
                openRateScreen = {
                    navController.navigate(RateDestination) {
                        popUpTo(SplashDestination) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<RateDestination> {
            RateScreen(
                pickCurrency = { currency ->
                    navController.navigate(PickCurrencyDestination(currency.code)) {
                        launchSingleTop = true
                    }
                },
            )
        }
        composable<PickCurrencyDestination> {
            val route = it.toRoute<PickCurrencyDestination>()
            PickCurrencyScreen(
                currencyCode = route.currencyCode,
                navigateBack = {
                    navController.popBackStack(RateDestination, false)
                }
            )
        }
    }
}