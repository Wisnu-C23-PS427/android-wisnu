package space.mrandika.wisnu.model.city

import com.google.gson.annotations.SerializedName
import space.mrandika.wisnu.model.poi.POI

data class CityItinerariesResponse(

	@field:SerializedName("data")
	val data: List<ItineraryItem> = emptyList(),

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int
)

data class ItineraryItem(

	@field:SerializedName("poi")
	val poi: List<POI> = emptyList(),

	@field:SerializedName("day")
	val day: Int? = null
)
