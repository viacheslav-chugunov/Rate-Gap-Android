package zenith.apps.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import zenith.apps.core.util.CoroutineDispatchers
import zenith.apps.core.util.DefaultCoroutineDispatchers
import zenith.apps.core.util.DefaultResourceProvider
import zenith.apps.core.util.ResourceProvider

@Module
@InstallIn(SingletonComponent::class)
internal interface CoreBinding {

    @Binds
    fun bindResourceProvider(
        defaultResourceProvider: DefaultResourceProvider
    ): ResourceProvider

    @Binds
    fun bindCoroutineDispatchers(
        defaultCoroutineDispatchers: DefaultCoroutineDispatchers
    ): CoroutineDispatchers
}