package space.mrandika.wisnu.ui.order.detail

import space.mrandika.wisnu.model.transaction.Transaction

data class OrderUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val order: Transaction? = null
)