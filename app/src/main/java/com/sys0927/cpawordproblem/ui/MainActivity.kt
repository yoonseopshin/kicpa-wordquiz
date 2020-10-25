package com.sys0927.cpawordproblem.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.sys0927.cpawordproblem.R
import com.sys0927.cpawordproblem.adapters.MyFragmentStateAdapter
import com.sys0927.cpawordproblem.data.ProblemType
import com.sys0927.cpawordproblem.viewmodels.MainViewModel
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
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.loadProblemSetFromAssets(ProblemType.Accounting)
        viewModel.loadProblemSetFromAssets(ProblemType.Business)

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