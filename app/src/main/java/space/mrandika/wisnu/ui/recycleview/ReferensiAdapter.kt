package space.mrandika.wisnu.ui.recycleview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import space.mrandika.wisnu.R

class ReferensiAdapter(private val chips: List<String>) : RecyclerView.Adapter<ReferensiAdapter.ChipViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChipViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chip_items, parent, false)
        return ChipViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChipViewHolder, position: Int) {
        val chipText = chips[position]
        holder.bind(chipText)
    }

    override fun getItemCount(): Int = chips.size

    inner class ChipViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val chip: Chip = itemView.findViewById(R.id.chip)

        fun bind(chipText: String) {
            chip.text = chipText
        }
    }
}