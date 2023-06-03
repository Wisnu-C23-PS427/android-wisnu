package space.mrandika.wisnu.ui.poi.poigalery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.FragmentGaleryPoiBinding

class GalleryPoiFragment : Fragment() {

    private var _binding : FragmentGaleryPoiBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGaleryPoiBinding.inflate(inflater, container, false)
        return  binding.root
    }

}