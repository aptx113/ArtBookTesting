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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danteyu.studio.artbooktesting.data.repository.ArtRepository
import com.danteyu.studio.artbooktesting.model.ImageResponse
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
class SearchArtViewModel @Inject constructor(private val repository: ArtRepository) : ViewModel() {

    private val _imagesFlow = MutableStateFlow<Resource<ImageResponse>>(Resource.loading(null))
    val imagesFlow: StateFlow<Resource<ImageResponse>> = _imagesFlow

    private val _selectedImageFlow = MutableStateFlow("")
    val selectedImageFlow: StateFlow<String> = _selectedImageFlow

    fun setSelectedImage(url: String) {
        _selectedImageFlow.value = url
    }

    fun searchImage(searchQuery: String) {
        if (searchQuery.isEmpty()) return

        _imagesFlow.value = Resource.loading(null)
        viewModelScope.launch {
            _imagesFlow.value = repository.getImage(searchQuery)
        }
    }
}
