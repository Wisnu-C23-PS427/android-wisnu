package space.mrandika.wisnu.model.poi

import com.google.gson.annotations.SerializedName

data class POIsResponse(
	@field:SerializedName("status")
	val status: Int,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("preview")
	val preview: Boolean? = null,

	@field:SerializedName("size")
	val size: Int? = null,

	@field:SerializedName("page")
	val page: Int? = null,

	@field:SerializedName("data")
	val data: List<POI> = emptyList(),
)
