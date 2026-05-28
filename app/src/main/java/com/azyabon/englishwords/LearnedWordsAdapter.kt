package com.azyabon.englishwords

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.azyabon.englishwords.databinding.LearnedWordItemBinding

class LearnedWordsAdapter(words: List<Word>) :
    RecyclerView.Adapter<LearnedWordsAdapter.LearnedWordViewHolder>() {

    private val words = words.toMutableList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LearnedWordViewHolder {
        return LearnedWordViewHolder(
            LearnedWordItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun deleteItem(position: Int) {
        val word = words[position]

        WordsRepository.deleteLearnedWord(word.id)
        words.removeAt(position)

        notifyItemRemoved(position)
    }

    override fun onBindViewHolder(
        holder: LearnedWordViewHolder,
        position: Int
    ) {
        val word = words[position]

        holder.binding.tvOriginalWord.text = word.original
        holder.binding.tvTranslatedWord.text = word.translate
    }

    override fun getItemCount(): Int {
        return words.size
    }

    class LearnedWordViewHolder(val binding: LearnedWordItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}