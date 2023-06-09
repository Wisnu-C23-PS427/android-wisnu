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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.FragmentRegisterNameBinding
import space.mrandika.wisnu.utils.ViewUtils

@AndroidEntryPoint
class RegisterNameFragment : Fragment() {
    private var _binding: FragmentRegisterNameBinding? = null
    private val binding get() = _binding
    private val viewModel : RegisterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterNameBinding.inflate(inflater, container, false)
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
        val fragmentContainer: FragmentContainerView? = activity?.findViewById(R.id.nav_host_fragment)

        ViewUtils.hideViews(btnSecondary,tvForgotPassword)
        btnMain?.isEnabled = false
        tvTitle?.setText(R.string.title_register_name)
        tvDescription?.setText(R.string.description_register_name)
        btnMain?.setText(R.string.next)
        errorCheck()

        fragmentContainer?.updateLayoutParams<ConstraintLayout.LayoutParams> {
           tvDescription?.let {
               topToBottom = it.id
               topToTop = ConstraintLayout.LayoutParams.UNSET
           }
        }

        toolbar?.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        btnMain?.setOnClickListener {
            findNavController().navigate(R.id.action_registerNameFragment_to_registerEmailFragment)
        }
    }

    private fun errorCheck(){
        binding?.tfRegisterName?.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val name = p0.toString().trim()
                val error = when {
                    name.isEmpty() -> "Field ini tidak boleh kosong"
                    else -> {
                        enableButton()
                        null
                    }
                }

                binding?.nameTextInputLayout?.error = error
            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.updateName(p0.toString())
            }
        })
    }

    private fun enableButton(){
        val name = binding?.tfRegisterName?.text
        val btnMain: Button? = activity?.findViewById(R.id.btn_main)
        btnMain?.isEnabled = !name.isNullOrEmpty()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}