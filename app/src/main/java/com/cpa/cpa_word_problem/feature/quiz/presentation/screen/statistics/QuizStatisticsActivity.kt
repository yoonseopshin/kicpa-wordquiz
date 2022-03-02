package com.cpa.cpa_word_problem.feature.quiz.presentation.screen.statistics

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import com.cpa.cpa_word_problem.R
import com.cpa.cpa_word_problem.base.BaseActivity
import com.cpa.cpa_word_problem.databinding.ActivityQuizStatisticsBinding
import com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem
import com.cpa.cpa_word_problem.feature.quiz.presentation.adapters.NoteAdapter
import com.cpa.cpa_word_problem.feature.quiz.presentation.adapters.NoteResultHeaderAdapter
import com.cpa.cpa_word_problem.feature.quiz.presentation.adapters.TimerAdapter
import com.cpa.cpa_word_problem.feature.quiz.presentation.mapper.toDomain
import com.cpa.cpa_word_problem.feature.quiz.presentation.mapper.toModel
import com.cpa.cpa_word_problem.feature.quiz.presentation.model.ProblemModel
import com.cpa.cpa_word_problem.feature.quiz.presentation.model.UserSolvedProblemModel
import com.cpa.cpa_word_problem.feature.quiz.presentation.model.from
import com.cpa.cpa_word_problem.feature.quiz.presentation.screen.main.MainActivity
import com.cpa.cpa_word_problem.feature.quiz.presentation.screen.main.MainTab
import com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.ProblemDetailActivity
import com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.ProblemDetailMode
import com.cpa.cpa_word_problem.feature.quiz.presentation.util.Constants
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class QuizStatisticsActivity : BaseActivity() {

    private lateinit var binding: ActivityQuizStatisticsBinding
    private val viewModel: QuizStatisticsViewModel by viewModels()
    private val timerAdapter: TimerAdapter by lazy { TimerAdapter() }
    private val noteResultHeaderAdapter: NoteResultHeaderAdapter by lazy {
        NoteResultHeaderAdapter().also { adapter ->
            adapter.onNoteResultHeaderClick = {
                startActivity(
                    MainActivity.newIntent(
                        context = this,
                        destination = MainTab.Note
                    ).apply {
                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                )
            }
        }
    }
    private val noteAdapter: NoteAdapter by lazy {
        NoteAdapter().also { adapter ->
            adapter.onProblemClick = { problem ->
                startActivity(
                    ProblemDetailActivity.newIntent(
                        this,
                        ProblemDetailMode.Detail,
                        problem.toModel()
                    )
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizStatisticsBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)
        initView()
        parseIntent()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_quiz_statistics_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.confirm -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.recyclerView.adapter = ConcatAdapter(
            timerAdapter,
            noteResultHeaderAdapter,
            noteAdapter
        )
    }

    private fun parseIntent() {
        intent.extras?.let { extras ->
            var times: List<Int> = emptyList()
            var userSelectedIndices: List<Int> = emptyList()
            var problems: List<Problem> = emptyList()

            (extras.getParcelableArrayList<ProblemModel>(Constants.problems))?.let { problemModel ->
                problems = problemModel.toList().toDomain()
            }

            (extras.getIntegerArrayList(Constants.selected))?.let { selected ->
                userSelectedIndices = selected
            }

            (extras.getIntegerArrayList(Constants.timesPerProblem))?.let { timesPerProblem ->
                times = timesPerProblem
                if (times.sum() > 0) {
                    timerAdapter.timesPerProblem = timesPerProblem
                    timerAdapter.notifyItemInserted(0)
                }
            }

            viewModel.setWrongProblems(problems, userSelectedIndices)


            noteAdapter.submitList(
                listOf<UserSolvedProblemModel>().from(
                    times,
                    userSelectedIndices,
                    problems
                )
            )
        }
    }

    companion object {
        fun newIntent(
            context: Context,
            problems: List<ProblemModel> = listOf(),
            selected: List<Int> = listOf(),
            timesPerProblem: List<Long> = listOf(),
        ) = Intent(context, QuizStatisticsActivity::class.java).apply {
            putParcelableArrayListExtra(Constants.problems, ArrayList(problems))
            putExtra(Constants.selected, ArrayList(selected))
            putExtra(Constants.timesPerProblem, ArrayList(timesPerProblem))
        }
    }

}