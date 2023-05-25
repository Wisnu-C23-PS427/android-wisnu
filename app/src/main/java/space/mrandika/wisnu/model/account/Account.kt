package space.mrandika.wisnu.model.account

import com.google.gson.annotations.SerializedName

data class Account(
    @field:SerializedName("phone")
    val phone: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("email")
    val email: String? = null
)
