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
package com.danteyu.studio.artbooktesting.data.source.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.danteyu.studio.artbooktesting.MainCoroutineRule
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

/**
 * Created by George Yu in Jun. 2021.
 */
@ExperimentalCoroutinesApi
class ApiServiceTest : ApiAbstract<ApiService>() {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: ApiService

    @Before
    fun initService() {
        service = createService(ApiService::class.java)
    }

    @Throws(IOException::class)
    @Test
    fun getImageFromNetwork() = runBlocking {
        enqueueResponse("/ImageResult.json")
        val response = service.getImage("")
        val responseBody = requireNotNull(response.body()?.hits)
        mockWebServer.takeRequest()

        val loaded = responseBody[0]
        Truth.assertThat(responseBody.count()).isEqualTo(20)
        Truth.assertThat(loaded.user).isEqualTo("NickyPe")
        Truth.assertThat(loaded.comments).isEqualTo(72)
        Truth.assertThat(loaded.favorites).isEqualTo(24)
        Truth.assertThat(loaded.previewURL)
            .isEqualTo("https://cdn.pixabay.com/photo/2021/01/04/10/16/rooster-5887065_150.jpg")
    }
}
