package space.mrandika.wisnu.ui.event.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.ActivityEventListBinding
import space.mrandika.wisnu.model.event.Event
import space.mrandika.wisnu.model.transaction.Transaction
import space.mrandika.wisnu.ui.event.detail.EventDetailActivity
import space.mrandika.wisnu.ui.event.detail.EventViewModel
import space.mrandika.wisnu.ui.order.detail.OrderDetailActivity
import space.mrandika.wisnu.ui.order.list.OrdersAdapter

@AndroidEntryPoint
class EventListActivity : AppCompatActivity() {
    /**
     * ViewBinding
     */
    private lateinit var binding: ActivityEventListBinding

    private val viewModel: EventsViewModel by viewModels()

    private lateinit var adapter: EventsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.getEvents()

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                loadingStateIsToggled(state.isLoading)
                errorStateIsToggled(state.isLoading)
                emptyStateIsToggled(state.isEmpty)

                setData(state.events)
            }
        }

        binding.apply {
            toolbar.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }

            stateError.button.setOnClickListener {
                viewModel.getEvents()
            }

            detailContent.rvEvents.layoutManager = GridLayoutManager(this@EventListActivity, 2)
        }
    }

    private fun loadingStateIsToggled(value: Boolean) {
        Log.d("OrderListFragment-isLoading", value.toString())
        binding.apply {
            stateLoading.root.visibility = if (value) View.VISIBLE else View.GONE
            detailContent.root.visibility = if (!value) View.VISIBLE else View.GONE
        }
    }

    private fun errorStateIsToggled(value: Boolean) {
        Log.d("OrderListFragment-isError", value.toString())
        binding.apply {
            stateError.root.visibility = if (value) View.VISIBLE else View.GONE
            detailContent.root.visibility = if (!value) View.VISIBLE else View.GONE
        }
    }

    private fun emptyStateIsToggled(value: Boolean) {
        Log.d("OrderListFragment-isEmpty", value.toString())
        binding.apply {
            stateEmpty.root.visibility = if (value) View.VISIBLE else View.GONE
            detailContent.root.visibility = if (!value) View.VISIBLE else View.GONE
        }
    }

    private fun setData(value: List<Event>) {
        adapter = EventsAdapter(value)

        binding.apply {
            detailContent.rvEvents.adapter = adapter
        }

        adapter.setOnItemClickCallback(object : EventsAdapter.OnItemClickCallback {
            override fun onItemClicked(id: Int) {
                showDetail(id)
            }
        })
    }

    private fun showDetail(id: Int) {
        val intent = Intent(this, EventDetailActivity::class.java).apply {
            putExtra("id", id)
        }

        startActivity(intent)
    }
}