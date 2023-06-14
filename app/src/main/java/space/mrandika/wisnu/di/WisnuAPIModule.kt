package space.mrandika.wisnu.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import space.mrandika.wisnu.config.WisnuAPIConfig
import space.mrandika.wisnu.prefs.TokenPreferences
import space.mrandika.wisnu.service.WisnuAPIService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class WisnuAPIModule {

    @Provides
    @Singleton
    fun provideWisnuAPI(): WisnuAPIService = WisnuAPIConfig.getApiService()
}