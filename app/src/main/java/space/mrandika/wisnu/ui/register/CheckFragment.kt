package space.mrandika.wisnu.ui.register


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import space.mrandika.wisnu.R
import space.mrandika.wisnu.ViewUtils
import space.mrandika.wisnu.databinding.FragmentCheckBinding


class CheckFragment : Fragment() {
    private var _binding: FragmentCheckBinding? = null
    private val binding get() = _binding!!
    private val viewModel : RegisterViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCheckBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnMain: Button? = activity?.findViewById(R.id.btn_main)
        val btnSecondary: Button? = activity?.findViewById(R.id.btn_secondary)
        val tvChangeInformation : TextView? = activity?.findViewById(R.id.tv_forget_password)
        val tvTitle : TextView? = activity?.findViewById(R.id.tv_title)
        val tvDescription : TextView? = activity?.findViewById(R.id.tv_description)

        ViewUtils.hideViews(btnSecondary)
        ViewUtils.showViews(tvChangeInformation)
        btnMain?.setText(R.string.register)
        tvChangeInformation?.setText(R.string.change_information)
        tvTitle?.setText(R.string.title_register_check)
        tvDescription?.setText(R.string.description_register_check)
        setData()
        btnMain?.setOnClickListener {
            val data = viewModel.state.value
            lifecycleScope.launch {

            }
        }
    }

    private fun setData(){
        binding.tvNameCheck.text = viewModel.state.value.name
        binding.tvEmailCheck.text = viewModel.state.value.email
        binding.tvHandphoneCheck.text = viewModel.state.value.phoneNumber
        binding.tvReferensiCheck.text = viewModel.state.value.interest.joinToString(", ")

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}