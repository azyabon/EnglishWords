package com.azyabon.englishwords

object WordsRepository {

    private val topics = mutableListOf(
        Topic(
            id = "animals",
            title = "Animals",
            icon = R.drawable.ic_cat,
            words = mutableListOf (
                Word(id = "cat", original = "Cat", translate = "Кот"),
                Word(id = "dog", original = "Dog", translate = "Собака"),
                Word(id = "bird", original = "Bird", translate = "Птица"),
                Word(id = "fish", original = "Fish", translate = "Рыба"),
                Word(id = "lion", original = "Lion", translate = "Лев"),
                Word(id = "tiger", original = "Tiger", translate = "Тигр"),
            ),
        ),
        Topic(
            id = "fruits",
            title = "Fruits",
            icon = R.drawable.ic_apple,
            words = mutableListOf (
                Word(id = "apple", original = "Apple", translate = "Яблоко"),
                Word(id = "banana", original = "Banana", translate = "Банан"),
                Word(id = "orange", original = "Orange", translate = "Апельсин"),
                Word(id = "grape", original = "Grape", translate = "Виноград"),
                Word(id = "strawberry", original = "Strawberry", translate = "Клубника"),
                Word(id = "watermelon", original = "Watermelon", translate = "Арбуз"),
            ),
        )
    )

    fun getTopics(): List<Topic> = topics

    fun getWordsByTopicId(topicId: String): List<Word> {
        return topics.find { it.id == topicId }?.words ?: emptyList()
    }

    fun getLearnedWords(): List<Word> {
        return topics.flatMap { it.words }.filter { it.learned }
    }

    fun getLearnedWordsCountByTopicId(topicId: String): Int {
        return topics.find { it.id == topicId }?.words?.count { it.learned } ?: 0
    }

    fun getTotalWordsCountByTopicId(topicId: String): Int {
        return topics.find { it.id == topicId }?.words?.size ?: 0
    }
}