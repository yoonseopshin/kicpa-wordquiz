package com.cpa.cpa_word_problem.presentation

import android.os.Bundle
import androidx.activity.viewModels
import com.cpa.cpa_word_problem.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.ysshin.cpaquiz.shared.android.base.BaseActivity
import com.ysshin.cpaquiz.shared.android.util.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        parseIntent()
    }

    private fun init() {
        with(binding) {
            viewPager.adapter = MainFragmentStateAdapter(
                    fragmentManager = supportFragmentManager,
                    lifecycle = lifecycle
            )
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.setIcon(MainTab.getIcon(position))
            }.attach()
        }
    }

    private fun parseIntent() {
        intent?.extras?.let { extras ->
            (extras.getSerializable(Constants.destination) as? MainTab)?.let { tab ->
                binding.viewPager.currentItem = tab.ordinal
            }
        }
    }

    override fun onBackPressed() {
        if (binding.tabLayout.selectedTabPosition != MainTab.Home.ordinal) {
            binding.viewPager.setCurrentItem(MainTab.Home.ordinal, true)
            return
        }

        super.onBackPressed()
    }

}