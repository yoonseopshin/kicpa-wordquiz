package com.cpa.cpa_word_problem.feature.quiz.presentation.screen.main.home

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.cpa.cpa_word_problem.R
import com.cpa.cpa_word_problem.base.BaseFragment
import com.cpa.cpa_word_problem.databinding.FragmentHomeBinding
import com.cpa.cpa_word_problem.feature.quiz.domain.model.QuizType
import com.cpa.cpa_word_problem.feature.quiz.presentation.notifications.AlarmNotification
import com.cpa.cpa_word_problem.feature.quiz.presentation.receivers.AlarmReceiver
import com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.ProblemDetailActivity
import com.cpa.cpa_word_problem.feature.quiz.presentation.util.AdConstants
import com.cpa.cpa_word_problem.utils.invisible
import com.cpa.cpa_word_problem.utils.setOnThrottleClick
import com.cpa.cpa_word_problem.utils.visibleOrGone
import com.google.android.ads.nativetemplates.NativeTemplateStyle
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val viewModel: HomeViewModel by viewModels()
    private val bsQuizBehavior: BottomSheetBehavior<ConstraintLayout> by lazy {
        BottomSheetBehavior.from(binding.bsQuiz.root)
    }
    private lateinit var onBackPressedCallback: OnBackPressedCallback
    lateinit var adLoader: AdLoader

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (bsQuizBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                    bsQuizBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                    return
                }

                requireActivity().finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onDetach() {
        super.onDetach()
        onBackPressedCallback.remove()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentHomeBinding.inflate(layoutInflater).also {
        _binding = it
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeViewModel()
        viewModel.requestNextExamDate()
        loadAd()
    }

    private fun loadAd() {
        adLoader = AdLoader.Builder(requireContext(), AdConstants.QUIZ_NATIVE_AD_MEDIUM)
            .forNativeAd { nativeAd ->
                val styles = NativeTemplateStyle.Builder()
                    .withMainBackgroundColor(ColorDrawable(colorAsInt(R.color.theme_color)))
                    .withCallToActionBackgroundColor(ColorDrawable(colorAsInt(R.color.primaryDarkColor)))
                    .withCallToActionTypefaceColor(colorAsInt(R.color.secondaryTextColor))
                    .build()
                binding.adTemplateView.setStyles(styles)
                binding.adTemplateView.setNativeAd(nativeAd)


                if (adLoader.isLoading) {

                } else {

                }

                if (isDetached) {
                    nativeAd.destroy()
                    return@forNativeAd
                }
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    // TODO: Handle AdError
                }

                override fun onAdClicked() {
                    super.onAdClicked()
                }

                override fun onAdClosed() {
                    super.onAdClosed()
                }

                override fun onAdImpression() {
                    super.onAdImpression()
                }

                override fun onAdLoaded() {
                    super.onAdLoaded()
                }

                override fun onAdOpened() {
                    super.onAdOpened()
                }
            })
            .withNativeAdOptions(NativeAdOptions.Builder().build())
            .build()

        adLoader.loadAd(AdRequest.Builder().build())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView() {
        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.more_vert -> {
                    when (bsQuizBehavior.state) {
                        BottomSheetBehavior.STATE_COLLAPSED -> {
                            bsQuizBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                        }
                        else -> Unit
                    }
                    true
                }
                else -> false
            }
        }

        binding.swTimePicker.setOnCheckedChangeListener { button, isChecked ->
            viewModel.setAlarm(isChecked)
        }

        bsQuizBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        binding.fabCloseBsQuiz.invisible()
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        binding.fabCloseBsQuiz.show()
                    }
                    else -> Unit
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) = Unit
        })
        bsQuizBehavior.peekHeight = 0

        binding.fabCloseBsQuiz.setOnThrottleClick {
            bsQuizBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        with(binding.layAccounting) {
            tvSubjectTitle.text = getString(R.string.accounting)

            btnQuiz.backgroundTintList = color(R.color.accounting_highlight_color)
            root.setCardBackgroundColor(color(R.color.accounting_highlight_color_0_20))

            root.setOnThrottleClick {
                startActivity(
                    ProblemDetailActivity.newIntent(
                        context = requireContext(),
                        quizType = QuizType.Accounting,
                        quizNumbers = viewModel.quizNumber.value,
                        useTimer = viewModel.useTimer.value
                    )
                )
            }
        }

        with(binding.layBusiness) {
            tvSubjectTitle.text = getString(R.string.business)

            btnQuiz.backgroundTintList = color(R.color.business_highlight_color)
            root.setCardBackgroundColor(color(R.color.business_highlight_color_0_20))

            root.setOnThrottleClick {
                startActivity(
                    ProblemDetailActivity.newIntent(
                        context = requireContext(),
                        quizType = QuizType.Business,
                        quizNumbers = viewModel.quizNumber.value,
                        useTimer = viewModel.useTimer.value
                    )
                )
            }
        }

        with(binding.layCommercialLaw) {
            tvSubjectTitle.text = getString(R.string.commercial_law)

            btnQuiz.backgroundTintList = color(R.color.commercial_law_highlight_color)
            root.setCardBackgroundColor(color(R.color.commercial_law_highlight_color_0_20))

            root.setOnThrottleClick {
                startActivity(
                    ProblemDetailActivity.newIntent(
                        context = requireContext(),
                        quizType = QuizType.CommercialLaw,
                        quizNumbers = viewModel.quizNumber.value,
                        useTimer = viewModel.useTimer.value
                    )
                )
            }
        }

        with(binding.bsQuiz) {
            layQuizNum.setOnThrottleClick {
                val numberPicker = NumberPicker(requireActivity()).apply {
                    minValue = 3
                    maxValue = 25
                    value = viewModel.quizNumber.value
                }

                MaterialAlertDialogBuilder(requireActivity())
                    .setMessage("문제 수를 고르세요.")
                    .setView(numberPicker)
                    .setPositiveButton("확인") { _, _ ->
                        viewModel.setQuizNumber(numberPicker.value)
                    }
                    .setNegativeButton("취소") { _, _ -> }
                    .create().show()
            }

            layTimer.setOnClickListener {
                swTimer.performClick()
            }

            swTimer.setOnCheckedChangeListener { _, isChecked ->
                viewModel.setTimer(isChecked)
            }
        }

        binding.tvTimePickerDesc.setOnThrottleClick {
            if (viewModel.useAlarm.value.not()) return@setOnThrottleClick
            viewModel.showTimePicker()
        }

    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    viewModel.homeState.collect { state ->
                        when (state) {
                            is HomeState.ShowTimePicker -> {
                                val calendar = Calendar.getInstance().apply {
                                    timeInMillis = System.currentTimeMillis()
                                }

                                val listener =
                                    TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                                        calendar.set(Calendar.MINUTE, minute)
                                        viewModel.setAlarmTime(hourOfDay, minute)
                                    }

                                TimePickerDialog(
                                    requireActivity(),
                                    listener,
                                    calendar.get(Calendar.HOUR_OF_DAY),
                                    calendar.get(Calendar.MINUTE),
                                    true
                                ).apply {
                                    setOnCancelListener {
                                        if (viewModel.isAlarmSet().not()) {
                                            viewModel.setAlarm(false)
                                        }
                                    }
                                }.show()
                            }
                        }
                    }
                }

                launch {
                    viewModel.quizNumber.collectLatest { quizNumbers ->
                        binding.bsQuiz.tvQuizNumResult.text = quizNumbers.toString()
                    }
                }

                launch {
                    viewModel.useTimer.collectLatest { useTimer ->
                        binding.bsQuiz.swTimer.isChecked = useTimer
                    }
                }

                launch {
                    viewModel.useAlarm.collectLatest { useAlarm ->
                        binding.swTimePicker.isChecked = useAlarm
                        handleAlarm(useAlarm)
                    }
                }

                launch {
                    viewModel.accountingCount.collectLatest { count ->
                        binding.layAccounting.tvSubjectCount.visibleOrGone(count > 0)
                        binding.layAccounting.tvSubjectCount.text =
                            getString(R.string.subject_total_count, count)
                    }
                }

                launch {
                    viewModel.businessCount.collectLatest { count ->
                        binding.layBusiness.tvSubjectCount.visibleOrGone(count > 0)
                        binding.layBusiness.tvSubjectCount.text =
                            getString(R.string.subject_total_count, count)
                    }
                }

                launch {
                    viewModel.commercialLawCount.collectLatest { count ->
                        binding.layCommercialLaw.tvSubjectCount.visibleOrGone(count > 0)
                        binding.layCommercialLaw.tvSubjectCount.text =
                            getString(R.string.subject_total_count, count)
                    }
                }
            }
        }
    }

    private fun handleAlarm(useAlarm: Boolean) {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
        }

        val alarmManager = requireContext().getSystemService(AlarmManager::class.java)

        val alarmIntent = Intent(
            context,
            AlarmReceiver::class.java
        ).let { intent ->
            PendingIntent.getBroadcast(
                context,
                AlarmNotification.REQUEST_CODE,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

        if (useAlarm) {
            alarmManager.cancel(alarmIntent)

            calendar.set(Calendar.HOUR_OF_DAY, viewModel.alarmHourOfDay.value)
            calendar.set(Calendar.MINUTE, viewModel.alarmMinute.value)

            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                alarmIntent
            )
        } else {
            alarmManager.cancel(alarmIntent)
        }
    }
}
