package space.mrandika.wisnu.model.transaction

import com.google.gson.annotations.SerializedName
import space.mrandika.wisnu.model.guide.Guide
import space.mrandika.wisnu.model.poi.POI

data class Transaction(
    @field:SerializedName("pois")
    val pois: List<POI> = emptyList(),

    @field:SerializedName("guide")
    val guide: Guide? = null,

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

