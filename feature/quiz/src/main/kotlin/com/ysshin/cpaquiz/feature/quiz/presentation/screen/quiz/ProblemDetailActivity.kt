package com.ysshin.cpaquiz.feature.quiz.presentation.screen.quiz

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import com.ysshin.cpaquiz.core.android.base.BaseActivity
import com.ysshin.cpaquiz.core.android.util.parcelable
import com.ysshin.cpaquiz.core.android.util.repeatOnLifecycleStarted
import com.ysshin.cpaquiz.core.android.util.serializable
import com.ysshin.cpaquiz.core.android.util.setOnDoubleClick
import com.ysshin.cpaquiz.core.android.util.setOnThrottleClick
import com.ysshin.cpaquiz.domain.model.ProblemDetailMode
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.feature.quiz.R
import com.ysshin.cpaquiz.feature.quiz.databinding.ActivityProblemDetailBinding
import com.ysshin.cpaquiz.feature.quiz.presentation.mapper.toDomain
import com.ysshin.cpaquiz.feature.quiz.presentation.model.ProblemModel
import com.ysshin.cpaquiz.feature.quiz.presentation.util.QuizConstants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.update
import timber.log.Timber

@Deprecated("Migrate to QuestionActivity")
@AndroidEntryPoint
class ProblemDetailActivity : BaseActivity() {

    private lateinit var binding: ActivityProblemDetailBinding
    private val viewModel: ProblemDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProblemDetailBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setContentView(binding.root)
        initView()
        observeViewModel()
    }

    override fun onStart() {
        super.onStart()
        parseIntent()
    }

    override fun onResume() {
        super.onResume()
        viewModel.resume()
    }

    override fun onPause() {
        super.onPause()
        viewModel.pause()
    }

    private fun initView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.fabNext.setOnThrottleClick { calculate() }

        with(binding.layProblemDetail) {
            rb0.setOnDoubleClick { calculate() }
            rb1.setOnDoubleClick { calculate() }
            rb2.setOnDoubleClick { calculate() }
            rb3.setOnDoubleClick { calculate() }
            rb4.setOnDoubleClick { calculate() }
        }
    }

    private fun calculate() {
        if (binding.layProblemDetail.rgQuestions.checkedRadioButtonId > 0) {
            viewModel.calculate()
            viewModel.next()
        } else {
            Snackbar.make(
                binding.fabNext,
                getString(R.string.msg_need_answer),
                Snackbar.LENGTH_SHORT
            ).apply {
                anchorView = binding.fabNext
                setAction(R.string.confirm) { dismiss() }
            }.show()
        }
    }

    private fun observeViewModel() {
        repeatOnLifecycleStarted {
            viewModel.quizEvent.collect { event ->
                Timber.d("$event")
            }
        }
    }

    private fun parseIntent() {
        intent.extras?.let { extras ->
            (extras.serializable(QuizConstants.mode, ProblemDetailMode::class.java))?.let { mode ->
                viewModel.mode.value = mode

                when (mode) {
                    ProblemDetailMode.Quiz -> {
                        supportActionBar?.title = getString(R.string.quiz)
                        parseQuizIntent(extras)
                    }
                    ProblemDetailMode.Detail -> {
                        supportActionBar?.title = ""
                        parseDetailIntent(extras)
                    }
                }
            }
        }
    }

    private fun parseQuizIntent(extras: Bundle) {
        (extras.serializable(QuizConstants.quizType, QuizType::class.java))?.let { type ->
            viewModel.quizType.update { type }
        }

        (extras.getInt(QuizConstants.quizNumbers)).let { quizNumbers ->
            viewModel.start(quizNumbers)
        }

        (extras.getBoolean(QuizConstants.useTimer)).let { useTimer ->
            viewModel.useTimer.value = useTimer
        }
    }

    private fun parseDetailIntent(extras: Bundle) {
        (extras.parcelable(QuizConstants.problemModel, ProblemModel::class.java))?.let { problemModel ->
            viewModel.problem.value = problemModel.toDomain()
        }
    }

    companion object {
        // From Detail
        fun newIntent(
            context: Context,
            mode: ProblemDetailMode = ProblemDetailMode.Detail,
            problemModel: ProblemModel? = null,
        ) = Intent(context, ProblemDetailActivity::class.java).apply {
            putExtra(QuizConstants.mode, mode)
            problemModel?.let { problemModel -> putExtra(QuizConstants.problemModel, problemModel) }
        }

        // From Quiz
        fun newIntent(
            context: Context,
            mode: ProblemDetailMode = ProblemDetailMode.Quiz,
            quizType: QuizType,
            quizNumbers: Int,
            useTimer: Boolean = false,
        ) = Intent(context, ProblemDetailActivity::class.java).apply {
            putExtra(QuizConstants.mode, mode)
            putExtra(QuizConstants.quizType, quizType)
            putExtra(QuizConstants.quizNumbers, quizNumbers)
            putExtra(QuizConstants.useTimer, useTimer)
        }
    }
}

private fun ActivityProblemDetailBinding.getUserAnswerIndex(): Int =
    when (layProblemDetail.rgQuestions.checkedRadioButtonId) {
        R.id.rb0 -> 0
        R.id.rb1 -> 1
        R.id.rb2 -> 2
        R.id.rb3 -> 3
        R.id.rb4 -> 4
        else -> throw IndexOutOfBoundsException("Invalid index for radio group.")
    }
