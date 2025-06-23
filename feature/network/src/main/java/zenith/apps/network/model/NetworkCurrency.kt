package zenith.apps.network.model

import zenith.apps.core.model.Currency

class NetworkCurrency(
    val code: String,
    val rate: Double,
    val name: String,
    val icon: Int,
    val isCrypto: Boolean
) {
    override fun equals(other: Any?): Boolean =
        other is Currency && other.code == code

    override fun hashCode(): Int = code.hashCode()

    fun toCurrency(isInFavourite: Boolean) = Currency(
        code = code,
        rate = rate,
        name = name,
        icon = icon,
        isCrypto = isCrypto,
        isInFavourite = isInFavourite
    )
}