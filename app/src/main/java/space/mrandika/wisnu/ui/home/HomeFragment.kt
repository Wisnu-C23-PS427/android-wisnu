package space.mrandika.wisnu.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.FragmentHomeBinding
import space.mrandika.wisnu.model.event.Event
import space.mrandika.wisnu.model.poi.POI
@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.getEvent()
            viewModel.state.collect{
               setEvent(viewModel.state.value.events)
            }
        }
        lifecycleScope.launch {
            viewModel.getRecommendation()
            viewModel.state.collect{
                setRecommendation(viewModel.state.value.recommendation)
            }
        }
        setIcons()
    }
    private fun setIcons(){
        val listIcons : List<Int> = listOf(
            R.drawable.montain,
            R.drawable.montain,
            R.drawable.montain,
            R.drawable.montain,
            R.drawable.montain,
            R.drawable.montain,
            R.drawable.montain,
            R.drawable.montain,
        )

        binding.rvIcon.layoutManager = GridLayoutManager(requireContext(),4)
        val adapter = IconsAdapter(listIcons)
        binding.rvIcon.adapter = adapter
    }
    private fun setRecommendation(recommendation : List<POI>){
        binding.rvRecomendation.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        val adapter = RecommendationAdapter(recommendation)
        binding.rvRecomendation.adapter = adapter
    }
    private fun setEvent(event: List<Event>){
        val adapter = EventAdapter(event)
        binding.rvEvent.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.rvEvent.adapter = adapter
    }
}