package space.mrandika.wisnu.ui.itinerary.transaction.detailpayment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.FragmentTransactionDetailBinding
import space.mrandika.wisnu.model.guide.Guide
import space.mrandika.wisnu.ui.itinerary.ItineraryViewModel
import space.mrandika.wisnu.ui.itinerary.transaction.payment.PaymentFragment

class DetailTransactionFragment : Fragment() {
    private var _binding : FragmentTransactionDetailBinding? = null
    private val binding get() = _binding
    private var count = 1
    private val viewModel : ItineraryViewModel by activityViewModels()
    private var totalCost : Int = 0

    private var totalTicket: Int = 0
    private var totalGuide : Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTransactionDetailBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar: Toolbar? = activity?.findViewById(R.id.toolbar)

        toolbar?.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        setViewActivity()

        Log.d("DetailTransactionFragment-tickets", viewModel.state.value.tickets.toString())
        Log.d("DetailTransactionFragment-guide", viewModel.state.value.guide.toString())

        if (viewModel.state.value.tickets.isEmpty() && viewModel.state.value.guide == null) {
            noTransaction()
        } else if (viewModel.state.value.tickets.isEmpty() && viewModel.state.value.guide != null) {
            viewModel.state.value.guide?.guide?.let { withGuide(it) }
        } else if (viewModel.state.value.tickets.isNotEmpty() && viewModel.state.value.guide == null) {
            withTicket()
        } else {
            viewModel.state.value.guide?.guide?.let { withGuide(it) }
            withTicket()
        }

        sumCost()
    }

    private fun withGuide(guide: Guide) {
        val tvDescription : TextView? = activity?.findViewById(R.id.tv_description)

        totalGuide = viewModel.state.value.guide?.guide?.price ?: 0

        binding?.apply {
            guideDuration.tvTypePerson.text = getString(R.string.durasi_perjalanan)
            guideDuration.tvAge.text = getString(R.string.card_description_duration)
            guideDuration.tvAge.fontFeatureSettings = R.font.poppins_medium.toString()
            guideDuration.iconCardPeople.visibility = View.GONE
            guideDuration.tvCount.text = count.toString()
            tvGuidePrice.text = totalGuide.toString()
            tvTicketPrice.text = totalTicket.toString()

            tvDescription?.apply {
                visibility = View.VISIBLE
                text = String.format(getString(R.string.guide_selection_transaction, guide.name))
            }

            guideDuration.btnPlus.setOnClickListener {
                count++
                totalGuide = count * (viewModel.state.value.guide?.guide?.price ?: 0)
                guideDuration.tvCount.text = count.toString()
                tvGuidePrice.text = totalGuide.toString()
                sumCost()
            }

            guideDuration.btnMinus.setOnClickListener {
                if (count != 1) {
                    count--
                }

                totalGuide = count * (viewModel.state.value.guide?.guide?.price ?: 0)
                guideDuration.tvCount.text = count.toString()
                tvGuidePrice.text = totalGuide.toString()
                sumCost()
            }
        }
    }

    private fun withTicket() {
        viewModel.state.value.tickets.forEach { ticket ->
            totalTicket += ticket.poi.tickets?.adultPrice ?: 0
            totalTicket += ticket.poi.tickets?.childPrice ?: 0
        }

        binding?.apply {
            if (viewModel.state.value.guide == null) {
                guideDuration.root.visibility = View.GONE
            }

            tvGuidePrice.text = totalGuide.toString()
            tvTicketPrice.text = totalTicket.toString()
        }

        val tvDescription : TextView? = activity?.findViewById(R.id.tv_description)
        tvDescription?.apply {
            visibility = View.VISIBLE
            text = context.getString(R.string.tiket_kamu_diatur_wisnu)
        }
    }

    private fun noTransaction(){
        val tvDescription : TextView? = activity?.findViewById(R.id.tv_description)
        val tvTitle : TextView? = activity?.findViewById(R.id.tv_title)
        tvDescription?.apply {
            visibility = View.VISIBLE
            text = context.getString(R.string.kamu_dapat_melihat_perjalanan_yang_disimpan_pada_menu_akun)
        }

        tvTitle?.apply {
            tvTitle.visibility = View.VISIBLE
            tvTitle.text = context.getString(R.string.simpan_perjalanan)
        }

        binding?.apply {
            detailTransaction.visibility = View.GONE
            guideDuration.root.visibility = View.GONE
            paymentMethod.visibility = View.GONE

            btnNext.setText(R.string.action_save)
            btnNext.setOnClickListener {
                viewModel.saveTrip {
                    activity?.finish()
                }
            }
        }
    }
    private fun sumCost() {
        viewModel.apply {
            totalCost =  totalTicket + totalGuide + 2000

            binding?.tvApplicationCostPrice?.text = 2000.toString()
            binding?.tvTotalCost?.text = totalCost.toString()
        }

    }

    private fun setViewActivity() {
        val tvTitle : TextView? = activity?.findViewById(R.id.tv_title)

        binding?.btnNext?.setOnClickListener {
            lifecycleScope.launch {
                viewModel.createTransaction {
                    findNavController().navigate(R.id.action_detailFragment_to_paymentFragment)
                }
            }
        }

        tvTitle?.apply {
            tvTitle.visibility = View.VISIBLE
            tvTitle.text = context.getString(R.string.buat_transaksi)
        }
    }
}