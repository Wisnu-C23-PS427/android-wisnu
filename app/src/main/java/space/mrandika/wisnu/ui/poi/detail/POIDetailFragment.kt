package space.mrandika.wisnu.ui.poi.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import space.mrandika.wisnu.BuildConfig
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.FragmentPoiDetailBinding
import space.mrandika.wisnu.databinding.GuideSheetBinding
import space.mrandika.wisnu.model.guide.Guide
import space.mrandika.wisnu.model.poi.POI
import space.mrandika.wisnu.ui.poi.categories.POICategoryFragment
import space.mrandika.wisnu.ui.poi.gallery.GalleryPoiFragment

@AndroidEntryPoint
class PoiDetailFragment: Fragment() {
    private var _binding : FragmentPoiDetailBinding? = null
    private val binding get() = _binding

    private val viewModel: POIDetailViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPoiDetailBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch{
            viewModel.getDetailPoi(1)
            viewModel.state.collect{
                isLoading(it.isLoading)
                isError(it.isError)
                isEmpty(it.isEmpty)

                it.DetailResult?.data?.let { PoiData ->
                    setData(PoiData)
                    PoiData.guides?.let { guides -> setDataAdapter(guides) }
                }

            }
        }
        binding?.detailContent?.apply {
            tvPlace.setOnClickListener {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, POICategoryFragment())
                    .commit()
            }

            btnGallery.setOnClickListener {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, GalleryPoiFragment())
                    .commit()
            }
        }
    }
    private fun setData(data: POI){
        binding?.detailContent?.apply {
            if (BuildConfig.IS_SERVICE_UP) {
                Glide.with(ivImageDetail)
                    .load(data.image)
                    .into(ivImageDetail)
            } else {
                ivImageDetail.setImageResource(R.drawable.mock_attraction_item)
            }

            tvPlace.text = data.name
            tvLocation.text = data.location
            tvBackgroundStory.text = data.backgroundStory
        }
    }
    private fun setDataAdapter(data : List<Guide>){
        binding?.detailContent?.apply {
            rvGuide.layoutManager = LinearLayoutManager(requireContext())

            val adapter = GuideAdapter(data)
            adapter.setOnItemClickCallback(object : GuideAdapter.OnItemClickCallback {
                override fun onItemClicked(guide: Guide) {
                    showGuide(guide)
                }
            })

            rvGuide.adapter = adapter
            rvGuide.isNestedScrollingEnabled = false
        }

    }

    private fun isLoading(value:Boolean){
        binding?.apply {
            stateLoading.root.visibility = if (value) View.VISIBLE else View.GONE
            detailContent.root.visibility = if (!value) View.VISIBLE else View.GONE
        }
    }
    private fun isError(value:Boolean){
        binding?.apply {
            stateError.root.visibility = if (value) View.VISIBLE else View.GONE
            detailContent.root.visibility = if (!value) View.VISIBLE else View.GONE
        }
    }
    private fun isEmpty(value:Boolean){
        binding?.apply {
            stateEmpty.root.visibility = if (value) View.VISIBLE else View.GONE
            detailContent.root.visibility = if (!value) View.VISIBLE else View.GONE
        }
    }
    private fun showGuide(guide: Guide) {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val bottomSheetBinding: GuideSheetBinding = GuideSheetBinding.inflate(layoutInflater,null, false)

        bottomSheetDialog.setContentView(bottomSheetBinding.root)
        bottomSheetBinding.apply {
            if (BuildConfig.IS_SERVICE_UP) {
                Glide.with(ivGuideImage)
                    .load(guide.image)
                    .into(ivGuideImage)
            } else {
                ivGuideImage.setImageResource(R.drawable.mock_guide_item)
            }

            tvGuideName.text = guide.name
        }

        bottomSheetBinding.btnClose.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()
    }

}