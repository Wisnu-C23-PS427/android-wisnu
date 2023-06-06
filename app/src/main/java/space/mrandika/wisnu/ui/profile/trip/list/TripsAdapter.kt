package space.mrandika.wisnu.ui.profile.trip.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import space.mrandika.wisnu.databinding.ItemTripBinding
import space.mrandika.wisnu.entity.Trip

class TripsAdapter(private val trips: List<Trip>) : RecyclerView.Adapter<TripsAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTripBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val data = trips[position]
        viewHolder.bind(data)
    }

    override fun getItemCount() = trips.size

    inner class ViewHolder(private val binding: ItemTripBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Trip) {
            binding.apply {
                textTripName.text = item.city_name
                textTripDate.text = item.createdAt

                itemView.setOnClickListener {
                    onItemClickCallback.onItemClicked(item.id, item.city_name)
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(id: Int, name: String)
    }
}