package space.mrandika.wisnu.ui.itinerary.itineraryticket

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import space.mrandika.wisnu.databinding.CardItineraryItemsBinding
import space.mrandika.wisnu.databinding.TicketItemsBinding
import space.mrandika.wisnu.model.poi.POI
import space.mrandika.wisnu.ui.itinerary.ItineraryViewModel

class ItineraryTicketAdapter(private val itinerary: List<POI>, private val viewModel: ItineraryViewModel): RecyclerView.Adapter<ItineraryTicketAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = TicketItemsBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val data = itinerary[position]
        viewHolder.bind(data)
    }

    override fun getItemCount(): Int = itinerary.size

    inner class ViewHolder(private val binding: TicketItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: POI) {
            binding.apply {
                val childTicket = item.tickets?.childPrice
                val adultTicket = item.tickets?.adultPrice
                tvPlaceName.text = item.name
                tvAdultPrice.text = childTicket.toString()
                tvChildPrice.text = adultTicket.toString()

                viewModel.apply {
                    var totalTicket = state.value.ticketTotal
                    val child = state.value.child
                    val adult = state.value.adult
                    if (childTicket != null && adultTicket != null) {
                        totalTicket += (childTicket * child) + (adultTicket * adult)
                        Log.d("total ticket ", totalTicket.toString())
                    }
                    setTotalTicket(totalTicket)
                }
            }
        }
    }
}