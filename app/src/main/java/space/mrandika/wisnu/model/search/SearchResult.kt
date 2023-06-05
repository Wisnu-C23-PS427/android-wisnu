package space.mrandika.wisnu.model.search

import com.google.gson.annotations.SerializedName
import space.mrandika.wisnu.model.city.City
import space.mrandika.wisnu.model.poi.POI

data class SearchResult(
    @field:SerializedName("cities")
    val cities: List<City> = emptyList(),

    @field:SerializedName("poi")
    val poi: List<POI> = emptyList()
)
