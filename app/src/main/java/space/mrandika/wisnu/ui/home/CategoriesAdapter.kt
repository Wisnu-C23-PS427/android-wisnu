package space.mrandika.wisnu.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import space.mrandika.wisnu.BuildConfig
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.ItemCategoryBinding
import space.mrandika.wisnu.model.category.Category

class CategoriesAdapter(private val categories: List<Category>) : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val data = categories[position]
        viewHolder.bind(data)
    }

    override fun getItemCount(): Int = categories.size

    inner class ViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Category) {
            binding.apply {
                if (BuildConfig.IS_SERVICE_UP) {
                    Glide.with(itemView.context)
                        .load(item.image)
                        .into(btnIcons)
                } else {
                    btnIcons.setImageResource(R.drawable.mock_category_mountain)
                }
            }
        }
    }
}