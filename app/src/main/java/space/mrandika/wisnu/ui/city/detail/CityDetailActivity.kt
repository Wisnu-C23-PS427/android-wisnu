package space.mrandika.wisnu.ui.city.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import space.mrandika.wisnu.BuildConfig
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.ActivityCityDetailBinding
import space.mrandika.wisnu.model.city.City
import space.mrandika.wisnu.model.poi.POI
import space.mrandika.wisnu.ui.itinerary.ItineraryActivity
import space.mrandika.wisnu.ui.itinerary.ItineraryViewModel
import space.mrandika.wisnu.ui.poi.detail.POIDetailActivity

@AndroidEntryPoint
class CityDetailActivity : AppCompatActivity() {
    /**
     * ViewBinding
     */
    private lateinit var binding: ActivityCityDetailBinding

    private val viewModel : CityViewModel by viewModels()
    private val activityViewModel : ItineraryViewModel by viewModels()

    private var cityId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        cityId = intent.getIntExtra("id", 0)

        viewModel.getCity(cityId ?: 0)

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                loadingStateIsToggled(state.isLoading)
                errorStateIsToggled(state.isError)

                binding.detailContent.root.visibility = if (!state.isLoading && !state.isError) View.VISIBLE else View.GONE

                state.CityResult?.let { setViewData(it) }
                state.CityResult?.poi?.let { setAdapterCity(it) }

                toolbar.title = state.CityResult?.name
            }
        }

        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.apply {
            stateError.button.setOnClickListener {
                viewModel.getCity(cityId ?: 0)
            }

            detailContent.buttonPlan.setOnClickListener {
                val intent = Intent(this@CityDetailActivity, ItineraryActivity::class.java).apply {
                    putExtra("id", cityId)
                    putExtra("name", viewModel.state.value.CityResult?.name ?: "")
                }

                startActivity(intent)
            }
        }
    }

    private fun loadingStateIsToggled(value: Boolean) {
        Log.d("OrderListFragment-isLoading", value.toString())
        binding.apply {
            stateLoading.root.visibility = if (value) View.VISIBLE else View.GONE
        }
    }

    private fun errorStateIsToggled(value: Boolean) {
        Log.d("OrderListFragment-isError", value.toString())
        binding.apply {
            stateError.root.visibility = if (value) View.VISIBLE else View.GONE
        }
    }

    private fun setViewData(city: City){
        binding.detailContent.apply {
            if (BuildConfig.IS_SERVICE_UP) {
                Glide.with(ivCityImage)
                    .load(city.image)
                    .into(ivCityImage)
            } else {
                ivCityImage.setImageResource(R.drawable.mock_attraction_item)
            }

            tvCityName.text = city.name
            tvCapital.text = city.location
            tvStory.text = city.description

            city.name?.let { activityViewModel.setCity(it) }
        }
    }

    private fun setAdapterCity(listPOi : List<POI>){
        val adapter = CityPOIAdapter(listPOi)

        binding.detailContent.rvPoi.apply {
            this.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            this.adapter = adapter
        }

        adapter.setOnItemClickCallback(object : CityPOIAdapter.OnItemClickCallback {
            override fun onItemClicked(id: Int) {
                val intent = Intent(this@CityDetailActivity, POIDetailActivity::class.java).apply {
                    putExtra("id", id)
                }

                startActivity(intent)
            }

        })
    }
}