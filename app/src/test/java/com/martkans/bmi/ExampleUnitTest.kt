package com.martkans.bmi

import com.martkans.bmi.logic.BmiForKgCm
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun for_valid_data_return_bmi(){
        val bmi = BmiForKgCm(65, 170)
        assertEquals(22.491, bmi.countBmi(), 0.001)
    }
}
