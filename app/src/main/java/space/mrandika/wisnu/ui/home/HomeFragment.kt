package space.mrandika.wisnu.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }
    private fun setIcons(){
        binding.rvIcon.layoutManager = GridLayoutManager(requireContext(),4)
        binding.rvIcon.adapter
    }
    private fun setRecommendation(){
        binding.rvRecomendation.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.rvRecomendation.adapter
    }
    private fun setEvent(){

        binding.rvEvent.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.rvEvent.adapter
    }
}