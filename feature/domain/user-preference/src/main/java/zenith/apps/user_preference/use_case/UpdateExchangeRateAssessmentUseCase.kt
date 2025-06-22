package zenith.apps.user_preference.use_case

import zenith.apps.user_preference.model.ExchangeRateAssessment
import zenith.apps.user_preference.repository.UserPreferenceRepository
import javax.inject.Inject

class UpdateExchangeRateAssessmentUseCase @Inject constructor(
    private val userPreferenceRepository: UserPreferenceRepository
) {
    suspend operator fun invoke(assessment: ExchangeRateAssessment) {
        userPreferenceRepository.updateExchangeRateAssessment(assessment)
    }
}