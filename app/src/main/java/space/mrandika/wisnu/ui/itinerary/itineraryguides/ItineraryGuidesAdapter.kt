package space.mrandika.wisnu.ui.itinerary.itineraryguides

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.GuidesItemsBinding
import space.mrandika.wisnu.model.guide.Guide
import space.mrandika.wisnu.model.poi.POI
import space.mrandika.wisnu.ui.itinerary.ItineraryViewModel

class ItineraryGuidesAdapter(private val guide: List<Guide>, private val viewModel : ItineraryViewModel) :
    RecyclerView.Adapter<ItineraryGuidesAdapter.ViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = GuidesItemsBinding.inflate(
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

    inner class ViewHolder(private val binding: GuidesItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Guide) {
            binding.apply {
                Glide.with(guideImage)
                    .load(item.image)
                    .into(guideImage)
                guideName.text = item.name
                guidePrice.text = item.price.toString()
                guideRating.text = item.avgStar.toString()

                if (adapterPosition == selectedPosition) {
                    guideItems.setBackgroundResource(R.drawable.selected_guides)
                } else {
                    guideItems.setBackgroundResource(R.drawable.not_selected_guide)
                }

                guideItems.setOnClickListener {
                    if (adapterPosition != selectedPosition) {
                        val previousSelectedPosition = selectedPosition
                        selectedPosition = adapterPosition
                        notifyItemChanged(previousSelectedPosition)
                        notifyItemChanged(selectedPosition)

                        item.price?.let { price -> viewModel.setTotalGuide(price) }
                    }
                }
            }
        }
    }
}
