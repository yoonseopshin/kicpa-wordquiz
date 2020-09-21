package com.cpa.cpa_word_problem.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.room.Room
import com.cpa.cpa_word_problem.R
import com.cpa.cpa_word_problem.data.*
import org.json.JSONObject

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val startYear = application.resources.getString(R.string.START_YEAR).toInt()
    val endYear = application.resources.getString(R.string.END_YEAR).toInt()
    var turn = 1
    var probSize = 0
    private val preferenceManager = PreferenceManager(application)
    private val accountingData = ArrayList<ProblemData>()
    private val businessData = ArrayList<ProblemData>()
    private val db = Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        ProblemContract.ProblemEntity.TABLE_NAME
    ).addMigrations(AppMigration.MIGRATION_1_2).build()
    private val wrongProblems = LinkedHashSet<ProblemData>()
    var backKeyPressedTime: Long = 0
    val problemToPosition = hashMapOf(3 to 0, 5 to 1, 7 to 2, 10 to 3)

    fun getRandomProblem(option: QuizOption): ProblemData {
        val candidate = arrayListOf<ProblemData>()
        for (i in accountingData) {
            if (i.year in option.years) {
                candidate.add(i)
            }
        }
        return candidate.random()
    }

    fun isWrongProblemExist() = wrongProblems.isNotEmpty()

    fun addWrongProblems(wrongProblems: ArrayList<ProblemData>) =
        this.wrongProblems.addAll(wrongProblems)

    fun getWrongProblemList() = wrongProblems.toList()

    fun clearWrongProblems() = wrongProblems.clear()

    fun getProblemDao() = db.problemDao()

    fun getSelectedYear() = preferenceManager.getSelectedYear()

    fun getSelectedProblemSize() = preferenceManager.getSelectedProblemSize()

    fun setProblemSize(problemSize: Int) = preferenceManager.setSelectedProblemSize(problemSize)

    fun setSelectedYear(yearBitSet: Int) = preferenceManager.setSelectedYear(yearBitSet)

    fun setQuizEffect(isTurnOn: Boolean) = preferenceManager.setQuizEffect(isTurnOn)

    fun isQuizEffectOn() = preferenceManager.getQuizEffect()

    fun loadJson(type: ProblemType) {
        val assetManager = getApplication<Application>().resources.assets
        val inputStream = when (type) {
            ProblemType.Accounting -> assetManager.open("accounting.json")
            ProblemType.Business -> assetManager.open("business.json")
        }
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
                val data = ProblemData(pid, year, description, p1, p2, p3, p4, p5, answer, type.toString())
                when (type) {
                    ProblemType.Accounting -> accountingData.add(data)
                    ProblemType.Business -> businessData.add(data)
                }
            }
        }
    }

}