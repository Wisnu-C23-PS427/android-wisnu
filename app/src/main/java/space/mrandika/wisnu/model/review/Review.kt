package space.mrandika.wisnu.model.review

import com.google.gson.annotations.SerializedName

data class Review(
    @field:SerializedName("star")
    val star: Int? = null,

    @field:SerializedName("review")
    val review: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int? = null
)
