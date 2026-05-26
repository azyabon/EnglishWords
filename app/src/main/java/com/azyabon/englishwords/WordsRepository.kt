package com.azyabon.englishwords

object WordsRepository {
    val dictionary = mutableListOf(
        Word("Carriage", "Вагон"),
        Word("Journey", "Путешествие"),
        Word("Destination", "Место назначения"),
        Word("Adventure", "Приключение"),
        Word("Curious", "Любопытный"),
        Word("Courage", "Храбрость"),
        Word("Furious", "Яростный"),
        Word("Whisper", "Шёпот"),
        Word("Strange", "Странный"),
        Word("Ordinary", "Обычный"),
        Word("Disappear", "Исчезать"),
        Word("Appear", "Появляться"),
        Word("Remind", "Напоминать"),
        Word("Promise", "Обещание / Обещать"),
        Word("Sincere", "Искренний"),
        Word("Guilty", "Виновный"),
        Word("Blame", "Вина / Обвинять"),
        Word("Support", "Поддержка / Поддерживать"),
        Word("Refuse", "Отказываться"),
        Word("Beg", "Умолять"),
        Word("Hesitate", "Колебаться"),
        Word("Confess", "Признаваться"),
        Word("Pretend", "Притворяться"),
        Word("Annoying", "Раздражающий"),
        Word("Jealous", "Ревнивый / Завистливый"),
        Word("Proud", "Гордый"),
        Word("Shy", "Застенчивый"),
        Word("Lonely", "Одинокий"),
        Word("Anxious", "Встревоженный"),
        Word("Grateful", "Благодарный"),
        Word("Curiosity", "Любопытство"),
        Word("Relief", "Облегчение"),
        Word("Disappointment", "Разочарование"),
        Word("Rude", "Грубый"),
        Word("Polite", "Вежливый")
    )

    fun getLearnedWords(): List<Word> {
        return dictionary.filter { it.learned }
    }
}