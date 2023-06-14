package space.mrandika.wisnu.repository

import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking
import space.mrandika.wisnu.BuildConfig
import space.mrandika.wisnu.R
import space.mrandika.wisnu.model.category.CategoriesResponse
import space.mrandika.wisnu.model.city.CityItinerariesResponse
import space.mrandika.wisnu.model.poi.POIResponse
import space.mrandika.wisnu.model.poi.POIsResponse
import space.mrandika.wisnu.prefs.TokenPreferences
import space.mrandika.wisnu.service.WisnuAPIService
import javax.inject.Inject

class POIRepository @Inject constructor(
    private val service: WisnuAPIService,
    private val tokenPreferences: TokenPreferences,
    private val assetManager: AssetManager
) {
    suspend fun getRecommendedPOIs(
        preview: Boolean = true,
        page: Int = 1,
        size: Int = 5
    ): Flow<Result<POIsResponse>> = flow {
        try {
            val response: POIsResponse = if (BuildConfig.IS_SERVICE_UP) {
                var token = "Bearer "

                runBlocking {
                    token += tokenPreferences.getAccessToken().first()
                }

                service.getPOIsRecommendation(token, preview, page, size)
            } else {
                val gson = Gson()
                val stringJson = assetManager.getStringJson(R.raw.recom_home)

                delay(2500L)
                gson.fromJson(stringJson, POIsResponse::class.java)
            }

            emit(Result.success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getCategories(): Flow<Result<CategoriesResponse>> = flow {
        try {
            val response: CategoriesResponse = if (BuildConfig.IS_SERVICE_UP) {
                service.getCategories()
            } else {
                val gson = Gson()
                val stringJson = assetManager.getStringJson(R.raw.category)

                delay(2500L)
                gson.fromJson(stringJson, CategoriesResponse::class.java)
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
                var token = "Bearer "

                runBlocking {
                    token += tokenPreferences.getAccessToken().first()
                }

                service.getPOIbyCategory(token, category)
            } else {
                val gson = Gson()
                val stringJson = assetManager.getStringJson(R.raw.poi_cat)

                delay(2500L)
                gson.fromJson(stringJson, POIsResponse::class.java)
            }

            emit(Result.success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getPOIsByCity(
        cityId: Int,
        numDays: Int
    ): Flow<Result<CityItinerariesResponse>> = flow {
        try {
            val response: CityItinerariesResponse = if (BuildConfig.IS_SERVICE_UP) {
                var token = "Bearer "

                runBlocking {
                    token += tokenPreferences.getAccessToken().first()
                }

                service.getCityItinerary(token, cityId, numDays)
            } else {
                val gson = Gson()
                val stringJson = assetManager.getStringJson(R.raw.poi_itinerary)

                delay(2500L)
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
                var token = "Bearer "

                runBlocking {
                    token += tokenPreferences.getAccessToken().first()
                }

                service.getPOIdetail(token, id)
            } else {
                val gson = Gson()
                val stringJson = assetManager.getStringJson(R.raw.poi_detail)

                delay(2500L)
                gson.fromJson(stringJson, POIResponse::class.java)
            }

            emit(Result.success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)
}