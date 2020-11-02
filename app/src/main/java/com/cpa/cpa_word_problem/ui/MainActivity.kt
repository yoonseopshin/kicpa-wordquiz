package com.cpa.cpa_word_problem.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModelProvider
import com.cpa.cpa_word_problem.R
import com.cpa.cpa_word_problem.adapters.MyFragmentStateAdapter
import com.cpa.cpa_word_problem.data.ProblemType
import com.cpa.cpa_word_problem.viewmodels.MainViewModel
import com.google.android.material.tabs.TabLayoutMediator
import com.kakao.adfit.ads.AdListener
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
        viewModel.apply {
            loadProblemSetFromAssets(ProblemType.Accounting)
            loadProblemSetFromAssets(ProblemType.Business)
        }
        setViewPager()
        setAdView()
    }

    private fun setViewPager() {
        viewPager2.adapter = MyFragmentStateAdapter(this)
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.setIcon(tabIconList[position])
        }.attach()
    }

    private fun setAdView() {
        adView.setClientId("DAN-qxqjtd0wo5dd")
        adView.setAdListener(object : AdListener {
            override fun onAdLoaded() {
                Log.d("banner", "Ad banner loaded")
            }

            override fun onAdFailed(errorCode: Int) {
                Log.e("banner", "$errorCode")
            }

            override fun onAdClicked() {
                Log.d("banner", "Ad banner clicked")
            }
        })

        lifecycle.addObserver(object: LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
            fun onResume() {
                adView.resume()
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            fun onPause() {
                adView.pause()
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy() {
                adView.destroy()
            }
        })
        adView.loadAd()
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