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

import com.danteyu.studio.artbooktesting.MainCoroutineRule
import com.danteyu.studio.artbooktesting.data.mock.mockImageResponse
import com.danteyu.studio.artbooktesting.data.repository.ArtRepository
import com.danteyu.studio.artbooktesting.utils.Resource
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by George Yu in Jun. 2021.
 */
@ExperimentalCoroutinesApi
class SearchArtViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @MockK
    lateinit var artRepository: ArtRepository
    lateinit var viewModel: SearchArtViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = SearchArtViewModel(artRepository)
    }

    @Test
    fun searchImage() {
        mainCoroutineRule.runBlockingTest {
            coEvery { artRepository.getImage(any()) } returns Resource.success(mockImageResponse)
            viewModel.searchImage("Mona")
            val value = viewModel.imagesFlow.first()
            Truth.assertThat(value.data).isEqualTo(mockImageResponse)
            coVerify { artRepository.getImage(any()) }
        }
    }
}
