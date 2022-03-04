package com.cpa.cpa_word_problem.feature.quiz.presentation.screen.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.cpa.cpa_word_problem.R
import com.cpa.cpa_word_problem.base.BaseActivity
import com.cpa.cpa_word_problem.databinding.ActivityMainBinding
import com.cpa.cpa_word_problem.feature.quiz.presentation.util.Constants
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        syncRemoteProblems()
        parseIntent()
    }

    private fun initView() {
        // TODO: 설정 페이지 완성 후 추가
//        val tabIcons = listOf(R.drawable.ic_home, R.drawable.ic_note, R.drawable.ic_settings)
        val tabIcons = listOf(R.drawable.ic_home, R.drawable.ic_note)

        with(binding) {
            viewPager.adapter = MainFragmentStateAdapter(
                fragmentManager = supportFragmentManager,
                lifecycle = lifecycle
            )
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.setIcon(tabIcons[position])
            }.attach()
            viewPager.isUserInputEnabled = false
        }
    }

    private fun syncRemoteProblems() {
        viewModel.syncRemoteProblems()
    }

    private fun parseIntent() {
        intent?.extras?.let { extras ->
            (extras.getSerializable(Constants.destination) as? MainTab)?.let { tab ->
                binding.viewPager.currentItem = tab.ordinal
            }
        }
    }

    override fun onBackPressed() {
        if (binding.tabLayout.selectedTabPosition != HOME_PAGE_INDEX) {
            binding.viewPager.setCurrentItem(HOME_PAGE_INDEX, true)
            return
        }

        super.onBackPressed()
    }

    companion object {
        fun newIntent(context: Context, destination: MainTab = MainTab.Home) =
            Intent(context, MainActivity::class.java).apply {
                putExtra(Constants.destination, destination)
            }
    }

}