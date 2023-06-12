package space.mrandika.wisnu.model.category

import com.google.gson.annotations.SerializedName

data class Category(

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int? = null
)
