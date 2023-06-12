package space.mrandika.wisnu.ui.poi.categories

import POIAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.coroutines.launch
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.ActivityPoiCategoryBinding
import space.mrandika.wisnu.databinding.ActivityPoiDetailBinding
import space.mrandika.wisnu.model.poi.POI

class POICategoryActivity : AppCompatActivity() {
    /**
     * ViewBinding
     */
    private lateinit var binding: ActivityPoiCategoryBinding

    private val viewModel: POICategoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPoiCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            viewModel.getPoiCategory("Gunung")

            viewModel.state.collect{ state ->
                isLoading(state.isLoading)
                isError(state.isError)
                setData(state.categories)
            }
        }
    }

    private fun setData(pois: List<POI>){
        val adapter = POIAdapter(pois)

        binding.apply {
            rvPois.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            rvPois.adapter = adapter
        }
    }

    private fun isLoading(value:Boolean){
        binding.apply {
            stateLoading.root.visibility = if (value) View.VISIBLE else View.GONE
            rvPois.visibility = if (!value) View.VISIBLE else View.GONE
        }
    }

    private fun isError(value:Boolean){
        binding.apply {
            stateError.root.visibility = if (value) View.VISIBLE else View.GONE
            rvPois.visibility = if (!value) View.VISIBLE else View.GONE
        }
    }

    private fun isEmpty(value:Boolean){
        binding.apply {
            stateEmpty.root.visibility = if (value) View.VISIBLE else View.GONE
            rvPois.visibility = if (!value) View.VISIBLE else View.GONE
        }
    }
}