package space.mrandika.wisnu.ui.poi.poidetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.FragmentPoiDetailBinding

class PoiDetailFragment : Fragment() {
    private var _binding : FragmentPoiDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel:PoiDetailViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPoiDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}