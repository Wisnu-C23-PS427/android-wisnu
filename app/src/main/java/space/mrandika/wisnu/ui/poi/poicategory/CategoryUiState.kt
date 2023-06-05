package space.mrandika.wisnu.ui.poi.poicategory

import space.mrandika.wisnu.model.poi.POI
import space.mrandika.wisnu.model.poi.POIResponse

data class CategoryUiState (
    val isLoading :Boolean = false,
    val isError : Boolean = false,
    val CategoryResult : List<POI>? = emptyList()
)