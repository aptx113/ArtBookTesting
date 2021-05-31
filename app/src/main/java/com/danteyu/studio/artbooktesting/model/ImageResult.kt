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
package com.danteyu.studio.artbooktesting.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageResult(
    @Json(name = "comments")
    val comments: Int,
    @Json(name = "downloads")
    val downloads: Int,
    @Json(name = "favorites")
    val favorites: Int,
    @Json(name = "id")
    val id: Int,
    @Json(name = "imageHeight")
    val imageHeight: Int,
    @Json(name = "imageSize")
    val imageSize: Int,
    @Json(name = "imageWidth")
    val imageWidth: Int,
    @Json(name = "largeImageURL")
    val largeImageURL: String,
    @Json(name = "likes")
    val likes: Int,
    @Json(name = "pageURL")
    val pageURL: String,
    @Json(name = "previewHeight")
    val previewHeight: Int,
    @Json(name = "previewURL")
    val previewURL: String,
    @Json(name = "previewWidth")
    val previewWidth: Int,
    @Json(name = "tags")
    val tags: String,
    @Json(name = "type")
    val type: String,
    @Json(name = "user")
    val user: String,
    @Json(name = "user_id")
    val userId: Int,
    @Json(name = "userImageURL")
    val userImageURL: String,
    @Json(name = "views")
    val views: Int,
    @Json(name = "webformatHeight")
    val webformatHeight: Int,
    @Json(name = "webformatURL")
    val webformatURL: String,
    @Json(name = "webformatWidth")
    val webformatWidth: Int
)
