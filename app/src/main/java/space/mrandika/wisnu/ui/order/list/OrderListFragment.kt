package space.mrandika.wisnu.ui.order.list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.FragmentOrderListBinding
import space.mrandika.wisnu.model.transaction.Transaction
import space.mrandika.wisnu.ui.order.detail.OrderDetailActivity

@AndroidEntryPoint
class OrderListFragment : Fragment() {
    private var _binding: FragmentOrderListBinding? = null
    private val binding get() = _binding

    private lateinit var adapter: OrdersAdapter
    private val viewModel: OrdersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOrderListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textTab: TextView? = activity?.findViewById(R.id.text_current_tab)
        textTab?.text = resources.getString(R.string.tab_order)

        getData()

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                loadingStateIsToggled(state.isLoading)
                errorStateIsToggled(state.isError)
                emptyStateIsToggled(state.isEmpty)
                filterIsChanged(state.filter)

                binding?.ordersContent?.rvOrders?.visibility = if (!state.isLoading && !state.isError && !state.isEmpty) View.VISIBLE else View.GONE

                setData(state.tickets)
            }
        }

        val layoutManager = LinearLayoutManager(activity)

        binding?.stateError?.button?.setOnClickListener {
            getData()
        }

        binding?.ordersContent?.apply {
            // Set default state
            chipOrderFilter.check(R.id.chip_all)

            // Add listener for chip group
            chipOrderFilter.setOnCheckedStateChangeListener { _, checkedIds ->
                if (checkedIds.contains(R.id.chip_all)) {
                    viewModel.setFilter("all")
                } else if (checkedIds.contains(R.id.chip_ticket)) {
                    viewModel.setFilter("ticket")
                } else if (checkedIds.contains(R.id.chip_guide)) {
                    viewModel.setFilter("guide")
                }
            }

            rvOrders.layoutManager = layoutManager
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun getData() {
        lifecycleScope.launch {
            Log.d("OrderListFragment", "onViewCreated called!")
            viewModel.getTickets("all")
        }
    }

    private fun loadingStateIsToggled(value: Boolean) {
        Log.d("OrderListFragment-isLoading", value.toString())
        binding?.apply {
            stateLoading.root.visibility = if (value) View.VISIBLE else View.GONE
        }
    }

    private fun errorStateIsToggled(value: Boolean) {
        Log.d("OrderListFragment-isError", value.toString())
        binding?.apply {
            stateError.root.visibility = if (value) View.VISIBLE else View.GONE
        }
    }

    private fun emptyStateIsToggled(value: Boolean) {
        Log.d("OrderListFragment-isEmpty", value.toString())
        binding?.apply {
            stateEmpty.root.visibility = if (value) View.VISIBLE else View.GONE
        }
    }

    private suspend fun filterIsChanged(value: String) {
        viewModel.getTickets(value)
    }

    private fun setData(value: List<Transaction>) {
        adapter = OrdersAdapter(value, this.requireActivity())

        binding?.apply {
            ordersContent.rvOrders.adapter = adapter
        }

        adapter.setOnItemClickCallback(object : OrdersAdapter.OnItemClickCallback {
            override fun onItemClicked(id: Int) {
                showDetail(id)
            }
        })
    }

    private fun showDetail(id: Int) {
        val intent = Intent(activity, OrderDetailActivity::class.java).apply {
            putExtra("id", id)
        }

        startActivity(intent)
    }
}