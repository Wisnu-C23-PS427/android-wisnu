package space.mrandika.wisnu.model.ticket

import com.google.gson.annotations.SerializedName

data class TicketResponse(
    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: Int,

    @field:SerializedName("data")
    val data: Ticket? = null,
)