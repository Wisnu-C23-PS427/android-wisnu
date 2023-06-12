package space.mrandika.wisnu.ui.profile.trip.detail

import space.mrandika.wisnu.entity.ItineraryWithPOIs
import space.mrandika.wisnu.entity.Trip

data class TripUiState(
    val isError: Boolean = false,
    val itineraries: List<ItineraryWithPOIs> = emptyList()
)
