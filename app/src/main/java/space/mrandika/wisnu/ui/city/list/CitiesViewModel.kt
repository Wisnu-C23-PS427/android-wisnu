package space.mrandika.wisnu.ui.city.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import space.mrandika.wisnu.model.city.City
import space.mrandika.wisnu.repository.CityRepository
import javax.inject.Inject

@HiltViewModel
class CitiesViewModel @Inject constructor(
    private val repo: CityRepository
): ViewModel() {
    private val _state = MutableStateFlow(CitiesUiState())
    val state: StateFlow<CitiesUiState> = _state

    fun getCities() {
        viewModelScope.launch {
            Log.d("CitiesViewModel", "Starting to get cities")
            setError(false)
            setLoading(true)

            repo.getCities(false, 1, 10).collect { result ->
                setLoading(false)

                result.onSuccess { response ->
                    setData(response.data)
                    Log.d("CitiesViewModel", response.toString())
                }

                result.onFailure {
                    setError(true)
                }
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

    private fun setEmpty(value: Boolean) {
        _state.update { currentState ->
            currentState.copy(isEmpty = value)
        }
    }

    private fun setData(value: List<City>) {
        if (value.isEmpty()) {
            setEmpty(true)
        } else {
            setEmpty(false)
        }

        _state.update { currentState ->
            currentState.copy(cities = value)
        }
    }
}