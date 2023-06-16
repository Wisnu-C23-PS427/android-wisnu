package space.mrandika.wisnu.ui.order.list

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import space.mrandika.wisnu.model.transaction.Transaction
import space.mrandika.wisnu.repository.TransactionRepository
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val repo: TransactionRepository
): ViewModel() {
    private val _state = MutableStateFlow(OrdersUiState())
    val state: StateFlow<OrdersUiState> = _state

    suspend fun getTickets(filter: String) {
        Log.d("TicketsViewModel", "Starting to get orders for $filter...")
        setError(false)
        setLoading(true)

        repo.getTransactions(filter).collect { result ->
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

    private fun setData(value: List<Transaction>) {
        _state.update { currentState ->
            currentState.copy(isEmpty = value.isEmpty())
        }

        _state.update { currentState ->
            currentState.copy(tickets = value)
        }
    }
}