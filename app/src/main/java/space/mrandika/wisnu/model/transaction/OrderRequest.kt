package space.mrandika.wisnu.model.transaction

import com.google.gson.annotations.SerializedName

data class OrderRequest(
    @field:SerializedName("ticket")
    val ticket: List<POITicketOrder> = emptyList(),

    @field:SerializedName("guide")
    val name: POIGuideOrder? = null,
)

data class POITicketOrder(
    @field:SerializedName("poi_id")
    val id: Int,

    @field:SerializedName("num_adult")
    val numOfAdult: Int,

    @field:SerializedName("num_child")
    val numOfChild: Int,
)

data class POIGuideOrder(
    @field:SerializedName("poi_id")
    val id: Int,

    @field:SerializedName("guide_id")
    val guideId: Int,

    @field:SerializedName("min_multiplier")
    val minuteMultiplier: Int,
)