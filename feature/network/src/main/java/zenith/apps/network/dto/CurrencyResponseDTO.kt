package zenith.apps.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class CurrencyResponseDTO(
    @SerialName("date")
    val updatedAt: String,
    @SerialName("eur")
    val rates: Map<String, Double>
)