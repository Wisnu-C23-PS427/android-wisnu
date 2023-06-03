package space.mrandika.wisnu.ui.poi.poidetail

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.ActivityDetailPoiBinding
import space.mrandika.wisnu.databinding.GuideSheetBinding
import space.mrandika.wisnu.model.guide.Guide
import space.mrandika.wisnu.model.poi.POI
@AndroidEntryPoint
class DetailPoiActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailPoiBinding
    private val viewModel: PoiDetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPoiBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.detailContent.btnGallery.setOnClickListener {

        }
        lifecycleScope.launch {
            viewModel.getDetailPoi(2)
            viewModel.state.collect {state ->
                isLoading(state.isLoading)
                isError(state.isError)
                state.DetailResult?.data?.guides?.let { data -> setDataAdapter(data) }
                state.DetailResult?.data?.let { data -> setData(data) }
            }
        }
    }

    private fun showGuide() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val bottomSheetBinding: GuideSheetBinding = GuideSheetBinding.inflate(layoutInflater,null, false)
        bottomSheetDialog.setContentView(bottomSheetBinding.root)
        bottomSheetBinding.apply {
            val image = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fen.wikipedia.org%2Fwiki%2FImage&psig=AOvVaw0zyFBferV3mAzw2fB8P36Y&ust=1685904925893000&source=images&cd=vfe&ved=0CBEQjRxqFwoTCNjwxbPjp_8CFQAAAAAdAAAAABAE"
            Glide.with(ivGuideImage)
                .load(image)
                .into(ivGuideImage)
            tvGuideName.text = "Data"
        }
        bottomSheetBinding.btnClose.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.show()
    }

    private fun setData(data: POI){
        binding.detailContent.apply {
            Glide.with(ivImageDetail)
                .load(data.image)
                .into(ivImageDetail)
            tvPlace.text = data.name
            tvLocation.text = data.location
            tvBackgroundStory.text = data.backgroundStory
        }
    }
    private fun setDataAdapter(data : List<Guide>){
        binding.detailContent.apply {
            rvGuide.layoutManager = LinearLayoutManager(this@DetailPoiActivity)
            val adapter = GuideAdapter(data){guide->
                lifecycleScope.launch {
                    guide.id?.let { viewModel.getDetailPoi(it) }
                    viewModel.state.collect{ state ->
                        isLoading(state.isLoading)
                        isError(state.isError)
                        isEmpty(state.isEmpty)
                    }
                }
                showGuide()
            }
            rvGuide.adapter = adapter
        }
    }
    private fun isLoading(value:Boolean){
        binding.apply {
            stateLoading.root.visibility = if (value) View.VISIBLE else View.GONE
            detailContent.root.visibility = if (!value) View.VISIBLE else View.GONE
        }
    }
    private fun isError(value:Boolean){
        binding.apply {
            stateError.root.visibility = if (value) View.VISIBLE else View.GONE
            detailContent.root.visibility = if (!value) View.VISIBLE else View.GONE
        }
    }
    private fun isEmpty(value:Boolean){
        binding.apply {
            stateEmpty.root.visibility = if (value) View.VISIBLE else View.GONE
            detailContent.root.visibility = if (!value) View.VISIBLE else View.GONE
        }
    }

}