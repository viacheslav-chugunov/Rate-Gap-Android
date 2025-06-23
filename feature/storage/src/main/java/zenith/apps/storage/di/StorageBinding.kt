package zenith.apps.storage.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import zenith.apps.storage.data_source.DefaultFavouritesDataSource
import zenith.apps.storage.data_source.DefaultSettingsDataSource
import zenith.apps.storage.data_source.FavouritesDataSource
import zenith.apps.storage.data_source.SettingsDataSource

@Module
@InstallIn(SingletonComponent::class)
internal interface StorageBinding {
    @Binds
    fun bindSettingsDataSource(
        defaultSettingsDataSource: DefaultSettingsDataSource
    ): SettingsDataSource

    @Binds
    fun bindFavouritesDataSource(
        defaultFavouritesDataSource: DefaultFavouritesDataSource
    ): FavouritesDataSource
}