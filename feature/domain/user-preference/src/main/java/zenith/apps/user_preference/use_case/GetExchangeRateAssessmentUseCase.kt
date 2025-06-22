package zenith.apps.user_preference.use_case

import kotlinx.coroutines.flow.Flow
import zenith.apps.user_preference.model.ExchangeRateAssessment
import zenith.apps.user_preference.repository.UserPreferenceRepository
import javax.inject.Inject

class GetExchangeRateAssessmentUseCase @Inject constructor(
    private val userPreferenceRepository: UserPreferenceRepository
) {
    operator fun invoke(): Flow<ExchangeRateAssessment> {
        return userPreferenceRepository.exchangeRateAssessment
    }
}