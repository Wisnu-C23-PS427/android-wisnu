package space.mrandika.wisnu.ui.itinerary.ticket

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
import space.mrandika.wisnu.databinding.FragmentItineraryTicketBinding
import space.mrandika.wisnu.model.poi.POI
import space.mrandika.wisnu.model.transaction.POITicketOrder
import space.mrandika.wisnu.ui.itinerary.ItineraryViewModel

class ItineraryTicketFragment : Fragment() {
    private var _binding : FragmentItineraryTicketBinding? = null
    private val binding get() = _binding
    private val viewModel : ItineraryViewModel by activityViewModels()

    val pois: MutableList<POI> = mutableListOf()
    val tickets: MutableList<POITicketOrder> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItineraryTicketBinding.inflate(inflater,container,false)
        return binding?.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar: Toolbar? = activity?.findViewById(R.id.toolbar)

        toolbar?.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        lifecycleScope.launch {
            val numAdult = viewModel.state.value.adult
            val numChild = viewModel.state.value.child

            viewModel.state.collect {state ->
                pois.clear()
                tickets.clear()

                state.itineraries.forEach { itinerary ->
                    itinerary.poi.filter { poi ->
                        poi.tickets?.isTicketingEnabled == true
                    }.forEach { poi ->
                        pois.add(poi)
                        tickets.add(POITicketOrder(poi.id ?: 0, poi, numAdult, numChild))
                    }
                }

                setTicket(pois)

                setViewActivity()
            }
        }

    }

    private fun setTicket(data: List<POI>){
        binding?.rvTicket?.apply {
            adapter = ItineraryTicketAdapter(data)

            layoutManager = LinearLayoutManager(requireContext())
        }
    }
    private fun setViewActivity() {
        val tvTitle : TextView? = activity?.findViewById(R.id.tv_title)
        val tvDescription : TextView? = activity?.findViewById(R.id.tv_description)

        binding?.apply {
            btnSkip.setOnClickListener {
                // Set to empty
                viewModel.setTicket(mutableListOf())

                findNavController().navigate(R.id.action_ticketFragment_to_guideFragment)
            }

            btnNext.setOnClickListener {
                viewModel.setTicket(tickets)
                findNavController().navigate(R.id.action_ticketFragment_to_guideFragment)
            }
        }

        tvDescription?.apply {
            visibility = View.VISIBLE
            text = "Mau sekalian beli di Wisnu?"
        }

        tvTitle?.apply {
            tvTitle.visibility = View.VISIBLE
            tvTitle.text = "Perlu tiket\n" + "masuk, nih."
        }
    }
}