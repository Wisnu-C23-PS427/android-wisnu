package space.mrandika.wisnu.service

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import space.mrandika.wisnu.model.poi.POIResponse
import space.mrandika.wisnu.model.poi.POIsResponse
import space.mrandika.wisnu.model.search.SearchResponse

interface POIService {
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

    @FormUrlEncoded
    @POST("poi/search")
    suspend fun searchPOI(
        @Field("keyword") keyword: String,
        @Field("filter") filter: String,
    ): SearchResponse

    @GET("poi/{id}")
    suspend fun getPOIdetail(
        @Path("id") id: String
    ): POIResponse
}