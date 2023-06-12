package space.mrandika.wisnu.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import space.mrandika.wisnu.model.gallery.Gallery
import space.mrandika.wisnu.model.guide.Guide
import space.mrandika.wisnu.model.poi.Position
import space.mrandika.wisnu.model.ticket.TicketPrice

@Entity(tableName = "poi")
data class POI(
    @PrimaryKey
    @ColumnInfo("id")
    val id: Int,

    @ColumnInfo(name = "itinerary_id")
    val itineraryId: Int,

    @ColumnInfo("name")
    val name: String,

    @ColumnInfo("image")
    val image: String,

    @ColumnInfo("location")
    val location: String
)