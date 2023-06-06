package space.mrandika.wisnu.ui.order.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.ItemOrderBinding
import space.mrandika.wisnu.model.ticket.Ticket
import space.mrandika.wisnu.model.transaction.OrderData
import space.mrandika.wisnu.model.transaction.Transaction

class OrdersAdapter(private val orders: List<Transaction>, private val context: Context) : RecyclerView.Adapter<OrdersAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val data = orders[position]
        viewHolder.bind(data)
    }

    override fun getItemCount() = orders.size

    inner class ViewHolder(private val binding: ItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Transaction) {
            binding.apply {
                val resId: Int
                val orderType: String

                if (item.isGuideOrder == true && item.isTicketOrder == true) {
                    resId = R.drawable.baseline_how_to_reg_24_white
                    orderType = context.resources.getString(R.string.trip_package)
                } else if (item.isGuideOrder == true && item.isTicketOrder == false) {
                    resId = R.drawable.baseline_people_24_white
                    orderType = context.resources.getString(R.string.trip_guide)
                } else {
                    resId = R.drawable.baseline_confirmation_number_24_white
                    orderType = context.resources.getString(R.string.trip_ticket)
                }

                imageOrder.setImageResource(resId)
                textOrderName.text = orderType
                textOrderDate.text = item.createdAt
                textOrderPrice.text = "Rp${item.price}"

                itemView.setOnClickListener {
                    item.id?.let { id -> onItemClickCallback.onItemClicked(id) }
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(id: Int)
    }
}