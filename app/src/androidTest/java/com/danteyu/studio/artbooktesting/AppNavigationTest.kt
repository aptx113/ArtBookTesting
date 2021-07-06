/*
 * Copyright 2021 Dante Yu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.danteyu.studio.artbooktesting

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.danteyu.studio.artbooktesting.ui.searchArt.ImageViewHolder
import com.danteyu.studio.artbooktesting.utils.EspressoIdlingResource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by George Yu in Jun. 2021.
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
@HiltAndroidTest
class AppNavigationTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        hiltRule.inject()
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun artScreen_clickOnFab_navigateToArtDetails() {
        onView(withId(R.id.fab_arts)).perform(click())

        onView(withId(R.id.recyclerView_arts)).check(doesNotExist())

        onView(withId(R.id.name_edit)).check(matches(isDisplayed()))
        onView(withId(R.id.artist_edit)).check(matches(isDisplayed()))
        onView(withId(R.id.year_edit)).check(matches(isDisplayed()))
        onView(withId(R.id.save_btn)).check(matches(isDisplayed()))
    }

    @Test
    fun artDetailsScreen_clickOnImage_navigateToSearchArt() {
        onView(withId(R.id.fab_arts)).perform(click())
        onView(withId(R.id.artDetail_img)).perform(click())

        onView(withId(R.id.artDetail_img)).check(doesNotExist())
        onView(withId(R.id.name_edit)).check(doesNotExist())
        onView(withId(R.id.artist_edit)).check(doesNotExist())
        onView(withId(R.id.year_edit)).check(doesNotExist())
        onView(withId(R.id.save_btn)).check(doesNotExist())

        onView(withId(R.id.search_edit_txt)).check(matches(isDisplayed()))
        onView(withId(R.id.search_recycler)).check(matches(isDisplayed()))
    }

    @Test
    fun searchArtScreen_clickImage_navigateToArtDetails() {
        onView(withId(R.id.fab_arts)).perform(click())
        onView(withId(R.id.artDetail_img)).perform(click())
        onView(withId(R.id.search_edit_txt)).perform(replaceText("Mona"))

        Thread.sleep(1500L)
        onView(withId(R.id.search_recycler)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ImageViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.name_edit)).check(matches(isDisplayed()))
        onView(withId(R.id.artist_edit)).check(matches(isDisplayed()))
        onView(withId(R.id.year_edit)).check(matches(isDisplayed()))
        onView(withId(R.id.save_btn)).check(matches(isDisplayed()))
    }
}
