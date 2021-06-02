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
package com.danteyu.studio.artbooktesting.factory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.danteyu.studio.artbooktesting.ui.art.ArtAdapter
import com.danteyu.studio.artbooktesting.ui.art.ArtFragment
import com.danteyu.studio.artbooktesting.ui.searchArt.ImageAdapter
import com.danteyu.studio.artbooktesting.ui.searchArt.SearchArtFragment
import javax.inject.Inject

/**
 * Created by George Yu in 6月. 2021.
 */
class ArtFragmentFactory @Inject constructor(
    private val artAdapter: ArtAdapter,
    private val imageAdapter: ImageAdapter
) :
    FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            ArtFragment::class.java.name -> ArtFragment(artAdapter)
            SearchArtFragment::class.java.name -> SearchArtFragment(imageAdapter)
            else -> super.instantiate(classLoader, className)
        }
    }
}
