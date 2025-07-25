package zenith.apps.rategap

import kotlinx.serialization.Serializable

@Serializable
data object SplashDestination

@Serializable
data object RateDestination

@Serializable
data class PickCurrencyDestination(val currencyCode: String)