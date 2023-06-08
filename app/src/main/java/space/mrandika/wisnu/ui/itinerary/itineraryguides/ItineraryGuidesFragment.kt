package space.mrandika.wisnu.ui.itinerary.itineraryguides

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
import space.mrandika.wisnu.databinding.FragmentItinerartGuidesBinding
import space.mrandika.wisnu.model.poi.POI
import space.mrandika.wisnu.ui.itinerary.ItineraryViewModel
import space.mrandika.wisnu.ui.itinerary.transaction.detailpayment.DetailTransactionFragment

class ItineraryGuidesFragment : Fragment() {
    private var _binding : FragmentItinerartGuidesBinding? = null
    private val binding get() = _binding!!
    private val activityViewModel: ItineraryViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItinerartGuidesBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewActivity()
        lifecycleScope.launch {
            activityViewModel.state.value.apply {
                PoiData?.let { setData(it) }
            }
        }

    }
    private fun setData(data :List<POI>){

        for(poi in data){
            Log.d("data Guides", poi.toString())
            binding.rvGuides.apply {
                adapter = poi.guides?.let { ItineraryGuidesAdapter(it, activityViewModel) }
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }
    private fun setViewActivity() {
        val tvTitle : TextView? = activity?.findViewById(R.id.tv_title)
        val tvDescription : TextView? = activity?.findViewById(R.id.tv_description)
        val btnMain : MaterialButton? = activity?.findViewById(R.id.btn_main)
        val tvButton : TextView? = activity?.findViewById(R.id.tv_text_button)
        tvButton?.apply {
            visibility = View.VISIBLE
            text = "lewati"
            setOnClickListener {
                activityViewModel.state.value.includeGuide = false
                activityViewModel.setTotalGuide(0)
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, DetailTransactionFragment())
                    .commit()
            }
        }
        btnMain?.apply {
            text = "Lanjut"
            setIconResource(R.drawable.baseline_arrow_forward_24)
            setOnClickListener {
                activityViewModel.state.value.includeGuide = true
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, DetailTransactionFragment())
                    .commit()
            }
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