package com.maksonic.imagepicker.feature_picker

import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment

/**
 * @Author: maksonic on 23.12.2021
 */
interface ImagePicker {
    val pickImage: ActivityResultLauncher<String>
}

/*
*  val pickImages = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri.let {
                binding.image.load(uri)
            }
        }*/