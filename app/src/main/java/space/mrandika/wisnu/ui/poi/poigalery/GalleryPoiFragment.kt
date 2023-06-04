package space.mrandika.wisnu.ui.poi.poigalery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.coroutines.launch
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.FragmentGaleryPoiBinding
import space.mrandika.wisnu.model.gallery.Gallery
import space.mrandika.wisnu.ui.poi.poidetail.PoiDetailViewModel

class GalleryPoiFragment : Fragment() {
    private var _binding : FragmentGaleryPoiBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PoiDetailViewModel by viewModels()
    companion object{
        val idPOI = 0
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGaleryPoiBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.getDetailPoi(idPOI)
        }
    }
    private fun setSeparateImage(gallery : List<Gallery>) {
        val listVr: MutableList<Gallery> = mutableListOf()
        val listImage: MutableList<Gallery> = mutableListOf()
        for(image in gallery){
            if(image.isVrCapable == true){
                listVr.add(image)
            }else{
                listImage.add(image)
            }
        }
       setData(listImage)
    }
    private fun setData(listImage: MutableList<Gallery>) {
        val list = listOf(
            R.drawable.depok,
            R.drawable.wisnu_empty,
            R.drawable.wisnu_error,
            R.drawable.galery_icon,
            R.drawable.baseline_image_24
        )
        val galleryAdapter = GalleryAdapter(list)
        binding.rvGallery.adapter = galleryAdapter
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.rvGallery.layoutManager = layoutManager
    }
}