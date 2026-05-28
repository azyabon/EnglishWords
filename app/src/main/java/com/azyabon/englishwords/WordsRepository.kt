package com.azyabon.englishwords

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

object WordsRepository {

    private val LEARNED_WORD_IDS = stringSetPreferencesKey("learned_word_ids")

    private val topics = mutableListOf(
        Topic(
            id = "animals",
            title = "Animals",
            icon = R.drawable.ic_cat,
            words = mutableListOf(
                Word(id = "cat", original = "Cat", translate = "Кот"),
                Word(id = "dog", original = "Dog", translate = "Собака"),
                Word(id = "bird", original = "Bird", translate = "Птица"),
                Word(id = "fish", original = "Fish", translate = "Рыба"),
                Word(id = "lion", original = "Lion", translate = "Лев"),
                Word(id = "tiger", original = "Tiger", translate = "Тигр"),
                Word(id = "elephant", original = "Elephant", translate = "Слон"),
                Word(id = "bear", original = "Bear", translate = "Медведь"),
                Word(id = "monkey", original = "Monkey", translate = "Обезьяна"),
                Word(id = "giraffe", original = "Giraffe", translate = "Жираф"),
                Word(id = "zebra", original = "Zebra", translate = "Зебра"),
                Word(id = "cheetah", original = "Cheetah", translate = "Гепард")
            ),
        ),
        Topic(
            id = "fruits",
            title = "Fruits",
            icon = R.drawable.ic_apple,
            words = mutableListOf(
                Word(id = "apple", original = "Apple", translate = "Яблоко"),
                Word(id = "banana", original = "Banana", translate = "Банан"),
                Word(id = "orange", original = "Orange", translate = "Апельсин"),
                Word(id = "grape", original = "Grape", translate = "Виноград"),
                Word(id = "strawberry", original = "Strawberry", translate = "Клубника"),
                Word(id = "watermelon", original = "Watermelon", translate = "Арбуз"),
            ),
        ),
        Topic(
            id = "vegetables",
            title = "Vegetables",
            icon = R.drawable.ic_cucumber,
            words = mutableListOf(
                Word(id = "carrot", original = "Carrot", translate = "Морковь"),
                Word(id = "potato", original = "Potato", translate = "Картофель"),
                Word(id = "tomato", original = "Tomato", translate = "Помидор"),
                Word(id = "cucumber", original = "Cucumber", translate = "Огурец"),
                Word(id = "onion", original = "Onion", translate = "Лук"),
                Word(id = "pepper", original = "Pepper", translate = "Перец"),
            ),
        ),
        Topic(
            id = "cosmos",
            title = "Cosmos",
            icon = R.drawable.ic_astronaut,
            words = mutableListOf(
                Word(id = "sun", original = "Sun", translate = "Солнце"),
                Word(id = "moon", original = "Moon", translate = "Луна"),
                Word(id = "earth", original = "Earth", translate = "Земля"),
                Word(id = "venus", original = "Venus", translate = "Венера"),
                Word(id = "mercury", original = "Mercury", translate = "Меркурий"),
                Word(id = "neptune", original = "Neptune", translate = "Нептун"),
                Word(id = "uranus", original = "Uranus", translate = "Уран"),
                Word(id = "pluto", original = "Pluto", translate = "Плутон"),
                Word(id = "comet", original = "Comet", translate = "Комета"),
                Word(id = "asteroid", original = "Asteroid", translate = "Астероид"),
                Word(id = "galaxy", original = "Galaxy", translate = "Галактика"),
                Word(id = "meteor", original = "Meteor", translate = "Метеор"),
                Word(id = "spaceship", original = "Spaceship", translate = "Космический корабль"),
                Word(id = "mars", original = "Mars", translate = "Марс"),
                Word(id = "jupiter", original = "Jupiter", translate = "Юпитер"),
                Word(id = "saturn", original = "Saturn", translate = "Сатурн"),
                Word(id = "astronaut", original = "Astronaut", translate = "Астронавт"),
            ),
        ),
        Topic(
            id = "verbs",
            title = "Verbs",
            icon = R.drawable.ic_runner,
            words = mutableListOf(
                Word(id = "run", original = "Run", translate = "Бежать"),
                Word(id = "jump", original = "Jump", translate = "Прыгать"),
                Word(id = "swim", original = "Swim", translate = "Плавать"),
                Word(id = "eat", original = "Eat", translate = "Есть"),
                Word(id = "drink", original = "Drink", translate = "Пить"),
                Word(id = "sleep", original = "Sleep", translate = "Спать"),
                Word(id = "read", original = "Read", translate = "Читать"),
                Word(id = "write", original = "Write", translate = "Писать"),
                Word(id = "speak", original = "Speak", translate = "Говорить"),
                Word(id = "listen", original = "Listen", translate = "Слушать"),
                Word(id = "walk", original = "Walk", translate = "Ходить"),
                Word(id = "drive", original = "Drive", translate = "Водить"),
            ),
        ),
    )

    suspend fun init(context: Context) {
        val learnedWordIds = context.wordsDataStore.data
            .map { preferences -> preferences[LEARNED_WORD_IDS] ?: emptySet() }
            .first()

        topics
            .flatMap { it.words }
            .forEach { word ->
                word.learned = word.id in learnedWordIds
            }
    }

    suspend fun saveProgress(context: Context) {
        val learnedWordIds = topics
            .flatMap { it.words }
            .filter { it.learned }
            .map { it.id }
            .toSet()

        context.wordsDataStore.edit { preferences ->
            preferences[LEARNED_WORD_IDS] = learnedWordIds
        }
    }

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