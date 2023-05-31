package space.mrandika.wisnu.ui.fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.Navigation
import space.mrandika.wisnu.R
import space.mrandika.wisnu.ViewUtils
import space.mrandika.wisnu.databinding.FragmentCheckBinding
import space.mrandika.wisnu.ui.NavigationActivity


class CheckFragment : Fragment() {
    private var _binding: FragmentCheckBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCheckBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnMain: Button? = activity?.findViewById(R.id.btn_main)
        val btnSecondary: Button? = activity?.findViewById(R.id.btn_secondary)
        val tvChangeInformation : TextView? = activity?.findViewById(R.id.tv_forget_password)
        val tvTitle : TextView? = activity?.findViewById(R.id.tv_title)
        val tvDescription : TextView? = activity?.findViewById(R.id.tv_description)

        ViewUtils.hideViews(btnSecondary)
        ViewUtils.showViews(tvChangeInformation)
        btnMain?.setText(R.string.register)
        tvChangeInformation?.setText(R.string.change_information)
        tvTitle?.setText(R.string.title_register_check)
        tvDescription?.setText(R.string.description_register_check)

        btnMain?.setOnClickListener {
            startActivity(Intent(requireActivity(),NavigationActivity::class.java))
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}