package space.mrandika.wisnu.ui.poi.categories

import POIAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import space.mrandika.wisnu.databinding.ActivityPoiCategoryBinding
import space.mrandika.wisnu.model.poi.POI
import space.mrandika.wisnu.ui.poi.detail.POIDetailActivity

@AndroidEntryPoint
class POICategoryActivity : AppCompatActivity() {
    /**
     * ViewBinding
     */
    private lateinit var binding: ActivityPoiCategoryBinding

    private val viewModel: POICategoryViewModel by viewModels()

    private var category: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPoiCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        category = intent.getStringExtra("category")

        val toolbar: Toolbar = binding.toolbar
        toolbar.title = category
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        lifecycleScope.launch {
            category?.let {
                viewModel.getPoiCategory(it.lowercase())
            }

            viewModel.state.collect { state ->
                isLoading(state.isLoading)
                isError(state.isError)
                isEmpty(state.isEmpty)
                setData(state.categories)

                binding.categoryContent.root.visibility = if (!state.isLoading && !state.isError) View.VISIBLE else View.GONE
            }
        }
    }

    private fun setData(pois: List<POI>){
        val adapter = POIAdapter(pois)

        binding.categoryContent.apply {
            rvPois.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            rvPois.adapter = adapter
        }

        adapter.setOnItemClickCallback(object : POIAdapter.OnItemClickCallback {
            override fun onItemClicked(id: Int) {
                val intent = Intent(this@POICategoryActivity, POIDetailActivity::class.java).apply {
                    putExtra("id", id)
                }

                startActivity(intent)
            }
        })
    }

    private fun isLoading(value:Boolean){
        binding.apply {
            stateLoading.root.visibility = if (value) View.VISIBLE else View.GONE
        }
    }

    private fun isError(value:Boolean){
        binding.apply {
            stateError.root.visibility = if (value) View.VISIBLE else View.GONE
        }
    }

    private fun isEmpty(value:Boolean){
        binding.apply {
            stateEmpty.root.visibility = if (value) View.VISIBLE else View.GONE
        }
    }
}