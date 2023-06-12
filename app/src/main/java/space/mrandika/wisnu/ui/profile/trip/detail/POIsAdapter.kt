package space.mrandika.wisnu.ui.profile.trip.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import space.mrandika.wisnu.BuildConfig
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.ItemAttractionFullBinding
import space.mrandika.wisnu.entity.POI

class POIsAdapter(private val pois: List<POI>, private val context: Context) : RecyclerView.Adapter<POIsAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: POIsAdapter.OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: POIsAdapter.OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAttractionFullBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val data = pois[position]
        viewHolder.bind(data)
    }

    override fun getItemCount() = pois.size

    inner class ViewHolder(private val binding: ItemAttractionFullBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: POI) {
            binding.apply {
                if (BuildConfig.IS_SERVICE_UP) {
                    Glide.with(context)
                        .load(item.image)
                        .into(ivCardImage)
                } else {
                    ivCardImage.setImageResource(R.drawable.mock_attraction_item)
                }

                tvTitle.text = item.name
                tvSubtitle.text = item.location

                itemView.setOnClickListener {
                    onItemClickCallback.onItemClicked(item.id)
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(id: Int)
    }
}