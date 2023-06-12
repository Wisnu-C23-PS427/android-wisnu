package space.mrandika.wisnu.ui.order.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import space.mrandika.wisnu.model.transaction.Transaction
import space.mrandika.wisnu.repository.TransactionRepository
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val repo: TransactionRepository
): ViewModel() {
    private val _state = MutableStateFlow(OrderUiState())
    val state: StateFlow<OrderUiState> = _state

    fun getOrder(id: Int) {
        viewModelScope.launch {
            Log.d("OrderViewModel", "Starting to get order for $id...")
            setError(false)
            setLoading(true)

            repo.getTransaction(id).collect { result ->
                setLoading(false)

                result.onSuccess { response ->
                    response.data?.let { order -> setData(order) }
                    Log.d("OrderViewModel", response.toString())
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

    private fun setData(value: Transaction) {
        _state.update { currentState ->
            currentState.copy(order = value)
        }
    }
}