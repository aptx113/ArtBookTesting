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
package com.danteyu.studio.artbooktesting.ui.art

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.danteyu.studio.artbooktesting.R
import com.danteyu.studio.artbooktesting.factory.ArtFragmentFactory
import com.danteyu.studio.artbooktesting.utils.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

/**
 * Created by George Yu in Jun. 2021.
 */
@MediumTest
@HiltAndroidTest
class ArtFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: ArtFragmentFactory

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testNavigateToArtDetails() {
        val navController = mockk<NavController>(relaxUnitFun = true)
        launchFragmentInHiltContainer<ArtFragment>(factory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
        }
        Espresso.onView(ViewMatchers.withId(R.id.fab_arts)).perform(ViewActions.click())
        verify { navController.navigate(ArtFragmentDirections.actionArtFragmentToArtDetailsFragment()) }
    }
}
