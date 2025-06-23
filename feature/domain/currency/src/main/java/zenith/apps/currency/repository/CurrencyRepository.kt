package zenith.apps.currency.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
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

interface CurrencyRepository {
    suspend fun loadCurrencies()
    val allCurrencies: Flow<List<Currency>>
    val currentExchangePair: Flow<ExchangePair>
    val favouriteExchangePairs: Flow<List<ExchangePair>>
    suspend fun updateExchangePair(exchangePair: ExchangePair)
    suspend fun updateFavouriteState(currency: Currency)
    suspend fun updateFavouriteState(exchangePair: ExchangePair)
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
    override val favouriteExchangePairs: MutableSharedFlow<List<ExchangePair>> = MutableSharedFlow(replay = 1)

    init {
        coroutineScope.launch {
            combine(
                networkCurrencies.distinctUntilChanged(),
                favouritesDataSource.allFavouriteCurrencyCodes.distinctUntilChanged(),
                favouritesDataSource.allFavouriteExchangePairCodes.distinctUntilChanged()
            ) { networkCurrencies, favouriteCurrencyCodes, favouriteExchangePairCodes ->
                val currencies = networkCurrencies.map { networkCurrency ->
                    val isInFavourites = favouriteCurrencyCodes.contains(networkCurrency.code)
                    networkCurrency.toCurrency(isInFavourites)
                }.sortedWith(
                    compareByDescending<Currency> { it.isInFavourite }.thenBy { it.name }
                )
                val favouriteExchangePairs = favouriteExchangePairCodes.mapNotNull { (fromCode, toCode) ->
                    val fromCurrency = currencies.firstOrNull { it.code == fromCode } ?: return@mapNotNull null
                    val toCurrency = currencies.firstOrNull { it.code == toCode } ?: return@mapNotNull null
                    val isInFavourite = favouriteExchangePairCodes.contains(fromCode to toCode)
                    ExchangePair(fromCurrency, toCurrency, isInFavourite)
                }
                currencies to favouriteExchangePairs
            }.collectLatest { (currencies, exchangePairs) ->
                allCurrencies.emit(currencies)
                favouriteExchangePairs.emit(exchangePairs)
            }
        }
    }

    override val currentExchangePair: Flow<ExchangePair> = combine(
        allCurrencies.distinctUntilChanged(),
        settingsDataSource.getString(KEY_EXCHANGE_PAIR_FROM_CURRENCY, "eur").distinctUntilChanged(),
        settingsDataSource.getString(KEY_EXCHANGE_PAIR_TO_CURRENCY, "usd").distinctUntilChanged(),
        favouritesDataSource.allFavouriteExchangePairCodes
    ) { currencies, fromCode, toCode, favouritePairs ->
        val fromCurrency = currencies.first { it.code == fromCode }
        val toCurrency = currencies.first { it.code == toCode }
        val isInFavourite = favouritePairs.contains(fromCurrency.code to toCurrency.code)
        ExchangePair(fromCurrency, toCurrency, isInFavourite)
    }

    override suspend fun updateExchangePair(exchangePair: ExchangePair) {
        settingsDataSource.putString(KEY_EXCHANGE_PAIR_FROM_CURRENCY, exchangePair.fromCurrency.code)
        settingsDataSource.putString(KEY_EXCHANGE_PAIR_TO_CURRENCY, exchangePair.toCurrency.code)
    }

    override suspend fun updateFavouriteState(currency: Currency) {
        favouritesDataSource.updateFavouriteCurrencyState(currency.code, !currency.isInFavourite)
    }

    override suspend fun updateFavouriteState(exchangePair: ExchangePair) {
        favouritesDataSource.updateFavouriteExchangePair(
            exchangePair.fromCurrency.code,
            exchangePair.toCurrency.code,
            !exchangePair.isInFavourite
        )
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