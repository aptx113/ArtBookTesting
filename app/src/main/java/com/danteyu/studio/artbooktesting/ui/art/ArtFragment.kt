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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.danteyu.studio.artbooktesting.databinding.FragArtBinding
import com.danteyu.studio.artbooktesting.ext.observeInLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * Created by George Yu in May. 2021.
 */
@AndroidEntryPoint
class ArtFragment @Inject constructor(private val adapter: ArtAdapter) : Fragment() {

    private lateinit var viewDataBinding: FragArtBinding
    private val viewModel: ArtViewModel by viewModels()
    private val swipeCallback =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val layoutPosition = viewHolder.layoutPosition
                val selectedArt = adapter.currentList[layoutPosition]
                viewModel.deleteArt(selectedArt)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding = FragArtBinding.inflate(inflater, container, false)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewDataBinding.viewModel = viewModel
        viewDataBinding.lifecycleOwner = viewLifecycleOwner
        viewDataBinding.recyclerViewArts.adapter = adapter

        viewDataBinding.recyclerViewArts.adapter = adapter
        ItemTouchHelper(swipeCallback).attachToRecyclerView(viewDataBinding.recyclerViewArts)

        viewModel.artsFlow
            .onEach { adapter.submitList(it) }
            .observeInLifecycle(viewLifecycleOwner)

        viewDataBinding.recyclerViewArts.adapter = adapter
        ItemTouchHelper(swipeCallback).attachToRecyclerView(viewDataBinding.recyclerViewArts)

        viewModel.artsFlow
            .onEach { adapter.submitList(it) }
            .observeInLifecycle(viewLifecycleOwner)

        viewModel.navigateToArtDetailsFlow
            .onEach {
                findNavController().navigate(
                    ArtFragmentDirections.actionArtFragmentToArtDetailsFragment()
                )
            }.observeInLifecycle(viewLifecycleOwner)
    }
}
