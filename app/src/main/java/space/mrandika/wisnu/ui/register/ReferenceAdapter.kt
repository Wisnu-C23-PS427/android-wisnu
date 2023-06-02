package space.mrandika.wisnu.ui.register

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import space.mrandika.wisnu.R

class ReferenceAdapter(private val chips: List<String>,private val viewModel : RegisterViewModel) : RecyclerView.Adapter<ReferenceAdapter.ChipViewHolder>() {

    private val selectedChips: MutableList<String>  = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChipViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chips_items, parent, false)
        return ChipViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChipViewHolder, position: Int) {
        val chipText = chips[position]
        holder.bind(chipText)

        holder.chip.setOnClickListener {
            val selectedChip = chips[position]

            if (selectedChips.contains(selectedChip)) {
                selectedChips.remove(selectedChip)
                viewModel.updateInteresting(selectedChips)
                holder.chip.isChecked = false
            } else {
                selectedChips.add(selectedChip)
                viewModel.updateInteresting(selectedChips)
                holder.chip.isChecked = true
            }
        }
    }

    override fun getItemCount(): Int = chips.size

    inner class ChipViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val chip: Chip = itemView.findViewById(R.id.chip)

        fun bind(chipText: String) {
            chip.text = chipText
        }
    }

}
