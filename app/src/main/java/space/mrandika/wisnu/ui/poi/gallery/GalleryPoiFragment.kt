package space.mrandika.wisnu.ui.poi.gallery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import space.mrandika.wisnu.BuildConfig
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.FragmentGaleryPoiBinding
import space.mrandika.wisnu.model.gallery.Gallery
import space.mrandika.wisnu.ui.poi.detail.POIDetailViewModel
@AndroidEntryPoint
class GalleryPoiFragment : Fragment() {
    private var _binding : FragmentGaleryPoiBinding? = null
    private val binding get() = _binding
    private val viewModel: POIDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGaleryPoiBinding.inflate(inflater, container, false)
        return  binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.getDetailPoi(idPOI)

            viewModel.state.value.apply {
                DetailResult?.data?.galleries?.let { galleries ->
                    setData(galleries.filter { gallery ->
                        gallery.isVrCapable == false
                    })
                }
            }
        }
    }

    private fun setData(listImage: List<Gallery>) {
        val galleryAdapter = GalleryAdapter(listImage)
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        binding?.apply {
            rvGallery.adapter = galleryAdapter
            rvGallery.layoutManager = layoutManager
        }
    }

    companion object {
        const val idPOI = 0
    }
}