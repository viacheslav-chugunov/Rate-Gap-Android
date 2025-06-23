package zenith.apps.currency.use_case

import zenith.apps.currency.model.ExchangePair
import zenith.apps.currency.repository.CurrencyRepository
import javax.inject.Inject

class UpdateExchangePairFavouriteStateUseCase @Inject constructor(
    private val currencyRepository: CurrencyRepository
) {

    suspend operator fun invoke(exchangePair: ExchangePair) {
        currencyRepository.updateFavouriteState(exchangePair)
    }
}