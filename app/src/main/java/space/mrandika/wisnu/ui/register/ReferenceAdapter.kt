package space.mrandika.wisnu.ui.register

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import space.mrandika.wisnu.databinding.ItemChipBinding
import space.mrandika.wisnu.model.category.Category

class ReferenceAdapter(private val chips: List<Category>) : RecyclerView.Adapter<ReferenceAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemChipBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val chip = chips[position]
        viewHolder.bind(chip)
    }

    override fun getItemCount(): Int = chips.size

    inner class ViewHolder(private val binding: ItemChipBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Category) {
            binding.apply {
                chip.text = item.name

                itemView.setOnClickListener {
                    onItemClickCallback.onItemClicked(binding.chip)
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(chip: Chip)
    }
}
