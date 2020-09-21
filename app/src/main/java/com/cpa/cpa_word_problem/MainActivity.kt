package com.cpa.cpa_word_problem

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.cpa.cpa_word_problem.adapters.MyFragmentStateAdapter
import com.cpa.cpa_word_problem.databinding.ActivityMainBinding
import com.cpa.cpa_word_problem.viewmodels.MainViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    private val tabIconList = arrayListOf(
        R.drawable.ic_quiz,
        R.drawable.ic_study,
        R.drawable.ic_setting
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        viewModel = ViewModelProviders.of(this)[MainViewModel::class.java]
        binding.viewModel = viewModel

        viewModel.loadJson()

        viewPager2.adapter = MyFragmentStateAdapter(this)
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.setIcon(tabIconList[position])
        }.attach()
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() > viewModel.backKeyPressedTime + 2000) {
            viewModel.backKeyPressedTime = System.currentTimeMillis()
            Toast.makeText(this, "한 번 더 누르면 앱을 종료합니다.", Toast.LENGTH_SHORT).show()
            return
        } else {
            moveTaskToBack(true)
            finish()
        }
    }

}