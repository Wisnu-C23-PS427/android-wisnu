package space.mrandika.wisnu.model.category

import com.google.gson.annotations.SerializedName

data class CategoriesResponse(
    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: Int,

    @field:SerializedName("data")
    val data: List<Category> = emptyList(),
)