package com.azyabon.englishwords

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.azyabon.englishwords.databinding.FragmentLearnWordsBinding
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class LearnWordsFragment : Fragment() {

    private var _binding: FragmentLearnWordsBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("View binding is only valid between onCreate and onDestroy")
    private var timer: CountDownTimer? = null
    private var learnedWordsCount = 0
    private var totalWordsCount = 0
    private var isAnswered = false
    private lateinit var answerViews: List<AnswerView>

    private data class AnswerView(
        val container: LinearLayout,
        val number: TextView,
        val value: TextView,
    )

    companion object {
        private const val ARG_TOPIC_ID = "ARG_TOPIC_ID"

        fun newInstance(topicId: String): LearnWordsFragment {
            return LearnWordsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TOPIC_ID, topicId)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLearnWordsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireContext().startService(
            Intent(requireContext(), CounterService::class.java)
        )

        val topicId: String = arguments?.getString(ARG_TOPIC_ID) ?: ""

        answerViews = listOf(
            AnswerView(binding.llAnswer1, binding.tvVariantNumber1, binding.tvVariantValue1),
            AnswerView(binding.llAnswer2, binding.tvVariantNumber2, binding.tvVariantValue2),
            AnswerView(binding.llAnswer3, binding.tvVariantNumber3, binding.tvVariantValue3),
            AnswerView(binding.llAnswer4, binding.tvVariantNumber4, binding.tvVariantValue4),
        )

        val trainer = LearnWordsTrainer(topicId)

        showNextQuestion(trainer)

        binding.pbTimer.max = 60
        binding.pbTimer.progress = 60

        timer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = ((millisUntilFinished) / 1000).toInt()

                binding.pbTimer.progress = secondsRemaining
            }

            override fun onFinish() {
                binding.pbTimer.progress = 0
                openTrainingResult()
            }
        }.start()

        with(binding) {
            btnContinue.setOnClickListener {
                clResult.isVisible = false
                answerViews.forEach { markAnswerNeutral(it) }

                showNextQuestion(trainer)
            }

            ibClose.setOnClickListener {
                parentFragmentManager.popBackStack()
                requireContext().stopService(
                    Intent(requireContext(), CounterService::class.java)
                )
            }

            btnSkip.setOnClickListener {
                trainer.skipCurrentQuestion()
                showNextQuestion(trainer)
            }
        }
    }

    private fun onAnswerClick(
        answerIndex: Int,
        answerView: AnswerView,
        trainer: LearnWordsTrainer,
    ) {
        if (isAnswered) return

        isAnswered = true

        if (trainer.checkAnswer(answerIndex)) {
            markAnswerCorrect(answerView)
            learnedWordsCount++

            viewLifecycleOwner.lifecycleScope.launch {
                WordsRepository.saveProgress(requireContext())
            }

            showResult(true)
        } else {
            markAnswerWrong(answerView)
            trainer.skipCurrentQuestion()
            showResult(false)
        }
    }

    private fun showNextQuestion(trainer: LearnWordsTrainer) {
        val firstQuestion: Question? = trainer.getNextQuestion()

        with(binding) {
            if (firstQuestion == null || firstQuestion.variants.size < NUMBER_OF_ANSWERS) {
                openTrainingResult()
            } else {
                isAnswered = false
                totalWordsCount++
                btnSkip.isVisible = trainer.getAvailableQuestionsCount() > 1
                tvCurrentWord.isVisible = true
                tvCurrentWord.text = firstQuestion.correctAnswer.original

                answerViews.forEachIndexed { index, answerView ->
                    answerView.number.text = "${index + 1}"
                    answerView.value.text = firstQuestion.variants[index].translate
                }

                answerViews.forEachIndexed { index, answerView ->
                    answerView.container.setOnClickListener {
                        onAnswerClick(index, answerView, trainer)
                    }
                }
            }
        }
    }

    private fun markAnswerNeutral(answerView: AnswerView) {

        answerView.container.background = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.shape_rounded_containers
        )

        answerView.number.apply {
            background = ContextCompat.getDrawable(
                requireContext(),
                R.drawable.shape_rounded_variants
            )

            setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.textVariantsColor
                )
            )
        }

        answerView.value.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.textVariantsColor
            )
        )
    }

    private fun markAnswerWrong(answerView: AnswerView) {
        answerView.container.background = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.shape_rounded_containers_wrong
        )

        answerView.number.apply {
            background = ContextCompat.getDrawable(
                requireContext(),
                R.drawable.shape_rounded_variants_wrong
            )

            setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            )
        }

        answerView.value.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.wrongAnswerColor
            )
        )
    }

    private fun markAnswerCorrect(answerView: AnswerView) {
        answerView.container.background = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.shape_rounded_containers_correct
        )

        answerView.number.apply {
            background = ContextCompat.getDrawable(
                requireContext(),
                R.drawable.shape_rounded_variants_correct
            )
            setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            )
        }

        answerView.value.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.correctAnswerColor
            )
        )
    }

    private fun showResult(isCorrect: Boolean) {
        val color: Int
        val messageText: String
        val resultIconResource: Int

        if (isCorrect) {
            color = ContextCompat.getColor(requireContext(), R.color.correctAnswerColor)
            resultIconResource = R.drawable.ic_correct
            messageText = getString(R.string.title_correct)
        } else {
            color = ContextCompat.getColor(requireContext(), R.color.wrongAnswerColor)
            resultIconResource = R.drawable.ic_wrong
            messageText = getString(R.string.title_wrong)
        }

        with(binding) {
            btnSkip.isVisible = false
            clResult.isVisible = true
            btnContinue.setTextColor(color)
            clResult.setBackgroundColor(color)
            tvResultMessage.text = messageText
            ivResultIcon.setImageResource(resultIconResource)
        }
    }

    private fun openTrainingResult() {
        timer?.cancel()
        timer = null

        parentFragmentManager.beginTransaction()
            .replace(
                R.id.flContainer,
                TrainingResultFragment.newInstance(learnedWordsCount, totalWordsCount)
            )
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("LearnWordsFragment", "onDestroyView: LearnWordsFragment")
        timer?.cancel()
        timer = null
        _binding = null
    }
}