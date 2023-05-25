package space.mrandika.wisnu.model.poi

import com.google.gson.annotations.SerializedName

data class POIResponse(
	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int,

	@field:SerializedName("data")
	val data: POI? = null,
)
