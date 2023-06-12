package space.mrandika.wisnu.service

import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import space.mrandika.wisnu.model.account.AccountResponse
import space.mrandika.wisnu.model.auth.LoginRequest
import space.mrandika.wisnu.model.auth.LoginResponse
import space.mrandika.wisnu.model.auth.RegisterRequest
import space.mrandika.wisnu.model.auth.RegisterResponse
import space.mrandika.wisnu.model.category.CategoriesResponse
import space.mrandika.wisnu.model.city.CitiesResponse
import space.mrandika.wisnu.model.city.CityResponse
import space.mrandika.wisnu.model.event.EventResponse
import space.mrandika.wisnu.model.event.EventsResponse
import space.mrandika.wisnu.model.guide.GuideResponse
import space.mrandika.wisnu.model.poi.POIResponse
import space.mrandika.wisnu.model.poi.POIsResponse
import space.mrandika.wisnu.model.search.SearchResponse
import space.mrandika.wisnu.model.ticket.TicketResponse
import space.mrandika.wisnu.model.ticket.TicketsResponse
import space.mrandika.wisnu.model.transaction.OrderRequest
import space.mrandika.wisnu.model.transaction.OrderResponse
import space.mrandika.wisnu.model.transaction.TransactionResponse
import space.mrandika.wisnu.model.transaction.TransactionsResponse

interface WisnuAPIService {
    @POST("register")
    suspend fun register(
        @Body request: RegisterRequest
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @GET("account")
    suspend fun account(
        @Header("Authorization") token: String
    ): AccountResponse

    @GET("city")
    suspend fun getCities(
        @Header("Authorization") token: String,
        @Query("preview") preview: Boolean,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): CitiesResponse

    @GET("city/{id}")
    suspend fun getCity(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): CityResponse

    @GET("city/{id}/itinerary")
    suspend fun getCityItinerary(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): POIsResponse

    @FormUrlEncoded
    @POST("search")
    suspend fun search(
        @Header("Authorization") token: String,
        @Field("keyword") keyword: String,
        @Field("filter") filter: String,
    ): SearchResponse

    @GET("discover")
    suspend fun discover(
        @Header("Authorization") token: String
    ): SearchResponse

    @GET("events")
    suspend fun getEvents(
        @Header("Authorization") token: String,
        @Query("preview") preview: Boolean,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): EventsResponse

    @GET("events")
    suspend fun getEvent(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): EventResponse

    @GET("guide/{id}")
    suspend fun getGuide(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): GuideResponse

    @GET("pois/categories")
    suspend fun getCategories(): CategoriesResponse

    @GET("pois/recommendation")
    suspend fun getPOIsRecommendation(
        @Header("Authorization") token: String,
        @Query("preview") preview: Boolean,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): POIsResponse

    @GET("poi")
    suspend fun getPOIbyCategory(
        @Header("Authorization") token: String,
        @Query("category") category: String,
    ): POIsResponse

    @GET("poi/{id}")
    suspend fun getPOIdetail(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): POIResponse

    @GET("tickets")
    suspend fun getTickets(
        @Header("Authorization") token: String,
        @Field("filter") filter: String,
    ): TicketsResponse

    @GET("ticket/{id}")
    suspend fun getTicket(
        @Header("Authorization") token: String,
        @Path("id") id: String,
    ): TicketResponse

    @POST("transaction/new")
    suspend fun createOrder(
        @Header("Authorization") token: String,
        @Body request: OrderRequest
    ): OrderResponse

    @GET("transactions")
    suspend fun getTransactions(
        @Header("Authorization") token: String,
        @Field("filter") filter: String,
    ): TransactionsResponse

    @GET("transaction")
    suspend fun getTransaction(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
    ): TransactionResponse
}