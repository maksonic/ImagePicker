package com.maksonic.imagepicker.feature_picker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import coil.load
import com.maksonic.imagepicker.feature_picker.base.BaseScreen
import com.maksonic.imagepicker.feature_picker.databinding.ScreenPickerImageBinding
import com.maksonic.imagepicker.feature_picker.navigation.Communication
import org.koin.android.ext.android.inject

/**
 * @Author: maksonic on 23.12.2021
 */
private const val inputType = "image/*"

class ImagePickerScreen : BaseScreen<ScreenPickerImageBinding>(), ImagePicker {
    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> ScreenPickerImageBinding
        get() = ScreenPickerImageBinding::inflate

    private val communication by inject<Communication>()

    override val pickImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri.let {
                binding.image.load(uri)
            }
        }

    override fun prepareView(savedInstanceState: Bundle?) {
        binding.btnPickImage.setOnClickListener {
            communication.showPickerSheet(this)
            // pickImage.launch(inputType)
        }
    }
}


