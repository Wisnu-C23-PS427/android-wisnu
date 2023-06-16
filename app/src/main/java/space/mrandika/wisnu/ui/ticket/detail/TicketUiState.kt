package space.mrandika.wisnu.ui.ticket.detail

import space.mrandika.wisnu.model.ticket.Ticket

data class TicketUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val ticket: Ticket? = null
)
