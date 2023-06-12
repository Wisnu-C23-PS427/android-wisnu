package space.mrandika.wisnu.model.poi

import com.google.gson.annotations.SerializedName
import space.mrandika.wisnu.model.gallery.Gallery
import space.mrandika.wisnu.model.guide.Guide
import space.mrandika.wisnu.model.ticket.TicketPrice
import java.io.Serializable

data class POI(
    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("tickets")
    val tickets: TicketPrice? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("location")
    val location: String? = null,

    @field:SerializedName("background_story")
    val backgroundStory: String? = null,

    @field:SerializedName("position")
    val position: Position? = null,

    @field:SerializedName("guides")
    val guides: List<Guide>? = null,

    @field:SerializedName("galleries")
    val galleries: List<Gallery>? = null,

    @field:SerializedName("id")
    val id: Int? = null
)

data class Position(
    @field:SerializedName("long")
    val long: Double? = null,

    @field:SerializedName("lat")
    val lat: Double? = null
)