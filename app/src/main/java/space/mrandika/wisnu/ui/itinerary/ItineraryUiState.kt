package space.mrandika.wisnu.ui.itinerary

import space.mrandika.wisnu.model.city.ItineraryItem
import space.mrandika.wisnu.model.guide.Guide
import space.mrandika.wisnu.model.poi.POI
import space.mrandika.wisnu.model.ticket.Ticket
import space.mrandika.wisnu.model.ticket.TicketPrice
import space.mrandika.wisnu.model.transaction.POIGuideOrder
import space.mrandika.wisnu.model.transaction.POITicketOrder

data class ItineraryUiState (
    val isLoading : Boolean = false,
    val isError : Boolean = false,
    val itineraries : List<ItineraryItem> = emptyList(),

    val cityId: Int = 0,
    val city : String = "",
    var day : Int = 0,

    val adult : Int = 1,
    val child : Int = 0,

    var tickets : List<POITicketOrder> = emptyList(),
    var guide : POIGuideOrder? = null,

    val duration : Int = 0
)