package space.mrandika.wisnu.ui.poi.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import space.mrandika.wisnu.BuildConfig
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.ItemGuideBinding
import space.mrandika.wisnu.model.guide.Guide

class GuideAdapter(private val guides: List<Guide>): RecyclerView.Adapter<GuideAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGuideBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val data = guides[position]

        viewHolder.bind(data)
    }

    override fun getItemCount(): Int = guides.size

    inner class ViewHolder(private val binding: ItemGuideBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Guide) {
            binding.apply {
                if (BuildConfig.IS_SERVICE_UP) {
                    Glide.with(ivImageGuide)
                        .load(item.image)
                        .into(ivImageGuide)
                } else {
                    ivImageGuide.setImageResource(R.drawable.mock_guide_item)

                    tvName.text = item.name
                    tvPrice.text = item.price.toString()
                }

                itemView.setOnClickListener {
                    onItemClickCallback.onItemClicked(item)
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(guide: Guide)
    }
}