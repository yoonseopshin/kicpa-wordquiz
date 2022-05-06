package com.cpa.cpa_word_problem.feature.quiz.presentation.screen.main.note

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cpa.cpa_word_problem.R
import com.ysshin.shared.base.BaseFragment
import com.cpa.cpa_word_problem.databinding.FragmentNoteBinding
import com.cpa.cpa_word_problem.feature.quiz.presentation.adapter.*
import com.cpa.cpa_word_problem.feature.quiz.presentation.mapper.toModel
import com.cpa.cpa_word_problem.feature.quiz.presentation.model.UserSolvedProblemModel
import com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.ProblemDetailActivity
import com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.ProblemDetailMode
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ysshin.shared.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NoteFragment : BaseFragment() {

    private var _binding: FragmentNoteBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val viewModel: NoteViewModel by viewModels()
    private val bsSearchBehavior: BottomSheetBehavior<ConstraintLayout> by lazy {
        BottomSheetBehavior.from(binding.bsSearch.layout)
    }

    private val adNativeBannerAboveWrongNoteAdapter: AdNativeBannerAdapter by lazy { AdNativeBannerAdapter() }
    private val wrongNoteHeaderAdapter: CommonNoteHeaderAdapter by lazy {
        CommonNoteHeaderAdapter().apply {
            headerTitle = getString(R.string.wrong_note)
            isToggleable = true
            onHeaderClick = {
                viewModel.toggleWrongNote()
            }
            onHeaderLongClick = {
                MaterialAlertDialogBuilder(requireActivity())
                    .setMessage("모든 오답문제를 삭제하시겠습니까?")
                    .setPositiveButton("확인") { _, _ ->
                        viewModel.deleteAllWrongProblems()
                    }
                    .setNegativeButton("취소") { _, _ -> }
                    .create().show()
            }
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
                MaterialAlertDialogBuilder(requireActivity())
                    .setMessage("선택한 오답문제를 삭제하시겠습니까?")
                    .setPositiveButton("확인") { _, _ ->
                        viewModel.deleteWrongProblem(problem.year, problem.pid, problem.type)
                    }
                    .setNegativeButton("취소") { _, _ -> }
                    .create().show()
            }
        }
    }
    private val totalNoteHeaderAdapter: CommonNoteHeaderAdapter by lazy {
        CommonNoteHeaderAdapter().apply {
            headerTitle = getString(R.string.total_note)
            isToggleable = true
            onHeaderClick = {
                viewModel.toggleTotalNote()
            }
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
                binding.recyclerView.scrollToPosition(0)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentNoteBinding.inflate(layoutInflater, container, false).also {
        _binding = it
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }.root

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

        bsSearchBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
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
        bsSearchBehavior.peekHeight = 0

        binding.fabCloseBsSearch.setOnThrottleClick {
            bsSearchBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        with(binding.bsSearch) {
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

        binding.recyclerView.adapter = ConcatAdapter(
            adNativeBannerAboveWrongNoteAdapter,
            wrongNoteHeaderAdapter,
            wrongNoteAdapter,
            totalNoteHeaderAdapter,
            totalNoteAdapter,
            searchedProblemsHeaderAdapter,
            searchedProblemsAdapter,
            scrollToTopAdapter
        )

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
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    viewModel.wrongProblems.collectLatest { wrongProblems ->
                        wrongProblems.map { wrongProblem ->
                            UserSolvedProblemModel(problem = wrongProblem)
                        }.let { list ->
                            wrongNoteHeaderAdapter.showOrHide(list.isNotEmpty())
                            wrongNoteAdapter.submitList(list)
                        }
                    }
                }

                launch {
                    viewModel.problems.collectLatest { problems ->
                        totalNoteAdapter.submitList(problems.map { problem ->
                            UserSolvedProblemModel(problem = problem)
                        })
                    }
                }

                launch {
                    viewModel.userInputText.collectLatest { userInputText ->
                        if (userInputText.isBlank()) {
                            binding.toolbar.menu.findItem(R.id.search).iconTintList =
                                color(R.color.daynight_gray700s)

                            wrongNoteHeaderAdapter.showOrHide(viewModel.wrongProblems.value.isNotEmpty())
                            wrongNoteAdapter.show()
                            totalNoteHeaderAdapter.show()
                            totalNoteAdapter.show()
                            searchedProblemsHeaderAdapter.hide()
                            searchedProblemsAdapter.hide()
                            scrollToTopAdapter.show()
                        } else {
                            binding.toolbar.menu.findItem(R.id.search).iconTintList =
                                color(R.color.daynight_pastel_blue)

                            viewModel.search(userInputText.trim())
                            wrongNoteHeaderAdapter.hide()
                            wrongNoteAdapter.hide()
                            totalNoteHeaderAdapter.hide()
                            totalNoteAdapter.hide()
                            searchedProblemsHeaderAdapter.show()
                            searchedProblemsAdapter.show()
                        }
                    }
                }

                launch {
                    viewModel.searchedProblems.collectLatest { problems ->
                        if (viewModel.isSearching().not()) return@collectLatest

                        if (problems.isEmpty()) {
                            scrollToTopAdapter.hide()
                        } else {
                            scrollToTopAdapter.showOrHide(problems.size > 15)
                        }

                        searchedProblemsAdapter.submitList(problems.map { problem ->
                            UserSolvedProblemModel(problem = problem)
                        })
                    }
                }

                launch {
                    viewModel.isWrongNoteOpened.collectLatest { isOpened ->
                        wrongNoteHeaderAdapter.isShowing = isOpened

                        if (isOpened) {
                            wrongNoteAdapter.submitList(viewModel.wrongProblems.value.map { problem ->
                                UserSolvedProblemModel(problem = problem)
                            })
                        } else {
                            wrongNoteAdapter.submitList(emptyList())
                        }
                    }
                }

                launch {
                    viewModel.isTotalNoteOpened.collectLatest { isOpened ->
                        totalNoteHeaderAdapter.isShowing = isOpened

                        if (isOpened) {
                            totalNoteAdapter.submitList(viewModel.problems.value.map { problem ->
                                UserSolvedProblemModel(problem = problem)
                            })
                        } else {
                            totalNoteAdapter.submitList(emptyList())
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
