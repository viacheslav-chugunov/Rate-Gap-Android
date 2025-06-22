package zenith.apps.user_preference.model

data class ExchangeRateAssessment(
    val minimum: Double,
    val medium: Double,
    val maximum: Double,
) {

    companion object {
        val DEFAULT = ExchangeRateAssessment(
            minimum = 5.0,
            medium = 10.0,
            maximum = 15.0,
        )
    }

}