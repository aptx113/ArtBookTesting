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
import androidx.lifecycle.asFlow
import com.danteyu.studio.artbooktesting.MainCoroutineRule
import com.danteyu.studio.artbooktesting.data.repository.FakeArtRepository
import com.danteyu.studio.artbooktesting.utils.Status
import com.google.common.truth.Truth
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
class ArtDetailsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: ArtDetailsViewModel

    @Before
    fun setUp() {
        viewModel = ArtDetailsViewModel(FakeArtRepository())
    }

    @Test
    fun makeArt_withoutYear_returnsError() {
        mainCoroutineRule.runBlockingTest {
            viewModel.name.value = "Mona"
            viewModel.artist.value = "Da Vinci"
            viewModel.year.value = ""
            viewModel.makeArt()
            val value = viewModel.insertArtMsgFlow.first()
            Truth.assertThat(value.status).isEqualTo(Status.ERROR)
        }
    }

    @Test
    fun makeArt_withoutName_returnsError() {
        mainCoroutineRule.runBlockingTest {
            viewModel.name.value = ""
            viewModel.artist.value = "Da Vinci"
            viewModel.year.value = "1800"
            viewModel.makeArt()
            val value = viewModel.insertArtMsgFlow.first()
            Truth.assertThat(value.status).isEqualTo(Status.ERROR)
        }
    }

    @Test
    fun makeArt_withoutArtist_returnsError() {
        mainCoroutineRule.runBlockingTest {
            viewModel.name.value = "Mona"
            viewModel.artist.value = ""
            viewModel.year.value = "1800"
            viewModel.makeArt()
            val value = viewModel.insertArtMsgFlow.first()
            Truth.assertThat(value.status).isEqualTo(Status.ERROR)
        }
    }

    @Test
    fun setImageUrl_url_returnsUrl() {
        mainCoroutineRule.runBlockingTest {
            viewModel.setImageUrl("url")
            val value = viewModel.imageUrl.asFlow().first()
            Truth.assertThat(value).isEqualTo("url")
        }
    }

    @Test
    fun resetArtMsg_returnsLoading() {
        mainCoroutineRule.runBlockingTest {
            viewModel.resetInsertArtMsg()
            val value = viewModel.insertArtMsgFlow.first()
            Truth.assertThat(value.status).isEqualTo(Status.LOADING)
        }
    }
}
