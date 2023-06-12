package space.mrandika.wisnu.ui.ticket.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import space.mrandika.wisnu.databinding.ItemTicketHolderBinding
import space.mrandika.wisnu.model.ticket.TicketItem

class ChildTicketAdapter(private val tickets: List<TicketItem>) : RecyclerView.Adapter<ChildTicketAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTicketHolderBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val data = tickets[position]
        viewHolder.bind(data)
    }

    override fun getItemCount() = tickets.size

    inner class ViewHolder(private val binding: ItemTicketHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TicketItem) {
            binding.apply {
                textTicketHolderName.text = item.name
            }
        }
    }
}