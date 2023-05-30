package space.mrandika.wisnu.ui.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import space.mrandika.wisnu.R
import space.mrandika.wisnu.ViewUtils
import space.mrandika.wisnu.databinding.FragmentReferensiBinding
import space.mrandika.wisnu.ui.recycleview.ReferensiAdapter


class ReferensiFragment : Fragment() {
    private var _binding: FragmentReferensiBinding? = null
    private lateinit var chipRecyclerView: RecyclerView

    private val binding get() = _binding!!

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
        tvTitle?.text ="Punya\n" +
                "preferensi? \uD83E\uDD14"
        tvDescription?.text = ""
        ViewUtils.showViews(tvTextButton)
        tvTextButton?.text = "Lewati"
        btnMain?.setOnClickListener {
            findNavController().navigate(R.id.action_referensiFragment_to_checkFragment)
        }
        tvTextButton?.setOnClickListener {
            findNavController().navigate(R.id.action_referensiFragment_to_checkFragment)
        }
        chipRecyclerView = binding.rvChip
        chipRecyclerView.layoutManager = LinearLayoutManager(requireContext(),)


        val chipData = listOf("Chip 1", "Chip 2", "Chip 3")

        val chipAdapter = ReferensiAdapter(chipData)
        chipRecyclerView.adapter = chipAdapter
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}