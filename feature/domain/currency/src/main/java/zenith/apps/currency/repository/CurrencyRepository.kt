package zenith.apps.currency.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emitAll
import zenith.apps.core.model.Currency
import zenith.apps.network.data_source.NetworkCurrencyDataSource
import javax.inject.Inject
import javax.inject.Singleton

interface CurrencyRepository {
    suspend fun loadCurrencies()
    val allCurrencies: Flow<List<Currency>>

}

@Singleton
internal class DefaultCurrencyRepository @Inject constructor(
    private val networkCurrencyDataSource: NetworkCurrencyDataSource
) : CurrencyRepository {
    override val allCurrencies: MutableStateFlow<List<Currency>> = MutableStateFlow(emptyList())

    override suspend fun loadCurrencies() {
        allCurrencies.emit(networkCurrencyDataSource.fetchAllCurrencies())
    }
}