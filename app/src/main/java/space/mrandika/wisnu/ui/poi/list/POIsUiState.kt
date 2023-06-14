package space.mrandika.wisnu.ui.poi.list

import space.mrandika.wisnu.model.poi.POI
import space.mrandika.wisnu.model.event.Event

data class POIsUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isEmpty: Boolean = false,
    val pois: List<POI> = emptyList()
)
