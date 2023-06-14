package space.mrandika.wisnu.ui.itinerary.poi

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.ItemItineraryBinding
import space.mrandika.wisnu.model.city.ItineraryItem

class ItineraryAdapter(private val itineraries: List<ItineraryItem>, private val context: Context): RecyclerView.Adapter<ItineraryAdapter.ViewHolder>() {
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
        fun bind(item: ItineraryItem) {
            binding.apply {
                textDayNum.text = String.format(context.resources.getString(R.string.day_num, item.day.toString()))

                val poisAdapter = POIsAdapter(item.poi, context)

                rvPoi.layoutManager = LinearLayoutManager(context)
                rvPoi.adapter = poisAdapter
                rvPoi.isNestedScrollingEnabled = false

//                poisAdapter.setOnItemClickCallback(object : POIsAdapter.OnItemClickCallback {
//                    override fun onItemClicked(id: Int) {
//                        val intent = Intent(context, POIDetailActivity::class.java).apply {
//                            putExtra("id", id)
//                        }
//
//                        context.startActivity(intent)
//                    }
//                })
            }
        }
    }
}