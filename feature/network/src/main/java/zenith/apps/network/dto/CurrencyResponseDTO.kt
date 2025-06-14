package zenith.apps.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class CurrencyResponseDTO(
    @SerialName("data")
    val updatedAt: String,
    @SerialName("eur")
    val currencies: List<CurrencyDTO>
)