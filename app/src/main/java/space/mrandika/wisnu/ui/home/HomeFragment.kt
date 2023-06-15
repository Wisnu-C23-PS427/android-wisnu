package space.mrandika.wisnu.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
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
import space.mrandika.wisnu.ui.city.list.CityListActivity
import space.mrandika.wisnu.ui.event.detail.EventDetailActivity
import space.mrandika.wisnu.ui.event.list.EventListActivity
import space.mrandika.wisnu.ui.poi.categories.POICategoryActivity
import space.mrandika.wisnu.ui.poi.detail.POIDetailActivity
import space.mrandika.wisnu.ui.poi.list.POIListActivity

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

        viewModel.getCategories()

        viewModel.getRecommendation()
        viewModel.getEvent()

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                val stateData = state.categories.isEmpty() && state.recommendations.isEmpty() && state.events.isEmpty()
                loadingStateIsToggled(stateData)
                errorStateIsToggled(state.isError)

                binding?.detailContent?.root?.visibility = if ((!stateData || state.isLoading) && !state.isError) View.VISIBLE else View.GONE

                setCategories(state.categories)
                setRecommendation(state.recommendations)
                setEvent(state.events)
            }
        }

        binding?.detailContent?.apply {
            rvIcon.layoutManager = GridLayoutManager(requireContext(),3)

            btnPoiAll.setOnClickListener {
                val intent = Intent(requireActivity(), POIListActivity::class.java)
                startActivity(intent)
            }

            btnEventAll.setOnClickListener {
                val intent = Intent(requireActivity(), EventListActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun loadingStateIsToggled(value: Boolean) {
        Log.d("TicketListFragment-isLoading", value.toString())
        binding?.apply {
            stateLoading.root.visibility = if (value) View.VISIBLE else View.GONE
        }
    }

    private fun errorStateIsToggled(value: Boolean) {
        Log.d("TicketListFragment-isError", value.toString())
        binding?.apply {
            stateError.root.visibility = if (value) View.VISIBLE else View.GONE
        }
    }

    private fun setCategories(data: List<Category>) {
        val adapter = CategoriesAdapter(data)

        binding?.detailContent?.apply {
            rvIcon.adapter = adapter
        }

        adapter.setOnItemClickCallback(object : CategoriesAdapter.OnItemClickCallback {
            override fun onItemClicked(name: String) {
                val intent = Intent(requireActivity(), POICategoryActivity::class.java).apply {
                    putExtra("category", name)
                }

                startActivity(intent)
            }
        })
    }

    private fun setRecommendation(recommendation : List<POI>){
        val adapter = RecommendationAdapter(recommendation)

        binding?.detailContent?.apply {
            rvRecomendation.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            rvRecomendation.adapter = adapter
        }

        adapter.setOnItemClickCallback(object : RecommendationAdapter.OnItemClickCallback {
            override fun onItemClicked(id: Int) {
                val intent = Intent(requireActivity(), POIDetailActivity::class.java).apply {
                    putExtra("id", id)
                }

                startActivity(intent)
            }
        })
    }

    private fun setEvent(event: List<Event>){
        val adapter = EventsAdapter(event)

        binding?.detailContent?.apply {
            rvEvent.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            rvEvent.adapter = adapter
        }

        adapter.setOnItemClickCallback(object : EventsAdapter.OnItemClickCallback {
            override fun onItemClicked(id: Int) {
                val intent = Intent(requireActivity(), EventDetailActivity::class.java).apply {
                    putExtra("id", id)
                }

                startActivity(intent)
            }

        })
    }
}