package space.mrandika.wisnu.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import space.mrandika.wisnu.R
import space.mrandika.wisnu.databinding.FragmentPreferenceBinding
import space.mrandika.wisnu.model.category.Category
import space.mrandika.wisnu.utils.ViewUtils

@AndroidEntryPoint
class PreferenceFragment : Fragment() {
    private var _binding: FragmentPreferenceBinding? = null

    private lateinit var adapter: ReferenceAdapter

    private val binding get() = _binding
    private val viewModel : RegisterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPreferenceBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCategories()

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                setData(state.interestData)
                viewModel.updateInteresting(state.interest)
            }
        }

        val btnMain: Button? = activity?.findViewById(R.id.btn_main)
        val btnSecondary: Button? = activity?.findViewById(R.id.btn_secondary)
        val tvTextButton : TextView? = activity?.findViewById(R.id.tv_forget_password)
        val tvTitle : TextView? = activity?.findViewById(R.id.tv_title)
        val tvDescription : TextView? = activity?.findViewById(R.id.tv_description)
        val toolbar: Toolbar? = activity?.findViewById(R.id.toolbar)

        tvTitle?.setText(R.string.title_register_referensi)
        tvDescription?.setText(R.string.description_register_referensi)
        ViewUtils.showViews(tvTextButton)
        tvTextButton?.setText(R.string.skip)

        toolbar?.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        btnMain?.setOnClickListener {
            findNavController().navigate(R.id.action_preferenceFragment_to_checkFragment)
        }

        tvTextButton?.setOnClickListener {
            findNavController().navigate(R.id.action_preferenceFragment_to_checkFragment)
        }

        binding?.apply {
            rvChip.layoutManager = GridLayoutManager(context, 3)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setData(value: List<Category>) {
        adapter = ReferenceAdapter(value)

        binding?.apply {
            rvChip.adapter = adapter
        }

        adapter.setOnItemClickCallback(object : ReferenceAdapter.OnItemClickCallback {
            override fun onItemClicked(chip: Chip) {
                viewModel.state.value.interest.apply {
                    if (this.contains(chip.text)) {
                        this.remove(chip.text)
                        chip.isChecked = false
                    } else {
                        this.add(chip.text.toString())
                        chip.isChecked = true
                    }
                }
            }
        })
    }
}