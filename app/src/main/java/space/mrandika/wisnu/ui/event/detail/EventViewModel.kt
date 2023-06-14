package space.mrandika.wisnu.ui.event.detail

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
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val repo: EventRepository
): ViewModel() {
    private val _state = MutableStateFlow(EventUiState())
    val state: StateFlow<EventUiState> = _state

    fun getEvent(id: Int) {
        viewModelScope.launch {
            Log.d("EventViewModel", "Starting to get event for $id...")
            setError(false)
            setLoading(true)

            repo.getEvent(id).collect { result ->
                setLoading(false)

                result.onSuccess { response ->
                    response.data?.let { event -> setData(event) }
                    Log.d("EventViewModel", response.toString())
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

    private fun setData(value: Event) {
        _state.update { currentState ->
            currentState.copy(event = value)
        }
    }
}