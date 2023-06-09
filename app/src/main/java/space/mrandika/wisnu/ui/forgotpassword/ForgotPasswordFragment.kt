package space.mrandika.wisnu.ui.forgotpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.FragmentForgotPasswordBinding


class ForgotPasswordFragment : Fragment() {
    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tvTitle : TextView? = activity?.findViewById(R.id.tv_title)
        val tvDescription : TextView? = activity?.findViewById(R.id.tv_description)
        val btnMain: Button? = activity?.findViewById(R.id.btn_main)
        val toolbar: Toolbar? = activity?.findViewById(R.id.toolbar)

        btnMain?.setText(R.string.send)
        tvTitle?.setText(R.string.title_forgot_password)
        tvDescription?.setText(R.string.description_forgot_password)

        toolbar?.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        btnMain?.setOnClickListener {

        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}