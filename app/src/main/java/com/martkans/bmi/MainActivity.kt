package com.martkans.bmi

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.martkans.bmi.logic.Bmi
import com.martkans.bmi.logic.BmiForKgCm

import kotlinx.android.synthetic.main.activity_main.*
import java.math.RoundingMode
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        countBtn.setOnClickListener{
            val height = heightInput.text.toString()
            val mass = massInput.text.toString()

            val df = DecimalFormat("#.##")
            df.roundingMode = RoundingMode.HALF_EVEN
            val bmi = BmiForKgCm(mass.toInt(), height.toInt())

            yourBMITxtView.text = df.format(bmi.countBmi()).toString()
            yourBMIrangeTxtView.text = bmiLevel(bmi)

        }
    }


    private fun bmiLevel(bmi: Bmi) : String{
        val bmiVal = bmi.countBmi()

        if (bmiVal < 18.5)
            return "UNDERWEIGHT"
        else if (bmiVal <= 24.9)
            return "HEALTHY"
        else if (bmiVal <= 29.9)
            return "OVERWEIGHT"
        else if (bmiVal <= 34.9)
            return "OBESITY"
        else
            return "SEVERE OBESITY"

    }
}
