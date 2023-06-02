package space.mrandika.wisnu.ui.wellcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import space.mrandika.wisnu.R
import space.mrandika.wisnu.ViewUtils
import space.mrandika.wisnu.databinding.FragmentWellcomeBinding

class WelcomeFragment : Fragment() {
    private var _binding: FragmentWellcomeBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWellcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cardView: CardView? = activity?.findViewById(R.id.card_view)
        val btnMain: Button? = activity?.findViewById(R.id.btn_main)
        val btnSecondary: Button? = activity?.findViewById(R.id.btn_secondary)
        val tvForgotPassword : TextView? = activity?.findViewById(R.id.tv_forget_password)
        val toolbar : Toolbar? = activity?.findViewById(R.id.toolbar)
        ViewUtils.hideViews(tvForgotPassword)
        ViewUtils.showViews(cardView,toolbar,btnMain,btnSecondary)
        btnMain?.isEnabled = true
        btnMain?.setText(R.string.login)
        btnSecondary?.setText(R.string.register)
        btnSecondary?.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_registerNameFragment)
        }
        btnMain?.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_loginFragment)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}