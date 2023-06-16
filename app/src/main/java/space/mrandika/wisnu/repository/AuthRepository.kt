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
import space.mrandika.wisnu.model.auth.LoginRequest
import space.mrandika.wisnu.model.auth.LoginResponse
import space.mrandika.wisnu.model.auth.LogoutResponse
import space.mrandika.wisnu.model.auth.RegisterRequest
import space.mrandika.wisnu.model.auth.RegisterResponse
import space.mrandika.wisnu.prefs.TokenPreferences
import space.mrandika.wisnu.service.WisnuAPIService
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val service: WisnuAPIService,
    private val pref: TokenPreferences,
    private val assetManager: AssetManager
) {
    // Auth call
    suspend fun login(email: String, password: String): Flow<Result<LoginResponse>> = flow {
        try {
            val request = LoginRequest(email, password)

            val response: LoginResponse = if (BuildConfig.IS_SERVICE_UP) {
                service.login(request)
            } else {
                val gson = Gson()
                val stringJson = assetManager.getStringJson(R.raw.login)

                delay(2500L)
                gson.fromJson(stringJson, LoginResponse::class.java)
            }

            emit(Result.success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun register(
        name: String,
        email: String,
        phoneNumber: String,
        password: String,
        interest: List<String>
    ): Flow<Result<RegisterResponse>> = flow {
        try {
            val request = RegisterRequest(name, email, phoneNumber, password, interest)

            val response: RegisterResponse = if (BuildConfig.IS_SERVICE_UP) {
                service.register(request)
            } else {
                val gson = Gson()
                val stringJson = assetManager.getStringJson(R.raw.register)

                delay(2500L)
                gson.fromJson(stringJson, RegisterResponse::class.java)
            }

            emit(Result.success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun logout(): Flow<Result<LogoutResponse>> = flow {
        try {
            val response: LogoutResponse = if (BuildConfig.IS_SERVICE_UP) {
                var token = "Bearer "

                runBlocking {
                    token += pref.getAccessToken().first()
                }

                service.logout(token)
            } else {
                val gson = Gson()
                val stringJson = assetManager.getStringJson(R.raw.login)

                delay(2500L)
                gson.fromJson(stringJson, LogoutResponse::class.java)
            }

            emit(Result.success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    // Token-related
    suspend fun saveAccessToken(token: String) {
        pref.saveAccessToken(token)
    }

    suspend fun removeAccessToken() {
        pref.removeAccessToken()
    }

    fun getAccessToken(): Flow<String?> = pref.getAccessToken()
}