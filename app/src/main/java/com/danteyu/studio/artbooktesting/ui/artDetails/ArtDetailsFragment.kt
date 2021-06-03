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

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.danteyu.studio.artbooktesting.R
import com.danteyu.studio.artbooktesting.databinding.FragArtDetailsBinding
import com.danteyu.studio.artbooktesting.ext.observeInLifecycle
import com.danteyu.studio.artbooktesting.utils.Status
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

/**
 * Created by George Yu in May. 2021.
 */
@SuppressWarnings("EmptyFunctionBlock")
@AndroidEntryPoint
class ArtDetailsFragment : Fragment() {

    private lateinit var viewDataBinding: FragArtDetailsBinding
    private val viewModel: ArtDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding = FragArtDetailsBinding.inflate(inflater, container, false)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewDataBinding.lifecycleOwner = viewLifecycleOwner
        viewDataBinding.viewModel = viewModel

        setupListeners()

        viewModel.navigateToSearchArtFlow
            .onEach {
                findNavController().navigate(
                    ArtDetailsFragmentDirections.actionArtDetailsFragmentToSearchArtFragment()
                )
            }
            .observeInLifecycle(viewLifecycleOwner)

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>("key")
            ?.observe(viewLifecycleOwner) {
                viewModel.setImageUrl(it)
            }
        viewModel.insertArtMsgFlow
            .onEach {
                when (it.status) {
                    Status.SUCCESS -> {
                        Toast.makeText(requireContext(), "Success", Toast.LENGTH_LONG).show()
                        findNavController().popBackStack()
                        viewModel.resetInsertArtMsg()
                    }
                    Status.ERROR -> {
                        Toast.makeText(requireContext(), it.message ?: "Error", Toast.LENGTH_LONG)
                            .show()
                        viewModel.resetInsertArtMsg()
                        validateForm(
                            viewDataBinding.nameEdit,
                            viewDataBinding.artNameTxtField
                        )
                        validateForm(viewDataBinding.artistEdit, viewDataBinding.artArtistTxtField)
                        validateForm(viewDataBinding.yearEdit, viewDataBinding.artYearTxtField)
                    }
                    Status.LOADING -> {
                    }
                }
            }.observeInLifecycle(viewLifecycleOwner)
    }

    private fun setupListeners() {
        viewDataBinding.nameEdit.addTextChangedListener(TextFieldValidation(viewDataBinding.nameEdit))
        viewDataBinding.artistEdit.addTextChangedListener(TextFieldValidation(viewDataBinding.artistEdit))
        viewDataBinding.yearEdit.addTextChangedListener(TextFieldValidation(viewDataBinding.yearEdit))
    }

    private fun validateForm(
        textFieldEditText: TextInputEditText,
        textInputLayout: TextInputLayout
    ) {
        if (textFieldEditText.text.toString().trim().isEmpty()) {
            textInputLayout.error = "Required field"
            textFieldEditText.requestFocus()
        } else {
            textInputLayout.isErrorEnabled = false
        }
    }

    inner class TextFieldValidation(private val view: View) : TextWatcher {

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            when (view.id) {
                R.id.name_edit -> validateForm(
                    viewDataBinding.nameEdit,
                    viewDataBinding.artNameTxtField
                )
                R.id.artist_edit -> validateForm(
                    viewDataBinding.artistEdit,
                    viewDataBinding.artArtistTxtField
                )
                R.id.year_edit -> validateForm(
                    viewDataBinding.yearEdit,
                    viewDataBinding.artYearTxtField
                )
            }
        }

        override fun afterTextChanged(s: Editable?) {}
    }
}
