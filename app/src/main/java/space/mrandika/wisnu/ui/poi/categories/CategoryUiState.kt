package space.mrandika.wisnu.ui.poi.categories

import space.mrandika.wisnu.model.poi.POI

data class CategoryUiState (
    val isLoading :Boolean = false,
    val isError : Boolean = false,
    val isEmpty : Boolean = false,
    val categories : List<POI> = emptyList()
)