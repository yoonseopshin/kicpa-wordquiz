package com.ysshin.cpaquiz.feature.quiz.presentation.screen.statistics

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import com.ysshin.cpaquiz.domain.model.Problem
import com.ysshin.cpaquiz.domain.model.ProblemDetailMode
import com.ysshin.cpaquiz.feature.quiz.R
import com.ysshin.cpaquiz.feature.quiz.databinding.ActivityQuizStatisticsBinding
import com.ysshin.cpaquiz.feature.quiz.presentation.adapter.AdNativeBannerAdapter
import com.ysshin.cpaquiz.feature.quiz.presentation.adapter.NoteAdapter
import com.ysshin.cpaquiz.feature.quiz.presentation.adapter.NoteResultHeaderAdapter
import com.ysshin.cpaquiz.feature.quiz.presentation.adapter.TimerAdapter
import com.ysshin.cpaquiz.feature.quiz.presentation.mapper.toDomain
import com.ysshin.cpaquiz.feature.quiz.presentation.mapper.toModel
import com.ysshin.cpaquiz.feature.quiz.presentation.model.ProblemModel
import com.ysshin.cpaquiz.feature.quiz.presentation.model.UserSolvedProblemModel
import com.ysshin.cpaquiz.feature.quiz.presentation.model.from
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.quiz.ProblemDetailActivity
import com.ysshin.cpaquiz.feature.quiz.presentation.util.QuizConstants
import com.ysshin.cpaquiz.shared.android.base.BaseActivity
import com.ysshin.cpaquiz.shared.android.bridge.MainScreenNavigator
import com.ysshin.cpaquiz.shared.android.util.AdConstants
import com.ysshin.cpaquiz.shared.android.util.parcelableArrayList
import com.ysshin.cpaquiz.shared.android.util.repeatOnLifecycleStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class QuizStatisticsActivity : BaseActivity() {

    @Inject
    lateinit var mainScreenNavigator: MainScreenNavigator

    private lateinit var binding: ActivityQuizStatisticsBinding
    private val viewModel: QuizStatisticsViewModel by viewModels()
    private val reviewManager: ReviewManager by lazy { ReviewManagerFactory.create(this) }

    private val timerAdapter: TimerAdapter by lazy { TimerAdapter() }
    private val adNativeBannerAboveNoteResultAdapter: AdNativeBannerAdapter by lazy { AdNativeBannerAdapter() }
    private val noteResultHeaderAdapter: NoteResultHeaderAdapter by lazy {
        NoteResultHeaderAdapter().also { adapter ->
            adapter.onNoteResultHeaderClick = {
                startActivity(
                    mainScreenNavigator.noteScreenIntent(
                        context = this,
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    )
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

    private var interstitialAd: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizStatisticsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        observeViewModel()
        parseIntent()
    }

    private fun observeViewModel() {
        repeatOnLifecycleStarted {
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
            }
        )

        interstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
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
            noteAdapter
        )
    }

    private fun parseIntent() {
        intent.extras?.let { extras ->
            var times: List<Int> = emptyList()
            var userSelectedIndices: List<Int> = emptyList()
            var problems: List<Problem> = emptyList()

            (extras.parcelableArrayList(QuizConstants.problems,
                ProblemModel::class.java))?.let { problemModel ->
                problems = problemModel.toList().toDomain()
            }

            (extras.getIntegerArrayList(QuizConstants.selected))?.let { selected ->
                userSelectedIndices = selected
            }

            (extras.getIntegerArrayList(QuizConstants.timesPerProblem))?.let { timesPerProblem ->
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

    override fun onDestroy() {
        super.onDestroy()
        interstitialAd = null
    }

    companion object {
        fun newIntent(
            context: Context,
            problems: List<ProblemModel> = listOf(),
            selected: List<Int> = listOf(),
            timesPerProblem: List<Long> = listOf(),
        ) = Intent(context, QuizStatisticsActivity::class.java).apply {
            putParcelableArrayListExtra(QuizConstants.problems, ArrayList(problems))
            putExtra(QuizConstants.selected, ArrayList(selected))
            putExtra(QuizConstants.timesPerProblem, ArrayList(timesPerProblem))
        }
    }
}
