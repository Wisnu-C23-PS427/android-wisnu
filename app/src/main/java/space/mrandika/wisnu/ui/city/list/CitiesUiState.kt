package space.mrandika.wisnu.ui.city.list

import space.mrandika.wisnu.model.city.City

data class CitiesUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isEmpty: Boolean = false,
    val cities: List<City> = emptyList()
)
