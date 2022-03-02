package com.cpa.cpa_word_problem.feature.quiz.presentation.screen.main.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.cpa.cpa_word_problem.R
import com.cpa.cpa_word_problem.base.BaseFragment
import com.cpa.cpa_word_problem.databinding.FragmentHomeBinding
import com.cpa.cpa_word_problem.feature.quiz.domain.model.QuizType
import com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.ProblemDetailActivity
import com.cpa.cpa_word_problem.utils.invisible
import com.cpa.cpa_word_problem.utils.setOnThrottleClick
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val viewModel: HomeViewModel by viewModels()
    private val bsQuizBehavior: BottomSheetBehavior<ConstraintLayout> by lazy {
        BottomSheetBehavior.from(binding.bsQuiz.root)
    }
    private lateinit var onBackPressedCallback: OnBackPressedCallback

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

                AlertDialog.Builder(requireActivity())
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

    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
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
            }
        }
    }
}