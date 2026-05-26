package com.azyabon.englishwords

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.azyabon.englishwords.databinding.FragmentLearnWordsBinding

class LearnWordsFragment : Fragment() {

    private var _binding: FragmentLearnWordsBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("View binding is only valid between onCreate and onDestroy")
    private var timer: CountDownTimer? = null

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

        val trainer = LearnWordsTrainer()
        showNextQuestion(trainer)

        timer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.tvTimer.text = "${millisUntilFinished / 1000}s"
            }

            override fun onFinish() {
                parentFragmentManager.popBackStack()
            }
        }.start()

        with(binding) {
            btnContinue.setOnClickListener {
                clResult.isVisible = false
                markAnswerNeutral(llAnswer1, tvVariantNumber1, tvVariantValue1)
                markAnswerNeutral(llAnswer2, tvVariantNumber2, tvVariantValue2)
                markAnswerNeutral(llAnswer3, tvVariantNumber3, tvVariantValue3)
                markAnswerNeutral(llAnswer4, tvVariantNumber4, tvVariantValue4)

                showNextQuestion(trainer)
            }

            ibClose.setOnClickListener {
                parentFragmentManager.popBackStack()
            }

            btnSkip.setOnClickListener {
                showNextQuestion(trainer)
            }
        }
    }

    private fun showNextQuestion(trainer: LearnWordsTrainer) {
        val firstQuestion: Question? = trainer.getNextQuestion()
        with(binding) {
            if (firstQuestion == null || firstQuestion.variants.size < NUMBER_OF_ANSWERS) {
                tvCurrentWord.isVisible = false
                llVariants.isVisible = false
                btnSkip.text = "Complete"
            } else {
                btnSkip.isVisible = true
                tvCurrentWord.isVisible = true
                tvCurrentWord.text = firstQuestion.correctAnswer.original

                tvVariantValue1.text = firstQuestion.variants[0].translate
                tvVariantValue2.text = firstQuestion.variants[1].translate
                tvVariantValue3.text = firstQuestion.variants[2].translate
                tvVariantValue4.text = firstQuestion.variants[3].translate

                tvVariantNumber1.text = "1"
                tvVariantNumber2.text = "2"
                tvVariantNumber3.text = "3"
                tvVariantNumber4.text = "4"

                llAnswer1.setOnClickListener {
                    if (trainer.checkAnswer(0)) {
                        markAnswerCorrect(llAnswer1, tvVariantNumber1, tvVariantValue1)
                        showResult(true)
                    } else {
                        markAnswerWrong(llAnswer1, tvVariantNumber1, tvVariantValue1)
                        showResult(false)
                    }
                }

                llAnswer2.setOnClickListener {
                    if (trainer.checkAnswer(1)) {
                        markAnswerCorrect(llAnswer2, tvVariantNumber2, tvVariantValue2)
                        showResult(true)
                    } else {
                        markAnswerWrong(llAnswer2, tvVariantNumber2, tvVariantValue2)
                        showResult(false)
                    }
                }

                llAnswer3.setOnClickListener {
                    if (trainer.checkAnswer(2)) {
                        markAnswerCorrect(llAnswer3, tvVariantNumber3, tvVariantValue3)
                        showResult(true)
                    } else {
                        markAnswerWrong(llAnswer3, tvVariantNumber3, tvVariantValue3)
                        showResult(false)
                    }
                }

                llAnswer4.setOnClickListener {
                    if (trainer.checkAnswer(3)) {
                        markAnswerCorrect(llAnswer4, tvVariantNumber4, tvVariantValue4)
                        showResult(true)
                    } else {
                        markAnswerWrong(llAnswer4, tvVariantNumber4, tvVariantValue4)
                        showResult(false)
                    }
                }
            }
        }

    }

    private fun markAnswerNeutral(
        llAnswer: LinearLayout,
        tvVariantNumber: TextView,
        tvVariantValue: TextView,
    ) {

        llAnswer.background = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.shape_rounded_containers
        )

        tvVariantNumber.apply {
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

        tvVariantValue.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.textVariantsColor
            )
        )
    }

    private fun markAnswerWrong(
        llAnswer: LinearLayout,
        tvVariantNumber: TextView,
        tvVariantValue: TextView,
    ) {
        llAnswer.background = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.shape_rounded_containers_wrong
        )

        tvVariantNumber.background = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.shape_rounded_variants_wrong
        )

        tvVariantNumber.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.white
            )
        )

        tvVariantValue.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.wrongAnswerColor
            )
        )
    }

    private fun markAnswerCorrect(
        llAnswer: LinearLayout,
        tvVariantNumber: TextView,
        tvVariantValue: TextView,
    ) {
        llAnswer.background = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.shape_rounded_containers_correct
        )

        tvVariantNumber.background = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.shape_rounded_variants_correct
        )

        tvVariantNumber.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.white
            )
        )

        tvVariantValue.setTextColor(
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

    override fun onDestroyView() {
        super.onDestroyView()
        timer?.cancel()
        timer = null
        _binding = null
    }
}