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
package com.danteyu.studio.artbooktesting.utils

import androidx.test.espresso.idling.net.UriIdlingResource
import com.danteyu.studio.artbooktesting.BASE_URL

/**
 * Created by George Yu in Jun. 2021.
 */
object EspressoUriIdlingResource {

    private const val RESOURCE = "DATA_LOADED"
    private const val TIMEOUT_MS = 5000L

    @JvmField
    val uriIdlingResource = UriIdlingResource(RESOURCE, TIMEOUT_MS)

    fun beginLoad() {
        uriIdlingResource.beginLoad(BASE_URL)
    }

    fun endLoad() {
        uriIdlingResource.endLoad(BASE_URL)
    }
}
