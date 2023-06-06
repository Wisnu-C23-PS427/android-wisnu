package space.mrandika.wisnu.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.FragmentHomeBinding
import space.mrandika.wisnu.model.category.Category
import space.mrandika.wisnu.model.event.Event
import space.mrandika.wisnu.model.poi.POI
@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textTab: TextView? = activity?.findViewById(R.id.text_current_tab)
        textTab?.text = resources.getString(R.string.tab_home)

        lifecycleScope.launch {
            viewModel.getCategories()
            viewModel.getEvent()
            viewModel.getRecommendation()

            viewModel.state.collect { state ->
                setCategories(state.categories)
                setRecommendation(state.recommendations)
                setEvent(state.events)
            }
        }

        binding?.apply {
            rvIcon.layoutManager = GridLayoutManager(requireContext(),4)
        }
    }
    private fun setCategories(data: List<Category>) {
        val adapter = CategoriesAdapter(data)

        binding?.apply {
            rvIcon.adapter = adapter
        }
    }
    private fun setRecommendation(recommendation : List<POI>){
        val adapter = RecommendationAdapter(recommendation)

        binding?.apply {
            rvRecomendation.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            rvRecomendation.adapter = adapter
        }
    }
    private fun setEvent(event: List<Event>){
        val adapter = EventsAdapter(event)

        binding?.apply {
            rvEvent.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            rvEvent.adapter = adapter
        }
    }
}