package space.mrandika.wisnu.ui.event.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import space.mrandika.wisnu.model.event.Event
import space.mrandika.wisnu.repository.EventRepository
import space.mrandika.wisnu.ui.event.detail.EventUiState
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(
    private val repo: EventRepository
): ViewModel() {
    private val _state = MutableStateFlow(EventsUiState())
    val state: StateFlow<EventsUiState> = _state

    fun getEvents() {
        viewModelScope.launch {
            Log.d("EventsViewModel", "Starting to get events")
            setError(false)
            setLoading(true)

            repo.getEvents(false, 1, 10).collect { result ->
                setLoading(false)

                result.onSuccess { response ->
                    setData(response.data)
                    Log.d("EventsViewModel", response.toString())
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

    private fun setData(value: List<Event>) {
        if (value.isEmpty()) {
            setEmpty(true)
        } else {
            setEmpty(false)
        }

        _state.update { currentState ->
            currentState.copy(events = value)
        }
    }
}