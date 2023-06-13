package space.mrandika.wisnu.ui.itinerary.daycount

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.google.android.material.button.MaterialButton
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.FragmentDayCountBinding
import space.mrandika.wisnu.ui.itinerary.ItineraryViewModel
import space.mrandika.wisnu.ui.itinerary.poi.ItineraryPoiFragment


class DayCountFragment : Fragment() {
    private var _binding : FragmentDayCountBinding? = null
    private val binding get() = _binding

    private val activityViewModel : ItineraryViewModel by activityViewModels()
    private var dayCount = 1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDayCountBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            tvDayCount.text = dayCount.toString()
            btnAddDay.setOnClickListener {
                dayCount++
                tvDayCount.text = dayCount.toString()
            }

            btnMinusDay.setOnClickListener {
                if(dayCount != 1){
                    dayCount--
                }

                tvDayCount.text = dayCount.toString()
            }
        }


        setView()
        setDataViewModel()
    }
    private fun setDataViewModel(){
        activityViewModel.state.value.apply {
            day = dayCount
        }
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
                    .replace(R.id.fragmentContainer, ItineraryPoiFragment())
                    .commit()
            }
        }

        tvDescription?.apply {
            visibility = View.VISIBLE
            text = "Berapa lama, nih?"
        }

        tvTitle?.visibility = View.VISIBLE

        activityViewModel.state.value.apply{
            tvTitle?.text = String.format(getString(R.string.title_destination), city)
        }
    }
}