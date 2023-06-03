package space.mrandika.wisnu.model.transaction

import com.google.gson.annotations.SerializedName

data class TransactionResponse(
    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: Int,

    @field:SerializedName("data")
    val data: Transaction? = null,
)