package zenith.apps.splash.screen

internal data class SplashState(
    val isLoading: Boolean = true,
    val error: Throwable? = null,
) {
    val isDataLoaded = !isLoading && error == null
}