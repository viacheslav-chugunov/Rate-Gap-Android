package zenith.apps.storage.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FAVOURITE_CURRENCY")
internal class FavouriteCurrencyEntity(
    @PrimaryKey
    @ColumnInfo(name = "CODE")
    val code: String
) {

    override fun equals(other: Any?): Boolean =
        other is FavouriteCurrencyEntity && other.code == code

    override fun hashCode(): Int = code.hashCode()

}