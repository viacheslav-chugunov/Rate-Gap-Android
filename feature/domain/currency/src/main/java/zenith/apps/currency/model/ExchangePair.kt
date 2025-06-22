package zenith.apps.currency.model

import zenith.apps.core.model.Currency

class ExchangePair(
    val fromCurrency: Currency,
    val toCurrency: Currency
) {
    val rate: Double = toCurrency.rate / fromCurrency.rate

    override fun equals(other: Any?): Boolean =
        other is ExchangePair && other.fromCurrency == fromCurrency && other.toCurrency == toCurrency

    override fun hashCode(): Int =
        fromCurrency.hashCode() + toCurrency.hashCode()

}