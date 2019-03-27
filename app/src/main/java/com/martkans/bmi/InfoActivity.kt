package com.martkans.bmi

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_info.*

class InfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        val bmiValueText = getString(R.string.bmi_info_bmi_value) + "\n" + intent.getStringExtra("bmiValue")
        val bmiDescriptionText = intent.getStringExtra("bmiRange") + " " + getString(R.string.bmi_lorem_ipsum)

        bmiValueET.text = bmiValueText
        bmiDescriptionET.text = bmiDescriptionText
    }
}
