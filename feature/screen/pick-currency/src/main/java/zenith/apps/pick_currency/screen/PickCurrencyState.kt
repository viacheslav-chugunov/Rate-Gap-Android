package zenith.apps.pick_currency.screen

import zenith.apps.core.model.Currency

internal data class PickCurrencyState(
    val currencies: List<Currency> = emptyList(),
    val searchQuery: String = "",
    val currencyPicked: Boolean = false
)