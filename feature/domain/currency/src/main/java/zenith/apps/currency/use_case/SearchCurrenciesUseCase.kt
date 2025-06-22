package zenith.apps.currency.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import zenith.apps.core.model.Currency
import zenith.apps.currency.repository.CurrencyRepository
import javax.inject.Inject

class SearchCurrenciesUseCase @Inject constructor(
    private val currencyRepository: CurrencyRepository
) {

    operator fun invoke(query: String): Flow<List<Currency>> {
        val formattedQuery = query.trim().lowercase()
        return currencyRepository.allCurrencies.map { currencies ->
            currencies.filter { currency ->
                currency.name.contains(query, ignoreCase = true) ||
                        currency.code.contains(query, ignoreCase = true)
            }
        }
    }

}