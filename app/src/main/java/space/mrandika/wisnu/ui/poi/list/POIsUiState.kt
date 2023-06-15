package space.mrandika.wisnu.ui.poi.list

import space.mrandika.wisnu.model.poi.POI

data class POIsUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isEmpty: Boolean = false,
    val pois: List<POI> = emptyList()
)
