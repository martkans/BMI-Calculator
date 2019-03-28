package com.martkans.bmi


import android.support.test.espresso.Espresso.onView
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
class MainActivityDisplayingTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityDisplayingTest() {
        val heightTextView = onView(withId(R.id.heightHistoryTV))
        heightTextView.check(matches(isDisplayed()))

        val massTextView = onView(withId(R.id.heightHistoryTV))
        massTextView.check(matches(isDisplayed()))

        val countButton = onView(withId(R.id.countBtn))
        countButton.check(matches(isDisplayed()))
    }
}
