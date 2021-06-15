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

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.danteyu.studio.artbooktesting.data.source.local.Art
import com.danteyu.studio.artbooktesting.databinding.ItemArtBinding

/**
 * Created by George Yu in Jun. 2021.
 */
class ArtViewHolder(private val viewDataBinding: ItemArtBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {

    fun bind(item: Art) {
        viewDataBinding.art = item
        viewDataBinding.executePendingBindings()
    }

    companion object {
        fun create(parent: ViewGroup): ArtViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemArtBinding.inflate(layoutInflater, parent, false)
            return ArtViewHolder(binding)
        }
    }
}
