package space.mrandika.wisnu.ui.itinerary.person

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.FragmentItineraryPersonBinding
import space.mrandika.wisnu.ui.itinerary.ItineraryViewModel
import space.mrandika.wisnu.ui.itinerary.ticket.ItineraryTicketFragment

class ItineraryPersonFragment : Fragment() {
    private var _binding : FragmentItineraryPersonBinding? = null
    private val binding get() = _binding!!
    private val activityViewModel: ItineraryViewModel by activityViewModels()
    private var adult = 1
    private var child = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       _binding = FragmentItineraryPersonBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar: Toolbar? = activity?.findViewById(R.id.toolbar)

        toolbar?.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        setViewActivity()
        setView()
    }
    private fun setView(){
        binding.cardAdult.apply {
            tvTypePerson.text = "Orang Dewasa"
            tvAge.text = "Untuk umur 15 tahun keatas."
            tvCount.text = adult.toString()
            Glide.with(iconCardPeople)
                .load(R.drawable.baseline_people_alt_24)
                .into(iconCardPeople)
            btnPlus.setOnClickListener {
                adult++
                tvCount.text = adult.toString()
            }
            btnMinus.setOnClickListener {
                if (adult != 1){
                    adult--
                }
                tvCount.text = adult.toString()
            }
        }

        binding.cardChild.apply {
            tvTypePerson.text = "Adik dan anak-anak"
            tvAge.text = "Untuk umur dibawah 15 tahun."
            tvCount.text = child.toString()
            Glide.with(iconCardPeople)
                .load(R.drawable.baseline_emoji_people_24_secondary)
                .into(iconCardPeople)
            btnPlus.setOnClickListener {
                child++
                tvCount.text = child.toString()
            }
            btnMinus.setOnClickListener {
                if (child != 0){
                    child--
                }
                tvCount.text = child.toString()
            }
        }
    }
    private fun setViewActivity() {
        val tvTitle : TextView? = activity?.findViewById(R.id.tv_title)
        val tvDescription : TextView? = activity?.findViewById(R.id.tv_description)

        binding.btnSkip.setOnClickListener {
            activityViewModel.setAdult(1)
            findNavController().navigate(R.id.action_personFragment_to_ticketFragment)
        }

        binding.btnNext.setOnClickListener {
            activityViewModel.setAdult(adult)
            activityViewModel.setChild(child)

            findNavController().navigate(R.id.action_personFragment_to_ticketFragment)
        }

        tvDescription?.apply {
            visibility = View.VISIBLE
            text = "Agar perjalananmu lebih sat set sat set"
        }

        tvTitle?.apply {
            tvTitle.visibility = View.VISIBLE
            tvTitle.text = "Kesini\nbareng siapa?"
        }
    }
}