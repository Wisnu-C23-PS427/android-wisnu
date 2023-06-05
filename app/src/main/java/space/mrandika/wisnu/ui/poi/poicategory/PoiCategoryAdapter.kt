import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import space.mrandika.wisnu.databinding.CardItemsCategoryBinding


class PoiCategoryAdapter(private val galleryList : List<Int>): RecyclerView.Adapter<PoiCategoryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PoiCategoryAdapter.ViewHolder {
        val binding = CardItemsCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PoiCategoryAdapter.ViewHolder, position: Int) {
        val data = galleryList[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = galleryList.size

    inner class ViewHolder(private val binding: CardItemsCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Int) {
            binding.apply {
                Glide.with(imageCategory)
                    .load(item)
                    .into(imageCategory)
//                tvNamePoi.text = item.name
//                tvLocationPoi.text = item.location
            }
        }
    }
}