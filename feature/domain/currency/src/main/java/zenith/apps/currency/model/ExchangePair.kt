package zenith.apps.currency.model

import zenith.apps.core.model.Currency

data class ExchangePair(
    val fromCurrency: Currency,
    val toCurrency: Currency,
    val isInFavourite: Boolean
) {
    val rate: Double = toCurrency.rate / fromCurrency.rate

    fun swap(): ExchangePair = ExchangePair(toCurrency, fromCurrency, false)

    override fun equals(other: Any?): Boolean =
        other is ExchangePair && other.fromCurrency == fromCurrency
                && other.toCurrency == toCurrency && other.isInFavourite == isInFavourite

    override fun hashCode(): Int =
        fromCurrency.hashCode() + toCurrency.hashCode() + isInFavourite.hashCode()

    companion object {
        val UAH_BTC_TEST = ExchangePair(
            fromCurrency = Currency.UAH_TEST,
            toCurrency = Currency.BTC_TEST,
            isInFavourite = false
        )
    }
}