package space.mrandika.wisnu.ui.profile.trip.list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import space.mrandika.wisnu.databinding.ActivityTripListBinding
import space.mrandika.wisnu.entity.Trip
import space.mrandika.wisnu.ui.profile.trip.detail.TripDetailActivity

@AndroidEntryPoint
class TripListActivity : AppCompatActivity() {
    /**
     * ViewBinding
     */
    private lateinit var binding: ActivityTripListBinding

    private lateinit var adapter: TripsAdapter
    private val viewModel: TripsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTripListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.getTrips()

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                errorStateIsToggled(state.isError)
                emptyStateIsToggled(state.isEmpty)
                setData(state.trips)
            }
        }

        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val layoutManager = LinearLayoutManager(this)
        binding.tripsContent.rvTrips.layoutManager = layoutManager
    }

    private fun errorStateIsToggled(value: Boolean) {
        Log.d("TripListActivity-isError", value.toString())
        binding.apply {
            stateError.root.visibility = if (value) View.VISIBLE else View.GONE
            tripsContent.root.visibility = if (!value) View.VISIBLE else View.GONE
        }
    }

    private fun emptyStateIsToggled(value: Boolean) {
        Log.d("TripListActivity-isEmpty", value.toString())
        binding.apply {
            stateEmpty.root.visibility = if (value) View.VISIBLE else View.GONE
            tripsContent.root.visibility = if (!value) View.VISIBLE else View.GONE
        }
    }

    private fun setData(value: List<Trip>) {
        adapter = TripsAdapter(value)

        binding.tripsContent.apply {
            rvTrips.adapter = adapter
        }

        adapter.setOnItemClickCallback(object : TripsAdapter.OnItemClickCallback {
            override fun onItemClicked(id: Int, name: String) {
                showDetail(id, name)
            }
        })
    }

    private fun showDetail(id: Int, name: String) {
        val intent = Intent(this, TripDetailActivity::class.java).apply {
            putExtra("id", id)
            putExtra("name", name)
        }

        startActivity(intent)
    }
}