package zenith.apps.currency.use_case

import kotlinx.coroutines.flow.Flow
import zenith.apps.currency.model.ExchangePair
import zenith.apps.currency.repository.CurrencyRepository
import javax.inject.Inject

class GetExchangePairUseCase @Inject constructor(
    private val currencyRepository: CurrencyRepository
) {

    operator fun invoke(): Flow<ExchangePair> {
        return currencyRepository.currentExchangePair
    }
}