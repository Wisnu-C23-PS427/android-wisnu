package space.mrandika.wisnu.repository

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
import space.mrandika.wisnu.model.event.EventResponse
import space.mrandika.wisnu.model.event.EventsResponse
import space.mrandika.wisnu.prefs.TokenPreferences
import space.mrandika.wisnu.service.WisnuAPIService
import javax.inject.Inject

class EventRepository @Inject constructor(
    private val service: WisnuAPIService,
    private val tokenPreferences: TokenPreferences,
    private val assetManager: AssetManager
) {
    suspend fun getEvents(
        preview: Boolean = true,
        page: Int = 1,
        size: Int = 5
    ): Flow<Result<EventsResponse>> = flow {
        try {
            val response: EventsResponse = if (BuildConfig.IS_SERVICE_UP) {
                var token = "Bearer "

                runBlocking {
                    token += tokenPreferences.getAccessToken().first()
                }

                service.getEvents(token, preview, page, size)
            } else {
                val gson = Gson()
                val stringJson = assetManager.getStringJson(R.raw.event_home)

                delay(2500L)
                gson.fromJson(stringJson, EventsResponse::class.java)
            }

            emit(Result.success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getEvent(
        id: Int
    ): Flow<Result<EventResponse>> = flow {
        try {
            val response: EventResponse = if (BuildConfig.IS_SERVICE_UP) {
                var token = "Bearer "

                runBlocking {
                    token += tokenPreferences.getAccessToken().first()
                }

                service.getEvent(token, id)
            } else {
                val gson = Gson()
                val stringJson = assetManager.getStringJson(R.raw.event_detail)

                delay(2500L)
                gson.fromJson(stringJson, EventResponse::class.java)
            }

            emit(Result.success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)
}