package space.mrandika.wisnu.ui.search

import space.mrandika.wisnu.model.category.Category
import space.mrandika.wisnu.model.city.City
import space.mrandika.wisnu.model.poi.POI

data class SearchUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isEmpty: Boolean = false,
    val categories: List<Category> = emptyList(),
    val keyword: String = "",
    val filter: String = "all",
    val cities: List<City> = emptyList(),
    val pois: List<POI> = emptyList(),
)