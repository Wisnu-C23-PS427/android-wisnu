package space.mrandika.wisnu.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import space.mrandika.wisnu.BuildConfig
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.ItemAttractionBinding
import space.mrandika.wisnu.model.poi.POI


class RecommendationAdapter(private val pois: List<POI>): RecyclerView.Adapter<RecommendationAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: RecommendationAdapter.OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: RecommendationAdapter.OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAttractionBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val data = pois[position]
        viewHolder.bind(data)
    }

    override fun getItemCount(): Int = pois.size

    inner class ViewHolder(private val binding: ItemAttractionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: POI) {
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