package space.mrandika.wisnu.model.transaction

import com.google.gson.annotations.SerializedName
import space.mrandika.wisnu.model.ticket.Ticket

data class TicketOrderResponse(
	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int,

	@field:SerializedName("data")
	val data: Ticket? = null,
)