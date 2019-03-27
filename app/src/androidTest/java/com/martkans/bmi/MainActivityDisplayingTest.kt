package com.martkans.bmi


import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import android.view.ViewGroup
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
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
        val heightTextView = onView(withId(R.id.heightTV))
        heightTextView.check(matches(isDisplayed()))

        val massTextView = onView(withId(R.id.massTV))
        massTextView.check(matches(isDisplayed()))

        val countButton = onView(withId(R.id.countBtn))
        countButton.check(matches(isDisplayed()))
    }
}
