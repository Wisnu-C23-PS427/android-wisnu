package space.mrandika.wisnu.ui.itinerary.poi

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.FragmentItineraryPoiBinding
import space.mrandika.wisnu.model.city.ItineraryItem
import space.mrandika.wisnu.ui.itinerary.ItineraryViewModel
import space.mrandika.wisnu.ui.itinerary.person.ItineraryPersonFragment

@AndroidEntryPoint
class ItineraryPoiFragment : Fragment() {
    private var _binding : FragmentItineraryPoiBinding? = null
    private val binding get() = _binding
    private val viewModel : ItineraryViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItineraryPoiBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar: Toolbar? = activity?.findViewById(R.id.toolbar)

        toolbar?.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        getData()

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                loadingStateIsToggled(state.isLoading)
                errorStateIsToggled(state.isError)

                binding?.contentPoi?.root?.visibility = if (!state.isLoading && !state.isError) View.VISIBLE else View.GONE

                setData(state.itineraries)
            }
        }

        binding?.stateError?.button?.setOnClickListener {
            getData()
        }

        setView()
    }

    private fun getData() {
        lifecycleScope.launch {
            viewModel.getRecommendation(viewModel.state.value.cityId)
        }
    }

    private fun setData(itineraries: List<ItineraryItem>){
        binding?.contentPoi?.rvItineraries?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ItineraryAdapter(itineraries, requireContext())
        }
    }
    private fun loadingStateIsToggled(value: Boolean) {
        Log.d("TicketListFragment-isLoading", value.toString())
        binding?.apply {
            stateLoading.root.visibility = if (value) View.VISIBLE else View.GONE
        }
    }
    private fun errorStateIsToggled(value: Boolean) {
        Log.d("TicketListFragment-isLoading", value.toString())
        binding?.apply {
            stateError.root.visibility = if (value) View.VISIBLE else View.GONE
        }
    }
    private fun setView() {
        val tvTitle : TextView? = activity?.findViewById(R.id.tv_title)
        val tvDescription : TextView? = activity?.findViewById(R.id.tv_description)

        binding?.btnNext?.setOnClickListener {
            findNavController().navigate(R.id.action_poiFragment_to_personFragment)
        }

        tvDescription?.apply {
            visibility = View.VISIBLE
            text = "Wisnu rekomendasiin ini"
        }

        tvTitle?.visibility = View.VISIBLE

        viewModel.state.value.apply{
            tvTitle?.text = "Tempat\n" +"paling keren."
        }
    }
}