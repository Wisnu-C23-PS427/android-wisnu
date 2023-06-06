package space.mrandika.wisnu.ui.itinerary.cityDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.FragmentCityDetailBinding
import space.mrandika.wisnu.model.city.City
import space.mrandika.wisnu.model.poi.POI
import space.mrandika.wisnu.ui.itinerary.DayCountFragment
import space.mrandika.wisnu.ui.itinerary.ItineraryViewModel
import space.mrandika.wisnu.ui.itinerary.itineraryPoi.ItineraryPoiFragment

@AndroidEntryPoint
class CityDetailFragment : Fragment() {
    private var _binding : FragmentCityDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel : CityViewModel by viewModels()
    private val activityViewModel : ItineraryViewModel by activityViewModels()
    companion object{
        const val  idCity = 0
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCityDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setActivityView()
        lifecycleScope.launch {
            viewModel.getCity(idCity)
            viewModel.state.value.apply {
                CityResult?.let { setViewData(it) }
                CityResult?.poi?.let { setAdapterCity(it) }
            }
        }
    }

    private fun setActivityView() {
        val btnMain : MaterialButton? = activity?.findViewById(R.id.btn_main)
        val tvTitle : TextView? = activity?.findViewById(R.id.tv_title)
        val tvDescription : TextView? = activity?.findViewById(R.id.tv_description)

        btnMain?.text = buildString {
        append("RENCANAKAN")
    }
        btnMain?.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, DayCountFragment())
                .commit()
        }
        btnMain?.icon = null
        tvTitle?.visibility = View.GONE
        tvDescription?.visibility = View.GONE
    }

    private fun setViewData(city:City){
        binding.apply {
            Glide.with(ivCityImage)
                .load(city.image)
                .into(ivCityImage)
            tvCityName.text = city.name
            tvCapital.text = city.location
            tvStory.text = city.description

            city.name?.let { activityViewModel.setCity(it) }
        }
    }
    private fun setAdapterCity(listPOi : List<POI>){
        binding.rvPoi.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        val adapter = CityAdapter(listPOi)
        binding.rvPoi.adapter = adapter
    }
}