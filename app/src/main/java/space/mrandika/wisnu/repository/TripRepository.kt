package space.mrandika.wisnu.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import space.mrandika.wisnu.database.TripDao
import space.mrandika.wisnu.entity.Itinerary
import space.mrandika.wisnu.entity.ItineraryWithPOIs
import space.mrandika.wisnu.entity.POI
import space.mrandika.wisnu.entity.Trip
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject

class TripRepository @Inject constructor(
    private val dao: TripDao,
) {
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    suspend fun getTrips(): Flow<Result<List<Trip>>> = flow {
        try {
            val response = dao.getTrips()

            emit(Result.success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getItinerariesWithPOIs(tripId: Int): Flow<Result<List<ItineraryWithPOIs>>> = flow {
        try {
            val response = dao.getItinerariesWithPOIs(tripId)

            emit(Result.success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    fun saveTrip(trip: Trip) {
        executorService.execute { dao.insertTrip(trip) }
    }

    fun saveItinerary(itinerary: Itinerary) {
        executorService.execute { dao.insertItinerary(itinerary) }
    }

    fun savePoi(poi: POI) {
        executorService.execute { dao.insertPoi(poi) }
    }
}