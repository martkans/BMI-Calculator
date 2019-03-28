package com.martkans.bmi.logic

import java.util.Date

data class BmiResult(val bmiVal: String, val height:String, val mass:String, val date: Date, val resultColor: Int, val isImperial: Boolean)