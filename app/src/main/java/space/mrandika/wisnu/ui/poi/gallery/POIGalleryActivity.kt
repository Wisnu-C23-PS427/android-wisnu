package space.mrandika.wisnu.ui.poi.gallery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import space.mrandika.wisnu.databinding.ActivityPoiGalleryBinding
import space.mrandika.wisnu.model.gallery.Gallery
import space.mrandika.wisnu.model.guide.Guide
import space.mrandika.wisnu.ui.poi.detail.GuideAdapter

@AndroidEntryPoint
class POIGalleryActivity : AppCompatActivity() {
    /**
     * ViewBinding
     */
    private lateinit var binding: ActivityPoiGalleryBinding

    private lateinit var galleries: List<Gallery>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPoiGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        galleries = intent.getSerializableExtra("galleries") as List<Gallery>

        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        setVRImageData(galleries.filter { gallery ->
            gallery.isVrCapable == true && gallery.isFromWisnuTeam == true
        })

        setOfficialImageDate(galleries.filter { gallery ->
            gallery.isVrCapable == false && gallery.isFromWisnuTeam == true
        })

        setImageData(galleries.filter { gallery ->
            gallery.isVrCapable == false && gallery.isFromWisnuTeam == false
        })
    }

    private fun setVRImageData(listImage: List<Gallery>) {
        val vrGalleryAdapter = VRGalleryAdapter(listImage)
        val layoutManager = LinearLayoutManager(this)

        binding.apply {
            rvVrGallery.adapter = vrGalleryAdapter
            rvVrGallery.layoutManager = layoutManager
            rvVrGallery.isNestedScrollingEnabled = false

            vrGalleryAdapter.setOnItemClickCallback(object : VRGalleryAdapter.OnItemClickCallback {
                override fun onItemClicked(gallery: Gallery) {
                    Toast.makeText(this@POIGalleryActivity, "Still on development!", Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    private fun setOfficialImageDate(listImage: List<Gallery>) {
        val imageGalleryAdapter = ImageGalleryAdapter(listImage)
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        binding.apply {
            rvOfficialGallery.adapter = imageGalleryAdapter
            rvOfficialGallery.layoutManager = layoutManager
            rvOfficialGallery.isNestedScrollingEnabled = false
        }
    }

    private fun setImageData(listImage: List<Gallery>) {
        val imageGalleryAdapter = ImageGalleryAdapter(listImage)
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        binding.apply {
            rvGallery.adapter = imageGalleryAdapter
            rvGallery.layoutManager = layoutManager
            rvGallery.isNestedScrollingEnabled = false
        }
    }
}