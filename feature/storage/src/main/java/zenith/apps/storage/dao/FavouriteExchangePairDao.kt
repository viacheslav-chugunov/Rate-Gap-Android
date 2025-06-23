package zenith.apps.storage.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import zenith.apps.storage.entity.FavouriteExchangePairEntity

@Dao
internal interface FavouriteExchangePairDao {
    @Query("SELECT * FROM FAVOURITE_EXCHANGE_PAIR")
    fun getAll(): Flow<List<FavouriteExchangePairEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(favouriteExchangePairEntity: FavouriteExchangePairEntity)

    @Delete
    suspend fun remove(favouriteExchangePairEntity: FavouriteExchangePairEntity)

}