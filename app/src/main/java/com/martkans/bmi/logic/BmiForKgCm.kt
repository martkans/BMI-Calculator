package com.martkans.bmi.logic

class BmiForKgCm(override var mass: Double = 0.0, override var height: Double = 0.0) : Bmi {

    override fun countBmi(): Double {

        if (mass == 0.0 || height == 0.0) return 0.0

        return mass*10000.0 / (height*height)
    }
}