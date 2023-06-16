package space.mrandika.wisnu.ui.profile.trip.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import space.mrandika.wisnu.entity.ItineraryWithPOIs
import space.mrandika.wisnu.repository.TripRepository
import javax.inject.Inject

@HiltViewModel
class TripViewModel @Inject constructor(
    private val repo: TripRepository
): ViewModel() {
    private val _state = MutableStateFlow(TripUiState())
    val state: StateFlow<TripUiState> = _state

    fun getItineraries(tripId: Int) {
        viewModelScope.launch {
            Log.d("TripViewModel", "Starting to get itineraries for trip id $tripId...")

            repo.getItinerariesWithPOIs(tripId).collect { result ->
                result.onSuccess { response ->
                    setData(response)

                    Log.d("TripViewModel", response.toString())
                }

                result.onFailure {
                    setError(true)
                }
            }
        }
    }

    private fun setError(value: Boolean) {
        _state.update { currentState ->
            currentState.copy(isError = value)
        }
    }

    private fun setData(value: List<ItineraryWithPOIs>) {
        _state.update { currentState ->
            currentState.copy(itineraries = value)
        }
    }
}