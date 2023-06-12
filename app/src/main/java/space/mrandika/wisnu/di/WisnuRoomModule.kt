package space.mrandika.wisnu.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import space.mrandika.wisnu.database.AppDatabase
import space.mrandika.wisnu.database.TripDao
import space.mrandika.wisnu.database.TripRoomDatabaseCallback
import javax.inject.Provider
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class WisnuRoomModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
        provider: Provider<TripDao>
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "wisnu"
        ).addCallback(TripRoomDatabaseCallback(provider)).build()
    }

    @Provides
    fun provideTripDao(appDatabase: AppDatabase): TripDao {
        return appDatabase.tripDao()
    }
}