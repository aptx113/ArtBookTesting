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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.danteyu.studio.artbooktesting.ONE_SEC
import com.danteyu.studio.artbooktesting.SEARCH_IMAGE
import com.danteyu.studio.artbooktesting.databinding.FragSearchArtBinding
import com.danteyu.studio.artbooktesting.ext.observeInLifecycle
import com.danteyu.studio.artbooktesting.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by George Yu in May. 2021.
 */
@AndroidEntryPoint
class SearchArtFragment @Inject constructor(val adapter: ImageAdapter) : Fragment() {

    private lateinit var viewDataBinding: FragSearchArtBinding
    lateinit var viewModel: SearchArtViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding = FragSearchArtBinding.inflate(inflater, container, false)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(SearchArtViewModel::class.java)

        viewDataBinding.searchRecycler.adapter = adapter
        subscribeToCollector()

        adapter.setOnItemClickListener {
            viewModel.setSelectedImage(it)
            findNavController().popBackStack()
        }
        viewModel.selectedImageFlow.onEach {
            setFragmentResult(SEARCH_IMAGE, bundleOf(SEARCH_IMAGE to it))
        }.observeInLifecycle(viewLifecycleOwner)

        var job: Job? = null
        viewDataBinding.searchEditTxt.addTextChangedListener {
            job?.cancel()
            job = lifecycleScope.launch {
                delay(ONE_SEC)
                it?.let {
                    if (it.toString().isNotEmpty()) viewModel.searchImage(it.toString())
                }
            }
        }
    }

    private fun subscribeToCollector() {
        viewModel.imagesFlow
            .onEach {
                when (it.status) {
                    Status.SUCCESS -> {
                        it.data?.let { imageResponse ->
                            adapter.submitList(imageResponse.hits)
                        }
                        viewDataBinding.progressBar.visibility = View.GONE
                    }
                    Status.ERROR -> viewDataBinding.progressBar.visibility = View.GONE
                    Status.LOADING -> viewDataBinding.progressBar.visibility = View.VISIBLE
                }
            }.observeInLifecycle(viewLifecycleOwner)
    }
}
