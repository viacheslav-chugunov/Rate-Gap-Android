package zenith.apps.core.model

import zenith.apps.core.R

class Currency(
    val code: String,
    val rate: Double,
    val name: String,
    val icon: Int,
    val isCrypto: Boolean,
    val isInFavourite: Boolean
) {

    override fun equals(other: Any?): Boolean =
        other is Currency && other.code == code && other.isInFavourite == isInFavourite

    override fun hashCode(): Int = code.hashCode() + isInFavourite.hashCode()


    companion object {
        val UAH_TEST = Currency(
            code = "uah",
            rate = 1.0,
            name = "Ukrainian Hryvna",
            icon = R.drawable.ic_uah,
            isCrypto = false,
            isInFavourite = false
        )
        val BTC_TEST = Currency(
            code = "btc",
            rate = 1.25,
            name = "BTC",
            icon = R.drawable.ic_btc,
            isCrypto = true,
            isInFavourite = true
        )
    }
}