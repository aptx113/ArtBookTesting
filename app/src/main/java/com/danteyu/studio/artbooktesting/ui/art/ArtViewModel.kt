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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danteyu.studio.artbooktesting.data.repository.ArtRepository
import com.danteyu.studio.artbooktesting.data.source.local.Art
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by George Yu in May. 2021.
 */
@HiltViewModel
class ArtViewModel @Inject constructor(private val repository: ArtRepository) : ViewModel() {

    val artsFlow = repository.getAllArtsFlow()

    private val navigateToArtDetailsChannel = Channel<Boolean>(Channel.CONFLATED)
    val navigateToArtDetailsFlow = navigateToArtDetailsChannel.receiveAsFlow()

    fun deleteArt(art: Art) = viewModelScope.launch { repository.deleteArt(art) }

    fun onArtDetailsNavigated() = viewModelScope.launch { navigateToArtDetailsChannel.send(true) }
}
