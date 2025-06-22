package zenith.apps.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import zenith.apps.storage.entity.SettingEntity

@Dao
internal interface SettingDao {
    @Query("SELECT * FROM SETTING WHERE NAME = :name")
    fun get(name: String): Flow<SettingEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun put(settingEntity: SettingEntity)
}