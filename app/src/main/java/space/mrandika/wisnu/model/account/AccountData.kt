package space.mrandika.wisnu.model.account

import com.google.gson.annotations.SerializedName

data class AccountData(
    @field:SerializedName("account")
    val account: Account? = null,

    @field:SerializedName("preferences")
    val preferences: List<String> = emptyList(),
)
