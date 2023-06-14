package space.mrandika.wisnu.ui.event.list

import space.mrandika.wisnu.model.event.Event

data class EventsUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isEmpty: Boolean = false,
    val events: List<Event> = emptyList()
)
