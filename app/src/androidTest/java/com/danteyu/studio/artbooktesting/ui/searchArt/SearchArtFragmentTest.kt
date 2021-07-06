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
package com.danteyu.studio.artbooktesting.ui.searchArt

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.danteyu.studio.artbooktesting.R
import com.danteyu.studio.artbooktesting.SEARCH_IMAGE
import com.danteyu.studio.artbooktesting.data.mock.mockImageResult
import com.danteyu.studio.artbooktesting.data.repository.FakeArtRepository
import com.danteyu.studio.artbooktesting.factory.ArtFragmentFactory
import com.danteyu.studio.artbooktesting.utils.launchFragmentInHiltContainer
import com.google.common.truth.Truth
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

/**
 * Created by George Yu in Jun. 2021.
 */
@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class SearchArtFragmentTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: ArtFragmentFactory

    private lateinit var navController: NavController

    @Before
    fun setup() {
        hiltRule.inject()
        navController = mockk(relaxed = true)
    }

    @Test
    fun testSelectImage() = runBlockingTest {
        val testViewModel = SearchArtViewModel(FakeArtRepository())

        launchFragmentInHiltContainer<SearchArtFragment>(
            factory = fragmentFactory
        ) {
            Navigation.setViewNavController(requireView(), navController)
            adapter.submitList(listOf(mockImageResult))
            viewModel = testViewModel
        }

        onView(withId(R.id.search_recycler)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ImageViewHolder>(
                0,
                click()
            )
        )

        verify { navController.popBackStack() }
        Truth.assertThat(testViewModel.selectedImageFlow.first())
            .isEqualTo(mockImageResult.previewURL)
    }

    @Test
    fun testFragmentResult() {
        lateinit var actualResult: String

        launchFragmentInHiltContainer<SearchArtFragment>(factory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
            adapter.submitList(listOf(mockImageResult))
            setFragmentResultListener(SEARCH_IMAGE) { requestKey, bundle ->
                actualResult = bundle.getString(
                    requestKey
                ).toString()
            }
        }
        onView(withId(R.id.search_recycler)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ImageViewHolder>(0, click())
        )
        Truth.assertThat(actualResult).isEqualTo("dante.com")
    }
}
