package zenith.apps.user_preference.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import zenith.apps.storage.data_source.SettingsDataSource
import zenith.apps.user_preference.model.ExchangeRateAssessment
import javax.inject.Inject

interface UserPreferenceRepository {
    val exchangeRateAssessment: Flow<ExchangeRateAssessment>
    suspend fun updateExchangeRateAssessment(assessment: ExchangeRateAssessment)
}

internal class DefaultUserPreferenceRepository @Inject constructor(
    private val settingsDataSource: SettingsDataSource
) : UserPreferenceRepository {
    override val exchangeRateAssessment: Flow<ExchangeRateAssessment> = combine(
        settingsDataSource.getDouble(KEY_EXCHANGE_RATE_ASSESSMENT_MINIMUM, ExchangeRateAssessment.DEFAULT.minimum),
        settingsDataSource.getDouble(KEY_EXCHANGE_RATE_ASSESSMENT_MEDIUM, ExchangeRateAssessment.DEFAULT.medium),
        settingsDataSource.getDouble(KEY_EXCHANGE_RATE_ASSESSMENT_MAXIMUM, ExchangeRateAssessment.DEFAULT.maximum)
    ) { minimum, medium, maximum -> ExchangeRateAssessment(minimum, medium, maximum) }

    override suspend fun updateExchangeRateAssessment(assessment: ExchangeRateAssessment) {
        settingsDataSource.putDouble(KEY_EXCHANGE_RATE_ASSESSMENT_MINIMUM, assessment.minimum)
        settingsDataSource.putDouble(KEY_EXCHANGE_RATE_ASSESSMENT_MEDIUM, assessment.medium)
        settingsDataSource.putDouble(KEY_EXCHANGE_RATE_ASSESSMENT_MAXIMUM, assessment.maximum)
    }

    companion object {
        private const val KEY_EXCHANGE_RATE_ASSESSMENT_MINIMUM = "EXCHANGE_RATE_ASSESSMENT_MINIMUM"
        private const val KEY_EXCHANGE_RATE_ASSESSMENT_MEDIUM = "EXCHANGE_RATE_ASSESSMENT_MEDIUM"
        private const val KEY_EXCHANGE_RATE_ASSESSMENT_MAXIMUM = "EXCHANGE_RATE_ASSESSMENT_MAXIMUM"
    }

}