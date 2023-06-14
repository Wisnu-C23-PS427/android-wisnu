package space.mrandika.wisnu.ui.poi.list

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
import kotlinx.coroutines.launch
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.ActivityEventListBinding
import space.mrandika.wisnu.databinding.ActivityPoiListBinding
import space.mrandika.wisnu.model.event.Event
import space.mrandika.wisnu.model.poi.POI
import space.mrandika.wisnu.ui.event.detail.EventDetailActivity
import space.mrandika.wisnu.ui.event.list.EventsAdapter
import space.mrandika.wisnu.ui.event.list.EventsViewModel
import space.mrandika.wisnu.ui.poi.detail.POIDetailActivity

@AndroidEntryPoint
class POIListActivity : AppCompatActivity() {
    /**
     * ViewBinding
     */
    private lateinit var binding: ActivityPoiListBinding

    private val viewModel: POIsViewModel by viewModels()

    private lateinit var adapter: POIsAdapter

    private var isTopDestination: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPoiListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        isTopDestination = intent.getBooleanExtra("top_destination", false)

        if (isTopDestination) {
            supportActionBar?.title = getString(R.string.top_destination)
        }

        viewModel.getPois()

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                loadingStateIsToggled(state.isLoading)
                errorStateIsToggled(state.isError)
                emptyStateIsToggled(state.isEmpty)

                setData(state.pois)
            }
        }

        binding.apply {
            toolbar.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }

            detailContent.rvPois.layoutManager = GridLayoutManager(this@POIListActivity, 2)
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

    private fun setData(value: List<POI>) {
        adapter = POIsAdapter(value)

        binding.apply {
            detailContent.rvPois.adapter = adapter
        }

        adapter.setOnItemClickCallback(object : POIsAdapter.OnItemClickCallback {
            override fun onItemClicked(id: Int) {
                showDetail(id)
            }
        })
    }

    private fun showDetail(id: Int) {
        val intent = Intent(this, POIDetailActivity::class.java).apply {
            putExtra("id", id)
        }

        startActivity(intent)
    }
}