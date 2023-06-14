package space.mrandika.wisnu.ui.itinerary.transaction.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.FragmentPaymentBinding
import space.mrandika.wisnu.ui.itinerary.ItineraryViewModel

class PaymentFragment : Fragment() {
    private var _binding : FragmentPaymentBinding? = null
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPaymentBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar: Toolbar? = activity?.findViewById(R.id.toolbar)
        val tvDescription : TextView? = activity?.findViewById(R.id.tv_description)
        val tvTitle : TextView? = activity?.findViewById(R.id.tv_title)

        toolbar?.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        tvDescription?.text = getString(R.string.title_transaction_finish_description)
        tvTitle?.text = getString(R.string.title_transaction_finish)

        binding?.btnFinish?.setOnClickListener {
            activity?.finish()
        }
    }
}