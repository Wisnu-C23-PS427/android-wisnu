package space.mrandika.wisnu.service

import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import space.mrandika.wisnu.model.transaction.OrderRequest
import space.mrandika.wisnu.model.transaction.OrderResponse
import space.mrandika.wisnu.model.transaction.TransactionsResponse

interface TransactionService {
    @POST("orders/new")
    suspend fun createOrder(
        @Body request: OrderRequest
    ): OrderResponse

    @GET("orders")
    suspend fun getTransaction(
        @Field("filter") filter: String,
    ): TransactionsResponse
}