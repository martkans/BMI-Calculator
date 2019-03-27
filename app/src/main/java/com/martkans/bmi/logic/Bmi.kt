package com.martkans.bmi.logic

interface Bmi {
    var height:Double
    var mass:Double
    fun countBmi() : Double
}