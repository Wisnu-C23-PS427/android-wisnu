package space.mrandika.wisnu.service

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import space.mrandika.wisnu.model.city.CitiesResponse
import space.mrandika.wisnu.model.city.CityResponse
import space.mrandika.wisnu.model.poi.POIsResponse
import space.mrandika.wisnu.model.search.SearchResponse

interface CityService {
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
    @POST("city/search")
    suspend fun searchPOI(
        @Field("keyword") keyword: String,
        @Field("filter") filter: String,
    ): SearchResponse
}