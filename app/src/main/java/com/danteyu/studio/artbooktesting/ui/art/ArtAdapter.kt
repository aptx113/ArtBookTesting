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
package com.danteyu.studio.artbooktesting.ui.art

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.danteyu.studio.artbooktesting.data.source.local.Art
import com.danteyu.studio.artbooktesting.utils.SingleFieldDiffUtil
import javax.inject.Inject

/**
 * Created by George Yu in Jun. 2021.
 */
class ArtAdapter @Inject constructor() :
    ListAdapter<Art, ArtViewHolder>(SingleFieldDiffUtil<Art> { it.id }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtViewHolder =
        ArtViewHolder.create(parent)

    override fun onBindViewHolder(holder: ArtViewHolder, position: Int) {
        val art = getItem(position)
        holder.bind(art)
    }
}
