package space.mrandika.wisnu.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import space.mrandika.wisnu.entity.Itinerary
import space.mrandika.wisnu.entity.ItineraryWithPOIs
import space.mrandika.wisnu.entity.POIEntity
import space.mrandika.wisnu.entity.Trip

@Dao
interface TripDao {

    @Query("SELECT * FROM trip")
    fun getTrips(): List<Trip>

    @Transaction
    @Query("SELECT * FROM itinerary WHERE trip_id = :tripId")
    fun getItinerariesWithPOIs(tripId: Int): List<ItineraryWithPOIs>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTrip(trip: Trip)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertItinerary(itinerary: Itinerary)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPoi(poiEntity: POIEntity)
}