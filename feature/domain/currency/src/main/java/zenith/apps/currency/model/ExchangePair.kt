package zenith.apps.currency.model

import zenith.apps.core.model.Currency

data class ExchangePair(
    val fromCurrency: Currency,
    val toCurrency: Currency
) {
    val rate: Double = toCurrency.rate / fromCurrency.rate

    fun swap(): ExchangePair = ExchangePair(toCurrency, fromCurrency)

    override fun equals(other: Any?): Boolean =
        other is ExchangePair && other.fromCurrency == fromCurrency && other.toCurrency == toCurrency

    override fun hashCode(): Int =
        fromCurrency.hashCode() + toCurrency.hashCode()

}