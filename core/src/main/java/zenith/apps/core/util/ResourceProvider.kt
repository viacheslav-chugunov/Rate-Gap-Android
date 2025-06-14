package zenith.apps.core.util

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface ResourceProvider {
    fun getString(id: Int): String
    fun getString(id: Int, vararg args: Any): String
}

internal class DefaultResourceProvider @Inject constructor(
    @ApplicationContext private val context: Context
) : ResourceProvider {
    override fun getString(id: Int): String = context.getString(id)
    override fun getString(id: Int, vararg args: Any): String = context.getString(id, *args)
}