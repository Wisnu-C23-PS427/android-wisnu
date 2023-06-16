package space.mrandika.wisnu.model.auth

import com.google.gson.annotations.SerializedName
import space.mrandika.wisnu.model.account.Account

data class RegisterResponse(
	@field:SerializedName("status")
	val status: Int,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("data")
	val data: RegisterData? = null,
)

data class RegisterData(

	@field:SerializedName("preference")
	val preference: List<String?>? = null,

	@field:SerializedName("account")
	val account: Account? = null
)
