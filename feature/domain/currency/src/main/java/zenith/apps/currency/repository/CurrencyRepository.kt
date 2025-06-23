package zenith.apps.currency.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import zenith.apps.core.model.Currency
import zenith.apps.core.util.CoroutineDispatchers
import zenith.apps.currency.model.ExchangePair
import zenith.apps.network.data_source.NetworkCurrencyDataSource
import zenith.apps.network.model.NetworkCurrency
import zenith.apps.storage.data_source.FavouritesDataSource
import zenith.apps.storage.data_source.SettingsDataSource
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.coroutineContext

interface CurrencyRepository {
    suspend fun loadCurrencies()
    val allCurrencies: Flow<List<Currency>>
    val currentExchangePair: Flow<ExchangePair>
    suspend fun updateExchangePair(exchangePair: ExchangePair)
    suspend fun updateFavouriteState(currency: Currency)
}

@Singleton
internal class DefaultCurrencyRepository @Inject constructor(
    private val networkCurrencyDataSource: NetworkCurrencyDataSource,
    private val settingsDataSource: SettingsDataSource,
    private val favouritesDataSource: FavouritesDataSource,
    coroutineDispatchers: CoroutineDispatchers
) : CurrencyRepository {
    private val coroutineScope = CoroutineScope(coroutineDispatchers.io)
    private val networkCurrencies: MutableSharedFlow<List<NetworkCurrency>> = MutableSharedFlow(replay = 1)

    override val allCurrencies: MutableSharedFlow<List<Currency>> = MutableSharedFlow(replay = 1)

    init {
        coroutineScope.launch {
            combine(
                networkCurrencies,
                favouritesDataSource.allFavouriteCurrencyCodes().distinctUntilChanged()
            ) { networkCurrencies, favouriteCodes ->
                networkCurrencies.map { networkCurrency ->
                    val isInFavourites = favouriteCodes.contains(networkCurrency.code)
                    networkCurrency.toCurrency(isInFavourites)
                }.sortedWith(
                    compareByDescending<Currency> { it.isInFavourite }.thenBy { it.name }
                )
            }.collectLatest { currencies ->
                allCurrencies.emit(currencies)
            }
        }
    }

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
        settingsDataSource.putString(KEY_EXCHANGE_PAIR_FROM_CURRENCY, exchangePair.fromCurrency.code)
        settingsDataSource.putString(KEY_EXCHANGE_PAIR_TO_CURRENCY, exchangePair.toCurrency.code)
    }

    override suspend fun updateFavouriteState(currency: Currency) {
        favouritesDataSource.updateFavouriteState(currency.code, !currency.isInFavourite)
    }

    override suspend fun loadCurrencies() {
        val allCurrencies = networkCurrencyDataSource.fetchAllCurrencies()
        this.networkCurrencies.emit(allCurrencies)
    }

    companion object {
        private const val KEY_EXCHANGE_PAIR_FROM_CURRENCY = "EXCHANGE_PAIR_FROM_CURRENCY"
        private const val KEY_EXCHANGE_PAIR_TO_CURRENCY = "EXCHANGE_PAIR_TO_CURRENCY"
    }
}