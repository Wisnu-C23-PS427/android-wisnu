package space.mrandika.wisnu.model.ticket

import com.google.gson.annotations.SerializedName

data class TicketsResponse(
	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int,

	@field:SerializedName("data")
	val data: List<Ticket> = emptyList(),
)
