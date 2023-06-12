package space.mrandika.wisnu.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

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