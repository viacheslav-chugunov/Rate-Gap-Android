package zenith.apps.network.data_source

import kotlinx.coroutines.withContext
import zenith.apps.core.R
import zenith.apps.core.model.Currency
import zenith.apps.core.util.CoroutineDispatchers
import zenith.apps.core.util.ResourceProvider
import zenith.apps.network.api.CurrencyApi
import javax.inject.Inject

interface NetworkCurrencyDataSource {
    suspend fun fetchAllCurrencies(): List<Currency>
}

internal class DefaultNetworkCurrencyDataSource @Inject constructor(
    private val currencyApi: CurrencyApi,
    private val coroutineDispatchers: CoroutineDispatchers,
    private val resourceProvider: ResourceProvider
) : NetworkCurrencyDataSource {

    override suspend fun fetchAllCurrencies(): List<Currency> =
        withContext(coroutineDispatchers.io) {
            val response = currencyApi.fetchCurrencies()
            val codeToName = mapOf(
                "uah" to resourceProvider.getString(R.string.uah_name),
                "usd" to resourceProvider.getString(R.string.usd_name),
                "eur" to resourceProvider.getString(R.string.eur_name),
                "btc" to resourceProvider.getString(R.string.btc_name),
                "eth" to resourceProvider.getString(R.string.eth_name),
                "bnb" to resourceProvider.getString(R.string.bnb_name),
                "usdt" to resourceProvider.getString(R.string.usdt_name),
                "xrp" to resourceProvider.getString(R.string.xrp_name)
            )
            val codeToIcon = mapOf(
                "uah" to R.drawable.ic_ua_flag,
                "usd" to R.drawable.ic_us_flag,
                "eur" to R.drawable.ic_eu_flag,
                "btc" to R.drawable.ic_btc,
                "eth" to R.drawable.ic_eth,
                "bnb" to R.drawable.ic_bnb,
                "usdt" to R.drawable.ic_usdt,
                "xrp" to R.drawable.ic_xrp
            )
            response.rates.mapNotNull { (code, rate) ->
                Currency(
                    code = code,
                    rate = rate,
                    name = codeToName[code] ?: return@mapNotNull null,
                    icon = codeToIcon[code] ?: return@mapNotNull null
                )
            }
        }
}