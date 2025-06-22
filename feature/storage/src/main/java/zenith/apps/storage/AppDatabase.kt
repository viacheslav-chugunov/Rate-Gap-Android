package zenith.apps.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import zenith.apps.storage.dao.SettingDao
import zenith.apps.storage.entity.SettingEntity

@Database(
    version = 1,
    entities = [SettingEntity::class]
)
internal abstract class AppDatabase : RoomDatabase() {
    abstract val settingDao: SettingDao
}