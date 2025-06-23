package zenith.apps.rate.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import zenith.apps.currency.model.ExchangePair
import zenith.apps.currency.use_case.GetAllCurrenciesUseCase
import zenith.apps.currency.use_case.GetExchangePairUseCase
import zenith.apps.currency.use_case.GetFavouriteExchangePairsUseCase
import zenith.apps.currency.use_case.UpdateExchangePairFavouriteStateUseCase
import zenith.apps.currency.use_case.UpdateExchangePairUseCase
import zenith.apps.user_preference.use_case.GetExchangeRateAssessmentUseCase
import javax.inject.Inject

@HiltViewModel
internal class RateViewModel @Inject constructor(
    private val getExchangePairUseCase: GetExchangePairUseCase,
    private val getExchangeRateAssessmentUseCase: GetExchangeRateAssessmentUseCase,
    private val getFavouriteExchangePairsUseCase: GetFavouriteExchangePairsUseCase,
    private val updateExchangePairUseCase: UpdateExchangePairUseCase,
    private val updateExchangePairFavouriteStateUseCase: UpdateExchangePairFavouriteStateUseCase
) : ViewModel() {
    private val mutableState = MutableStateFlow(RateState())
    val state = mutableState.asStateFlow()

    init {
        viewModelScope.launch {
            getExchangePairUseCase().collectLatest { exchangePair ->
                mutableState.update { it.copy(exchangePair = exchangePair) }
            }
        }
        viewModelScope.launch {
            getExchangeRateAssessmentUseCase().collectLatest { exchangeRateAssessment ->
                mutableState.update { it.copy(exchangeRateAssessment = exchangeRateAssessment) }
            }
        }
        viewModelScope.launch {
            getFavouriteExchangePairsUseCase().collectLatest { favouriteExchangePairs ->
                mutableState.update { it.copy(favouriteExchangePairs = favouriteExchangePairs) }
            }
        }
    }

    fun updateFromCurrencyInput(input: String) {
        mutableState.update { it.copy(fromCurrencyInput = input) }
    }

    fun updateToCurrencyInput(input: String) {
        mutableState.update { it.copy(toCurrencyInput = input) }
    }

    fun swapExchangePair() {
        viewModelScope.launch {
            val exchangePair = mutableState.value.exchangePair ?: return@launch
            updateExchangePairUseCase(exchangePair.swap())
        }
    }

    fun updateFavouriteExchangePairState() {
        viewModelScope.launch {
            val exchangePair = mutableState.value.exchangePair ?: return@launch
            updateExchangePairFavouriteStateUseCase(exchangePair)
        }
    }

    fun pickExchangePair(exchangePair: ExchangePair) {
        viewModelScope.launch {
            updateExchangePairUseCase(exchangePair)
        }
    }
}