package space.mrandika.wisnu.repository

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import space.mrandika.wisnu.BuildConfig
import space.mrandika.wisnu.R
import space.mrandika.wisnu.model.city.CityItinerariesResponse
import space.mrandika.wisnu.model.poi.POIResponse
import space.mrandika.wisnu.model.poi.POIsResponse
import space.mrandika.wisnu.service.WisnuAPIService
import javax.inject.Inject

class POIRepository @Inject constructor(
    private val service: WisnuAPIService,
    private val assetManager: AssetManager
) {
    suspend fun getRecommendedPOIs(
        preview: Boolean = true,
        page: Int = 1,
        size: Int = 5
    ): Flow<Result<POIsResponse>> = flow {
        try {
            val response: POIsResponse = if (BuildConfig.IS_SERVICE_UP) {
                service.getPOIsRecommendation(preview, page, size)
            } else {
                val gson = Gson()
                val stringJson = assetManager.getStringJson(R.raw.recom_home)

                gson.fromJson(stringJson, POIsResponse::class.java)
            }

            emit(Result.success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getPOIsByCategory(
        category: String
    ): Flow<Result<POIsResponse>> = flow {
        try {
            val response: POIsResponse = if (BuildConfig.IS_SERVICE_UP) {
                service.getPOIbyCategory(category)
            } else {
                val gson = Gson()
                val stringJson = assetManager.getStringJson(R.raw.poi_cat)

                gson.fromJson(stringJson, POIsResponse::class.java)
            }

            emit(Result.success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getPOIsByCity(
        cityId: Int
    ): Flow<Result<CityItinerariesResponse>> = flow {
        try {
            val response: CityItinerariesResponse = if (BuildConfig.IS_SERVICE_UP) {
                service.getCityItinerary(cityId)
            } else {
                val gson = Gson()
                val stringJson = assetManager.getStringJson(R.raw.poi_itinerary)

                gson.fromJson(stringJson, CityItinerariesResponse::class.java)
            }

            emit(Result.success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getPOI(
        id: Int
    ): Flow<Result<POIResponse>> = flow {
        try {
            val response: POIResponse = if (BuildConfig.IS_SERVICE_UP) {
                service.getPOIdetail(id)
            } else {
                val gson = Gson()
                val stringJson = assetManager.getStringJson(R.raw.poi_detail)

                gson.fromJson(stringJson, POIResponse::class.java)
            }

            emit(Result.success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)
}