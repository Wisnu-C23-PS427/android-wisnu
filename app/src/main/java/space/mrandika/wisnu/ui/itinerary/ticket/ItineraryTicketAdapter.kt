package space.mrandika.wisnu.ui.itinerary.ticket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import space.mrandika.wisnu.databinding.ItemTicketTransactionBinding
import space.mrandika.wisnu.model.poi.POI

class ItineraryTicketAdapter(private val itinerary: List<POI>): RecyclerView.Adapter<ItineraryTicketAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTicketTransactionBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val data = itinerary[position]
        viewHolder.bind(data)
    }

    override fun getItemCount(): Int = itinerary.size

    inner class ViewHolder(private val binding: ItemTicketTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: POI) {
            binding.apply {
                val childTicket = item.tickets?.childPrice
                val adultTicket = item.tickets?.adultPrice

                tvPlaceName.text = item.name
                tvAdultPrice.text = childTicket.toString()
                tvChildPrice.text = adultTicket.toString()
            }
        }
    }
}