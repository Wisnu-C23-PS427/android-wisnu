package space.mrandika.wisnu.ui.event.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import space.mrandika.wisnu.BuildConfig
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.ActivityEventDetailBinding
import space.mrandika.wisnu.model.event.Event

@AndroidEntryPoint
class EventDetailActivity : AppCompatActivity() {
    /**
     * ViewBinding
     */
    private lateinit var binding: ActivityEventDetailBinding

    private val viewModel: EventViewModel by viewModels()

    private var id: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        id = intent.getIntExtra("id", 0)

        viewModel.getEvent(id ?: 0)

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                loadingStateIsToggled(state.isLoading)
                errorStateIsToggled(state.isError)

                binding.detailContent.root.visibility = if (!state.isLoading && !state.isError) View.VISIBLE else View.GONE

                state.event?.let { setData(it) }
            }
        }

        binding.apply {
            toolbar.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }

            stateError.button.setOnClickListener {
                viewModel.getEvent(id ?: 0)
            }
        }
    }

    private fun loadingStateIsToggled(value: Boolean) {
        Log.d("OrderDetailActivity-isLoading", value.toString())
        binding.apply {
            stateLoading.root.visibility = if (value) View.VISIBLE else View.GONE
        }
    }

    private fun errorStateIsToggled(value: Boolean) {
        Log.d("OrderDetailActivity-isError", value.toString())
        binding.apply {
            stateError.root.visibility = if (value) View.VISIBLE else View.GONE
        }
    }

    private fun setData(value: Event) {
        supportActionBar?.title = value.name

        binding.detailContent.apply {
            if (BuildConfig.IS_SERVICE_UP) {
                Glide.with(this@EventDetailActivity)
                    .load(value.image)
                    .into(ivEventImage)
            } else {
                ivEventImage.setImageResource(R.drawable.mock_attraction_item)
            }

            tvEventName.text = value.name
            tvLocation.text = value.location
            tvStory.text = value.description
            tvDate.text = value.date
        }
    }
}