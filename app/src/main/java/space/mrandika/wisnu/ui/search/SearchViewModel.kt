package space.mrandika.wisnu.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import space.mrandika.wisnu.model.category.Category
import space.mrandika.wisnu.model.city.City
import space.mrandika.wisnu.model.poi.POI
import space.mrandika.wisnu.repository.POIRepository
import space.mrandika.wisnu.repository.SearchRepository
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repo: SearchRepository,
    private val poiRepo: POIRepository
): ViewModel() {
    private val _state = MutableStateFlow(SearchUiState())
    val state: StateFlow<SearchUiState> = _state

    suspend fun getCategories() {
        Log.d("SearchViewModel", "Starting to get categories...")

        poiRepo.getCategories().collect { result ->
            result.onSuccess { response ->
                setCategories(response.data)
                Log.d("SearchViewModel-getCategories", response.toString())
            }

            result.onFailure {
                setError(true)
            }
        }
    }

    suspend fun getSearchResult(keyword: String, filter: String) {
        Log.d("SearchViewModel", "Starting to get result `$keyword` filter: $filter...")
        setError(false)
        setLoading(true)

        repo.getSearchResult(keyword, filter).collect { result ->
            setLoading(false)

            result.onSuccess { response ->
                response.data?.let {
                    setCities(it.cities)
                    setPois(it.poi)
                }

                Log.d("SearchViewModel-getSearchResult", response.toString())
            }

            result.onFailure {
                setError(true)
            }
        }
    }

    suspend fun getDiscoveryResult() {
        Log.d("SearchViewModel", "Starting to get discovery...")
        setError(false)
        setLoading(true)

        repo.getDiscoveryResult().collect { result ->
            setLoading(false)

            result.onSuccess { response ->
                response.data?.let {
                    setCities(it.cities)
                    setPois(it.poi)
                }

                Log.d("SearchViewModel-getDiscoveryResult", response.toString())
            }

            result.onFailure {
                setError(true)
            }
        }
    }

    private fun setLoading(value: Boolean) {
        _state.update { currentState ->
            currentState.copy(isLoading = value)
        }
    }

    private fun setError(value: Boolean) {
        _state.update { currentState ->
            currentState.copy(isError = value)
        }
    }

    fun setCategories(value: List<Category>) {
        _state.update { currentState ->
            currentState.copy(categories = value)
        }
    }

    fun setKeyword(value: String) {
        _state.update { currentState ->
            currentState.copy(keyword = value)
        }
    }

    fun setFilter(value: String) {
        _state.update { currentState ->
            currentState.copy(filter = value)
        }
    }

    private fun setCities(value: List<City>) {
        _state.update { currentState ->
            currentState.copy(cities = value)
        }
    }

    private fun setPois(value: List<POI>) {
        _state.update { currentState ->
            currentState.copy(pois = value)
        }
    }
}