package space.mrandika.wisnu.ui.poi.poicategory

import PoiCategoryAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.FragmentPoiCategoryBinding
import space.mrandika.wisnu.model.poi.POI
@AndroidEntryPoint
class PoiCategoryFragment : Fragment() {
    private var _binding : FragmentPoiCategoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PoiCategoryViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPoiCategoryBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.getPoiCategory("Gunung")
            viewModel.state.collect{
                isLoading(it.isLoading)
                isError(it.isError)
                it.CategoryResult?.let { POI -> setCategoryData(POI) }
            }
        }
    }

    private fun setCategoryData(category: List<POI>){
        val listItems = listOf(
            R.drawable.depok,
            R.drawable.depok,
            R.drawable.depok,
            R.drawable.depok
        )
        val adapter = PoiCategoryAdapter(listItems)
        binding.rvCategory.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        binding.rvCategory.adapter = adapter
    }
    private fun isLoading(value:Boolean){
        binding.apply {
            stateLoading.root.visibility = if (value) View.VISIBLE else View.GONE
            rvCategory.visibility = if (!value) View.VISIBLE else View.GONE
        }
    }
    private fun isError(value:Boolean){
        binding.stateError.root.visibility = if (value) View.VISIBLE else View.GONE
        binding.rvCategory.visibility = if (!value) View.VISIBLE else View.GONE
    }
    private fun isEmpty(value:Boolean){
        binding.apply {
            stateEmpty.root.visibility = if (value) View.VISIBLE else View.GONE
            rvCategory.visibility = if (!value) View.VISIBLE else View.GONE
        }
    }

}