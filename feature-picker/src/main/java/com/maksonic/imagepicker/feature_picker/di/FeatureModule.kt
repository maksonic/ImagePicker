package com.maksonic.imagepicker.feature_picker.di

import com.maksonic.imagepicker.feature_picker.ImagePickerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @Author: maksonic on 23.12.2021
 */
val featureModule = module {

    viewModel { ImagePickerViewModel(get()) }
}