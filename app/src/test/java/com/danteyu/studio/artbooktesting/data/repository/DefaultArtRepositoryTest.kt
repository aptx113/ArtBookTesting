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
package com.danteyu.studio.artbooktesting.data.repository

import com.danteyu.studio.artbooktesting.MainCoroutineRule
import com.danteyu.studio.artbooktesting.data.mock.mockArts
import com.danteyu.studio.artbooktesting.data.source.api.ApiService
import com.danteyu.studio.artbooktesting.data.source.local.ArtDao
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by George Yu in Jun. 2021.
 */
@ExperimentalCoroutinesApi
class DefaultArtRepositoryTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var repository: DefaultArtRepository
    private lateinit var dao: ArtDao
    private lateinit var apiService: ApiService

    @Before
    fun setUp() {
        dao = mockk()
        apiService = mockk()
        repository = DefaultArtRepository(apiService, dao)
    }

    @Test
    fun getAllArtsFlow_returnFlowOfArts() = runBlockingTest {
        val artsFlow = flowOf(mockArts)
        every { dao.getAllArtsFlow() }.returns(artsFlow)
        repository.getAllArtsFlow()
            .collect { result -> Truth.assertThat(result).isEqualTo(mockArts) }
    }
}
