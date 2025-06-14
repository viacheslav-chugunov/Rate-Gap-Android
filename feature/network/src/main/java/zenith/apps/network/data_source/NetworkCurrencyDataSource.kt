package zenith.apps.network.data_source

import kotlinx.coroutines.withContext
import zenith.apps.core.model.Currency
import zenith.apps.core.util.CoroutineDispatchers
import zenith.apps.network.api.CurrencyApi
import javax.inject.Inject

interface NetworkCurrencyDataSource {
    suspend fun fetchAllCurrencies(): List<Currency>
}

internal class DefaultNetworkCurrencyDataSource @Inject constructor(
    private val currencyApi: CurrencyApi,
    private val coroutineDispatchers: CoroutineDispatchers
) : NetworkCurrencyDataSource {

    override suspend fun fetchAllCurrencies(): List<Currency> =
        withContext(coroutineDispatchers.io) {
            val response = currencyApi.fetchCurrencies()
            response.currencies.map { dto ->
                Currency(
                    code = dto.code,
                    rate = dto.rate
                )
            }
        }
}