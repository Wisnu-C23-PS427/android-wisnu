package space.mrandika.wisnu.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import space.mrandika.wisnu.prefs.TokenPreferences
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "wisnu")

@Module
@InstallIn(SingletonComponent::class)
class WisnuDataStoreModule {

    @Provides
    @Singleton
    fun provideTokenPreferences(
        dataStore: DataStore<Preferences>
    ): TokenPreferences = TokenPreferences(dataStore)
}