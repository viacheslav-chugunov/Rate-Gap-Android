package zenith.apps.storage.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "FAVOURITE_EXCHANGE_PAIR",
    primaryKeys = ["FROM_CURRENCY_CODE", "TO_CURRENCY_CODE"]
)
internal class FavouriteExchangePairEntity(
    @ColumnInfo(name = "FROM_CURRENCY_CODE")
    val fromCurrencyCode: String,
    @ColumnInfo(name = "TO_CURRENCY_CODE")
    val toCurrencyCode: String
)