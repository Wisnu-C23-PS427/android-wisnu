import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import space.mrandika.wisnu.BuildConfig
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.ItemPoiExtendedBinding
import space.mrandika.wisnu.model.poi.POI
import space.mrandika.wisnu.ui.home.RecommendationAdapter


class POIAdapter(private val pois: List<POI>): RecyclerView.Adapter<POIAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: POIAdapter.OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: POIAdapter.OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): POIAdapter.ViewHolder {
        val binding = ItemPoiExtendedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: POIAdapter.ViewHolder, position: Int) {
        val data = pois[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = pois.size

    inner class ViewHolder(private val binding: ItemPoiExtendedBinding) :
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