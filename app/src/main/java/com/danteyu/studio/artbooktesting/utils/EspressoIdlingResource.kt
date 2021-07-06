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

/**
 * Created by George Yu in Jun. 2021.
 */
object EspressoIdlingResource {

    private const val RESOURCE = "GLOBAL"

    @JvmField
    val idlingResource = SimpleIdlingResource(RESOURCE)

    fun busy() {
        idlingResource.setIdle(true)
    }

    fun idle() {
        if (!idlingResource.isIdleNow) {
            idlingResource.setIdle(false)
        }
    }
}
