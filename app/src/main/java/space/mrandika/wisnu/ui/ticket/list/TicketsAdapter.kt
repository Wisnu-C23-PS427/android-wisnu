package space.mrandika.wisnu.ui.ticket.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import space.mrandika.wisnu.databinding.ItemTicketBinding
import space.mrandika.wisnu.model.ticket.Ticket

class TicketsAdapter(private val tickets: List<Ticket>) : RecyclerView.Adapter<TicketsAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTicketBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val data = tickets[position]
        viewHolder.bind(data)
    }

    override fun getItemCount() = tickets.size

    inner class ViewHolder(private val binding: ItemTicketBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Ticket) {
            binding.apply {
                textTicketName.text = item.poi?.name
                textTicketDate.text = item.createdAt

                itemView.setOnClickListener {
                    item.id?.let { id -> onItemClickCallback.onItemClicked(id) }
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(id: String)
    }
}