package space.mrandika.wisnu.ui.itinerary.itineraryperson

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.FragmentItineraryPersonBinding
import space.mrandika.wisnu.ui.itinerary.ItineraryViewModel

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
                .load(R.drawable.child_icon)
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
        val btnMain : MaterialButton? = activity?.findViewById(R.id.btn_main)
        val tvButton : TextView? = activity?.findViewById(R.id.tv_text_button)
        tvButton?.apply {
            visibility = View.VISIBLE
            text = "Aku Pergi Sendiri"
            setOnClickListener {
                activityViewModel.setAdult(1)
            }
        }
        btnMain?.apply {
            text = "Lanjut"
            setIconResource(R.drawable.baseline_arrow_forward_24)
            setOnClickListener {
                activityViewModel.setAdult(adult)
                activityViewModel.setChild(child)
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, ItineraryPersonFragment())
                    .commit()
            }
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