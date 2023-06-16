package space.mrandika.wisnu.ui.profile.trip.detail

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.ItemItineraryBinding
import space.mrandika.wisnu.entity.ItineraryWithPOIs
import space.mrandika.wisnu.ui.poi.detail.POIDetailActivity

class ItinerariesAdapter(private val itineraries: List<ItineraryWithPOIs>, private val context: Context) : RecyclerView.Adapter<ItinerariesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemItineraryBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val data = itineraries[position]
        viewHolder.bind(data)
    }

    override fun getItemCount() = itineraries.size

    inner class ViewHolder(private val binding: ItemItineraryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItineraryWithPOIs) {
            binding.apply {
                textDayNum.text = String.format(context.resources.getString(R.string.day_num, item.itinerary.day.toString()))

                val poisAdapter = POIsAdapter(item.poiEntity, context)

                rvPoi.layoutManager = LinearLayoutManager(context)
                rvPoi.adapter = poisAdapter
                rvPoi.isNestedScrollingEnabled = false

                poisAdapter.setOnItemClickCallback(object : POIsAdapter.OnItemClickCallback {
                    override fun onItemClicked(id: Int) {
                        val intent = Intent(context, POIDetailActivity::class.java).apply {
                            putExtra("id", id)
                        }

                        context.startActivity(intent)
                    }
                })
            }
        }
    }
}