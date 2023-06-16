package space.mrandika.wisnu.ui.home

import space.mrandika.wisnu.model.category.Category
import space.mrandika.wisnu.model.event.Event
import space.mrandika.wisnu.model.poi.POI

data class HomeUiState (
    val isLoading : Boolean = false,
    val isError : Boolean  = false,
    val categories : List<Category> = emptyList(),
    val recommendations : List<POI> = emptyList(),
    val events : List<Event> = emptyList()
)