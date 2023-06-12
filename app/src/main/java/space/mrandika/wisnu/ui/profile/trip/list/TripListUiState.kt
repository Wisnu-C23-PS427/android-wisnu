package space.mrandika.wisnu.ui.profile.trip.list

import space.mrandika.wisnu.entity.Trip

data class TripListUiState(
    val isError: Boolean = false,
    val isEmpty: Boolean = false,
    val trips: List<Trip> = emptyList()
)