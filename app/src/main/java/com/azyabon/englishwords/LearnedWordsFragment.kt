package com.azyabon.englishwords

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.azyabon.englishwords.databinding.FragmentLearnedWordsBinding
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

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
        val learnedWords: List<Word> = WordsRepository.getLearnedWords()

        with(binding) {
            tvNoLearnedWords.visibility = if (learnedWords.isEmpty()) View.VISIBLE else View.GONE
            rvLearnedWords.visibility = if (learnedWords.isEmpty()) View.GONE else View.VISIBLE

            val learnWordsAdapter = LearnedWordsAdapter(learnedWords)
            val swipeGesture = object : LearnedWordGesture(requireContext()) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                    viewLifecycleOwner.lifecycleScope.launch {
                        val position = viewHolder.bindingAdapterPosition
                        if (position == RecyclerView.NO_POSITION) return@launch

                        learnWordsAdapter.deleteItem(position)
                        WordsRepository.saveProgress(requireContext())

                        if (learnWordsAdapter.itemCount == 0) {
                            tvNoLearnedWords.visibility = View.VISIBLE
                            rvLearnedWords.visibility = View.GONE
                        }
                    }
                }
            }

            val touchHelper = ItemTouchHelper(swipeGesture)
            touchHelper.attachToRecyclerView(rvLearnedWords)

            rvLearnedWords.layoutManager = LinearLayoutManager(requireContext())
            rvLearnedWords.setHasFixedSize(true)
            rvLearnedWords.adapter = learnWordsAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}