package space.mrandika.wisnu.service

import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import space.mrandika.wisnu.model.auth.LoginResponse
import space.mrandika.wisnu.model.auth.RegisterRequest
import space.mrandika.wisnu.model.auth.RegisterResponse
import space.mrandika.wisnu.model.city.CitiesResponse
import space.mrandika.wisnu.model.city.CityResponse
import space.mrandika.wisnu.model.event.EventResponse
import space.mrandika.wisnu.model.guide.GuideResponse
import space.mrandika.wisnu.model.poi.POIResponse
import space.mrandika.wisnu.model.poi.POIsResponse
import space.mrandika.wisnu.model.search.SearchResponse
import space.mrandika.wisnu.model.ticket.TicketsResponse
import space.mrandika.wisnu.model.transaction.OrderRequest
import space.mrandika.wisnu.model.transaction.OrderResponse
import space.mrandika.wisnu.model.transaction.TransactionsResponse

interface WisnuAPIService {
    @POST("register")
    suspend fun register(
        @Body request: RegisterRequest
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("city")
    suspend fun getCities(
        @Query("preview") preview: Boolean,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): CitiesResponse

    @GET("city/{id}")
    suspend fun getCity(
        @Path("id") id: String
    ): CityResponse

    @GET("city/{id}/itinerary")
    suspend fun getCityItinerary(
        @Path("id") id: String
    ): POIsResponse

    @FormUrlEncoded
    @POST("search")
    suspend fun search(
        @Field("keyword") keyword: String,
        @Field("filter") filter: String,
    ): SearchResponse

    @GET("events")
    suspend fun getEvents(
        @Query("preview") preview: Boolean,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): EventResponse

    @GET("guide/{id}")
    suspend fun getGuide(
        @Path("id") id: String
    ): GuideResponse

    @GET("pois/recommendation")
    suspend fun getPOIsRecommendation(
        @Query("preview") preview: Boolean,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): POIsResponse

    @GET("poi")
    suspend fun getPOIbyCategory(
        @Query("category") category: String,
    ): POIsResponse

    @GET("poi/{id}")
    suspend fun getPOIdetail(
        @Path("id") id: String
    ): POIResponse

    @GET("tickets")
    suspend fun getTickets(
        @Field("filter") filter: String,
    ): TicketsResponse

    @GET("ticket/{id}")
    suspend fun getTicket(
        @Path("id") id: String,
    ): TicketsResponse

    @POST("orders/new")
    suspend fun createOrder(
        @Body request: OrderRequest
    ): OrderResponse

    @GET("orders")
    suspend fun getTransaction(
        @Field("filter") filter: String,
    ): TransactionsResponse
}