package space.mrandika.wisnu.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.ActivityAuthBinding
import space.mrandika.wisnu.ui.register.RegisterViewModel

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    private var _binding : ActivityAuthBinding? = null
    private val binding get() = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }
}