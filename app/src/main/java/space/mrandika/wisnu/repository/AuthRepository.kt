package space.mrandika.wisnu.repository

import android.content.Context
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import space.mrandika.wisnu.BuildConfig
import space.mrandika.wisnu.R
import space.mrandika.wisnu.model.auth.LoginResponse
import space.mrandika.wisnu.model.auth.RegisterRequest
import space.mrandika.wisnu.model.auth.RegisterResponse
import space.mrandika.wisnu.prefs.TokenPreferences
import space.mrandika.wisnu.service.WisnuAPIService
import space.mrandika.wisnu.utils.getJsonAsString
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val service: WisnuAPIService,
    private val pref: TokenPreferences,
    private val context: Context
) {
    // Auth call
    suspend fun login(email: String, password: String): Flow<Result<LoginResponse>> = flow {
        try {
            val response: LoginResponse = if (BuildConfig.IS_SERVICE_UP) {
                service.login(email, password)
            } else {
                val gson = Gson()

                val jsonResponse = getJsonAsString(context, R.raw.login)
                gson.fromJson(jsonResponse, LoginResponse::class.java)
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

                val jsonResponse = getJsonAsString(context, R.raw.register)
                gson.fromJson(jsonResponse, RegisterResponse::class.java)
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