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
import space.mrandika.wisnu.model.guide.GuideResponse
import space.mrandika.wisnu.prefs.TokenPreferences
import space.mrandika.wisnu.service.WisnuAPIService
import javax.inject.Inject

class GuideRepository @Inject constructor(
    private val service: WisnuAPIService,
    private val tokenPreferences: TokenPreferences,
    private val assetManager: AssetManager
)  {
    suspend fun getGuide(
        id: Int
    ): Flow<Result<GuideResponse>> = flow {
        try {
            val response: GuideResponse = if (BuildConfig.IS_SERVICE_UP) {
                var token = "Bearer "

                runBlocking {
                    token += tokenPreferences.getAccessToken().first()
                }

                service.getGuide(token, id)
            } else {
                val gson = Gson()
                val stringJson = assetManager.getStringJson(R.raw.guide_detail)

                delay(2500L)
                gson.fromJson(stringJson, GuideResponse::class.java)
            }

            emit(Result.success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)
}