package space.mrandika.wisnu.ui.order.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import space.mrandika.wisnu.databinding.ItemOrderPoiBinding
import space.mrandika.wisnu.model.poi.POI

class OrderPOIAdapter(private val pois: List<POI>) : RecyclerView.Adapter<OrderPOIAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemOrderPoiBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val data = pois[position]
        viewHolder.bind(data)
    }

    override fun getItemCount() = pois.size

    inner class ViewHolder(private val binding: ItemOrderPoiBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: POI) {
            binding.apply {
                textPoiName.text = item.name
            }
        }
    }
}