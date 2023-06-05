package space.mrandika.wisnu.model.auth

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("phone_number")
    val phoneNumber: String,

    @field:SerializedName("password")
    val password: String,

    @field:SerializedName("interest")
    val interest: List<String>,
)
