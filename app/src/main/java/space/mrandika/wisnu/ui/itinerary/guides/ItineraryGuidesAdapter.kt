package space.mrandika.wisnu.ui.itinerary.guides

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import space.mrandika.wisnu.BuildConfig
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.ItemTransactionGuideBinding
import space.mrandika.wisnu.model.guide.Guide
import space.mrandika.wisnu.ui.itinerary.ItineraryViewModel
import space.mrandika.wisnu.ui.itinerary.poi.POIsAdapter

class ItineraryGuidesAdapter(private val guide: List<Guide>) : RecyclerView.Adapter<ItineraryGuidesAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    private var selectedPosition = RecyclerView.NO_POSITION

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTransactionGuideBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val data = guide[position]
        viewHolder.bind(data)
    }

    override fun getItemCount(): Int = guide.size

    inner class ViewHolder(private val binding: ItemTransactionGuideBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Guide) {
            binding.apply {
                if (BuildConfig.IS_SERVICE_UP) {
                    Glide.with(guideImage)
                        .load(item.image)
                        .into(guideImage)
                } else {
                    guideImage.setImageResource(R.drawable.mock_guide_item)
                }

                guideName.text = item.name
                guidePrice.text = item.price.toString()
                guideRating.text = item.avgStar.toString()

                if (adapterPosition == selectedPosition) {
                    guideItems.setBackgroundResource(R.drawable.shape_rounded_primary)
                } else {
                    guideItems.setBackgroundResource(R.drawable.shape_rounded_border)
                }

                itemView.setOnClickListener {
                    if (adapterPosition != selectedPosition) {
                        val previousSelectedPosition = selectedPosition

                        selectedPosition = adapterPosition
                        notifyItemChanged(previousSelectedPosition)
                        notifyItemChanged(selectedPosition)
                    }

                    onItemClickCallback.onItemClicked(item)
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(guide: Guide)
    }
}
