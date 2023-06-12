package space.mrandika.wisnu.repository

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import space.mrandika.wisnu.BuildConfig
import space.mrandika.wisnu.R
import space.mrandika.wisnu.model.city.CitiesResponse
import space.mrandika.wisnu.model.city.CityResponse
import space.mrandika.wisnu.service.WisnuAPIService
import javax.inject.Inject

class CityRepository @Inject constructor(
    private val service: WisnuAPIService,
    private val assetManager: AssetManager
) {
    suspend fun getCities(
        preview: Boolean = true,
        page: Int = 1,
        size: Int = 5
    ): Flow<Result<CitiesResponse>> = flow {
        try {
            val response: CitiesResponse = if (BuildConfig.IS_SERVICE_UP) {
                service.getCities(preview, page, size)
            } else {
                val gson = Gson()
                val stringJson = assetManager.getStringJson(R.raw.city)

                gson.fromJson(stringJson, CitiesResponse::class.java)
            }

            emit(Result.success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getTopCities(
        preview: Boolean = true,
        page: Int = 1,
        size: Int = 5
    ): Flow<Result<CitiesResponse>> = flow {
        try {
            val response: CitiesResponse = if (BuildConfig.IS_SERVICE_UP) {
                service.getCities(preview, page, size)
            } else {
                val gson = Gson()
                val stringJson = assetManager.getStringJson(R.raw.city)

                gson.fromJson(stringJson, CitiesResponse::class.java)
            }

            emit(Result.success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getCity(
        id: Int
    ): Flow<Result<CityResponse>> = flow {
        try {
            val response: CityResponse = if (BuildConfig.IS_SERVICE_UP) {
                service.getCity(id)
            } else {
                val gson = Gson()
                val stringJson = assetManager.getStringJson(R.raw.city_detail)

                gson.fromJson(stringJson, CityResponse::class.java)
            }

            emit(Result.success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)
}