package com.martkans.bmi.logic

class BmiForKgCm(var mass: Int = 0, var height: Int = 0) : Bmi {

    override fun countBmi(): Double? {

        if (mass == 0 || height == 0) return null

        return mass*10000.0 / (height*height)
    }
}