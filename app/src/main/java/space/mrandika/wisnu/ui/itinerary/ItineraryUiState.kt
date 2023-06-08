package space.mrandika.wisnu.ui.itinerary

import space.mrandika.wisnu.model.guide.Guide
import space.mrandika.wisnu.model.poi.POI
import space.mrandika.wisnu.model.ticket.Ticket
import space.mrandika.wisnu.model.ticket.TicketPrice

data class ItineraryUiState (
    val isLoading : Boolean = false,
    val isError : Boolean = false,
    val PoiData : List<POI>? = emptyList(),
    val city : String = "",
    var day : Int = 0,
    val adult : Int = 1,
    val child : Int = 0,
    var includeTicket : Boolean = false,
    var includeGuide : Boolean = false,
    var Ticket : List<POI> = emptyList(),
    var guide : Int = 2000,
    val ticketTotal : Int = 0,
    val totalCost : Int = 0,
    val duration : Int = 0
)