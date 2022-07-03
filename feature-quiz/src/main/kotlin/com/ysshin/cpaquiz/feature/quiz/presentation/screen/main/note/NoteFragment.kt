package com.ysshin.cpaquiz.feature.quiz.presentation.screen.main.note

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import com.ysshin.cpaquiz.domain.model.Problem
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.feature.quiz.R
import com.ysshin.cpaquiz.feature.quiz.databinding.FragmentNoteBinding
import com.ysshin.cpaquiz.feature.quiz.presentation.adapter.*
import com.ysshin.cpaquiz.feature.quiz.presentation.mapper.toModel
import com.ysshin.cpaquiz.feature.quiz.presentation.model.UserSolvedProblemModel
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.quiz.ProblemDetailActivity
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.quiz.ProblemDetailMode
import com.ysshin.cpaquiz.shared.android.base.BaseFragment
import com.ysshin.cpaquiz.shared.android.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

@FlowPreview
@AndroidEntryPoint
class NoteFragment : BaseFragment() {

    private var _binding: FragmentNoteBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val viewModel: NoteViewModel by viewModels()
    private val bsSearchBehavior: BottomSheetBehavior<ConstraintLayout> by lazy {
        BottomSheetBehavior.from(binding.bsSearch.layout)
    }

    private val adNativeBannerOnNoteAdapter: AdNativeBannerAdapter by lazy { AdNativeBannerAdapter() }
    private val wrongNoteHeaderAdapter: CommonNoteHeaderAdapter by lazy {
        CommonNoteHeaderAdapter().apply {
            headerTitle = getString(R.string.wrong_note)
            onHeaderClick = {}
            onHeaderLongClick = {
                newInstance<DeleteAllWrongProblemDialogFragment>(
                    Constants.icon to R.drawable.ic_delete,
                    Constants.title to getString(R.string.delete_wrong_note),
                    Constants.description to getString(R.string.question_delete_all_wrong_note)
                ).show(childFragmentManager, DeleteAllWrongProblemDialogFragment::class.java.simpleName)
            }
            isShowing = false
        }
    }
    private val wrongNoteAdapter: NoteAdapter by lazy {
        NoteAdapter().also { adapter ->
            adapter.onProblemClick = { problem ->
                startActivity(
                    ProblemDetailActivity.newIntent(
                        requireContext(),
                        ProblemDetailMode.Detail,
                        problem.toModel()
                    ),
                )
            }
            adapter.onProblemLongClick = { problem ->
                newInstance<DeleteWrongProblemDialogFragment>(
                    Constants.icon to R.drawable.ic_delete,
                    Constants.title to getString(R.string.delete_wrong_problem),
                    Constants.description to getString(R.string.question_delete_wrong_note),
                    Constants.targetYear to problem.year,
                    Constants.targetPid to problem.pid,
                    Constants.targetType to problem.type,
                ).show(childFragmentManager, DeleteWrongProblemDialogFragment::class.java.simpleName)
            }
        }
    }
    private val totalNoteHeaderAdapter: CommonNoteHeaderAdapter by lazy {
        CommonNoteHeaderAdapter().apply {
            headerTitle = getString(R.string.total_note)
            onHeaderClick = {}
        }
    }
    private val totalNoteAdapter: NoteAdapter by lazy {
        NoteAdapter().also { adapter ->
            adapter.onProblemClick = { problem ->
                startActivity(
                    ProblemDetailActivity.newIntent(
                        requireContext(),
                        ProblemDetailMode.Detail,
                        problem.toModel()
                    ),
                )
            }
        }
    }
    private val searchedProblemsHeaderAdapter: CommonNoteHeaderAdapter by lazy {
        CommonNoteHeaderAdapter().apply {
            headerTitle = getString(R.string.searched_problem)
            isShowing = false
        }
    }
    private val searchedProblemsAdapter: NoteAdapter by lazy {
        NoteAdapter().also { adapter ->
            adapter.onProblemClick = { problem ->
                startActivity(
                    ProblemDetailActivity.newIntent(
                        requireContext(),
                        ProblemDetailMode.Detail,
                        problem.toModel()
                    ),
                )
            }
            adapter.isShowing = false
        }
    }
    private val scrollToTopAdapter: ScrollToTopAdapter by lazy {
        ScrollToTopAdapter().also { adapter ->
            adapter.onScrollToTopClick = {
                binding.appbar.setExpanded(true, true)
                binding.recyclerView.scrollToPosition(0)
            }
            adapter.isShowing = false
        }
    }
    private val adapter: ConcatAdapter by lazy {
        ConcatAdapter(
            wrongNoteHeaderAdapter,
            wrongNoteAdapter,
            totalNoteHeaderAdapter,
            totalNoteAdapter,
            searchedProblemsHeaderAdapter,
            searchedProblemsAdapter,
            scrollToTopAdapter
        ).also { adapter ->
            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                adapter.addAdapter(0, adNativeBannerOnNoteAdapter)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentNoteBinding.inflate(layoutInflater, container, false).also { _binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeViewModel()
    }

    private fun initView() {
        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.search -> {
                    when (bsSearchBehavior.state) {
                        BottomSheetBehavior.STATE_COLLAPSED -> {
                            bsSearchBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                        }
                        else -> Unit
                    }
                    true
                }
                else -> false
            }
        }

        bsSearchBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        binding.fabCloseBsSearch.invisible()
                        hideKeyboard()
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        binding.fabCloseBsSearch.show()
                        binding.bsSearch.etSearch.showKeyboard()
                    }
                    else -> Unit
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) = Unit
        })

        binding.fabCloseBsSearch.setOnThrottleClick {
            bsSearchBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        with(binding.bsSearch) {
            etSearch.textChanges()
                .map { it?.toString() ?: "" }
                .debounce(300L)
                .onEach { viewModel.updateUserInput(it) }
                .launchIn(lifecycleScope)

            etSearch.setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE -> {
                        bsSearchBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                        true
                    }
                    else -> false
                }
            }

            ivClear.setOnThrottleClick {
                etSearch.text.clear()
            }
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 20.toDp()) {
                    if (bsSearchBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                        bsSearchBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                    }
                }
            }
        })

        binding.chipSearchOff.setOnThrottleClick {
            binding.bsSearch.etSearch.text.clear()
        }

        binding.chipFilterYear.setOnThrottleClick {
            newInstance<YearFilterDialogFragment>(
                Constants.icon to R.drawable.ic_filter,
                Constants.title to getString(R.string.year),
                Constants.description to getString(R.string.choose_filtered_years),
                Constants.selectableTextItem to viewModel.selectableFilteredYears
            ).show(childFragmentManager, YearFilterDialogFragment::class.java.simpleName)
        }

        binding.chipFilterType.setOnThrottleClick {
            newInstance<QuizTypeFilterDialogFragment>(
                Constants.icon to R.drawable.ic_filter,
                Constants.title to getString(R.string.quiz_type),
                Constants.description to getString(R.string.choose_filtered_types),
                Constants.selectableTextItem to viewModel.selectableFilteredTypes
            ).show(childFragmentManager, QuizTypeFilterDialogFragment::class.java.simpleName)
        }

        binding.chipFilterClear.setOnThrottleClick {
            viewModel.setFilter(years = Problem.allYears(), types = QuizType.all())
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    viewModel.uiState.collectLatest { state ->
                        when (val totalProblemsState = state.totalProblemsUiState) {
                            is TotalProblemsUiState.Success -> {
                                val problems = totalProblemsState.data.map { problem ->
                                    UserSolvedProblemModel(problem = problem)
                                }
                                totalNoteAdapter.submitList(problems)
                            }
                            is TotalProblemsUiState.Error -> {
                                // TODO
                            }
                            is TotalProblemsUiState.Loading -> {
                                // TODO
                            }
                        }

                        when (val wrongProblemsState = state.wrongProblemsUiState) {
                            is WrongProblemsUiState.Success -> {
                                val problems = wrongProblemsState.data.map { problem ->
                                    UserSolvedProblemModel(problem = problem)
                                }
                                wrongNoteAdapter.submitList(problems)
                            }
                            is WrongProblemsUiState.Error -> {
                                // TODO
                            }
                            is WrongProblemsUiState.Loading -> {
                                // TODO
                            }
                        }

                        when (val searchedProblemsState = state.searchedProblemsUiState) {
                            is SearchedProblemsUiState.Success -> {
                                val problems = searchedProblemsState.data.map { problem ->
                                    UserSolvedProblemModel(problem = problem)
                                }
                                searchedProblemsAdapter.submitList(problems)
                            }
                            is SearchedProblemsUiState.Error -> {
                                // TODO
                            }
                            is SearchedProblemsUiState.Loading -> {
                                // TODO
                            }
                        }

                        when (state.userActionUiState) {
                            is UserActionUiState.OnViewing -> {
                                binding.toolbar.menu.findItem(R.id.search).iconTintList =
                                    color(R.color.daynight_gray700s)
                                binding.chipSearchOff.gone()
                                binding.layoutFilter.actionWithChild { isEnabled = true }

                                wrongNoteAdapter.show()
                                totalNoteHeaderAdapter.show()
                                totalNoteAdapter.show()
                                searchedProblemsHeaderAdapter.hide()
                                searchedProblemsAdapter.hide()
                            }
                            is UserActionUiState.OnSearching -> {
                                binding.toolbar.menu.findItem(R.id.search).iconTintList =
                                    color(R.color.daynight_pastel_blue)
                                binding.chipSearchOff.visible()
                                binding.layoutFilter.actionWithChild { isEnabled = false }

                                wrongNoteAdapter.hide()
                                totalNoteHeaderAdapter.hide()
                                totalNoteAdapter.hide()
                                searchedProblemsHeaderAdapter.show()
                                searchedProblemsAdapter.show()
                            }
                        }
                    }
                }

                launch {
                    viewModel.showWrongNoteHeader.collectLatest { isShowing ->
                        wrongNoteHeaderAdapter.showOrHide(isShowing)
                    }
                }

                launch {
                    viewModel.showScrollToTop.collectLatest { isShowing ->
                        scrollToTopAdapter.showOrHide(isShowing)
                    }
                }

                launch {
                    viewModel.isYearFiltering.collectLatest { isYearFiltering ->
                        binding.chipFilterYear.bindFiltering(isYearFiltering)
                    }
                }

                launch {
                    viewModel.isQuizTypeFiltering.collectLatest { isQuizTypeFiltering ->
                        binding.chipFilterType.bindFiltering(isQuizTypeFiltering)
                    }
                }

                viewModel.uiEvent.collect { event ->
                    handleEvent(event)
                }
            }
        }
    }

    private fun handleEvent(event: NoteUiEvent) {
        Timber.d("NoteUiEvent: $event")
        when (event) {
            is NoteUiEvent.ShowSnackbar -> {
                Snackbar.make(binding.root, event.message, Snackbar.LENGTH_SHORT).apply {
                    setAction(R.string.confirm) { dismiss() }
                }.show()
            }
        }
    }

    private fun Chip.bindFiltering(isFiltering: Boolean) {
        if (isFiltering) {
            chipBackgroundColor = context.colorStateList(R.color.primaryColor_0_15)
            chipStrokeColor = context.colorStateList(R.color.primaryColor)
        } else {
            chipBackgroundColor = context.colorStateList(R.color.daynight_gray070s)
            chipStrokeColor = context.colorStateList(R.color.daynight_gray300s)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
