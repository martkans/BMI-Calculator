package com.martkans.bmi

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.martkans.bmi.logic.Bmi
import com.martkans.bmi.logic.BmiForKgCm

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var bmi:BmiForKgCm = BmiForKgCm()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        countBtn.setOnClickListener{

            this.bmi.height = getAndValidateInput(heightInput, 100, 300, "height")
            this.bmi.mass = getAndValidateInput(massInput, 20, 300, "mass")

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

        yourBMITxtView.text = if (bmi.countBmi() == null) "" else String.format("%.2f", bmi.countBmi())
        yourBMIrangeTxtView.text = bmiLevel()
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
}
