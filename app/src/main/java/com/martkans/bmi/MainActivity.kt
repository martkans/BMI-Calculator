package com.martkans.bmi

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        countBtn.setOnClickListener{
            val height = heightInput.text.toString()
            val mass = massInput.text.toString()
            d("Height: ", height)
            d("Mass: ", mass)

        }
    }
}
