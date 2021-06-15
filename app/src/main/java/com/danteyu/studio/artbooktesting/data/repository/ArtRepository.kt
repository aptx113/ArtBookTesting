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

import com.danteyu.studio.artbooktesting.data.source.local.Art
import com.danteyu.studio.artbooktesting.model.ImageResponse
import com.danteyu.studio.artbooktesting.utils.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Created by George Yu in May. 2021.
 */
interface ArtRepository {

    fun getAllArtsFlow(): Flow<List<Art>>

    suspend fun insertArt(art: Art)

    suspend fun deleteArt(art: Art)

    suspend fun getImage(searchQuery: String): Resource<ImageResponse>
}
