package zenith.apps.rate.screen

import zenith.apps.currency.model.ExchangePair
import zenith.apps.user_preference.model.ExchangeRateAssessment

internal data class RateState(
    val exchangePair: ExchangePair? = null,
    val fromCurrencyInput: String = "",
    val toCurrencyInput: String = "",
    val exchangeRateAssessment: ExchangeRateAssessment = ExchangeRateAssessment.DEFAULT
)