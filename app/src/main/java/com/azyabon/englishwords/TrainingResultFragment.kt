package com.azyabon.englishwords

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.azyabon.englishwords.databinding.FragmentTrainingResultBinding

class TrainingResultFragment : Fragment() {

    private var _binding: FragmentTrainingResultBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("View binding is only valid between onCreate and onDestroy")

    companion object {
        private const val ARG_LEARNED_COUNT = "ARG_LEARNED_COUNT"
        private const val ARG_TOTAL_COUNT = "ARG_TOTAL_COUNT"

        fun newInstance(learnedCount: Int, totalCount: Int): TrainingResultFragment {
            return TrainingResultFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_LEARNED_COUNT, learnedCount)
                    putInt(ARG_TOTAL_COUNT, totalCount)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrainingResultBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val learnedCount = arguments?.getInt(ARG_LEARNED_COUNT) ?: 0
        val totalCount = arguments?.getInt(ARG_TOTAL_COUNT) ?: 0

        with(binding) {
            tvLearnedWords.text = "Learned: $learnedCount"
            tvTotalWords.text = "Total: $totalCount"

            btnFinish.setOnClickListener {
                parentFragmentManager.popBackStack(
                    null,
                    androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}