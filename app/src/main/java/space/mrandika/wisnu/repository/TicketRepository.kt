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
import space.mrandika.wisnu.model.ticket.TicketResponse
import space.mrandika.wisnu.model.ticket.TicketsResponse
import space.mrandika.wisnu.prefs.TokenPreferences
import space.mrandika.wisnu.service.WisnuAPIService
import javax.inject.Inject

class TicketRepository @Inject constructor(
    private val service: WisnuAPIService,
    private val tokenPreferences: TokenPreferences,
    private val assetManager: AssetManager
) {
    suspend fun getTickets(
        filter: String = "active"
    ): Flow<Result<TicketsResponse>> = flow {
        try {
            val response: TicketsResponse = if (BuildConfig.IS_SERVICE_UP) {
                var token = "Bearer "

                runBlocking {
                    token += tokenPreferences.getAccessToken().first()
                }

                service.getTickets(token, filter)
            } else {
                val gson = Gson()
                val stringJson = assetManager.getStringJson(R.raw.list_ticket)

                delay(2500L)
                gson.fromJson(stringJson, TicketsResponse::class.java)
            }

            emit(Result.success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getTicket(
        id: String
    ): Flow<Result<TicketResponse>> = flow {
        try {
            val response: TicketResponse = if (BuildConfig.IS_SERVICE_UP) {
                var token = "Bearer "

                runBlocking {
                    token += tokenPreferences.getAccessToken().first()
                }

                service.getTicket(token, id)
            } else {
                val gson = Gson()
                val stringJson = assetManager.getStringJson(R.raw.ticket_detail)

                delay(2500L)
                gson.fromJson(stringJson, TicketResponse::class.java)
            }

            emit(Result.success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)
}