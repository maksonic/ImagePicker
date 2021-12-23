package com.maksonic.imagepicker.feature_picker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.maksonic.imagepicker.feature_picker.base.BaseBottomSheet
import com.maksonic.imagepicker.feature_picker.databinding.BottomSheetImagePickerBinding

/**
 * @Author: maksonic on 23.12.2021
 */
class ImagePickerBottomSheet : BaseBottomSheet<BottomSheetImagePickerBinding>() {

    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> BottomSheetImagePickerBinding
        get() = BottomSheetImagePickerBinding::inflate

    override fun prepareView(savedInstanceState: Bundle?) {
    }

}
