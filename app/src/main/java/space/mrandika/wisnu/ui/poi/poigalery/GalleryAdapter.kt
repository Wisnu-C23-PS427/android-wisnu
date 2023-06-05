package space.mrandika.wisnu.ui.poi.poigalery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.GaleryItemsBinding
import space.mrandika.wisnu.model.gallery.Gallery

class GalleryAdapter(private val galleryList : List<Gallery>): RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryAdapter.ViewHolder {
        val binding = GaleryItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GalleryAdapter.ViewHolder, position: Int) {
        val data = galleryList[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = galleryList.size

    inner class ViewHolder(private val binding: GaleryItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Gallery) {
            binding.apply {
                Glide.with(imageGallery)
                    .load(item)
                    .into(imageGallery)
            }
        }
    }
}