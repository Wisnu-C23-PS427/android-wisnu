package space.mrandika.wisnu.ui.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import space.mrandika.wisnu.BuildConfig
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.FragmentAccountBinding
import space.mrandika.wisnu.model.account.Account
import space.mrandika.wisnu.ui.profile.trip.list.TripListActivity

class AccountFragment : Fragment() {
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding

    private val viewModel: AccountViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textTab: TextView? = activity?.findViewById(R.id.text_current_tab)
        textTab?.text = resources.getString(R.string.tab_account)

        lifecycleScope.launch {
            viewModel.getAccount()

            viewModel.state.collect { state ->
                loadingStateIsToggled(state.isLoading)
                errorStateIsToggled(state.isError)
                state.account?.let { account -> setData(account) }
            }
        }

        binding?.accountContent?.apply {
            menuMytrip.setOnClickListener {
                navigateToSavedTripList()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun loadingStateIsToggled(value: Boolean) {
        Log.d("AccountFragment-isLoading", value.toString())
        binding?.apply {
            stateLoading.root.visibility = if (value) View.VISIBLE else View.GONE
            accountContent.root.visibility = if (!value) View.VISIBLE else View.GONE
        }
    }

    private fun errorStateIsToggled(value: Boolean) {
        Log.d("AccountFragment-isError", value.toString())
        binding?.apply {
            stateError.root.visibility = if (value) View.VISIBLE else View.GONE
            accountContent.root.visibility = if (!value) View.VISIBLE else View.GONE
        }
    }

    private fun setData(value: Account) {
        binding?.accountContent?.apply {
            if (BuildConfig.IS_SERVICE_UP) {
                Glide.with(requireActivity())
                    .load(value.image)
                    .into(imageProfile)
            } else {
                imageProfile.setImageResource(R.drawable.mock_guide_item)
            }

            textName.text = value.name
            textEmail.text = value.email
            textJoinDate.text = String.format(resources.getString(R.string.join_date, value.createdAt))
        }
    }

    private fun navigateToSavedTripList() {
        val intent = Intent(activity, TripListActivity::class.java)

        startActivity(intent)
    }
}