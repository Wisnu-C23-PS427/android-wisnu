package space.mrandika.wisnu.model.account

import com.google.gson.annotations.SerializedName

data class AccountResponse(
    @field:SerializedName("status")
    val status: Int,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("data")
    val data: AccountData? = null,
)
