package space.mrandika.wisnu.model.transaction

import com.google.gson.annotations.SerializedName
import space.mrandika.wisnu.model.guide.Guide
import space.mrandika.wisnu.model.ticket.Ticket

data class OrderResponse(
	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int,

	@field:SerializedName("data")
	val data: OrderData? = null,
)

data class OrderData(
	@field:SerializedName("ticket")
	val ticket: List<Ticket> = emptyList(),

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("guide")
	val guide: Guide? = null
)




