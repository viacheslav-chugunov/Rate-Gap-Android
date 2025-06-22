package zenith.apps.rategap

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
                pickFromCurrency = {
                    navController.navigate(PickCurrencyDestination) {
                        launchSingleTop = true
                    }
                },
                pickToCurrency = {
                    navController.navigate(PickCurrencyDestination) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable<PickCurrencyDestination> {

        }
    }
}