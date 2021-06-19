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

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.danteyu.studio.artbooktesting.data.repository.ArtRepository
import com.danteyu.studio.artbooktesting.data.source.local.Art
import com.danteyu.studio.artbooktesting.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers

/**
 * Created by George Yu in Jun. 2021.
 */
@HiltViewModel
class ArtDetailsViewModel @Inject constructor(private val repository: ArtRepository) : ViewModel() {

    private val _imageUrl = MutableStateFlow("")
    val imageUrl: LiveData<String> = _imageUrl.asLiveData()

    val name = MutableLiveData<String>()
    val artist = MutableLiveData<String>()
    val year = MutableLiveData<String>()

    private val navigateToSearchArtChannel = Channel<Boolean>(Channel.CONFLATED)
    val navigateToSearchArtFlow = navigateToSearchArtChannel.receiveAsFlow()

    private val _insertArtMsgFlow = MutableStateFlow<Resource<Art>>(Resource.loading(null))
    val insertArtMsgFlow: StateFlow<Resource<Art>> = _insertArtMsgFlow

    private fun insertArt(art: Art) =
        viewModelScope.launch(Dispatchers.IO) { repository.insertArt(art) }

    fun setImageUrl(url: String) {
        _imageUrl.value = url
    }

    fun onSearchArtNavigated() = viewModelScope.launch { navigateToSearchArtChannel.send(true) }

    fun resetInsertArtMsg() {
        _insertArtMsgFlow.value = Resource.loading(null)
    }

    fun makeArt() {
        if (name.value.isNullOrEmpty() || artist.value.isNullOrEmpty() || year.value.isNullOrEmpty()) {
            _insertArtMsgFlow.value = Resource.error("Enter name, artist, year", null)
            return
        }

        val art = Art(
            name = name.value ?: "",
            artist = artist.value ?: "",
            year = year.value ?: "",
            imageUrl = _imageUrl.value
        )
        insertArt(art)
        _insertArtMsgFlow.value = Resource.success(art)
    }
}
