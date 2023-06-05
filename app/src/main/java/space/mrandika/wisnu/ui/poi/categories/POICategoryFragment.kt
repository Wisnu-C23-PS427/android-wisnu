package space.mrandika.wisnu.ui.poi.categories

import POIAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import space.mrandika.wisnu.databinding.FragmentPoiCategoryBinding
import space.mrandika.wisnu.model.poi.POI
@AndroidEntryPoint
class POICategoryFragment : Fragment() {
    private var _binding : FragmentPoiCategoryBinding? = null
    private val binding get() = _binding
    private val viewModel: POICategoryViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPoiCategoryBinding.inflate(inflater, container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

        binding?.apply {
            rvPois.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            rvPois.adapter = adapter
        }
    }
    private fun isLoading(value:Boolean){
        binding?.apply {
            stateLoading.root.visibility = if (value) View.VISIBLE else View.GONE
            rvPois.visibility = if (!value) View.VISIBLE else View.GONE
        }
    }
    private fun isError(value:Boolean){
        binding?.apply {
            stateError.root.visibility = if (value) View.VISIBLE else View.GONE
            rvPois.visibility = if (!value) View.VISIBLE else View.GONE
        }
    }
    private fun isEmpty(value:Boolean){
        binding?.apply {
            stateEmpty.root.visibility = if (value) View.VISIBLE else View.GONE
            rvPois.visibility = if (!value) View.VISIBLE else View.GONE
        }
    }

}