package space.mrandika.wisnu.model.ticket

import com.google.gson.annotations.SerializedName

data class TicketPrice(
    @field:SerializedName("is_ticketing_enabled")
    val isTicketingEnabled: Boolean,

    @field:SerializedName("adult_price")
    val adultPrice: Int? = null,

    @field:SerializedName("child_price")
    val childPrice: Int? = null,
)