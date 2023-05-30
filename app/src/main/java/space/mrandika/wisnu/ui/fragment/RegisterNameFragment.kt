package space.mrandika.wisnu.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import space.mrandika.wisnu.R
import space.mrandika.wisnu.ViewUtils
import space.mrandika.wisnu.databinding.FragmentRegisterNameBinding

class RegisterNameFragment : Fragment() {
    private var _binding: FragmentRegisterNameBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterNameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnMain: Button? = activity?.findViewById(R.id.btn_main)
        val btnSecondary: Button? = activity?.findViewById(R.id.btn_secondary)
        val tvForgotPassword : TextView? = activity?.findViewById(R.id.tv_forget_password)
        val tvTitle : TextView? = activity?.findViewById(R.id.tv_title)
        val tvDescription : TextView? = activity?.findViewById(R.id.tv_description)
        ViewUtils.hideViews(btnSecondary,tvForgotPassword)

        tvTitle?.setText(R.string.title_register_name)
        tvDescription?.setText(R.string.description_register_name)
        btnMain?.setText(R.string.next)

        btnMain?.setOnClickListener {
            findNavController().navigate(R.id.action_registerNameFragment_to_registerEmailFragment)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}