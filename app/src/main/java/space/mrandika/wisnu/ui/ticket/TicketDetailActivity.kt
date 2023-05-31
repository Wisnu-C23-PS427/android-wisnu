package space.mrandika.wisnu.ui.ticket

import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.oned.Code128Writer
import com.google.zxing.qrcode.QRCodeWriter
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.ActivityTicketDetailBinding
import space.mrandika.wisnu.databinding.SheetTicketDetailBinding

class TicketDetailActivity : AppCompatActivity() {
    /**
     * ViewBinding
     */
    private lateinit var binding: ActivityTicketDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTicketDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnShowTicket.setOnClickListener {
            showTicket()
        }
    }

    private fun showTicket() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val bottomSheetBinding: SheetTicketDetailBinding = SheetTicketDetailBinding.inflate(layoutInflater, null, false)
        bottomSheetDialog.setContentView(bottomSheetBinding.root)

        bottomSheetBinding.btnClose.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        val code = "TSCI-ABCD-EFGH-IJKL-MNOP"

        bottomSheetBinding.imageTicketCode.setImageBitmap(generateTicketCode(code))
        bottomSheetBinding.textTicketCode.text = code

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