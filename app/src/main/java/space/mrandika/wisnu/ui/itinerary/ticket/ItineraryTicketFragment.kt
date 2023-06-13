package space.mrandika.wisnu.ui.itinerary.ticket

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
import kotlinx.coroutines.launch
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.FragmentItineraryTicketBinding
import space.mrandika.wisnu.model.poi.POI
import space.mrandika.wisnu.model.ticket.Ticket
import space.mrandika.wisnu.model.transaction.POITicketOrder
import space.mrandika.wisnu.ui.itinerary.ItineraryViewModel
import space.mrandika.wisnu.ui.itinerary.guides.ItineraryGuidesFragment

class ItineraryTicketFragment : Fragment() {
    private var _binding : FragmentItineraryTicketBinding? = null
    private val binding get() = _binding!!
    private val viewModel : ItineraryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItineraryTicketBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            val pois: MutableList<POI> = mutableListOf()
            val tickets: MutableList<POITicketOrder> = mutableListOf()

            val numAdult = viewModel.state.value.adult
            val numChild = viewModel.state.value.child

            viewModel.state.value.apply{
                pois.clear()
                tickets.clear()

                itineraries.forEach { itinerary ->
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
        binding.rvTicket.apply {
            adapter = ItineraryTicketAdapter(data)

            layoutManager = LinearLayoutManager(requireContext())
        }
    }
    private fun setViewActivity() {
        val tvTitle : TextView? = activity?.findViewById(R.id.tv_title)
        val tvDescription : TextView? = activity?.findViewById(R.id.tv_description)
        val btnMain : MaterialButton? = activity?.findViewById(R.id.btn_main)
        val tvButton : TextView? = activity?.findViewById(R.id.tv_text_button)

        tvButton?.apply {
            visibility = View.VISIBLE
            text = context.getString(R.string.lewati)
            setOnClickListener {
                // Set to empty
                viewModel.setTicket(mutableListOf())

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, ItineraryGuidesFragment())
                    .commit()
            }
        }

        btnMain?.apply {
            text = context.getString(R.string.lanjut)
            setIconResource(R.drawable.baseline_arrow_forward_24)
            setOnClickListener {

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, ItineraryGuidesFragment())
                    .commit()
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