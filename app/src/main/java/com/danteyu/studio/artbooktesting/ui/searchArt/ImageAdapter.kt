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

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.danteyu.studio.artbooktesting.model.ImageResult
import com.danteyu.studio.artbooktesting.utils.SingleFieldDiffUtil
import javax.inject.Inject

/**
 * Created by George Yu in Jun. 2021.
 */
class ImageAdapter @Inject constructor() :
    ListAdapter<ImageResult, ImageViewHolder>(SingleFieldDiffUtil<ImageResult> { it.id }) {

    private var onItemClickListener: ((String) -> Unit)? = null

    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
        ImageViewHolder.create(parent)

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = getItem(position)
        holder.bind(image)
        holder.itemView.apply {
            setOnClickListener {
                onItemClickListener?.let {
                    it(image.previewURL)
                }
            }
        }
    }
}
