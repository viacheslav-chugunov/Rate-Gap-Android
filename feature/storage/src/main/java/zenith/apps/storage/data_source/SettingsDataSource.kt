package zenith.apps.storage.data_source

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import zenith.apps.core.util.CoroutineDispatchers
import zenith.apps.storage.dao.SettingDao
import javax.inject.Inject

interface SettingsDataSource {
    fun getInt(name: String, default: Int): Flow<Int>
    fun getDouble(name: String, default: Double): Flow<Double>
    fun getBoolean(name: String, default: Boolean): Flow<Boolean>
    fun getString(name: String, default: String): Flow<String>
    suspend fun putInt(name: String, value: Int)
    suspend fun putDouble(name: String, value: Double)
    suspend fun putBoolean(name: String, value: Boolean)
    suspend fun putString(name: String, value: String)
}

internal class DefaultSettingsDataSource @Inject constructor(
    private val settingDao: SettingDao,
    private val coroutineDispatchers: CoroutineDispatchers
): SettingsDataSource {

    override fun getInt(name: String, default: Int): Flow<Int> =
        settingDao.get(name).map { it?.value?.toIntOrNull() ?: default }

    override fun getDouble(name: String, default: Double): Flow<Double> =
        settingDao.get(name).map { it?.value?.toDoubleOrNull() ?: default }

    override fun getBoolean(name: String, default: Boolean): Flow<Boolean> =
        settingDao.get(name).map { it?.value?.toBoolean() ?: default }

    override fun getString(name: String, default: String): Flow<String> =
        settingDao.get(name).map { it?.value ?: default }

    override suspend fun putInt(name: String, value: Int) = withContext(coroutineDispatchers.io) {
        settingDao.put(zenith.apps.storage.entity.SettingEntity(name, value.toString()))
    }

    override suspend fun putDouble(name: String, value: Double) = withContext(coroutineDispatchers.io) {
        settingDao.put(zenith.apps.storage.entity.SettingEntity(name, value.toString()))
    }

    override suspend fun putBoolean(name: String, value: Boolean) = withContext(coroutineDispatchers.io) {
        settingDao.put(zenith.apps.storage.entity.SettingEntity(name, value.toString()))
    }

    override suspend fun putString(name: String, value: String) = withContext(coroutineDispatchers.io) {
        settingDao.put(zenith.apps.storage.entity.SettingEntity(name, value))
    }
}