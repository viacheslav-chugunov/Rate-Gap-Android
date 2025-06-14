package zenith.apps.currency.use_case

import zenith.apps.currency.repository.CurrencyRepository
import javax.inject.Inject

class LoadCurrenciesUseCase @Inject constructor(
    private val currencyRepository: CurrencyRepository
) {

    suspend operator fun invoke() {
        currencyRepository.loadCurrencies()
    }

}