package com.ysshin.cpaquiz.feature.quiz.presentation.screen.quiz

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.ysshin.cpaquiz.domain.model.ProblemDetailMode
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.feature.quiz.R
import com.ysshin.cpaquiz.feature.quiz.databinding.ActivityProblemDetailBinding
import com.ysshin.cpaquiz.feature.quiz.presentation.mapper.toDomain
import com.ysshin.cpaquiz.feature.quiz.presentation.mapper.toModel
import com.ysshin.cpaquiz.feature.quiz.presentation.model.ProblemModel
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.statistics.QuizStatisticsActivity
import com.ysshin.cpaquiz.feature.quiz.presentation.util.QuizConstants
import com.ysshin.cpaquiz.shared.android.base.BaseActivity
import com.ysshin.cpaquiz.shared.android.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

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
            viewModel.quizState.collect { event ->
                handleEvent(event)
                Timber.d("$event")
            }
        }
    }

    private fun handleEvent(state: QuizEvent) {
        when (state) {
            is QuizEvent.Started -> {
                viewModel.onStart {
                    viewModel.next()
                }
            }
            is QuizEvent.Paused -> viewModel.onPause()
            is QuizEvent.Resumed -> viewModel.onResume()
            is QuizEvent.Calculating -> viewModel.onCalculating(binding.getUserAnswerIndex())
            is QuizEvent.Correct -> {
                binding.coverView.blink(
                    color(android.R.color.transparent),
                    color(R.color.color_on_correct)
                )
            }
            is QuizEvent.Incorrect -> {
                binding.coverView.blink(
                    color(android.R.color.transparent),
                    color(R.color.color_on_incorrect)
                )
            }
            is QuizEvent.Next -> {
                viewModel.onNext {
                    binding.layProblemDetail.rgQuestions.clearCheck()
                    binding.scrollView.scrollToView(binding.toolbar)
                }
            }
            is QuizEvent.Ended -> {
                binding.fabNext.isEnabled = false
                with(binding.layProblemDetail) {
                    rb0.isEnabled = false
                    rb1.isEnabled = false
                    rb2.isEnabled = false
                    rb3.isEnabled = false
                    rb4.isEnabled = false
                }

                viewModel.onEnd {
                    lifecycleScope.launch {
                        delay(500L)
                        startActivity(
                            QuizStatisticsActivity.newIntent(
                                context = this@ProblemDetailActivity,
                                problems = viewModel.problems.toModel(),
                                selected = viewModel.selected,
                                timesPerProblem = viewModel.timesPerProblem,
                            )
                        )
                        finish()
                    }
                }
            }
        }
    }

    private fun parseIntent() {
        intent.extras?.let { extras ->
            (extras.getSerializable(QuizConstants.mode) as ProblemDetailMode).let { mode ->
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
        (extras.getSerializable(QuizConstants.quizType) as? QuizType)?.let { type ->
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
        (extras.getParcelable<ProblemModel>(QuizConstants.problemModel))?.let { problemModel ->
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
