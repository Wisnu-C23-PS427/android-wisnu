package space.mrandika.wisnu.ui.poi.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import space.mrandika.wisnu.BuildConfig
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.ActivityPoiDetailBinding
import space.mrandika.wisnu.databinding.SheetGuideDetailBinding
import space.mrandika.wisnu.model.guide.Guide
import space.mrandika.wisnu.model.poi.POI
import space.mrandika.wisnu.ui.poi.gallery.POIGalleryActivity
import java.io.Serializable

@AndroidEntryPoint
class POIDetailActivity : AppCompatActivity() {
    /**
     * ViewBinding
     */
    private lateinit var binding: ActivityPoiDetailBinding

    private val viewModel: POIDetailViewModel by viewModels()

    private var poiId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPoiDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = binding.detailContent.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        poiId = intent.getIntExtra("id", 0)

        poiId?.let { id ->
            viewModel.getDetailPoi(id)
        }

        lifecycleScope.launch{
            viewModel.state.collect{ state ->
                Log.d("POIDetailActivity", state.toString())
                loadingStateIsToggled(state.isLoading)
                errorStateIsToggled(state.isError)

                state.DetailResult?.data?.let { PoiData ->
                    toolbar.title = PoiData.name
                    setData(PoiData)
                    PoiData.guides?.let { guides -> setDataAdapter(guides) }
                }
            }
        }

        binding.detailContent.apply {
            btnGallery.setOnClickListener {
                val intent = Intent(this@POIDetailActivity, POIGalleryActivity::class.java).apply {
                    putExtra("galleries", viewModel.state.value.DetailResult?.data?.galleries as Serializable)
                }
                startActivity(intent)
            }
        }
    }

    private fun setData(data: POI){
        binding.detailContent.apply {
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
        binding.detailContent.apply {
            rvGuide.layoutManager = LinearLayoutManager(this@POIDetailActivity)

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

    private fun loadingStateIsToggled(value: Boolean) {
        Log.d("OrderDetailActivity-isLoading", value.toString())
        binding.apply {
            stateLoading.root.visibility = if (value) View.VISIBLE else View.GONE
            detailContent.root.visibility = if (!value) View.VISIBLE else View.GONE
        }
    }

    private fun errorStateIsToggled(value: Boolean) {
        Log.d("OrderDetailActivity-isError", value.toString())
        binding.apply {
            stateError.root.visibility = if (value) View.VISIBLE else View.GONE
            detailContent.root.visibility = if (!value) View.VISIBLE else View.GONE
        }
    }

    private fun showGuide(guide: Guide) {
        val bottomSheetDialog = BottomSheetDialog(this)
        val bottomSheetBinding: SheetGuideDetailBinding = SheetGuideDetailBinding.inflate(layoutInflater,null, false)

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

            lifecycleScope.launch {
                viewModel.getGuide(guide.id ?: 0)

                viewModel.state.collect { state ->
                    tvCity.text = state.DetailResult?.data?.location
                    tvAvgStar.text = state.GuideResult?.avgStar.toString()
                    rvReviews.adapter = GuideReviewAdapter(state.GuideResult?.reviews ?: emptyList())
                }
            }

            rvReviews.layoutManager = LinearLayoutManager(this@POIDetailActivity)
            rvReviews.isNestedScrollingEnabled = false
        }

        bottomSheetBinding.btnClose.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()
    }
}