package space.mrandika.wisnu.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.io.Serializable

@Entity(tableName = "trip")
data class Trip(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "city_name")
    val city_name: String,

    @ColumnInfo(name = "created_at")
    val createdAt: String,
)

data class TripWithItineraries(
    @Embedded val trip: Trip,

    @Relation(
        parentColumn = "id",
        entityColumn = "trip_id"
    )
    val itineraries: List<Itinerary>
)
