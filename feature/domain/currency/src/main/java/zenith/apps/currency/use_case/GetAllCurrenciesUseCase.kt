package zenith.apps.currency.use_case

import kotlinx.coroutines.flow.Flow
import zenith.apps.core.model.Currency
import zenith.apps.currency.repository.CurrencyRepository
import javax.inject.Inject

class GetAllCurrenciesUseCase @Inject constructor(
    private val currencyRepository: CurrencyRepository
) {

    operator fun invoke(): Flow<List<Currency>> {
        return currencyRepository.allCurrencies
    }

}