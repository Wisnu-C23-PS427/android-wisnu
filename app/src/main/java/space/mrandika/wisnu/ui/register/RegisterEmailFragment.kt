package space.mrandika.wisnu.ui.register

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.FragmentRegisterEmailBinding
import space.mrandika.wisnu.utils.ViewUtils

@AndroidEntryPoint
class RegisterEmailFragment : Fragment() {
    private var _binding: FragmentRegisterEmailBinding? = null

    private val binding get() = _binding
    private val viewModel:RegisterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterEmailBinding.inflate(inflater, container, false)
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
        errorCheck()
        tvTitle?.text = String.format(resources.getString(R.string.title_register_email), viewModel.state.value.name)
        tvDescription?.setText(R.string.description_register_email)
        btnMain?.setText(R.string.next)
        btnMain?.isEnabled = false

        toolbar?.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        btnMain?.setOnClickListener {
            findNavController().navigate(R.id.action_registerEmailFragment_to_registerPasswordFragment)
        }
    }

    private fun errorCheck(){
        binding?.tfRegisterEmail?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val email = p0.toString().trim()

                binding?.emailTextInputLayout?.error = when {
                    email.isEmpty() -> "Field ini tidak boleh kosong"
                    !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Email tidak valid"
                    else -> {
                        enableButton()
                        null
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.updateEmail(p0.toString())
            }
        })

        binding?.tfRegisterPhone?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val phoneNumber = p0.toString().trim()
                binding?.phoneTextInputLayout?.error = when {
                    phoneNumber.isEmpty() -> "Field ini tidak boleh kosong"
                    phoneNumber.length > 10 -> "Minimal 10 "
                    else -> {
                        enableButton()
                        null
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.updatePhoneNumber(p0.toString())
            }
        })
    }

    private fun enableButton(){
        val email  = binding?.tfRegisterEmail?.text
        val phoneNumber = binding?.tfRegisterPhone?.text
        val btnMain: Button? = activity?.findViewById(R.id.btn_main)
        btnMain?.isEnabled = !email.isNullOrEmpty() && !phoneNumber.isNullOrEmpty()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}