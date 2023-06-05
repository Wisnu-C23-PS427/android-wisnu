import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import space.mrandika.wisnu.BuildConfig
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.CardItemsCategoryBinding
import space.mrandika.wisnu.model.poi.POI


class POIAdapter(private val pois: List<POI>): RecyclerView.Adapter<POIAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): POIAdapter.ViewHolder {
        val binding = CardItemsCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: POIAdapter.ViewHolder, position: Int) {
        val data = pois[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = pois.size

    inner class ViewHolder(private val binding: CardItemsCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: POI) {
            binding.apply {
                if (BuildConfig.IS_SERVICE_UP) {
                    Glide.with(imageCategory)
                        .load(item.image)
                        .into(imageCategory)
                } else {
                    imageCategory.setImageResource(R.drawable.mock_attraction_item)
                }

                tvNamePoi.text = item.name
                tvLocationPoi.text = item.location
            }
        }
    }
}