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
import zenith.apps.currency.use_case.GetAllCurrenciesUseCase
import zenith.apps.currency.use_case.GetExchangePairUseCase
import zenith.apps.user_preference.use_case.GetExchangeRateAssessmentUseCase
import javax.inject.Inject

@HiltViewModel
internal class RateViewModel @Inject constructor(
    private val getExchangePairUseCase: GetExchangePairUseCase,
    private val getExchangeRateAssessmentUseCase: GetExchangeRateAssessmentUseCase,
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
    }

    fun updateFromCurrencyInput(input: String) {
        mutableState.update { it.copy(fromCurrencyInput = input) }
    }

    fun updateToCurrencyInput(input: String) {
        mutableState.update { it.copy(toCurrencyInput = input) }
    }
}