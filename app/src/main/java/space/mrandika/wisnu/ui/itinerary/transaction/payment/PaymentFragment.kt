package space.mrandika.wisnu.ui.itinerary.transaction.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.button.MaterialButton
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.FragmentPaymentBinding
import space.mrandika.wisnu.ui.itinerary.ItineraryViewModel

class PaymentFragment : Fragment() {
    private var _binding : FragmentPaymentBinding? = null
    private val binding get() = _binding
    private val  activityViewModel : ItineraryViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPaymentBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnMain : MaterialButton? = activity?.findViewById(R.id.btn_main)
        btnMain?.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
    }
}