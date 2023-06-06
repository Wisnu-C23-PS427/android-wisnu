package space.mrandika.wisnu.ui.order.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.ActivityOrderDetailBinding
import space.mrandika.wisnu.model.transaction.Transaction


@AndroidEntryPoint
class OrderDetailActivity : AppCompatActivity() {
    /**
     * ViewBinding
     */
    private lateinit var binding: ActivityOrderDetailBinding

    private val viewModel: OrderViewModel by viewModels()

    // View parameter
    private var id: Int? = null
    private lateinit var adapter: OrderPOIAdapter
    private lateinit var order: Transaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        id = intent.getIntExtra("id", 0)

        lifecycleScope.launch {
            id?.let { id -> viewModel.getOrder(id) }

            viewModel.state.collect { state ->
                loadingStateIsToggled(state.isLoading)
                errorStateIsToggled(state.isError)

                state.order?.let { order -> setData(order) }
            }
        }

        val layoutManager = object : LinearLayoutManager(this) { override fun canScrollVertically() = false }

        binding.orderContent.apply {
            rvPoi.layoutManager = layoutManager
        }
    }

    private fun loadingStateIsToggled(value: Boolean) {
        Log.d("OrderDetailActivity-isLoading", value.toString())
        binding.apply {
            stateLoading.root.visibility = if (value) View.VISIBLE else View.GONE
            orderContent.root.visibility = if (!value) View.VISIBLE else View.GONE
        }
    }

    private fun errorStateIsToggled(value: Boolean) {
        Log.d("OrderDetailActivity-isError", value.toString())
        binding.apply {
            stateError.root.visibility = if (value) View.VISIBLE else View.GONE
            orderContent.root.visibility = if (!value) View.VISIBLE else View.GONE
        }
    }

    private fun setData(value: Transaction) {
        order = value

        adapter = OrderPOIAdapter(value.pois)

        binding.orderContent.apply {
            val resId: Int
            val orderType: String
            val duration: Int = 0

            if (order.isGuideOrder == true && order.isTicketOrder == true) {
                resId = R.drawable.baseline_how_to_reg_64_primary
                orderType = resources.getString(R.string.trip_package)
            } else if (order.isGuideOrder == true && order.isTicketOrder == false) {
                resId = R.drawable.baseline_people_64_primary
                orderType = resources.getString(R.string.trip_guide)
            } else {
                resId = R.drawable.baseline_confirmation_number_64_primary
                orderType = resources.getString(R.string.trip_ticket)

                btnContactGuide.visibility = View.GONE
            }

            imageOrder.setImageResource(resId)
            textOrderName.text = orderType
            textOrderGuide.text = order.guide?.name ?: "-"
            textGuideDuration.text = "${order.guide?.startDate} - ${order.guide?.endDate}"
            textOrderDate.text = order.createdAt
            textOrderPrice.text = "Rp${order.price}"

            btnContactGuide.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:${order.guide?.phoneNumber}")
                startActivity(intent)
            }

            rvPoi.adapter = adapter
        }
    }
}