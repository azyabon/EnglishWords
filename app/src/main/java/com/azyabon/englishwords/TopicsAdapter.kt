package com.azyabon.englishwords

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.azyabon.englishwords.databinding.TopicCardBinding

class TopicsAdapter(
    private val topics: List<Topic>,
    private val onItemClick: (String) -> Unit
) :
    RecyclerView.Adapter<TopicsAdapter.TopicViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TopicViewHolder {
        return TopicViewHolder(
            TopicCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: TopicViewHolder,
        position: Int
    ) {
        val topic = topics[position]
        val totalWords = WordsRepository.getTotalWordsCountByTopicId(topic.id)
        val learnedWord = WordsRepository.getLearnedWordsCountByTopicId(topic.id)

        holder.binding.apply {
            tvTopicName.text = topic.title
            tvTopicTotalWords.text = "$learnedWord/$totalWords"
            imTopicIcon.setImageResource(topic.icon)

            topicCard.setOnClickListener {
                onItemClick(topic.id)
            }
        }
    }

    override fun getItemCount(): Int {
        return topics.size
    }

    class TopicViewHolder(val binding: TopicCardBinding) :
        RecyclerView.ViewHolder(binding.root)

}