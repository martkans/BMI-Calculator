package com.martkans.bmi

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import com.martkans.bmi.logic.BmiForKgCm

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var bmi:BmiForKgCm = BmiForKgCm()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        countBtn.setOnClickListener{

            this.bmi.height = getAndValidateInput(heightET, 100, 300, "height")
            this.bmi.mass = getAndValidateInput(massET, 20, 300, "mass")

            showResults()
        }
    }

    private fun getAndValidateInput(input: EditText, lowerLimit: Int, higherLimit:Int, inputCategory:String):Int{

        if(input.text.isEmpty() || input.text.toString().toInt() < lowerLimit || input.text.toString().toInt() > higherLimit){
            Toast.makeText(this, "Provide valid $inputCategory value!", Toast.LENGTH_SHORT).show()
            return 0
        }

        return input.text.toString().toInt()
    }

    private fun showResults(){

        yourBMITV.text = if (bmi.countBmi() == null) "" else String.format("%.2f", bmi.countBmi())
        yourBMIrangeTV.text = bmiLevel()
    }

    private fun bmiLevel() : String{
        val bmiVal = bmi.countBmi()

        when {
            bmiVal == null   -> return ""
            bmiVal < 18.5   -> return "UNDERWEIGHT"
            bmiVal <= 24.9  -> return "HEALTHY"
            bmiVal <= 29.9  -> return "OVERWEIGHT"
            bmiVal <= 34.9  -> return "OBESITY"
            else            -> return "SEVERE OBESITY"
        }

    }


    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState?.putString("yourBMITV", yourBMITV.text.toString())
        outState?.putString("yourBMIrangeTV", yourBMIrangeTV.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        yourBMITV.text = savedInstanceState?.getString("yourBMITV")
        yourBMIrangeTV.text = savedInstanceState?.getString("yourBMIrangeTV")
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

            item.isChecked = !item.isChecked
            Toast.makeText(this, "Imperial units Clicked", Toast.LENGTH_LONG).show()
            return true
        }

        if(id == R.id.historyMI) {

            Toast.makeText(this, "History Clicked", Toast.LENGTH_LONG).show()
            return true
        }

        return super.onOptionsItemSelected(item)

    }
}
