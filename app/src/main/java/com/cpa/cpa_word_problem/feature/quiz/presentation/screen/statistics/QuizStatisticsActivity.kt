package com.cpa.cpa_word_problem.feature.quiz.presentation.screen.statistics

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ConcatAdapter
import com.cpa.cpa_word_problem.R
import com.ysshin.shared.base.BaseActivity
import com.cpa.cpa_word_problem.databinding.ActivityQuizStatisticsBinding
import com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem
import com.cpa.cpa_word_problem.feature.quiz.presentation.adapter.*
import com.cpa.cpa_word_problem.feature.quiz.presentation.mapper.toDomain
import com.cpa.cpa_word_problem.feature.quiz.presentation.mapper.toModel
import com.cpa.cpa_word_problem.feature.quiz.presentation.model.ProblemModel
import com.cpa.cpa_word_problem.feature.quiz.presentation.model.UserSolvedProblemModel
import com.cpa.cpa_word_problem.feature.quiz.presentation.model.from
import com.cpa.cpa_word_problem.feature.quiz.presentation.screen.main.MainActivity
import com.cpa.cpa_word_problem.feature.quiz.presentation.screen.main.MainTab
import com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.ProblemDetailActivity
import com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.ProblemDetailMode
import com.cpa.cpa_word_problem.feature.quiz.presentation.util.AdConstants
import com.cpa.cpa_word_problem.feature.quiz.presentation.util.Constants
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import dagger.hilt.android.AndroidEntryPoint

import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class QuizStatisticsActivity : BaseActivity() {

    private lateinit var binding: ActivityQuizStatisticsBinding
    private val viewModel: QuizStatisticsViewModel by viewModels()
    private val reviewManager: ReviewManager by lazy { ReviewManagerFactory.create(this) }

    private val timerAdapter: TimerAdapter by lazy { TimerAdapter() }
    private val adNativeBannerAboveNoteResultAdapter: AdNativeBannerAdapter by lazy { AdNativeBannerAdapter() }
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
    private val adSmartBannerBelowNoteAdapter: AdSmartBannerAdapter by lazy { AdSmartBannerAdapter() }

    private var interstitialAd: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizStatisticsBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)
        initView()
        observeViewModel()
        parseIntent()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    viewModel.solvedQuiz.collect { solvedQuiz ->
                        if (viewModel.shouldShowAd(solvedQuiz)) {
                            showInterstitialAd()
                        }
                    }
                }

                launch {
                    viewModel.shouldShowInAppReview.collectLatest { shouldShowInAppReview ->
                        if (shouldShowInAppReview) {
                            showInAppReview()
                        }
                    }
                }
            }
        }
    }

    private fun showInAppReview() {
        val request = reviewManager.requestReviewFlow()
        request.addOnCompleteListener {
            if (it.isSuccessful) {
                val reviewInfo = it.result
                reviewManager.launchReviewFlow(this, reviewInfo)
            }
        }
    }

    private fun showInterstitialAd() {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            this,
            AdConstants.QUIZ_END_INTERSTITIAL_AD,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    interstitialAd = null
                }

                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                    ad.show(this@QuizStatisticsActivity)
                }
            })

        interstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
            }

            override fun onAdShowedFullScreenContent() {
                interstitialAd = null
            }
        }
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
            adNativeBannerAboveNoteResultAdapter,
            noteResultHeaderAdapter,
            noteAdapter,
            adSmartBannerBelowNoteAdapter
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