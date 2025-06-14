package zenith.apps.network.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import zenith.apps.network.data_source.DefaultNetworkCurrencyDataSource
import zenith.apps.network.data_source.NetworkCurrencyDataSource

@Module
@InstallIn(SingletonComponent::class)
internal interface NetworkBinding {

    @Binds
    fun bindNetworkCurrencyDataSource(
        defaultNetworkCurrencyDataSource: DefaultNetworkCurrencyDataSource
    ): NetworkCurrencyDataSource
}