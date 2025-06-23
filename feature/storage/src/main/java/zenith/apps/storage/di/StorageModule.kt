package zenith.apps.storage.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import zenith.apps.storage.AppDatabase
import zenith.apps.storage.dao.FavouriteCurrencyDao
import zenith.apps.storage.dao.SettingDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class StorageModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "rate-gap-storage.db"
    ).build()

    @Provides
    @Singleton
    fun provideSettingDao(
        appDatabase: AppDatabase
    ): SettingDao = appDatabase.settingDao

    @Provides
    @Singleton
    fun provideFavouriteCurrencyDao(
        appDatabase: AppDatabase
    ): FavouriteCurrencyDao = appDatabase.favouriteCurrencyDao

}