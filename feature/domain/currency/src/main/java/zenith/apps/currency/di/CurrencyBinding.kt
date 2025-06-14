package zenith.apps.currency.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import zenith.apps.currency.repository.CurrencyRepository
import zenith.apps.currency.repository.DefaultCurrencyRepository

@Module
@InstallIn(SingletonComponent::class)
internal interface CurrencyBinding {

    @Binds
    fun bindCurrencyRepository(
        defaultCurrencyRepository: DefaultCurrencyRepository
    ): CurrencyRepository
}