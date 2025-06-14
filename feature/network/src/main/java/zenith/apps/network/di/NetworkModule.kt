package zenith.apps.network.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import zenith.apps.network.api.CurrencyApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class NetworkModule {

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun provideCurrencyApi(
        json: Json,
        @ApplicationContext context: Context
    ): CurrencyApi {
        val client = OkHttpClient.Builder()
            .addInterceptor(ChuckerInterceptor(context))
            .build()
        return Retrofit.Builder()
            .client(client)
            .baseUrl("https://cdn.jsdelivr.net")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(CurrencyApi::class.java)
    }

}