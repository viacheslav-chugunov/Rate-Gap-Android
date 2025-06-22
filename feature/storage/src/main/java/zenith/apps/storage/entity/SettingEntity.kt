package zenith.apps.storage.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SETTING")
internal class SettingEntity(
    @PrimaryKey
    @ColumnInfo(name = "NAME")
    val name: String,
    @ColumnInfo(name = "VALUE")
    val value: String
)