package space.mrandika.wisnu.model.city

import com.google.gson.annotations.SerializedName

data class CitiesResponse(
	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int,

	@field:SerializedName("preview")
	val preview: Boolean? = null,

	@field:SerializedName("size")
	val size: Int? = null,

	@field:SerializedName("page")
	val page: Int? = null,

	@field:SerializedName("data")
	val data: List<City> = emptyList(),
)
