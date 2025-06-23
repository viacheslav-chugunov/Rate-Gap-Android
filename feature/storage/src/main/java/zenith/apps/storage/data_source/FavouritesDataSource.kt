package zenith.apps.storage.data_source

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import zenith.apps.core.util.CoroutineDispatchers
import zenith.apps.storage.dao.FavouriteCurrencyDao
import zenith.apps.storage.entity.FavouriteCurrencyEntity
import javax.inject.Inject

interface FavouritesDataSource {
    fun allFavouriteCurrencyCodes(): Flow<List<String>>
    suspend fun updateFavouriteState(code: String, isFavourite: Boolean)
}

internal class DefaultFavouritesDataSource @Inject constructor(
    private val favouriteCurrencyDao: FavouriteCurrencyDao,
    private val coroutineDispatchers: CoroutineDispatchers
): FavouritesDataSource {
    override fun allFavouriteCurrencyCodes(): Flow<List<String>> {
        return favouriteCurrencyDao.getAll().map { entities ->
            entities.map { entity ->
                entity.code
            }
        }
    }

    override suspend fun updateFavouriteState(code: String, isFavourite: Boolean) = withContext(coroutineDispatchers.io) {
        if (isFavourite) {
            favouriteCurrencyDao.add(FavouriteCurrencyEntity(code))
        } else {
            favouriteCurrencyDao.remove(FavouriteCurrencyEntity(code))
        }
    }
}