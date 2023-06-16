package space.mrandika.wisnu.ui.itinerary.guides

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.FragmentItineraryGuidesBinding
import space.mrandika.wisnu.model.guide.Guide
import space.mrandika.wisnu.model.transaction.POIGuideOrder
import space.mrandika.wisnu.ui.itinerary.ItineraryViewModel

class ItineraryGuidesFragment : Fragment() {
    private var _binding : FragmentItineraryGuidesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ItineraryViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItineraryGuidesBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar: Toolbar? = activity?.findViewById(R.id.toolbar)

        toolbar?.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        setViewActivity()

        val guides: MutableList<Guide> = mutableListOf()

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                state.itineraries.forEach { itinerary ->
                    itinerary.poi.forEach { poi ->
                        poi.guides?.let {
                            guides.clear()
                            guides.addAll(it)
                        }
                    }
                }
            }
        }

        setData(guides)
    }
    private fun setData(data: List<Guide>) {
        val adapter = ItineraryGuidesAdapter(data)

        binding.rvGuides.apply {
            this.layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }

        adapter.setOnItemClickCallback(object : ItineraryGuidesAdapter.OnItemClickCallback {
            override fun onItemClicked(guide: Guide) {
                viewModel.setGuide(POIGuideOrder(1, guide.id ?: 0, guide, 1))
            }


        })
    }

    private fun setViewActivity() {
        val tvTitle : TextView? = activity?.findViewById(R.id.tv_title)
        val tvDescription : TextView? = activity?.findViewById(R.id.tv_description)

        binding.btnSkip.setOnClickListener {
            viewModel.setGuide(null)

            findNavController().navigate(R.id.action_guideFragment_to_detailFragment)
        }

        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_guideFragment_to_detailFragment)
        }

        tvDescription?.apply {
            visibility = View.VISIBLE
            text = "Rekomendasi TemanWisnu"
        }

        tvTitle?.apply {
            tvTitle.visibility = View.VISIBLE
            tvTitle.text = "Mau \nditemenin?"
        }
    }
}