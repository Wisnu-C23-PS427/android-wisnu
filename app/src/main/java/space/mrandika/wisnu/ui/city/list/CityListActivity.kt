package space.mrandika.wisnu.ui.city.list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import space.mrandika.wisnu.databinding.ActivityCityListBinding
import space.mrandika.wisnu.model.city.City
import space.mrandika.wisnu.ui.city.detail.CityDetailActivity

@AndroidEntryPoint
class CityListActivity : AppCompatActivity() {
    /**
     * ViewBinding
     */
    private lateinit var binding: ActivityCityListBinding

    private val viewModel: CitiesViewModel by viewModels()

    private lateinit var adapter: CitiesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.getCities()

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                loadingStateIsToggled(state.isLoading)
                errorStateIsToggled(state.isError)
                emptyStateIsToggled(state.isEmpty)

                setData(state.cities)
            }
        }

        binding.apply {
            toolbar.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }

            stateError.button.setOnClickListener {
                viewModel.getCities()
            }

            detailContent.rvCities.layoutManager = GridLayoutManager(this@CityListActivity, 2)
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

    private fun setData(value: List<City>) {
        adapter = CitiesAdapter(value)

        binding.apply {
            detailContent.rvCities.adapter = adapter
        }

        adapter.setOnItemClickCallback(object : CitiesAdapter.OnItemClickCallback {
            override fun onItemClicked(id: Int) {
                showDetail(id)
            }
        })
    }

    private fun showDetail(id: Int) {
        val intent = Intent(this, CityDetailActivity::class.java).apply {
            putExtra("id", id)
        }

        startActivity(intent)
    }
}