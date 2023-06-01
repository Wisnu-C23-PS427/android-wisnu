package space.mrandika.wisnu.model.event

import com.google.gson.annotations.SerializedName

data class EventResponse(

    @field:SerializedName("data")
    val data: Event? = null,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: Int
)