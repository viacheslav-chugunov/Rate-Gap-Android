package zenith.apps.currency.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import zenith.apps.core.model.Currency
import zenith.apps.currency.repository.CurrencyRepository
import javax.inject.Inject

class SearchCurrenciesUseCase @Inject constructor(
    private val currencyRepository: CurrencyRepository
) {
    operator fun invoke(query: Flow<String>): Flow<List<Currency>> {
        return combine(query, currencyRepository.allCurrencies) { query, currencies ->
            val formattedQuery = query.trim().lowercase()
            if (formattedQuery.isEmpty()) {
                currencies
            } else {
                currencies.filter { currency ->
                    currency.name.contains(formattedQuery, ignoreCase = true) ||
                            currency.code.contains(formattedQuery, ignoreCase = true)
                }
            }
        }
    }
}