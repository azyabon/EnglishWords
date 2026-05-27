package com.azyabon.englishwords

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.azyabon.englishwords.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("View binding is only valid between onCreate and onDestroy")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvTopics.apply {
            val topicAdapter = TopicsAdapter(
                topics = WordsRepository.getTopics(),
                onItemClick = { topicId ->
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.flContainer, LearnWordsFragment.newInstance(topicId))
                        .addToBackStack(null)
                        .commit()
                }
            )

            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = topicAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}