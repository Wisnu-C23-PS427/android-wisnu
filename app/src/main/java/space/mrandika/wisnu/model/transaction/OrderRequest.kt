package space.mrandika.wisnu.model.transaction

import com.google.gson.annotations.SerializedName
import space.mrandika.wisnu.model.guide.Guide
import space.mrandika.wisnu.model.poi.POI

data class OrderRequest(
    @field:SerializedName("ticket")
    val ticket: List<POITicketOrder> = emptyList(),

    @field:SerializedName("guide")
    val name: POIGuideOrder? = null,
)

data class POITicketOrder(
    @field:SerializedName("poi_id")
    val id: Int,

    @field:SerializedName("poi")
    val poi: POI,

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

    @field:SerializedName("guide")
    val guide: Guide,

    @field:SerializedName("min_multiplier")
    val minuteMultiplier: Int,
)