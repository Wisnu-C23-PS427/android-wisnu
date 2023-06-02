package space.mrandika.wisnu.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import space.mrandika.wisnu.R
import space.mrandika.wisnu.ViewUtils
import space.mrandika.wisnu.databinding.FragmentLoginBinding
@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnMain: Button? = activity?.findViewById(R.id.btn_main)
        val btnSecondary: Button? = activity?.findViewById(R.id.btn_secondary)
        val tvForgotPassword : TextView? = activity?.findViewById(R.id.tv_forget_password)
        val tvTitle : TextView? = activity?.findViewById(R.id.tv_title)
        val tvDescription : TextView? = activity?.findViewById(R.id.tv_description)
        ViewUtils.hideViews(btnSecondary)
        ViewUtils.showViews(tvForgotPassword)
        btnMain?.setText(R.string.login)
        errorCheck()
        btnMain?.isEnabled = false
        tvTitle?.setText(R.string.title_login)
        tvForgotPassword?.setText(R.string.forget_password)
        btnMain?.setOnClickListener {
            lifecycleScope.launch {
                viewModel.userLogin(binding.tfLoginEmail.text.toString(),binding.tfLoginPassword.text.toString())
            }
        }
        tvForgotPassword?.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }
    }

    private fun errorCheck() {
        binding.tfLoginEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val email = p0.toString().trim()
                binding.tfLoginEmail.error = when {
                    email.isEmpty() -> "Field ini tidak boleh kosong"
                    !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Email tidak valid"
                    else -> {
                        enableButton()
                        null
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
        binding.tfLoginPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val password = p0.toString().trim()
                binding.tfLoginPassword.error = when {
                    password.isEmpty() -> "Field ini tidak boleh kosong"
                    password.length < 8 -> "Minimal 8 karakter"
                    else -> {
                        enableButton()
                        null
                    }
            }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }
    private fun enableButton(){
        val email = binding.tfLoginEmail.text
        val password = binding.tfLoginPassword.text
        val btnMain: Button? = activity?.findViewById(R.id.btn_main)
        btnMain?.isEnabled = !email.isNullOrEmpty() && !password.isNullOrEmpty()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}