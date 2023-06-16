package space.mrandika.wisnu.ui.register


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.FragmentCheckBinding
import space.mrandika.wisnu.model.auth.RegisterResponse
import space.mrandika.wisnu.utils.ViewUtils


class CheckFragment : Fragment() {
    private var _binding: FragmentCheckBinding? = null
    private val binding get() = _binding
    private val viewModel : RegisterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCheckBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnMain: Button? = activity?.findViewById(R.id.btn_main)
        val btnSecondary: Button? = activity?.findViewById(R.id.btn_secondary)
        val tvChangeInformation : TextView? = activity?.findViewById(R.id.tv_forget_password)
        val tvTitle : TextView? = activity?.findViewById(R.id.tv_title)
        val tvDescription : TextView? = activity?.findViewById(R.id.tv_description)

        val toolbar: Toolbar? = activity?.findViewById(R.id.toolbar)

        ViewUtils.hideViews(btnSecondary)
        ViewUtils.showViews(tvChangeInformation)
        btnMain?.setText(R.string.register)
        tvChangeInformation?.setText(R.string.change_information)
        tvTitle?.setText(R.string.title_register_check)
        tvDescription?.setText(R.string.description_register_check)
        setData()

        toolbar?.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        btnMain?.setOnClickListener {
            val data = viewModel.state.value
            lifecycleScope.launch {
                viewModel.registerUsers(data.name,data.email,data.phoneNumber,data.password,data.interest)
            }

            lifecycleScope.launch {
                viewModel.state.collect { uiState ->
                    Log.d("cek isLoading", uiState.isLoading.toString())
                    loadingStateIsToggled(uiState.isLoading)
                    errorStateIsToggled(uiState.isError)
                    successStateIsToggled(uiState.result)
                }
            }
        }

        btnSecondary?.setOnClickListener {
            findNavController().navigate(R.id.action_checkFragment_to_registerNameFragment)
        }
    }
    private fun successStateIsToggled(result: RegisterResponse?) {
        if (result?.data != null) {
            findNavController().navigate(R.id.action_checkFragment_to_loginFragment)
        }
    }

    private fun loadingStateIsToggled(value: Boolean) {
        binding?.apply {
            val loadingState : ProgressBar? = activity?.findViewById(R.id.auth_loading)
            val btnMain : Button? = activity?.findViewById(R.id.btn_main)

            loadingState?.visibility = if (value) View.VISIBLE else View.GONE
            btnMain?.visibility = if (value) View.GONE else View.VISIBLE
        }
    }
    private fun errorStateIsToggled(value: Boolean) {
        if (value) {
            Toast.makeText(requireActivity(), getString(R.string.register_fail_check), Toast.LENGTH_LONG).show()
        }
    }
    private fun setData() {
        binding?.apply {
            tvNameCheck.text = viewModel.state.value.name
            tvEmailCheck.text = viewModel.state.value.email
            tvHandphoneCheck.text = viewModel.state.value.phoneNumber
            tvReferensiCheck.text = viewModel.state.value.interest.joinToString(", ")
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}