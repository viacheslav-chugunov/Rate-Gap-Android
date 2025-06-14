package zenith.apps.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class CurrencyDTO(
    @SerialName("code")
    val code: String,
    @SerialName("rate")
    val rate: Double
) {
}