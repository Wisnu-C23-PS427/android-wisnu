package space.mrandika.wisnu.ui.ticket.list

import space.mrandika.wisnu.model.ticket.Ticket

data class TicketsUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isEmpty: Boolean = false,
    val filter: String = "active",
    val tickets: List<Ticket> = emptyList()
)
