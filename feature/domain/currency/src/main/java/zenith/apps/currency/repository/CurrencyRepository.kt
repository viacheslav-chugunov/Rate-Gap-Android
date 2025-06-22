package zenith.apps.currency.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import zenith.apps.core.model.Currency
import zenith.apps.currency.model.ExchangePair
import zenith.apps.network.data_source.NetworkCurrencyDataSource
import zenith.apps.storage.data_source.SettingsDataSource
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.coroutineContext

interface CurrencyRepository {
    suspend fun loadCurrencies()
    val allCurrencies: Flow<List<Currency>>
    val currentExchangePair: Flow<ExchangePair>
    suspend fun updateExchangePair(exchangePair: ExchangePair)
}

@Singleton
internal class DefaultCurrencyRepository @Inject constructor(
    private val networkCurrencyDataSource: NetworkCurrencyDataSource,
    private val settingsDataSource: SettingsDataSource
) : CurrencyRepository {
    override val allCurrencies: MutableSharedFlow<List<Currency>> = MutableSharedFlow(replay = 1)

    override val currentExchangePair: Flow<ExchangePair> = combine(
        allCurrencies,
        settingsDataSource.getString(KEY_EXCHANGE_PAIR_FROM_CURRENCY, "eur"),
        settingsDataSource.getString(KEY_EXCHANGE_PAIR_TO_CURRENCY, "usd")
    ) { currencies, fromCode, toCode ->
        val fromCurrency = currencies.first { it.code == fromCode }
        val toCurrency = currencies.first { it.code == toCode }
        ExchangePair(fromCurrency, toCurrency)
    }

    override suspend fun updateExchangePair(exchangePair: ExchangePair) {

    }

    override suspend fun loadCurrencies() {
        val allCurrencies = networkCurrencyDataSource.fetchAllCurrencies().sortedBy { it.name }
        this.allCurrencies.emit(allCurrencies)
    }

    companion object {
        private const val KEY_EXCHANGE_PAIR_FROM_CURRENCY = "EXCHANGE_PAIR_FROM_CURRENCY"
        private const val KEY_EXCHANGE_PAIR_TO_CURRENCY = "EXCHANGE_PAIR_TO_CURRENCY"
    }
}