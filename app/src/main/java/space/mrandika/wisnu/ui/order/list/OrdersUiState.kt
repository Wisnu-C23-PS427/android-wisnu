package space.mrandika.wisnu.ui.order.list

import space.mrandika.wisnu.model.transaction.Transaction

data class OrdersUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isEmpty: Boolean = false,
    val filter: String = "all",
    val tickets: List<Transaction> = emptyList()
)