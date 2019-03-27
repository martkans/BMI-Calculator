package com.martkans.bmi


import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {

        val heightEditText = onView(withId(R.id.heightET))
        heightEditText.perform(replaceText("177"), closeSoftKeyboard())

        val massEditText = onView(withId(R.id.massET))
        massEditText.perform(replaceText("69"), closeSoftKeyboard())

        val countButton = onView(withId(R.id.countBtn))
        countButton.perform(click())

        val yourBmiTextView = onView(withId(R.id.yourBMITV))
        yourBmiTextView.check(matches(withText("22,02")))

        val yourBmiRangeTextView = onView(withId(R.id.yourBMIrangeTV))
        yourBmiRangeTextView.check(matches(withText("HEALTHY")))
    }
}
