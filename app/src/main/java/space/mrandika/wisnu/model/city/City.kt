package space.mrandika.wisnu.model.city

import com.google.gson.annotations.SerializedName
import space.mrandika.wisnu.model.poi.POI

data class City(
    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("location")
    val location: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("poi")
    val poi: List<POI>? = emptyList(),

    @field:SerializedName("id")
    val id: Int? = null
)
