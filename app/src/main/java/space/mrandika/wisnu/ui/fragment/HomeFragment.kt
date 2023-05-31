package space.mrandika.wisnu.ui.fragment

import IconsAdapter
import android.os.Bundle
import android.provider.ContactsContract.Data
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import space.mrandika.wisnu.DataDummy
import space.mrandika.wisnu.databinding.FragmentHomeBinding
import space.mrandika.wisnu.ui.adapter.RecomendationAdapter
import space.mrandika.wisnu.ui.adapter.ReferensiAdapter


class HomeFragment : Fragment() {
   private var _binding : FragmentHomeBinding? = null
   private val binding get() = _binding!!
   override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View {
      _binding = FragmentHomeBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      setIcons()
      setRecommendation()
      setEvent()
   }

   private fun setIcons(){
      val listIcons = DataDummy.listIcon
      binding.rvIcon.layoutManager = GridLayoutManager(requireContext(),4)
      val adapter = IconsAdapter(listIcons)
      binding.rvIcon.adapter = adapter
   }
   private fun setRecommendation(){
      val listRecommendation = DataDummy.listRecomendation

      binding.rvRecomendation.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
      val adapter = RecomendationAdapter(listRecommendation)
      binding.rvRecomendation.adapter = adapter
   }
   private fun setEvent(){
      val listRecommendation = DataDummy.listEvent

      binding.rvEvent.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
      val adapter = RecomendationAdapter(listRecommendation)
      binding.rvEvent.adapter = adapter
   }
}