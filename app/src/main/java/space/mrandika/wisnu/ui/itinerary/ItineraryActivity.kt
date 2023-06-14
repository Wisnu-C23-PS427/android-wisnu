package space.mrandika.wisnu.ui.itinerary

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import dagger.hilt.android.AndroidEntryPoint
import space.mrandika.wisnu.databinding.ActivityItineraryBinding

@AndroidEntryPoint
class ItineraryActivity : AppCompatActivity() {
    /**
     * ViewBinding
     */
    private lateinit var binding: ActivityItineraryBinding
    private val viewModel: ItineraryViewModel by viewModels()

    private var cityId: Int? = null
    private var city: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItineraryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.title = ""

        cityId = intent.getIntExtra("id", 0)
        city = intent.getStringExtra("name")

        viewModel.setCityId(cityId ?: 0)
        viewModel.setCity(city ?: "")

        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}