package space.mrandika.wisnu.ui.poi.gallery

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import space.mrandika.wisnu.databinding.ActivityVrImageViewerBinding

class VRImageViewerActivity : AppCompatActivity() {
    /**
     * ViewBinding
     */
    private lateinit var binding: ActivityVrImageViewerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVrImageViewerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}