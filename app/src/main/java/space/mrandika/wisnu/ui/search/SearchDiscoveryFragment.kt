package space.mrandika.wisnu.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import space.mrandika.wisnu.databinding.FragmentSearchDiscoveryBinding
import space.mrandika.wisnu.model.city.City
import space.mrandika.wisnu.model.poi.POI
import space.mrandika.wisnu.ui.itinerary.city.CityDetailActivity
import space.mrandika.wisnu.ui.poi.detail.POIDetailActivity

@AndroidEntryPoint
class SearchDiscoveryFragment : Fragment() {
    private var _binding: FragmentSearchDiscoveryBinding? = null
    private val binding get() = _binding

    private lateinit var citiesAdapter: CitiesAdapter
    private lateinit var poiAdapter: POIAdapter
    private val viewModel: SearchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchDiscoveryBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.getDiscoveryResult()

            viewModel.state.collect { state ->
                setTopCityData(state.cities)
                setTopPoiData(state.pois)
            }
        }

        val cityLayoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL,false)
        val poiLayoutManager = GridLayoutManager(requireActivity(), 2)

        binding?.discoveryContent?.apply {
            rvTopCity.layoutManager = cityLayoutManager
            rvTopPoi.layoutManager = poiLayoutManager
            rvTopPoi.isNestedScrollingEnabled = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setTopCityData(value: List<City>) {
        citiesAdapter = CitiesAdapter(value)

        binding?.apply {
            discoveryContent.rvTopCity.adapter = citiesAdapter
        }

        citiesAdapter.setOnItemClickCallback(object : CitiesAdapter.OnItemClickCallback{
            override fun onItemClicked(id: Int) {
                val intent = Intent(requireActivity(), CityDetailActivity::class.java).apply {
                    putExtra("id", id)
                }

                startActivity(intent)
            }
        })
    }

    private fun setTopPoiData(value: List<POI>) {
        poiAdapter = POIAdapter(value)

        binding?.apply {
            discoveryContent.rvTopPoi.adapter = poiAdapter
        }

        poiAdapter.setOnItemClickCallback(object : POIAdapter.OnItemClickCallback {
            override fun onItemClicked(id: Int) {
                val intent = Intent(requireActivity(), POIDetailActivity::class.java).apply {
                    putExtra("id", id)
                }

                startActivity(intent)
            }
        })
    }
}