package space.mrandika.wisnu.ui.home

import space.mrandika.wisnu.model.event.Event
import space.mrandika.wisnu.model.poi.POI

data class HomeState (
    val isLoading : Boolean = false,
    val isError : Boolean  = false,
    val recommendation : List<POI> = emptyList(),
    val events : List<Event> = emptyList()
)