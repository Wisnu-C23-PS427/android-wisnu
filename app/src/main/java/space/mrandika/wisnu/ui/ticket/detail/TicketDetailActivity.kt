package space.mrandika.wisnu.ui.ticket.detail

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.zxing.BarcodeFormat
import com.google.zxing.oned.Code128Writer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import space.mrandika.wisnu.databinding.ActivityTicketDetailBinding
import space.mrandika.wisnu.databinding.SheetTicketDetailBinding
import space.mrandika.wisnu.model.ticket.Ticket

@AndroidEntryPoint
class TicketDetailActivity : AppCompatActivity() {
    /**
     * ViewBinding
     */
    private lateinit var binding: ActivityTicketDetailBinding

    private val viewModel: TicketViewModel by viewModels()

    // View parameter
    private var id: String? = null
    private lateinit var adultAdapter: AdultTicketAdapter
    private lateinit var childAdapter: ChildTicketAdapter
    private lateinit var ticket: Ticket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTicketDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        id = intent.getStringExtra("id")

        id?.let { id -> viewModel.getTicket(id) }

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                loadingStateIsToggled(state.isLoading)
                errorStateIsToggled(state.isError)

                state.ticket?.let { ticket -> setData(ticket) }
            }
        }

        val adultLayoutManager = object : LinearLayoutManager(this) { override fun canScrollVertically() = false }
        val childLayoutManager = object : LinearLayoutManager(this) { override fun canScrollVertically() = false }

        binding.ticketContent.apply {
            btnShowTicket.setOnClickListener {
                showTicket()
            }

            rvAdultTicketHolder.layoutManager = adultLayoutManager
            rvChildTicketHolder.layoutManager = childLayoutManager
        }
    }

    private fun loadingStateIsToggled(value: Boolean) {
        Log.d("TicketDetailActivity-isLoading", value.toString())
        binding.apply {
            stateLoading.root.visibility = if (value) View.VISIBLE else View.GONE
            ticketContent.root.visibility = if (!value) View.VISIBLE else View.GONE
        }
    }

    private fun errorStateIsToggled(value: Boolean) {
        Log.d("TicketDetailActivity-isError", value.toString())
        binding.apply {
            stateError.root.visibility = if (value) View.VISIBLE else View.GONE
            ticketContent.root.visibility = if (!value) View.VISIBLE else View.GONE
        }
    }

    private fun setData(value: Ticket) {
        ticket = value

        adultAdapter = AdultTicketAdapter(value.adult)
        childAdapter = ChildTicketAdapter(value.child)

        binding.ticketContent.apply {
            textTicketName.text = ticket.poi?.name
            textTicketDate.text = ticket.validDate
            textTicketAdultQty.text = "${ticket.adult.count()}x Rp. ${ticket.adult[0].price ?: 0}"
            textTicketChildQty.text = "${ticket.child.count()}x Rp. ${ticket.child[0].price ?: 0}"
            textTicketPrice.text = "Rp. " + ticket.totalPrice.toString()

            rvAdultTicketHolder.adapter = adultAdapter
            rvChildTicketHolder.adapter = childAdapter
        }
    }

    private fun showTicket() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val bottomSheetBinding: SheetTicketDetailBinding = SheetTicketDetailBinding.inflate(layoutInflater, null, false)
        bottomSheetDialog.setContentView(bottomSheetBinding.root)

        bottomSheetBinding.btnClose.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        id?.let {
            bottomSheetBinding.imageTicketCode.setImageBitmap(generateTicketCode(it))
            bottomSheetBinding.textTicketCode.text = it
        }

        bottomSheetDialog.show()
    }

    private fun generateTicketCode(code: String): Bitmap {
        val width = 2048
        val height = 512
        val bits = Code128Writer().encode(code, BarcodeFormat.CODE_128, width, height)

        return Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565).also {
            for (x in 0 until width) {
                for (y in 0 until height) {
                    it.setPixel(x, y, if (bits[x, y]) Color.BLACK else Color.WHITE)
                }
            }
        }
    }
}