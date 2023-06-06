package space.mrandika.wisnu.repository

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import space.mrandika.wisnu.BuildConfig
import space.mrandika.wisnu.R
import space.mrandika.wisnu.model.account.AccountResponse
import space.mrandika.wisnu.model.city.CitiesResponse
import space.mrandika.wisnu.service.WisnuAPIService
import javax.inject.Inject

class AccountRepository @Inject constructor(
    private val service: WisnuAPIService,
    private val assetManager: AssetManager
) {
    suspend fun getAccount(): Flow<Result<AccountResponse>> = flow {
        try {
            val response: AccountResponse = if (BuildConfig.IS_SERVICE_UP) {
                service.account()
            } else {
                val gson = Gson()
                val stringJson = assetManager.getStringJson(R.raw.profile)

                gson.fromJson(stringJson, AccountResponse::class.java)
            }

            emit(Result.success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)
}