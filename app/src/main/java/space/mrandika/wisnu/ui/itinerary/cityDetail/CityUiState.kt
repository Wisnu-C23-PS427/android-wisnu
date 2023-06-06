package space.mrandika.wisnu.ui.itinerary.cityDetail

import space.mrandika.wisnu.model.city.City

data class CityUiState (
    val isLoading : Boolean = false,
    val isError: Boolean = false ,
    val isEmpty: Boolean = false,
    val CityResult : City? = null
        )