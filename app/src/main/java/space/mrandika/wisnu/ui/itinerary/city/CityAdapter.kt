package space.mrandika.wisnu.ui.itinerary.city

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import space.mrandika.wisnu.BuildConfig
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.ItemCityPoiBinding
import space.mrandika.wisnu.model.poi.POI

class CityAdapter (private val POI : List<POI>) : RecyclerView.Adapter<CityAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityAdapter.ViewHolder {
        val binding = ItemCityPoiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CityAdapter.ViewHolder, position: Int) {
        val data = POI[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = POI.size

    inner class ViewHolder(private val binding: ItemCityPoiBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: POI) {
            binding.apply {
                if (BuildConfig.IS_SERVICE_UP) {
                    Glide.with(ivPoi)
                        .load(item.image)
                        .into(ivPoi)
                } else {
                   ivPoi.setImageResource(R.drawable.mock_attraction_item)
                }
            }
        }
    }
}