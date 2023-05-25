package space.mrandika.wisnu.service

import retrofit2.http.GET
import retrofit2.http.Query
import space.mrandika.wisnu.model.event.EventResponse

interface EventService {
    @GET("events")
    suspend fun getEvents(
        @Query("preview") preview: Boolean,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): EventResponse
}