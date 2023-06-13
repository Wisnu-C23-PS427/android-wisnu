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
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.FragmentSearchResultBinding
import space.mrandika.wisnu.model.city.City
import space.mrandika.wisnu.model.poi.POI
import space.mrandika.wisnu.ui.itinerary.city.CityDetailActivity
import space.mrandika.wisnu.ui.poi.detail.POIDetailActivity

@AndroidEntryPoint
class SearchResultFragment : Fragment() {
    private var _binding: FragmentSearchResultBinding? = null
    private val binding get() = _binding

    private lateinit var citiesAdapter: CitiesResultAdapter
    private lateinit var poiAdapter: POIAdapter
    private val viewModel: SearchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                viewModel.getSearchResult(state.keyword, state.filter)

                binding?.resultContent?.textResultQuery?.text = String.format(resources.getString(R.string.search_result_query, state.keyword))
                setTopCityData(state.cities)
                setTopPoiData(state.pois)
            }
        }

        val cityLayoutManager = LinearLayoutManager(requireActivity())
        val poiLayoutManager = GridLayoutManager(requireActivity(), 2)

        binding?.resultContent?.apply {
            rvCityResult.layoutManager = cityLayoutManager
            rvPoiResult.layoutManager = poiLayoutManager
            rvCityResult.isNestedScrollingEnabled = false
            rvPoiResult.isNestedScrollingEnabled = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setTopCityData(value: List<City>) {
        citiesAdapter = CitiesResultAdapter(value)

        binding?.apply {
            resultContent.rvCityResult.adapter = citiesAdapter
        }

        citiesAdapter.setOnItemClickCallback(object : CitiesResultAdapter.OnItemClickCallback{
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
            resultContent.rvPoiResult.adapter = poiAdapter
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