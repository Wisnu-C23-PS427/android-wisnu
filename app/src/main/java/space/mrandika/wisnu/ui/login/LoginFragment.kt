package space.mrandika.wisnu.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import space.mrandika.wisnu.MainActivity
import space.mrandika.wisnu.R
import space.mrandika.wisnu.ViewUtils
import space.mrandika.wisnu.databinding.FragmentLoginBinding
import space.mrandika.wisnu.databinding.StateLoadingBinding
import space.mrandika.wisnu.model.auth.LoginResponse

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding
    private val viewModel: LoginViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.state.collect { uiState ->
                Log.d("cek isLoading", uiState.isLoading.toString())
                loadingStateIsToggled(uiState.isLoading)
                errorStateIsToggled(uiState.isError)
                successStateIsToggled(uiState.LoginResult)
            }
        }

        val btnMain: Button? = activity?.findViewById(R.id.btn_main)
        val btnSecondary: Button? = activity?.findViewById(R.id.btn_secondary)
        val tvForgotPassword : TextView? = activity?.findViewById(R.id.tv_forget_password)
        val tvTitle : TextView? = activity?.findViewById(R.id.tv_title)
        val tvDescription : TextView? = activity?.findViewById(R.id.tv_description)
        val toolbar: Toolbar? = activity?.findViewById(R.id.toolbar)

        ViewUtils.hideViews(btnSecondary)
        ViewUtils.showViews(tvForgotPassword)
        btnMain?.setText(R.string.login)
        errorCheck()
        btnMain?.isEnabled = false
        tvTitle?.setText(R.string.title_login)

        // TODO: Enable forgot password
        tvForgotPassword?.visibility = View.GONE
        tvForgotPassword?.setText(R.string.forget_password)

        toolbar?.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        btnMain?.setOnClickListener {
            viewModel.login(binding?.tfLoginEmail?.text.toString(), binding?.tfLoginPassword?.text.toString())
        }

        tvForgotPassword?.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }
    }

    private fun successStateIsToggled(loginResult: LoginResponse?) {
        if (loginResult?.data != null){
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadingStateIsToggled(value: Boolean) {
        binding.apply {
            val loadingState : View? = activity?.findViewById(R.id.state_loading)
            val authContent : View? = activity?.findViewById(R.id.auth_content)
            Log.d("LoadingState", loadingState.toString())
            loadingState?.visibility = if (value) View.VISIBLE else View.GONE
            authContent?.visibility = if (!value) View.VISIBLE else View.GONE
        }
    }
    private fun errorStateIsToggled(value: Boolean) {
        binding.apply {
            val errorState : View? = activity?.findViewById(R.id.state_error)
            val authContent : View? = activity?.findViewById(R.id.auth_content)
            errorState?.visibility = if (value) View.VISIBLE else View.GONE
            authContent?.visibility = if (!value) View.VISIBLE else View.GONE
        }
    }
    private fun errorCheck() {
        binding?.tfLoginEmail?.addTextChangedListener(object : TextWatcher {
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
            }
        })

        binding?.tfLoginPassword?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val password = p0.toString().trim()

                binding?.passwordTextInputLayout?.error = when {
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
        val email = binding?.tfLoginEmail?.text
        val password = binding?.tfLoginPassword?.text
        val btnMain: Button? = activity?.findViewById(R.id.btn_main)
        btnMain?.isEnabled = !email.isNullOrEmpty() && !password.isNullOrEmpty()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}