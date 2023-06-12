package space.mrandika.wisnu.ui.ticket.list

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
import space.mrandika.wisnu.databinding.FragmentTicketListBinding
import space.mrandika.wisnu.model.ticket.Ticket
import space.mrandika.wisnu.ui.ticket.detail.TicketDetailActivity

@AndroidEntryPoint
class TicketListFragment : Fragment() {
    private var _binding: FragmentTicketListBinding? = null
    private val binding get() = _binding

    private lateinit var adapter: TicketsAdapter
    private val viewModel: TicketsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTicketListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textTab: TextView? = activity?.findViewById(R.id.text_current_tab)
        textTab?.text = resources.getString(R.string.tab_ticket)

        lifecycleScope.launch {
            viewModel.getTickets("active")

            viewModel.state.collect { state ->
                loadingStateIsToggled(state.isLoading)
                errorStateIsToggled(state.isError)
                emptyStateIsToggled(state.isEmpty)
                filterIsChanged(state.filter)

                setData(state.tickets)
            }
        }

        val layoutManager = LinearLayoutManager(activity)
        binding?.ticketsContent?.apply {
            // Set default state
            chipTicketFilter.check(R.id.chip_active)

            // Add listener for chip group
            chipTicketFilter.setOnCheckedStateChangeListener { _, checkedIds ->
                if (checkedIds.contains(R.id.chip_active)) {
                    viewModel.setFilter("active")
                } else if (checkedIds.contains(R.id.chip_expired)) {
                    viewModel.setFilter("expired")
                }
            }

            rvTickets.layoutManager = layoutManager
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun loadingStateIsToggled(value: Boolean) {
        Log.d("TicketListFragment-isLoading", value.toString())
        binding?.apply {
            stateLoading.root.visibility = if (value) View.VISIBLE else View.GONE
            ticketsContent.root.visibility = if (!value) View.VISIBLE else View.GONE
        }
    }

    private fun errorStateIsToggled(value: Boolean) {
        Log.d("TicketListFragment-isError", value.toString())
        binding?.apply {
            stateError.root.visibility = if (value) View.VISIBLE else View.GONE
            ticketsContent.root.visibility = if (!value) View.VISIBLE else View.GONE
        }
    }

    private fun emptyStateIsToggled(value: Boolean) {
        Log.d("TicketListFragment-isEmpty", value.toString())
        binding?.apply {
            stateEmpty.root.visibility = if (value) View.VISIBLE else View.GONE
            ticketsContent.root.visibility = if (!value) View.VISIBLE else View.GONE
        }
    }

    private suspend fun filterIsChanged(value: String) {
        viewModel.getTickets(value)
    }

    private fun setData(value: List<Ticket>) {
        adapter = TicketsAdapter(value)

        binding?.apply {
            ticketsContent.rvTickets.adapter = adapter
        }

        adapter.setOnItemClickCallback(object : TicketsAdapter.OnItemClickCallback {
            override fun onItemClicked(id: String) {
                showDetail(id)
            }
        })
    }

    private fun showDetail(id: String) {
        val intent = Intent(activity, TicketDetailActivity::class.java).apply {
            putExtra("id", id)
        }

        startActivity(intent)
    }
}