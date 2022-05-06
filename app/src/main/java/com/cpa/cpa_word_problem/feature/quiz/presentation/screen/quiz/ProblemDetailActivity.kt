package com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.cpa.cpa_word_problem.R
import com.ysshin.shared.base.BaseActivity
import com.ysshin.shared.common.ui.zigzag.blinkZigzag
import com.cpa.cpa_word_problem.databinding.ActivityProblemDetailBinding
import com.cpa.cpa_word_problem.feature.quiz.domain.model.QuizType
import com.cpa.cpa_word_problem.feature.quiz.presentation.mapper.toDomain
import com.cpa.cpa_word_problem.feature.quiz.presentation.mapper.toModel
import com.cpa.cpa_word_problem.feature.quiz.presentation.model.ProblemModel
import com.cpa.cpa_word_problem.feature.quiz.presentation.screen.statistics.QuizStatisticsActivity
import com.cpa.cpa_word_problem.feature.quiz.presentation.util.Constants
import com.google.android.material.snackbar.Snackbar
import com.ysshin.shared.util.*
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
            ).setAnchorView(binding.fabNext).show()
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.quizState.collect { event ->
                    handleEvent(event)
                    Timber.d("$event")
                }
            }
        }
    }

    private fun handleEvent(state: QuizState) {
        when (state) {
            is QuizState.Started -> {
                viewModel.onStart {
                    viewModel.next()
                }
            }
            is QuizState.Paused -> viewModel.onPause()
            is QuizState.Resumed -> viewModel.onResume()
            is QuizState.Calculating -> viewModel.onCalculating(binding.getUserAnswerIndex())
            is QuizState.Correct -> {
                binding.root.blink(
                    color(R.color.theme_color),
                    color(R.color.color_on_correct)
                )
                binding.toolbar.blink(
                    color(R.color.daynight_gray050s),
                    color(R.color.color_on_correct),
                )
                binding.layProblemDetail.layoutZigzag.blinkZigzag(
                    color(R.color.daynight_gray050s),
                    color(R.color.color_on_correct),
                )
            }
            is QuizState.Incorrect -> {
                binding.root.blink(
                    color(R.color.theme_color),
                    color(R.color.color_on_incorrect)
                )
                binding.toolbar.blink(
                    color(R.color.daynight_gray050s),
                    color(R.color.color_on_incorrect),
                )
                binding.layProblemDetail.layoutZigzag.blinkZigzag(
                    color(R.color.daynight_gray050s),
                    color(R.color.color_on_incorrect),
                )
            }
            is QuizState.Next -> {
                viewModel.onNext {
                    binding.layProblemDetail.rgQuestions.clearCheck()
                    binding.scrollView.scrollToView(binding.toolbar)
                }
            }
            is QuizState.Ended -> {
                binding.fabNext.isEnabled = false
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
            (extras.getSerializable(Constants.mode) as ProblemDetailMode).let { mode ->
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
        (extras.getSerializable(Constants.quizType) as? QuizType)?.let { type ->
            viewModel.quizType.update { type }
        }

        (extras.getInt(Constants.quizNumbers)).let { quizNumbers ->
            viewModel.start(quizNumbers)
        }

        (extras.getBoolean(Constants.useTimer)).let { useTimer ->
            viewModel.useTimer.value = useTimer
        }
    }

    private fun parseDetailIntent(extras: Bundle) {
        (extras.getParcelable<ProblemModel>(Constants.problemModel))?.let { problemModel ->
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
            putExtra(Constants.mode, mode)
            problemModel?.let { problemModel -> putExtra(Constants.problemModel, problemModel) }
        }

        // From Quiz
        fun newIntent(
            context: Context,
            mode: ProblemDetailMode = ProblemDetailMode.Quiz,
            quizType: QuizType,
            quizNumbers: Int,
            useTimer: Boolean = false,
        ) = Intent(context, ProblemDetailActivity::class.java).apply {
            putExtra(Constants.mode, mode)
            putExtra(Constants.quizType, quizType)
            putExtra(Constants.quizNumbers, quizNumbers)
            putExtra(Constants.useTimer, useTimer)
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