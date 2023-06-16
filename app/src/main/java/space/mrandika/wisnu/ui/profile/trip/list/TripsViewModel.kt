package space.mrandika.wisnu.ui.profile.trip.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import space.mrandika.wisnu.entity.Trip
import space.mrandika.wisnu.repository.TripRepository
import javax.inject.Inject

@HiltViewModel
class TripsViewModel @Inject constructor(
    private val repo: TripRepository
): ViewModel() {
    private val _state = MutableStateFlow(TripListUiState())
    val state: StateFlow<TripListUiState> = _state

    fun getTrips() {
        viewModelScope.launch {
            Log.d("TripsViewModel", "Starting to get saved trips...")

            repo.getTrips().collect { result ->
                result.onSuccess { response ->
                    setData(response)

                    Log.d("TripsViewModel", response.toString())
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

    private fun setEmpty(value: Boolean) {
        _state.update { currentState ->
            currentState.copy(isEmpty = value)
        }
    }

    private fun setData(value: List<Trip>) {
        _state.update { currentState ->
            currentState.copy(trips = value)
        }
    }
}