package space.mrandika.wisnu.model.auth

import com.google.gson.annotations.SerializedName

data class LogoutResponse(
    @field:SerializedName("status")
    val status: Int,

    @field:SerializedName("message")
    val message: String
)