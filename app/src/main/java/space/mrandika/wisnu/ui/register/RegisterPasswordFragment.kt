package space.mrandika.wisnu.ui.register

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import space.mrandika.wisnu.R
import space.mrandika.wisnu.ViewUtils
import space.mrandika.wisnu.databinding.FragmentRegisterPasswordBinding

class RegisterPasswordFragment : Fragment() {
    private var _binding: FragmentRegisterPasswordBinding? = null

    private val binding get() = _binding
    private val viewModel : RegisterViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterPasswordBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnMain: Button? = activity?.findViewById(R.id.btn_main)
        val btnSecondary: Button? = activity?.findViewById(R.id.btn_secondary)
        val tvForgotPassword : TextView? = activity?.findViewById(R.id.tv_forget_password)
        val tvTitle : TextView? = activity?.findViewById(R.id.tv_title)
        val tvDescription : TextView? = activity?.findViewById(R.id.tv_description)
        val toolbar: Toolbar? = activity?.findViewById(R.id.toolbar)

        ViewUtils.hideViews(btnSecondary,tvForgotPassword)
        btnMain?.isEnabled = false
        tvTitle?.setText(R.string.title_register_password)
        tvDescription?.setText(R.string.description_register_password)
        btnMain?.setText(R.string.next)
        errorCheck()

        toolbar?.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        btnMain?.setOnClickListener {
            findNavController().navigate(R.id.action_registerPasswordFragment_to_preferenceFragment)
        }
    }
    private fun errorCheck(){
        binding?.tfRegisterPassword?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val password = p0.toString().trim()
                binding?.tfRegisterPassword?.error = when {
                    password.isEmpty() -> "Field ini tidak boleh kosong"
                    else -> {
                        enableButton()
                        null
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.updatePassword(p0.toString())
            }
        })
    }
    private fun enableButton(){
        val password = binding?.tfRegisterPassword?.text
        val btnMain: Button? = activity?.findViewById(R.id.btn_main)
        btnMain?.isEnabled = !password.isNullOrEmpty()
    }
    override fun onDestroyView() {
        super.onDestroyView() 
        _binding = null
    }
}