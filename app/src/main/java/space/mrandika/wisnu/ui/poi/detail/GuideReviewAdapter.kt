package space.mrandika.wisnu.ui.poi.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import space.mrandika.wisnu.BuildConfig
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.ItemGuideBinding
import space.mrandika.wisnu.databinding.ItemReviewBinding
import space.mrandika.wisnu.model.guide.Guide
import space.mrandika.wisnu.model.review.Review

class GuideReviewAdapter(private val reviews: List<Review>): RecyclerView.Adapter<GuideReviewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val data = reviews[position]

        viewHolder.bind(data)
    }

    override fun getItemCount(): Int = reviews.size

    inner class ViewHolder(private val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Review) {
            binding.apply {
                tvName.text = item.name
                tvReview.text = item.review
                tvStar.text = item.star.toString()
            }
        }
    }
}