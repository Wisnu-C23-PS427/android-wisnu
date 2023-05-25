package space.mrandika.wisnu.service

import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Path
import space.mrandika.wisnu.model.ticket.TicketsResponse

interface TicketService {
    @GET("tickets")
    suspend fun getTickets(
        @Field("filter") filter: String,
    ): TicketsResponse

    @GET("ticket/{id}")
    suspend fun getTicket(
        @Path("id") id: String,
    ): TicketsResponse
}