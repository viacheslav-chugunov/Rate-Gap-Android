package zenith.apps.storage.data_source

import android.annotation.SuppressLint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import zenith.apps.core.util.CoroutineDispatchers
import zenith.apps.storage.dao.FavouriteCurrencyDao
import zenith.apps.storage.dao.FavouriteExchangePairDao
import zenith.apps.storage.entity.FavouriteCurrencyEntity
import zenith.apps.storage.entity.FavouriteExchangePairEntity
import javax.inject.Inject

interface FavouritesDataSource {
    val allFavouriteCurrencyCodes: Flow<List<String>>
    suspend fun updateFavouriteCurrencyState(code: String, isFavourite: Boolean)
    val allFavouriteExchangePairCodes: Flow<List<Pair<String, String>>>
    suspend fun updateFavouriteExchangePair(fromCode: String, toCode: String, isFavourite: Boolean)
}

@SuppressLint("ImplicitSamInstance")
internal class DefaultFavouritesDataSource @Inject constructor(
    private val favouriteCurrencyDao: FavouriteCurrencyDao,
    private val favouriteExchangePairDao: FavouriteExchangePairDao,
    private val coroutineDispatchers: CoroutineDispatchers
): FavouritesDataSource {
    override val allFavouriteCurrencyCodes: Flow<List<String>>
        get() = favouriteCurrencyDao.getAll().map { entities ->
            entities.map { entity ->
                entity.code
            }
        }

    override suspend fun updateFavouriteCurrencyState(code: String, isFavourite: Boolean) = withContext(coroutineDispatchers.io) {
        if (isFavourite) {
            favouriteCurrencyDao.add(FavouriteCurrencyEntity(code))
        } else {
            favouriteCurrencyDao.remove(FavouriteCurrencyEntity(code))
        }
    }

    override val allFavouriteExchangePairCodes: Flow<List<Pair<String, String>>>
        get() = favouriteExchangePairDao.getAll().map { entities ->
            entities.map { entity ->
                entity.fromCurrencyCode to entity.toCurrencyCode
            }
        }

    override suspend fun updateFavouriteExchangePair(
        fromCode: String,
        toCode: String,
        isFavourite: Boolean
    ) {
        if (isFavourite) {
            favouriteExchangePairDao.add(FavouriteExchangePairEntity(fromCode, toCode))
        } else {
            favouriteExchangePairDao.remove(FavouriteExchangePairEntity(fromCode, toCode))
        }
    }


}