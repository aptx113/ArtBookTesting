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
package com.danteyu.studio.artbooktesting.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.danteyu.studio.artbooktesting.ARTS_TABLE

/**
 * Created by George Yu in May. 2021.
 */
@Entity(tableName = ARTS_TABLE)
data class Art(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val artist: String,
    val year: Int,
    val imageUrl: String
)
