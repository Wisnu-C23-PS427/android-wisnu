package space.mrandika.wisnu.ui.profile.trip.detail

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
import space.mrandika.wisnu.databinding.ActivityTripDetailBinding
import space.mrandika.wisnu.entity.ItineraryWithPOIs

@AndroidEntryPoint
class TripDetailActivity : AppCompatActivity() {
    /**
     * ViewBinding
     */
    private lateinit var binding: ActivityTripDetailBinding

    private lateinit var adapter: ItinerariesAdapter
    private val viewModel: TripViewModel by viewModels()

    // View parameter
    private var tripId: Int? = null
    private var cityName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTripDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tripId = intent.getIntExtra("id", 0)
        cityName = intent.getStringExtra("name")

        supportActionBar?.title = cityName
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        tripId?.let { id ->
            viewModel.getItineraries(id)
        }

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                errorStateIsToggled(state.isError)
                setData(state.itineraries)
            }
        }

        val layoutManager = LinearLayoutManager(this)
        binding.tripContent.rvItineraries.layoutManager = layoutManager
    }

    private fun errorStateIsToggled(value: Boolean) {
        Log.d("TripDetailActivity-isError", value.toString())
        binding.apply {
            stateError.root.visibility = if (value) View.VISIBLE else View.GONE
            tripContent.root.visibility = if (!value) View.VISIBLE else View.GONE
        }
    }

    private fun setData(value: List<ItineraryWithPOIs>) {
        adapter = ItinerariesAdapter(value, this)

        binding.tripContent.apply {
            rvItineraries.adapter = adapter
        }
    }
}