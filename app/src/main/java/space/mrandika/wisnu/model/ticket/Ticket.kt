package space.mrandika.wisnu.model.ticket

import com.google.gson.annotations.SerializedName
import space.mrandika.wisnu.model.poi.POI

data class Ticket(
    @field:SerializedName("valid_date")
    val validDate: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("total_price")
    val totalPrice: Int? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("poi")
    val poi: POI? = null,

    @field:SerializedName("is_active")
    val isActive: Boolean? = null,

    @field:SerializedName("adult")
    val adult: List<TicketItem> = emptyList(),

    @field:SerializedName("child")
    val child: List<TicketItem> = emptyList()
)

data class TicketItem(
    @field:SerializedName("price")
    val price: Int? = null,

    @field:SerializedName("name")
    val name: String? = null
)