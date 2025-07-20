package zenith.apps.rategap

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import zenith.apps.core.ui.theme.RateGapTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var showSplash = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestNotificationPermission()
        showSplash = savedInstanceState?.getBoolean("showSplash") ?: true
        installSplashScreen().setKeepOnScreenCondition { showSplash }
        enableEdgeToEdge()
        setContent {
            RateGapTheme {
                MainScreen(
                    hideSplash = {
                        showSplash = false
                    }
                )
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("showSplash", showSplash)
    }

    private fun requestNotificationPermission() {
        if (!BuildConfig.DEBUG) return
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return
        requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1)
    }
}