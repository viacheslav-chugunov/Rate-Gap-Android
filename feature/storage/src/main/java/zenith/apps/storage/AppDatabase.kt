package zenith.apps.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import zenith.apps.storage.dao.FavouriteCurrencyDao
import zenith.apps.storage.dao.FavouriteExchangePairDao
import zenith.apps.storage.dao.SettingDao
import zenith.apps.storage.entity.FavouriteCurrencyEntity
import zenith.apps.storage.entity.FavouriteExchangePairEntity
import zenith.apps.storage.entity.SettingEntity

@Database(
    version = 1,
    entities = [
        SettingEntity::class,
        FavouriteCurrencyEntity::class,
        FavouriteExchangePairEntity::class
    ]
)
internal abstract class AppDatabase : RoomDatabase() {
    abstract val settingDao: SettingDao
    abstract val favouriteCurrencyDao: FavouriteCurrencyDao
    abstract val favouriteExchangePairDao: FavouriteExchangePairDao
}