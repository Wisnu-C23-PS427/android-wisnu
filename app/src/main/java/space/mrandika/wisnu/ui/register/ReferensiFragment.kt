package space.mrandika.wisnu.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import space.mrandika.wisnu.R
import space.mrandika.wisnu.ViewUtils
import space.mrandika.wisnu.databinding.FragmentReferensiBinding

@AndroidEntryPoint
class ReferensiFragment : Fragment() {
    private var _binding: FragmentReferensiBinding? = null
    private lateinit var chipRecyclerView: RecyclerView

    private val binding get() = _binding!!
    private val viewModel : RegisterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReferensiBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnMain: Button? = activity?.findViewById(R.id.btn_main)
        val btnSecondary: Button? = activity?.findViewById(R.id.btn_secondary)
        val tvTextButton : TextView? = activity?.findViewById(R.id.tv_forget_password)
        val tvTitle : TextView? = activity?.findViewById(R.id.tv_title)
        val tvDescription : TextView? = activity?.findViewById(R.id.tv_description)
        tvTitle?.setText(R.string.title_register_referensi)
        tvDescription?.setText(R.string.description_register_referensi)
        ViewUtils.showViews(tvTextButton)
        tvTextButton?.setText(R.string.skip)
        btnMain?.setOnClickListener {
            findNavController().navigate(R.id.action_referensiFragment_to_checkFragment)
        }
        tvTextButton?.setOnClickListener {
            findNavController().navigate(R.id.action_referensiFragment_to_checkFragment)
        }
        chipRecyclerView = binding.rvChip
        val spanCount = 3
        chipRecyclerView.layoutManager = GridLayoutManager(context, spanCount)
        val chipData = listOf("Gunung", "Pantai","Event","Sungai", "Danau","Hutan")
        val chipAdapter = ReferenceAdapter(chipData, viewModel)
        chipRecyclerView.adapter = chipAdapter
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}