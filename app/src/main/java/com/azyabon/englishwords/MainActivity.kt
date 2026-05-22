package com.azyabon.englishwords

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.azyabon.englishwords.databinding.ActivityLearnWordBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityLearnWordBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("View binding is only valid between onCreate and onDestroy")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLearnWordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            tvCurrentWord.text = "Galaxy"

            llAnswer3.setOnClickListener {
                markAnswerCorrect()
            }
        }
    }

    private fun markAnswerCorrect() {
        with(binding) {
            llAnswer3.background = ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.shape_rounded_containers_correct
            )

            tvVariantNumber3.background = ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.shape_rounded_variants_correct
            )

            tvVariantNumber3.setTextColor(
                ContextCompat.getColor(
                    this@MainActivity,
                    R.color.white
                )
            )

            tvVariantValue3.setTextColor(
                ContextCompat.getColor(
                    this@MainActivity,
                    R.color.correctAnswerColor
                )
            )

            btnSkip.isVisible = false

            clResult.setBackgroundColor(
                ContextCompat.getColor(
                    this@MainActivity,
                    R.color.correctAnswerColor
                )
            )

            ivResultIcon.setImageDrawable(
                ContextCompat.getDrawable(
                    this@MainActivity,
                    R.drawable.ic_correct
                )
            )

            tvResultMessage.text = resources.getString(R.string.title_correct)

            btnContinue.setTextColor(
                ContextCompat.getColor(
                    this@MainActivity,
                    R.color.correctAnswerColor
                )
            )

            clResult.isVisible = true
        }
    }
}