package zenith.apps.currency.use_case

import zenith.apps.core.model.Currency
import zenith.apps.currency.repository.CurrencyRepository
import javax.inject.Inject

class UpdateCurrencyFavouriteStateUseCase @Inject constructor(
    private val currencyRepository: CurrencyRepository
) {

    suspend operator fun invoke(currency: Currency) {
        currencyRepository.updateFavouriteState(currency)
    }

}