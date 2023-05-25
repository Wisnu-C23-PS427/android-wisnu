package space.mrandika.wisnu.model.transaction

import com.google.gson.annotations.SerializedName

data class Transaction(
    @field:SerializedName("is_ticket_order")
    val isTicketOrder: Boolean? = null,

    @field:SerializedName("price")
    val price: Int? = null,

    @field:SerializedName("is_guide_order")
    val isGuideOrder: Boolean? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("id")
    val id: Int? = null
)

