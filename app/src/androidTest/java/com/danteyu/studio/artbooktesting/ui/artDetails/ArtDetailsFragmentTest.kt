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
package com.danteyu.studio.artbooktesting.ui.artDetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.danteyu.studio.artbooktesting.R
import com.danteyu.studio.artbooktesting.SEARCH_IMAGE
import com.danteyu.studio.artbooktesting.utils.launchFragmentInHiltContainer
import com.google.common.truth.Truth
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by George Yu in Jun. 2021.
 */
@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ArtDetailsFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun testNavigateToSearchArt() {
        val navController = mockk<NavController>(relaxUnitFun = true)
        launchFragmentInHiltContainer<ArtDetailsFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }
        onView(withId(R.id.artDetail_img)).perform(click())
        verify { navController.navigate(ArtDetailsFragmentDirections.actionArtDetailsFragmentToSearchArtFragment()) }
    }

    @Test
    fun testFragmentResultListener() {
        launchFragmentInHiltContainer<ArtDetailsFragment> {
            val expectedResult = "result"
            parentFragmentManager.setFragmentResult(
                SEARCH_IMAGE,
                bundleOf(SEARCH_IMAGE to expectedResult)
            )
            Truth.assertThat(result).isEqualTo(expectedResult)
        }
    }

    @Test
    fun testSave() {
        val navController = mockk<NavController>(relaxed = true)
        launchFragmentInHiltContainer<ArtDetailsFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }
        onView(withId(R.id.name_edit)).perform(replaceText("Mona"))
        onView(withId(R.id.artist_edit)).perform(replaceText("Da Vinci"))
        onView(withId(R.id.year_edit)).perform(replaceText("1500"))
        onView(withId(R.id.save_btn)).perform(click())

        verify { navController.popBackStack() }
    }
}
