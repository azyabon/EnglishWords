package com.azyabon.englishwords

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.azyabon.englishwords.databinding.FragmentLearnedWordsBinding

class LearnedWordsFragment : Fragment() {

    private var _binding: FragmentLearnedWordsBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("View binding is only valid between onCreate and onDestroy")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLearnedWordsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val learnedWords = WordsRepository.getLearnedWords()

        with(binding) {
            testTvWords.text = learnedWords.joinToString(separator = "\n") { "${it.original} - ${it.translate}" }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}