package space.mrandika.wisnu.ui.poi.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import space.mrandika.wisnu.model.poi.POI
import space.mrandika.wisnu.repository.POIRepository
import javax.inject.Inject

@HiltViewModel
class POIsViewModel @Inject constructor(
    private val repo: POIRepository
): ViewModel() {
    private val _state = MutableStateFlow(POIsUiState())
    val state: StateFlow<POIsUiState> = _state

    fun getPois() {
        viewModelScope.launch {
            Log.d("POIsViewModel", "Starting to get pois")
            setError(false)
            setLoading(true)

            repo.getRecommendedPOIs(false, 1, 10).collect { result ->
                setLoading(false)

                result.onSuccess { response ->
                    setData(response.data)
                    Log.d("POIsViewModel", response.toString())
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

    private fun setData(value: List<POI>) {
        if (value.isEmpty()) {
            setEmpty(true)
        } else {
            setEmpty(false)
        }

        _state.update { currentState ->
            currentState.copy(pois = value)
        }
    }
}