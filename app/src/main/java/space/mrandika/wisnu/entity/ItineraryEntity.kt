package space.mrandika.wisnu.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "itinerary")
data class Itinerary(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "trip_id")
    val tripId: Int,

    @ColumnInfo(name = "day")
    val day: Int,
)

data class ItineraryWithPOIs(
    @Embedded val itinerary: Itinerary,

    @Relation(
        parentColumn = "id",
        entityColumn = "itinerary_id"
    )
    val poiEntity: List<POIEntity>
)