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
import space.mrandika.wisnu.databinding.FragmentRegisterEmailBinding


class RegisterEmailFragment : Fragment() {
    private var _binding: FragmentRegisterEmailBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterEmailBinding.inflate(inflater, container, false)
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

        tvTitle?.text = "Hai,\n" +
                "Andika \uD83D\uDC4B\uD83C\uDFFB"
        tvDescription?.text = "Hubungin kamu kemana nih?"

        btnMain?.setOnClickListener {
            findNavController().navigate(R.id.action_registerEmailFragment_to_registerPasswordFragment)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}