package com.martkans.bmi

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.martkans.bmi.logic.Bmi
import com.martkans.bmi.logic.BmiForKgCm
import com.martkans.bmi.logic.BmiForLbIn

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val upperHeightLimitCm: Double = 300.0
    private val lowerHeightLimitCm: Double = 100.0

    private val upperMassLimitKg: Double = 300.0
    private val lowerMassLimitKg: Double = 30.0

    private val cmToInMultiplier: Double = 0.39370079
    private val kgToLbMultiplier: Double = 2.20462262

    var bmi: Bmi = BmiForKgCm()

    private var upperHeightLimit: Double = upperHeightLimitCm
    private var lowerHeightLimit: Double = lowerHeightLimitCm

    private var upperMassLimit: Double = upperMassLimitKg
    private var lowerMassLimit: Double = lowerMassLimitKg

    private var isImperialUnits: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        countBtn.setOnClickListener{

            this.bmi.height = getAndValidateInput(heightET, lowerHeightLimit, upperHeightLimit, "height")
            this.bmi.mass = getAndValidateInput(massET, lowerMassLimit, upperMassLimit, "mass")

            showResults()
        }
    }

    private fun getAndValidateInput(input: EditText, lowerLimit: Double, upperLimit: Double, inputCategory: String): Double{

        if(input.text.isEmpty() || input.text.toString().toDouble() < lowerLimit || input.text.toString().toDouble() > upperLimit){
            Toast.makeText(this, "Provide valid $inputCategory value!", Toast.LENGTH_SHORT).show()
            return 0.0
        }

        return input.text.toString().toDouble()
    }

    private fun showResults(){

        val bmiRangeDescription = bmiLevel()

        yourBMITV.setTextColor(ContextCompat.getColor(this, bmiRangeDescription.second))
        yourBMITV.text = if (bmi.countBmi() == null) "" else String.format("%.2f", bmi.countBmi())

        yourBMIrangeTV.setText(bmiRangeDescription.first)

        if(bmi.countBmi() != null){
            infoIB.visibility = View.VISIBLE
        } else {
            infoIB.visibility = View.INVISIBLE
        }
    }

    private fun bmiLevel(): Pair<Int, Int> {
        val bmiVal = bmi.countBmi()

        when {
            bmiVal == null   -> return Pair(0, 0)
            bmiVal < 18.5   -> return Pair(R.string.bmi_main_range_underweight, R.color.rozPompejski)
            bmiVal <= 24.9  -> return Pair(R.string.bmi_main_range_healthy, R.color.grynszpan)
            bmiVal <= 29.9  -> return Pair(R.string.bmi_main_range_overweight, R.color.lapisLazuli)
            bmiVal <= 34.9  -> return Pair(R.string.bmi_main_range_obesity, R.color.kobaltowy)
            else            -> return Pair(R.string.bmi_main_range_severe_obesity, R.color.jagodowy)
        }

    }


    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState?.putString("yourBMITV", yourBMITV.text.toString())
        outState?.putString("yourBMIrangeTV", yourBMIrangeTV.text.toString())
        outState?.putInt("bmiResultColor", yourBMITV.currentTextColor)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        if(savedInstanceState?.getInt("bmiResultColor") != null){
            yourBMITV.setTextColor(savedInstanceState.getInt("bmiResultColor"))
        }

        yourBMITV.text = savedInstanceState?.getString("yourBMITV")
        yourBMIrangeTV.text = savedInstanceState?.getString("yourBMIrangeTV")

        if(savedInstanceState?.getString("yourBMITV") != null){
            infoIB.visibility = View.VISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        val id = item.itemId

        if (id == R.id.aboutMI) {
            Toast.makeText(this, "About Clicked", Toast.LENGTH_LONG).show()
            return true
        }

        if (id == R.id.imperialMI) {

            if(item.title == getString(R.string.bmi_menu_imperial_units)){
                isImperialUnits = true
                item.title = getString(R.string.bmi_menu_si_units)
                changeToImperialUnits()
            } else {
                isImperialUnits = false
                item.title = getString(R.string.bmi_menu_imperial_units)
                changeToSIUnits()
            }


            heightET.text.clear()
            massET.text.clear()
            yourBMITV.text = ""
            yourBMIrangeTV.text = ""
            infoIB.visibility = View.INVISIBLE

            return true
        }

        if(id == R.id.historyMI) {

            Toast.makeText(this, "History Clicked", Toast.LENGTH_LONG).show()
            return true
        }

        return super.onOptionsItemSelected(item)

    }

    private fun changeToImperialUnits(){
        bmi = BmiForLbIn()
        heightTV.text = getString(R.string.bmi_main_height_in)
        massTV.text = getString(R.string.bmi_main_mass_lb)


        upperHeightLimit = upperHeightLimitCm*cmToInMultiplier
        lowerHeightLimit = lowerHeightLimitCm*cmToInMultiplier

        upperMassLimit = upperMassLimitKg*kgToLbMultiplier
        lowerMassLimit = lowerMassLimitKg*kgToLbMultiplier
    }

    private fun changeToSIUnits(){

        bmi = BmiForKgCm()
        heightTV.text = getString(R.string.bmi_main_height_cm)
        massTV.text = getString(R.string.bmi_main_mass_kg)


        upperHeightLimit = upperHeightLimitCm
        lowerHeightLimit = lowerHeightLimitCm

        upperMassLimit = upperMassLimitKg
        lowerMassLimit = lowerMassLimitKg
    }
}
