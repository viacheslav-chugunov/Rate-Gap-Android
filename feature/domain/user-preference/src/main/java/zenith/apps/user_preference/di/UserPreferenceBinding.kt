package zenith.apps.user_preference.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import zenith.apps.user_preference.repository.DefaultUserPreferenceRepository
import zenith.apps.user_preference.repository.UserPreferenceRepository

@Module
@InstallIn(SingletonComponent::class)
internal interface UserPreferenceBinding {

    @Binds
    fun bindUserPreferenceRepository(
        defaultUserPreferenceRepository: DefaultUserPreferenceRepository
    ): UserPreferenceRepository
}