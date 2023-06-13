package space.mrandika.wisnu.ui.itinerary.poi

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.FragmentItineraryPoiBinding
import space.mrandika.wisnu.model.city.ItineraryItem
import space.mrandika.wisnu.model.poi.POI
import space.mrandika.wisnu.ui.itinerary.ItineraryViewModel
import space.mrandika.wisnu.ui.itinerary.person.ItineraryPersonFragment

@AndroidEntryPoint
class ItineraryPoiFragment : Fragment() {
    private var _binding : FragmentItineraryPoiBinding? = null
    private val binding get() = _binding!!
    private val viewModel : ItineraryViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItineraryPoiBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.getRecommendation(viewModel.state.value.cityId)

            viewModel.state.collect { state ->
                loadingStateIsToggled(state.isLoading)
                errorStateIsToggled(state.isError)

                setData(state.itineraries)
            }
        }
    }
    private fun setData(itineraries: List<ItineraryItem>){
        binding.contentPoi.rvItineraries.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ItineraryAdapter(itineraries, requireContext())
        }
    }
    private fun loadingStateIsToggled(value: Boolean) {
        Log.d("TicketListFragment-isLoading", value.toString())
        binding.apply {
            stateLoading.root.visibility = if (value) View.VISIBLE else View.GONE
            contentPoi.root.visibility = if (!value) View.VISIBLE else View.GONE
        }
    }
    private fun errorStateIsToggled(value: Boolean) {
        Log.d("TicketListFragment-isLoading", value.toString())
        binding.apply {
            stateError.root.visibility = if (value) View.VISIBLE else View.GONE
            contentPoi.root.visibility = if (!value) View.VISIBLE else View.GONE
        }
        setView()
    }
    private fun setView() {
        val tvTitle : TextView? = activity?.findViewById(R.id.tv_title)
        val tvDescription : TextView? = activity?.findViewById(R.id.tv_description)
        val btnMain : MaterialButton? = activity?.findViewById(R.id.btn_main)
        btnMain?.apply {
            text = "Lanjut"
            setIconResource(R.drawable.baseline_arrow_forward_24)
            setOnClickListener {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, ItineraryPersonFragment())
                    .commit()
            }
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