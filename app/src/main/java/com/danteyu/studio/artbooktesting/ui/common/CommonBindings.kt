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
package com.danteyu.studio.artbooktesting.ui.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.danteyu.studio.artbooktesting.R

/**
 * Created by George Yu in Jun. 2021.
 */
object CommonBindings {

    @JvmStatic
    @BindingAdapter("imgUrl")
    fun bindImageUrl(img: ImageView, url: String?) {
        if (url == null) return
        img.load(url) {
            crossfade(true)
            placeholder(R.drawable.ic_launcher_background)
        }
    }
}
