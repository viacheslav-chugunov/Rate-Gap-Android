package zenith.apps.network.api

import retrofit2.http.GET
import zenith.apps.network.dto.CurrencyResponseDTO

internal interface CurrencyApi {
    @GET("npm/@fawazahmed0/currency-api@latest/v1/currencies/eur.json")
    suspend fun fetchCurrencies(): CurrencyResponseDTO
}