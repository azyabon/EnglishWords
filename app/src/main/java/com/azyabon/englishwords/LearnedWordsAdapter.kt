package com.azyabon.englishwords

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.azyabon.englishwords.databinding.LearnedWordItemBinding

class LearnedWordsAdapter(private val words: List<Word>) :
    RecyclerView.Adapter<LearnedWordsAdapter.LearnedWordViewHolder>() {

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