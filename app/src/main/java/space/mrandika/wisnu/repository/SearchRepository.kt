package space.mrandika.wisnu.repository

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import space.mrandika.wisnu.BuildConfig
import space.mrandika.wisnu.R
import space.mrandika.wisnu.model.poi.POIsResponse
import space.mrandika.wisnu.model.search.SearchResponse
import space.mrandika.wisnu.service.WisnuAPIService
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val service: WisnuAPIService,
    private val assetManager: AssetManager
) {
    suspend fun getSearchResult(
        keyword: String,
        filter: String
    ): Flow<Result<SearchResponse>> = flow {
        try {
            val response: SearchResponse = if (BuildConfig.IS_SERVICE_UP) {
                service.search(keyword, filter)
            } else {
                val gson = Gson()
                val stringJson = assetManager.getStringJson(R.raw.search)

                gson.fromJson(stringJson, SearchResponse::class.java)
            }

            emit(Result.success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getDiscoveryResult(): Flow<Result<SearchResponse>> = flow {
        try {
            val response: SearchResponse = if (BuildConfig.IS_SERVICE_UP) {
                service.discover()
            } else {
                val gson = Gson()
                val stringJson = assetManager.getStringJson(R.raw.search)

                gson.fromJson(stringJson, SearchResponse::class.java)
            }

            emit(Result.success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)
}