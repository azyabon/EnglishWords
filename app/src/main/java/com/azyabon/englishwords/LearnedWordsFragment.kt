package com.azyabon.englishwords

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.azyabon.englishwords.databinding.FragmentLearnedWordsBinding

class LearnedWordsFragment : Fragment() {

    private var _binding: FragmentLearnedWordsBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("View binding is only valid between onCreate and onDestroy")
    private lateinit var learnedWords: List<Word>

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
        learnedWords = WordsRepository.getLearnedWords()

        with(binding) {
            tvNoLearnedWords.visibility = if (learnedWords.isEmpty()) View.VISIBLE else View.GONE
            rvLearnedWords.visibility = if (learnedWords.isEmpty()) View.GONE else View.VISIBLE

            rvLearnedWords.layoutManager = LinearLayoutManager(requireContext())
            rvLearnedWords.setHasFixedSize(true)
            rvLearnedWords.adapter = LearnedWordsAdapter(learnedWords)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}