package space.mrandika.wisnu.ui.poi.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import space.mrandika.wisnu.BuildConfig
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.ItemGalleryBinding
import space.mrandika.wisnu.databinding.ItemGalleryVrBinding
import space.mrandika.wisnu.model.gallery.Gallery
import space.mrandika.wisnu.model.guide.Guide
import space.mrandika.wisnu.ui.poi.detail.GuideAdapter

class VRGalleryAdapter(private val galleries: List<Gallery>): RecyclerView.Adapter<VRGalleryAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: VRGalleryAdapter.OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: VRGalleryAdapter.OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VRGalleryAdapter.ViewHolder {
        val binding = ItemGalleryVrBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VRGalleryAdapter.ViewHolder, position: Int) {
        val data = galleries[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = galleries.size

    inner class ViewHolder(private val binding: ItemGalleryVrBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Gallery) {
            binding.apply {
                if (BuildConfig.IS_SERVICE_UP) {
                    Glide.with(imageGallery)
                        .load(item)
                        .into(imageGallery)
                } else {
                    imageGallery.setImageResource(R.drawable.mock_attraction_item)
                }

                textImageName.text = item.name

                itemView.setOnClickListener {
                    onItemClickCallback.onItemClicked(item)
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(gallery: Gallery)
    }
}