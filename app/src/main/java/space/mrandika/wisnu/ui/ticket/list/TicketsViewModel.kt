package space.mrandika.wisnu.ui.ticket.list

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import space.mrandika.wisnu.model.ticket.Ticket
import space.mrandika.wisnu.repository.TicketRepository
import javax.inject.Inject

@HiltViewModel
class TicketsViewModel @Inject constructor(
    private val repo: TicketRepository
): ViewModel() {
    private val _state = MutableStateFlow(TicketsUiState())
    val state: StateFlow<TicketsUiState> = _state

    suspend fun getTickets(filter: String) {
        Log.d("TicketsViewModel", "Starting to get tickets for $filter...")
        setError(false)
        setLoading(true)

        repo.getTickets(filter).collect { result ->
            setLoading(false)

            result.onSuccess { response ->
                setData(response.data)
                Log.d("TicketsViewModel", response.toString())
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

    fun setFilter(value: String) {
        _state.update { currentState ->
            currentState.copy(filter = value)
        }
    }

    private fun setData(value: List<Ticket>) {
        if (value.isEmpty()) {
            _state.update { currentState ->
                currentState.copy(isEmpty = true)
            }
        }

        _state.update { currentState ->
            currentState.copy(tickets = value)
        }
    }
}