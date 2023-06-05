package space.mrandika.wisnu.model.city

import com.google.gson.annotations.SerializedName

data class CityResponse(
    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: Int,

    @field:SerializedName("data")
    val data: City? = null,
)