package zenith.apps.pick_currency.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import zenith.apps.core.model.Currency
import zenith.apps.currency.use_case.GetExchangePairUseCase
import zenith.apps.currency.use_case.SearchCurrenciesUseCase
import zenith.apps.currency.use_case.UpdateCurrencyFavouriteStateUseCase
import zenith.apps.currency.use_case.UpdateExchangePairUseCase

@HiltViewModel(assistedFactory = PickCurrencyViewModel.Factory::class)
internal class PickCurrencyViewModel @AssistedInject constructor(
    @Assisted private val currencyCode: String,
    searchCurrenciesUseCase: SearchCurrenciesUseCase,
    private val getExchangePairUseCase: GetExchangePairUseCase,
    private val updateExchangePairUseCase: UpdateExchangePairUseCase,
    private val updateCurrencyFavouriteStateUseCase: UpdateCurrencyFavouriteStateUseCase
) : ViewModel() {
    private val mutableState = MutableStateFlow(PickCurrencyState())
    val state = mutableState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                searchCurrenciesUseCase(mutableState.map { it.searchQuery }.distinctUntilChanged()),
                mutableState.map { it.enableCurrenciesFilter }.distinctUntilChanged(),
                mutableState.map { it.enableCryptoFilter }.distinctUntilChanged(),
                mutableState.map { it.enableFavouriteFilter }.distinctUntilChanged()
            ) { currencies, enableCurrenciesFilter, enableCryptoFilter, enableFavouriteFilter ->
                currencies.filter { currency ->
                    if (!currency.isCrypto && !enableCurrenciesFilter) return@filter false
                    if (currency.isCrypto && !enableCryptoFilter) return@filter false
                    if (!currency.isInFavourite && enableFavouriteFilter) return@filter false
                    true
                }
            }.collectLatest { currencies ->
                mutableState.update {
                    it.copy(currencies = currencies)
                }
            }
        }
        viewModelScope.launch {
            getExchangePairUseCase().collectLatest { exchangePair ->

            }
        }
    }

    fun updateSearchQuery(query: String) {
        mutableState.update { it.copy(searchQuery = query) }
    }

    fun updateCurrency(currency: Currency) {
        viewModelScope.launch {
            val exchangePair = getExchangePairUseCase().first()
            if (exchangePair.toCurrency.code == currencyCode) {
                val newExchangePair = exchangePair.copy(toCurrency = currency)
                updateExchangePairUseCase(newExchangePair)
                mutableState.update { it.copy(currencyPicked = true) }
            } else if (exchangePair.fromCurrency.code == currencyCode) {
                val newExchangePair = exchangePair.copy(fromCurrency = currency)
                updateExchangePairUseCase(newExchangePair)
                mutableState.update { it.copy(currencyPicked = true) }
            }
        }
    }

    fun updateCurrencyFilter(enable: Boolean) {
        mutableState.update { it.copy(enableCurrenciesFilter = enable) }
    }

    fun updateCryptoFilter(enable: Boolean) {
        mutableState.update { it.copy(enableCryptoFilter = enable) }
    }

    fun updateFavouriteFilter(enable: Boolean) {
        mutableState.update { it.copy(enableFavouriteFilter = enable) }
    }

    fun updateFavouriteState(currency: Currency) {
        viewModelScope.launch {
            updateCurrencyFavouriteStateUseCase(currency)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(currencyCode: String): PickCurrencyViewModel
    }
}