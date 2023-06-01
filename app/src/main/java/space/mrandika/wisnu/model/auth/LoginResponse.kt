package space.mrandika.wisnu.model.auth

import com.google.gson.annotations.SerializedName

data class LoginResponse(
	@field:SerializedName("status")
	val status: Int,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("data")
	val data: LoginData? = null,
)

data class LoginData(
	@field:SerializedName("token")
	val token: String? = null
)
