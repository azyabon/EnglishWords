package com.azyabon.englishwords

data class Topic(
    val id: String,
    val title: String,
    val icon: Int,
    val words: List<Word>,
)

data class Word(
    val id: String,
    val original: String,
    val translate: String,
    var learned: Boolean = false,
)

data class Question(
    val variants: List<Word>,
    val correctAnswer: Word,
)

class LearnWordsTrainer(topicId: String) {

    private val dictionary = WordsRepository.getWordsByTopicId(topicId)

    private var currentQuestion: Question? = null

    fun getNextQuestion(): Question? {
        val notLearnedList = dictionary.filter { !it.learned }

        if (notLearnedList.isEmpty()) return null

        val correctAnswer = notLearnedList.random()

        val wrongAnswers = dictionary
            .filter { it.id != correctAnswer.id }
            .shuffled()
            .take(NUMBER_OF_ANSWERS - 1)

        val questionWords = (wrongAnswers + correctAnswer).shuffled()

        currentQuestion = Question(
            variants = questionWords,
            correctAnswer = correctAnswer
        )

        return currentQuestion
    }

    fun checkAnswer(userAnswerIndex: Int?): Boolean {

        return currentQuestion?.let {

            val correctAnswerId = it.variants.indexOf(it.correctAnswer)

            if (correctAnswerId == userAnswerIndex) {
                it.correctAnswer.learned = true
                true
            } else {
                false
            }
        } ?: false
    }
}

const val NUMBER_OF_ANSWERS: Int = 4