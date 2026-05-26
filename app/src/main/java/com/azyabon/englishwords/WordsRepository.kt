package com.azyabon.englishwords

object WordsRepository {
    val dictionary = mutableListOf(
        Word("Carriage", "Вагон"),
        Word("Babel fish", "Бабел-рыба"),
        Word("Gargle Blaster", "Громоглот"),
        Word("Hyperdrive", "Гипердвигатель"),
        Word("Hooloovoo", "Хулуву"),
        Word("Magrathea", "Магратея"),
        Word("Infinite Improbability", "Бесконечная вероятность"),
        Word("Hyper Space", "Гиперпространство"),
        Word("Guidebook", "Путеводитель"),
        Word("Spaceship", "Звездолет"),
        Word("Towel", "Полотенце"),
        Word("Paranoid Android", "Параноидальный Андройд"),
        Word("Pan Galactic", "Пангалактический"),
        Word("Deep Thought", "Глубокая мысль"),
        Word("Teleport", "Телепорт"),
        Word("Mind", "Разум"),
        Word("Universe", "Вселенная"),
        Word("Hitchhiker", "Автостопщик"),
        Word("Whale", "Кит"),
        Word("Petunias", "Петунии"),
        Word("Heart of Gold", "Сердце Золота"),
        Word("Galaxy", "Галактика"),
        Word("End of the Universe", "Конец Вселенной"),
        Word("Space", "Космос"),
        Word("Probability", "Вероятность"),
    )

    fun getLearnedWords(): List<Word> {
        return dictionary.filter { it.learned }
    }
}