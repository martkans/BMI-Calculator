package com.martkans.bmi

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import com.martkans.bmi.logic.Bmi
import com.martkans.bmi.logic.BmiForKgCm
import com.martkans.bmi.logic.BmiForLbIn

import kotlinx.android.synthetic.main.activity_main.*
import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.martkans.bmi.logic.BmiResult
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    companion object {
        const val KEY_BMI_VALUE: String = "bmiValue"
        const val KEY_BMI_RANGE: String = "bmiRange"
        const val SHARED_PREF_NAME: String = "historySharedPref"
        const val KEY_SHARED_PREF: String = "history"

        private const val KEY_BMI_RESULT_COLOR: String = "bmiResultColor"
        private const val KEY_IMPERIAL_UNITS_FLAG: String = "imperialUnitsFlag"

        private const val UPPER_HEIGHT_LIMIT_CM: Double = 300.0
        private const val LOWER_HEIGHT_LIMIT_CM: Double = 100.0

        private const val UPPER_MASS_LIMIT_KG: Double = 300.0
        private const val LOWER_MASS_LIMIT_KG: Double = 30.0

        private const val CM_TO_IN_MULTIPLIER: Double = 0.39370079
        private const val KG_TO_LB_MULTIPLIER: Double = 2.20462262

        private const val INPUT_HEIGHT_CATEGORY_NAME: String = "height"
        private const val INPUT_MASS_CATEGORY_NAME: String = "mass"
    }

    var bmi: Bmi = BmiForKgCm()

    private var upperHeightLimit: Double = UPPER_HEIGHT_LIMIT_CM
    private var lowerHeightLimit: Double = LOWER_HEIGHT_LIMIT_CM

    private var upperMassLimit: Double = UPPER_MASS_LIMIT_KG
    private var lowerMassLimit: Double = LOWER_MASS_LIMIT_KG

    private var isImperialUnits: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        countBtn.setOnClickListener{
            countBmi()
        }

        infoIB.setOnClickListener{
            getInfoAboutYourBmiRange()
        }
    }

    private fun countBmi(){
        this.bmi.height = getAndValidateInput(heightET, lowerHeightLimit, upperHeightLimit, INPUT_HEIGHT_CATEGORY_NAME)
        this.bmi.mass = getAndValidateInput(massET, lowerMassLimit, upperMassLimit, INPUT_MASS_CATEGORY_NAME)

        if(showResults()){
            saveResult()
        }
    }

    private fun showResults(): Boolean{

        if(bmi.countBmi() != 0.0){
            val bmiRangeDescription = bmiLevel()

            yourBMITV.setTextColor(ContextCompat.getColor(this, bmiRangeDescription.second))
            yourBMITV.text = String.format("%.2f", bmi.countBmi())

            yourBMIrangeTV.setText(bmiRangeDescription.first)

            infoIB.visibility = View.VISIBLE

            return true
        } else {
            infoIB.visibility = View.INVISIBLE
            return false
        }
    }

    private fun saveResult(){

        val result = BmiResult(yourBMITV.text.toString(), this.bmi.height.toString(),
            this.bmi.mass.toString(), Date(), yourBMIrangeTV.currentTextColor, isImperialUnits)

        val sharedPref = this.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val newHistory = ArrayList<BmiResult>()

        if(sharedPref.getString(KEY_SHARED_PREF, null) != null){

            val typeToken = object : TypeToken<ArrayList<BmiResult>>() {}
            val oldHistory: ArrayList<BmiResult> = Gson().fromJson<ArrayList<BmiResult>>(sharedPref.getString(KEY_SHARED_PREF, ""),
                typeToken.type)

            newHistory.add(result)

            if(oldHistory.size == 10){
                for(i in 0..8)
                    newHistory.add(oldHistory[i])
            } else {
                for (i in 0 until oldHistory.size)
                    newHistory.add(oldHistory[i])
            }

        } else {
            newHistory.add(result)
        }

        val editor = sharedPref.edit()
        editor.putString(KEY_SHARED_PREF, Gson().toJson(newHistory))
        editor.apply()

    }

    private fun getAndValidateInput(input: EditText, lowerLimit: Double, upperLimit: Double, inputCategory: String): Double{

        if(input.text.isEmpty() || input.text.toString().toDouble() < lowerLimit || input.text.toString().toDouble() > upperLimit){
            input.error = "Provide valid $inputCategory value!"
            return 0.0
        }

        return input.text.toString().toDouble()
    }

    private fun bmiLevel(): Pair<Int, Int> {
        val bmiVal = bmi.countBmi()

        return when {
            bmiVal == 0.0   -> Pair(0, 0)
            bmiVal < 18.5   -> Pair(R.string.bmi_main_range_underweight, R.color.pompeianRoses)
            bmiVal <= 24.9  -> Pair(R.string.bmi_main_range_healthy, R.color.verdigris)
            bmiVal <= 29.9  -> Pair(R.string.bmi_main_range_overweight, R.color.lapisLazuli)
            bmiVal <= 34.9  -> Pair(R.string.bmi_main_range_obesity, R.color.cobaltic)
            else            -> Pair(R.string.bmi_main_range_severe_obesity, R.color.blueberry)
        }

    }

    private fun getInfoAboutYourBmiRange(){
        val infoIntent = Intent(this, InfoActivity::class.java).apply {
            putExtra(KEY_BMI_VALUE, yourBMITV.text)
            putExtra(KEY_BMI_RANGE, yourBMIrangeTV.text)
        }
        startActivity(infoIntent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onMenuOpened(featureId: Int, menu: Menu?): Boolean {
        if(isImperialUnits){
            menu?.findItem(R.id.changeUnitsMI)?.title = getString(R.string.bmi_menu_si_units)
        }

        return super.onMenuOpened(featureId, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when (id) {
            R.id.aboutMI -> showAbout()
            R.id.changeUnitsMI -> changeUnits(item)
            R.id.historyMI -> showHistory()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showAbout(): Boolean {
        val aboutIntent = Intent(this, AboutActivity::class.java)
        startActivity(aboutIntent)

        return true
    }

    private fun changeUnits(item: MenuItem): Boolean {
        if(item.title == getString(R.string.bmi_menu_imperial_units)){
            isImperialUnits = true
            item.title = getString(R.string.bmi_menu_si_units)
            changeToImperialUnits()
        } else {
            isImperialUnits = false
            item.title = getString(R.string.bmi_menu_imperial_units)
            changeToSIUnits()
        }

        clearToStartingState()

        return true
    }

    private fun changeToImperialUnits(){
        bmi = BmiForLbIn()
        heightTV.text = getString(R.string.bmi_main_height_in)
        massTV.text = getString(R.string.bmi_main_mass_lb)


        upperHeightLimit = UPPER_HEIGHT_LIMIT_CM*CM_TO_IN_MULTIPLIER
        lowerHeightLimit = LOWER_HEIGHT_LIMIT_CM*CM_TO_IN_MULTIPLIER

        upperMassLimit = UPPER_MASS_LIMIT_KG*KG_TO_LB_MULTIPLIER
        lowerMassLimit = LOWER_MASS_LIMIT_KG*KG_TO_LB_MULTIPLIER
    }

    private fun changeToSIUnits(){

        bmi = BmiForKgCm()
        heightTV.text = getString(R.string.bmi_main_height_cm)
        massTV.text = getString(R.string.bmi_main_mass_kg)


        upperHeightLimit = UPPER_HEIGHT_LIMIT_CM
        lowerHeightLimit = LOWER_HEIGHT_LIMIT_CM

        upperMassLimit = UPPER_MASS_LIMIT_KG
        lowerMassLimit = LOWER_MASS_LIMIT_KG
    }

    private fun clearToStartingState(){

        heightET.text.clear()
        massET.text.clear()
        yourBMITV.text = ""
        yourBMIrangeTV.text = ""
        infoIB.visibility = View.INVISIBLE
    }

    private fun showHistory(): Boolean{
        val historyIntent = Intent(this, HistoryActivity::class.java)
        startActivity(historyIntent)

        return true
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState?.putString(KEY_BMI_VALUE, yourBMITV.text.toString())
        outState?.putString(KEY_BMI_RANGE, yourBMIrangeTV.text.toString())
        outState?.putInt(KEY_BMI_RESULT_COLOR, yourBMITV.currentTextColor)
        outState?.putBoolean(KEY_IMPERIAL_UNITS_FLAG, isImperialUnits)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        if (savedInstanceState != null) {
            if(savedInstanceState.getBoolean(KEY_IMPERIAL_UNITS_FLAG)){
                isImperialUnits = savedInstanceState.getBoolean(KEY_IMPERIAL_UNITS_FLAG)
                changeToImperialUnits()
            }
        }

        if(savedInstanceState?.getInt(KEY_BMI_RESULT_COLOR) != null){
            yourBMITV.setTextColor(savedInstanceState.getInt(KEY_BMI_RESULT_COLOR))
        }

        yourBMITV.text = savedInstanceState?.getString(KEY_BMI_VALUE)
        yourBMIrangeTV.text = savedInstanceState?.getString(KEY_BMI_RANGE)

        if(!savedInstanceState?.getString(KEY_BMI_VALUE).isNullOrBlank()){
            infoIB.visibility = View.VISIBLE
        } else {
            infoIB.visibility = View.INVISIBLE
        }
    }
}
