package space.mrandika.wisnu.model.event

import com.google.gson.annotations.SerializedName

data class Event(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("location")
	val location: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
