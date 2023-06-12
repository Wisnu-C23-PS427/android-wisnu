package space.mrandika.wisnu.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.FragmentSearchBinding
import space.mrandika.wisnu.extension.afterTextChanged
import space.mrandika.wisnu.model.category.Category

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding

    private val viewModel: SearchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textTab: TextView? = activity?.findViewById(R.id.text_current_tab)
        textTab?.text = resources.getString(R.string.tab_search)

        val fragmentManager = activity?.supportFragmentManager

        lifecycleScope.launch {
            viewModel.getCategories()
            setCategories(viewModel.state.value.categories)

            viewModel.state.collect { state ->
                Log.d("SearchFragment-state", state.toString())
            }
        }

        Log.d("SearchFragment", "fragmentManager is ${fragmentManager.toString()}")

        fragmentManager?.let {
            changeFragmentDiscovery(it)
        }

        binding?.searchContent?.apply {
            chipSearchFilter.check(R.id.chip_all)

            chipSearchFilter.setOnCheckedStateChangeListener { _, checkedIds ->
                Log.d("SearchFragment-chipSearchFilter listener", checkedIds.toString())
                val chip = activity?.findViewById<Chip>(checkedIds[0])

                val chipText = if (chip?.id == R.id.chip_all) {
                    "all"
                } else {
                    chip?.text.toString().lowercase().replace(" ", "_")
                }

                viewModel.setFilter(chipText)
            }

            etSearchKeyword.afterTextChanged { value ->
                lifecycleScope.launch {
                    // Debounce
                    delay(1500)

                    viewModel.setKeyword(value)

                    if (value.isNotEmpty() && fragmentManager != null) {
                        changeFragmentSearch(fragmentManager)
                    } else if (value.isEmpty() && fragmentManager != null) {
                        changeFragmentDiscovery(fragmentManager)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setCategories(value: List<Category>) {
        val chipGroup = binding?.searchContent?.chipSearchFilter

        value.forEach { category ->
            val chip = layoutInflater.inflate(R.layout.item_category_filter, chipGroup, false) as Chip

            chip.id = View.generateViewId()
            chip.text = category.name

            chipGroup?.addView(chip)
        }
    }

    private fun changeFragmentDiscovery(fragmentManager: FragmentManager) {
        val discoveryFragment = SearchDiscoveryFragment()
        val fragment = fragmentManager.findFragmentByTag(SearchDiscoveryFragment::class.java.simpleName)

        if (fragment !is SearchDiscoveryFragment) {
            fragmentManager.beginTransaction()
                .replace(R.id.container_search, discoveryFragment, SearchDiscoveryFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun changeFragmentSearch(fragmentManager: FragmentManager) {
        val resultFragment = SearchResultFragment()
        val fragment = fragmentManager.findFragmentByTag(SearchFragment::class.java.simpleName)

        if (fragment !is SearchFragment) {
            fragmentManager.beginTransaction()
                .replace(R.id.container_search, resultFragment, SearchFragment::class.java.simpleName)
                .commit()
        }
    }
}