package zenith.apps.storage.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import zenith.apps.storage.entity.FavouriteCurrencyEntity

@Dao
internal interface FavouriteCurrencyDao {

    @Query("SELECT * FROM FAVOURITE_CURRENCY")
    fun getAll(): Flow<List<FavouriteCurrencyEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(entity: FavouriteCurrencyEntity)

    @Delete
    suspend fun remove(entity: FavouriteCurrencyEntity)
}