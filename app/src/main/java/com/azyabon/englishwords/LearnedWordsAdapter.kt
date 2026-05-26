package com.azyabon.englishwords

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LearnedWordsAdapter(private val words: List<Word>) :
    RecyclerView.Adapter<LearnedWordsAdapter.LearnedWordViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LearnedWordsAdapter.LearnedWordViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.learned_word_item, parent, false)

        return LearnedWordViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: LearnedWordViewHolder,
        position: Int
    ) {
        val word = words[position]

        holder.originalTv.text = word.original
        holder.translateTv.text = word.translate
    }

    override fun getItemCount(): Int {
        return words.size
    }

    class LearnedWordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val originalTv: TextView = itemView.findViewById<TextView>(R.id.tvOriginalWord)
        val translateTv: TextView = itemView.findViewById<TextView>(R.id.tvTranslatedWord)
    }
}