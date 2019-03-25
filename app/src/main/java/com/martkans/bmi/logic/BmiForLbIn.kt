package com.martkans.bmi.logic

class BmiForLbIn(override var mass: Double = 0.0, override var height: Double = 0.0) : Bmi {

    override fun countBmi(): Double? {
        if (mass == 0.0 || height == 0.0) return null

        return mass*703.0 / (height*height)
    }
}