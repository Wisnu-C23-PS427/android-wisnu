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
import space.mrandika.wisnu.model.transaction.OrderRequest
import space.mrandika.wisnu.model.transaction.OrderResponse
import space.mrandika.wisnu.model.transaction.POIGuideOrder
import space.mrandika.wisnu.model.transaction.POITicketOrder
import space.mrandika.wisnu.model.transaction.TransactionResponse
import space.mrandika.wisnu.model.transaction.TransactionsResponse
import space.mrandika.wisnu.prefs.TokenPreferences
import space.mrandika.wisnu.service.WisnuAPIService
import javax.inject.Inject

class TransactionRepository @Inject constructor(
    private val service: WisnuAPIService,
    private val tokenPreferences: TokenPreferences,
    private val assetManager: AssetManager
) {
    suspend fun createTransaction(
        tickets: List<POITicketOrder>,
        guide: POIGuideOrder?
    ): Flow<Result<OrderResponse>> = flow {
        try {
            val request = OrderRequest(tickets, guide)

            val response: OrderResponse = if (BuildConfig.IS_SERVICE_UP) {
                var token = "Bearer "

                runBlocking {
                    token += tokenPreferences.getAccessToken().first()
                }

                service.createOrder(token, request)
            } else {
                val gson = Gson()
                val stringJson = assetManager.getStringJson(R.raw.ticket_trx)

                delay(2500L)
                gson.fromJson(stringJson, OrderResponse::class.java)
            }

            emit(Result.success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getTransactions(
        filter: String = "all"
    ): Flow<Result<TransactionsResponse>> = flow {
        try {
            val response: TransactionsResponse = if (BuildConfig.IS_SERVICE_UP) {
                var token = "Bearer "

                runBlocking {
                    token += tokenPreferences.getAccessToken().first()
                }

                service.getTransactions(token, filter)
            } else {
                val gson = Gson()
                val stringJson = assetManager.getStringJson(R.raw.list_transaction)

                delay(2500L)
                gson.fromJson(stringJson, TransactionsResponse::class.java)
            }

            emit(Result.success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getTransaction(
        id: Int
    ): Flow<Result<TransactionResponse>> = flow {
        try {
            val response: TransactionResponse = if (BuildConfig.IS_SERVICE_UP) {
                var token = "Bearer "

                runBlocking {
                    token += tokenPreferences.getAccessToken().first()
                }

                service.getTransaction(token, id)
            } else {
                val gson = Gson()
                val stringJson = assetManager.getStringJson(R.raw.order_package)

                delay(2500L)
                gson.fromJson(stringJson, TransactionResponse::class.java)
            }

            emit(Result.success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)
}