package com.ysshin.cpaquiz.feature.home.presentation.screen.main

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import com.google.android.ads.nativetemplates.NativeTemplateStyle
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.feature.home.R
import com.ysshin.cpaquiz.feature.home.databinding.FragmentHomeBinding
import com.ysshin.cpaquiz.shared.android.base.BaseFragment
import com.ysshin.cpaquiz.shared.android.bridge.ProblemDetailNavigator
import com.ysshin.cpaquiz.shared.android.util.AdConstants
import com.ysshin.cpaquiz.shared.android.util.Constants
import com.ysshin.cpaquiz.shared.android.util.newInstance
import com.ysshin.cpaquiz.shared.android.util.repeatOnLifecycleStarted
import com.ysshin.cpaquiz.shared.android.util.setOnThrottleClick
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Deprecated("Migrate to HomeComposeFragment")
@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    @Inject
    lateinit var problemDetailNavigator: ProblemDetailNavigator

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val viewModel: HomeViewModel by viewModels()
    private val bsQuizBehavior: BottomSheetBehavior<ConstraintLayout> by lazy {
        BottomSheetBehavior.from(binding.bsQuiz.layout)
    }
    private lateinit var onBackPressedCallback: OnBackPressedCallback
    private lateinit var adLoader: AdLoader

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // FIXME: Crash when turning it off and on quickly, so dealt with not being released _binding.
        // _binding = null
    }

    override fun onDetach() {
        super.onDetach()
        onBackPressedCallback.remove()
    }

    private fun initView() {
        val context = requireContext()

        loadAd()

        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.more_vert -> {
                    viewModel.setQuizSettingsOpened(true)
                    true
                }
                else -> false
            }
        }

        bsQuizBehavior.addBottomSheetCallback(object :
                BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    when (newState) {
                        BottomSheetBehavior.STATE_COLLAPSED -> {
                            viewModel.setQuizSettingsOpened(false)
                        }
                        BottomSheetBehavior.STATE_EXPANDED -> {
                            viewModel.setQuizSettingsOpened(true)
                        }
                        else -> Unit
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) = Unit
            })
        bsQuizBehavior.peekHeight = 0

        binding.fabCloseBsQuiz.setOnThrottleClick {
            // Invisible after animation
            bsQuizBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        binding.layAccounting.root.setOnThrottleClick {
            context.startActivity(
                problemDetailNavigator.quizIntent(
                    context = context,
                    quizType = QuizType.Accounting,
                    quizNumbers = viewModel.quizNumber.value,
                    useTimer = viewModel.useTimer.value,
                )
            )
        }

        binding.layBusiness.root.setOnThrottleClick {
            context.startActivity(
                problemDetailNavigator.quizIntent(
                    context = context,
                    quizType = QuizType.Business,
                    quizNumbers = viewModel.quizNumber.value,
                    useTimer = viewModel.useTimer.value,
                )
            )
        }

        binding.layCommercialLaw.root.setOnThrottleClick {
            context.startActivity(
                problemDetailNavigator.quizIntent(
                    context = context,
                    quizType = QuizType.CommercialLaw,
                    quizNumbers = viewModel.quizNumber.value,
                    useTimer = viewModel.useTimer.value,
                )
            )
        }

        binding.layTaxLaw.root.setOnThrottleClick {
            context.startActivity(
                problemDetailNavigator.quizIntent(
                    context = context,
                    quizType = QuizType.TaxLaw,
                    quizNumbers = viewModel.quizNumber.value,
                    useTimer = viewModel.useTimer.value,
                )
            )
        }

        with(binding.bsQuiz) {
            layQuizNum.setOnThrottleClick {
                newInstance<QuizNumberPickerDialogFragment>(
                    Constants.minNumber to 5,
                    Constants.maxNumber to 25,
                    Constants.defaultNumber to viewModel.quizNumber.value,
                    Constants.icon to R.drawable.ic_note_outlined,
                    Constants.title to getString(R.string.quiz_number_picker_title),
                    Constants.description to getString(R.string.quiz_number_picker_description)
                ).show(childFragmentManager, QuizNumberPickerDialogFragment::class.java.simpleName)
            }

            layTimer.setOnClickListener {
                swTimer.performClick()
            }

            swTimer.setOnCheckedChangeListener { _, isChecked ->
                viewModel.setTimer(isChecked)
            }
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.repeatOnLifecycleStarted {
            launch {
                viewModel.isQuizSettingsOpened.collectLatest { value ->
                    bsQuizBehavior.state = if (value) {
                        BottomSheetBehavior.STATE_EXPANDED
                    } else {
                        BottomSheetBehavior.STATE_COLLAPSED
                    }
                }
            }
        }
    }

    private fun loadAd() {
        runCatching {
            adLoader = AdLoader.Builder(requireContext(), AdConstants.QUIZ_NATIVE_AD_MEDIUM)
                .forNativeAd { nativeAd ->
                    val styles = NativeTemplateStyle.Builder()
                        .withMainBackgroundColor(ColorDrawable(colorAsInt(R.color.theme_color)))
                        .withCallToActionBackgroundColor(ColorDrawable(colorAsInt(R.color.primaryDarkColor)))
                        .withCallToActionTypefaceColor(colorAsInt(R.color.secondaryTextColor))
                        .build()
                    binding.adTemplateView.setStyles(styles)
                    binding.adTemplateView.setNativeAd(nativeAd)

                    if (isDetached) {
                        nativeAd.destroy()
                        return@forNativeAd
                    }
                }
                .withNativeAdOptions(NativeAdOptions.Builder().build())
                .build()
        }.onSuccess {
            adLoader.loadAd(AdRequest.Builder().build())
        }
    }
}
