package space.mrandika.wisnu.model.event

import com.google.gson.annotations.SerializedName

data class EventResponse(
	@field:SerializedName("preview")
	val preview: Boolean? = null,

	@field:SerializedName("size")
	val size: Int? = null,

	@field:SerializedName("data")
	val data: List<Event> = emptyList(),

	@field:SerializedName("page")
	val page: Int? = null,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int
)
