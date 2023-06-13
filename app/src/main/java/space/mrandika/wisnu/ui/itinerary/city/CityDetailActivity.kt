package space.mrandika.wisnu.ui.itinerary.city

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import space.mrandika.wisnu.BuildConfig
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.ActivityCityDetailBinding
import space.mrandika.wisnu.databinding.ActivityItineraryBinding
import space.mrandika.wisnu.model.city.City
import space.mrandika.wisnu.model.poi.POI
import space.mrandika.wisnu.ui.itinerary.ItineraryActivity
import space.mrandika.wisnu.ui.itinerary.ItineraryViewModel

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

        val toolbar: Toolbar = binding.detailContent.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        cityId = intent.getIntExtra("id", 0)

        lifecycleScope.launch {
            viewModel.getCity(cityId ?: 0)

            viewModel.state.value.apply {
                CityResult?.let { setViewData(it) }
                CityResult?.poi?.let { setAdapterCity(it) }

                toolbar.title = this.CityResult?.name
            }
        }

        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.detailContent.buttonPlan.setOnClickListener {
            val intent = Intent(this, ItineraryActivity::class.java).apply {
                putExtra("id", cityId)
                putExtra("name", viewModel.state.value.CityResult?.name ?: "")
            }

            startActivity(intent)
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
        val adapter = CityAdapter(listPOi)

        binding.detailContent.rvPoi.apply {
            this.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            this.adapter = adapter
        }
    }
}