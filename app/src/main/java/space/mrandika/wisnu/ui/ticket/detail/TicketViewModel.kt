package space.mrandika.wisnu.ui.ticket.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import space.mrandika.wisnu.model.ticket.Ticket
import space.mrandika.wisnu.repository.TicketRepository
import javax.inject.Inject

@HiltViewModel
class TicketViewModel @Inject constructor(
    private val repo: TicketRepository
): ViewModel() {
    private val _state = MutableStateFlow(TicketUiState())
    val state: StateFlow<TicketUiState> = _state

    fun getTicket(id: String) {
        viewModelScope.launch {
            Log.d("TicketViewModel", "Starting to get ticket for $id...")
            setError(false)
            setLoading(true)

            repo.getTicket(id).collect { result ->
                setLoading(false)

                result.onSuccess { response ->
                    response.data?.let { ticket -> setData(ticket) }
                    Log.d("TicketViewModel", response.toString())
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

    private fun setData(value: Ticket) {
        _state.update { currentState ->
            currentState.copy(ticket = value)
        }
    }
}