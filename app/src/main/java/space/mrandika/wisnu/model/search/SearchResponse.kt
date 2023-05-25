package space.mrandika.wisnu.model.search

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: Int,

    @field:SerializedName("data")
    val data: SearchResponse? = null,
)
