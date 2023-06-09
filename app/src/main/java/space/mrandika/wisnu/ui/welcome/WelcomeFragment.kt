package space.mrandika.wisnu.ui.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.fragment.findNavController
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.FragmentWelcomeBinding
import space.mrandika.wisnu.utils.ViewUtils

class WelcomeFragment : Fragment() {
    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cardView: CardView? = activity?.findViewById(R.id.card_view)
        val btnMain: Button? = activity?.findViewById(R.id.btn_main)
        val btnSecondary: Button? = activity?.findViewById(R.id.btn_secondary)
        val tvDescription : TextView? = activity?.findViewById(R.id.tv_description)
        val tvForgotPassword : TextView? = activity?.findViewById(R.id.tv_forget_password)
        val toolbar : Toolbar? = activity?.findViewById(R.id.toolbar)
        val fragmentContainer: FragmentContainerView? = activity?.findViewById(R.id.nav_host_fragment)

        ViewUtils.hideViews(tvForgotPassword)
        ViewUtils.showViews(cardView,toolbar,btnMain,btnSecondary)

        fragmentContainer?.updateLayoutParams<ConstraintLayout.LayoutParams> {
            toolbar?.let {
                topToTop = it.id
                topToBottom = ConstraintLayout.LayoutParams.UNSET
            }
        }

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