package space.mrandika.wisnu.service

import retrofit2.http.GET
import retrofit2.http.Path
import space.mrandika.wisnu.model.guide.GuideResponse

interface GuideService {
    @GET("guide/{id}")
    suspend fun getGuide(
        @Path("id") id: String
    ): GuideResponse
}