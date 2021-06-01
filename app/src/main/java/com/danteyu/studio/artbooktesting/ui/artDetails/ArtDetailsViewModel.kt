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
package com.danteyu.studio.artbooktesting.ui.artDetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danteyu.studio.artbooktesting.data.repository.ArtRepository
import com.danteyu.studio.artbooktesting.data.source.local.Art
import com.danteyu.studio.artbooktesting.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by George Yu in Jun. 2021.
 */
@HiltViewModel
class ArtDetailsViewModel @Inject constructor(private val repository: ArtRepository) : ViewModel() {

    lateinit var imageUrl: String

    val name = MutableLiveData<String>()
    val artist = MutableLiveData<String>()
    val year = MutableLiveData<String>()

    private val _insertArtMsgFlow = MutableStateFlow<Resource<Art>>(Resource.loading(null))
    val insertArtMsgFlow: StateFlow<Resource<Art>> = _insertArtMsgFlow

    private fun insertArt(art: Art) = viewModelScope.launch { repository.insertArt(art) }

    fun resetInsertArtMsg() {
        _insertArtMsgFlow.value = Resource.loading(null)
    }

    fun makeArt(name: String, artist: String, year: String) {
        if (name.isEmpty() || artist.isEmpty() || year.isEmpty()) {
            _insertArtMsgFlow.value = Resource.error("Enter name, artist, year", null)
            return
        }

        val art = Art(name = name, artist = artist, year = year, imageUrl = imageUrl)
        insertArt(art)
        _insertArtMsgFlow.value = Resource.success(art)
    }
}
