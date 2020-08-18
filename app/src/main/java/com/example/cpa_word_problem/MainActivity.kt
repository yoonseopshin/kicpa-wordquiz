package com.example.cpa_word_problem

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private var backKeyPressedTime: Long = 0
    val data = ArrayList<AccountingData>()
    var startYear = 0
    var endYear = 0
    val wrongProblems = LinkedHashSet<AccountingData>()
    lateinit var preferenceManager: PreferenceManager

    companion object {
        val TAB_ICON_LIST = arrayListOf(R.drawable.ic_home,
            R.drawable.ic_quiz,
            R.drawable.ic_study,
            R.drawable.ic_setting)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        startYear = getString(R.string.START_YEAR).toInt()
        endYear = getString(R.string.END_YEAR).toInt()

        loadJson()

        viewPager2.adapter = MyFragmentStateAdapter(this)
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.setIcon(TAB_ICON_LIST[position])
        }.attach()

        preferenceManager = PreferenceManager(this)
    }

    private fun loadJson() {
        val assetManager = resources.assets
        val inputStream = assetManager.open("accounting.json")
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        val jsonObject = JSONObject(jsonString)

        for (year in startYear..endYear) {
            val jsonArray = jsonObject.getJSONArray("$year")
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                val pid = obj.getInt("pid")
                val description = obj.getString("description")
                val p1 = obj.getString("p1")
                val p2 = obj.getString("p2")
                val p3 = obj.getString("p3")
                val p4 = obj.getString("p4")
                val p5 = obj.getString("p5")
                val answer = obj.getInt("answer")
                val accountingData = AccountingData(pid, year, description, p1, p2, p3, p4, p5, answer)
                data.add(accountingData)
            }
        }
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis()
            Toast.makeText(this, "한 번 더 누르면 앱을 종료합니다.", Toast.LENGTH_SHORT).show()
            return
        }
        else {
            moveTaskToBack(true)
            finish()
        }
    }

}