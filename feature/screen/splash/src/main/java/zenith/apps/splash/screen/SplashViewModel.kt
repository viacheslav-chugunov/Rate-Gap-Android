package zenith.apps.splash.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import zenith.apps.currency.use_case.LoadCurrenciesUseCase
import javax.inject.Inject

@HiltViewModel
internal class SplashViewModel @Inject constructor(
    private val loadCurrenciesUseCase: LoadCurrenciesUseCase
) : ViewModel() {
    private val mutableState = MutableStateFlow(SplashState())
    val state = mutableState.asStateFlow()

    init {
        loadCurrencies()
    }

    fun loadCurrencies() {
        viewModelScope.launch {
            try {
                mutableState.update { it.copy(isLoading = true) }
                loadCurrenciesUseCase()
                mutableState.update { it.copy(isLoading = false) }
            } catch (e: Throwable) {
                mutableState.update { it.copy(isLoading = false, error = e) }
            }
        }
    }

}