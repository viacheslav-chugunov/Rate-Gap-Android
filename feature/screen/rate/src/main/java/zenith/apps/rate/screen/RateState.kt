package zenith.apps.rate.screen

import zenith.apps.core.extension.asPrice
import zenith.apps.currency.model.ExchangePair
import zenith.apps.user_preference.model.ExchangeRateAssessment

internal data class RateState(
    val exchangePair: ExchangePair? = null,
    val fromCurrencyInput: String = "",
    val toCurrencyInput: String = "",
    val exchangeRateAssessment: ExchangeRateAssessment = ExchangeRateAssessment.DEFAULT
) {
    val fromCurrencyHint: String = when {
        fromCurrencyInput.isEmpty() && toCurrencyInput.isEmpty() -> "0"
        else -> {
            val toValue = toCurrencyInput.toDoubleOrNull()
            val exchangePair = exchangePair
            if (toValue == null || exchangePair == null) {
                "0"
            } else {
                (toValue / exchangePair.rate).asPrice
            }
        }
    }

    val toCurrencyHint: String = when {
        fromCurrencyInput.isEmpty() && toCurrencyInput.isEmpty() -> "0"
        else -> {
            val fromValue = fromCurrencyInput.toDoubleOrNull()
            val exchangePair = exchangePair
            if (fromValue == null || exchangePair == null) {
                "0"
            } else {
                (fromValue * exchangePair.rate).asPrice
            }
        }
    }

}