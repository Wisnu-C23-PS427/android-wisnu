package space.mrandika.wisnu.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import space.mrandika.wisnu.BuildConfig
import space.mrandika.wisnu.entity.Itinerary
import space.mrandika.wisnu.entity.POI
import space.mrandika.wisnu.entity.Trip
import javax.inject.Provider

@Database(entities = [Trip::class, Itinerary::class, POI::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun tripDao(): TripDao
}

class TripRoomDatabaseCallback (
    private val provider: Provider<TripDao>
): RoomDatabase.Callback() {
    private val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        if (!BuildConfig.IS_SERVICE_UP) {
            applicationScope.launch(Dispatchers.IO) {
                populateDatabase()
            }
        }
    }

    private fun populateDatabase() {
        val poi1 = POI(
            image = "www.path/to/poi_image.jpg",
            name = "POI Name",
            location = "Bojongsoang",
            id = 1,
            itineraryId = 1
        )

        val itinerary1 = Itinerary(
            id = 1,
            tripId = 1,
            day = 1
        )

        val trip = Trip(
            id = 1,
            city_name = "Kota Bandung",
            createdAt = System.currentTimeMillis().toString()
        )

        provider.get().insertTrip(trip)

        provider.get().insertItinerary(itinerary1)

        provider.get().insertPoi(poi1)
    }
}