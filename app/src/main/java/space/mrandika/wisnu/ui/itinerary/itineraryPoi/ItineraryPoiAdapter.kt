package space.mrandika.wisnu.ui.itinerary.itineraryPoi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import space.mrandika.wisnu.databinding.CardItineraryItemsBinding
import space.mrandika.wisnu.model.poi.POI

class ItineraryPoiAdapter(private val itinerary: List<POI>): RecyclerView.Adapter<ItineraryPoiAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardItineraryItemsBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val data = itinerary[position]
        viewHolder.bind(data)
    }

    override fun getItemCount(): Int = itinerary.size

    inner class ViewHolder(private val binding: CardItineraryItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: POI) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(item.image)
                    .into(imagePoiItinerary)
                tvItineraryPlacename.text = item.name
                tvItineraryLocation.text = item.location
            }
        }
    }
}