package space.mrandika.wisnu.ui.city.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import space.mrandika.wisnu.BuildConfig
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.ItemAttractionFullBinding
import space.mrandika.wisnu.model.city.City
import space.mrandika.wisnu.model.event.Event

class CitiesAdapter(private val cities: List<City>) : RecyclerView.Adapter<CitiesAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAttractionFullBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val data = cities[position]
        viewHolder.bind(data)
    }

    override fun getItemCount() = cities.size

    inner class ViewHolder(private val binding: ItemAttractionFullBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: City) {
            binding.apply {
                if (BuildConfig.IS_SERVICE_UP) {
                    Glide.with(itemView.context)
                        .load(item.image)
                        .into(ivCardImage)
                } else {
                    ivCardImage.setImageResource(R.drawable.mock_attraction_item)
                }

                tvTitle.text = item.name
                tvSubtitle.text = item.location

                itemView.setOnClickListener {
                    item.id?.let { id -> onItemClickCallback.onItemClicked(id) }
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(id: Int)
    }
}