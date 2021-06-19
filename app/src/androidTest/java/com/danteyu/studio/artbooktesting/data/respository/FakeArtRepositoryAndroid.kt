package com.danteyu.studio.artbooktesting.data.respository

import com.danteyu.studio.artbooktesting.data.mock.mockImageResponseAndroid
import com.danteyu.studio.artbooktesting.data.repository.ArtRepository
import com.danteyu.studio.artbooktesting.data.source.local.Art
import com.danteyu.studio.artbooktesting.model.ImageResponse
import com.danteyu.studio.artbooktesting.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Created by George Yu in Jun. 2021.
 */
class FakeArtRepositoryAndroid : ArtRepository {

    private val arts = mutableListOf<Art>()
    private val artsFlow = MutableStateFlow(arts)

    override fun getAllArtsFlow(): Flow<List<Art>> {
        return artsFlow
    }

    override suspend fun insertArt(art: Art) {
        arts.add(art)
        refreshData()
    }

    override suspend fun deleteArt(art: Art) {
        arts.remove(art)
        refreshData()
    }

    override suspend fun getImage(searchQuery: String): Resource<ImageResponse> {
        return Resource.success(mockImageResponseAndroid)
    }

    private fun refreshData() {
        artsFlow.value = arts
    }
}
