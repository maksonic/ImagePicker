package com.maksonic.imagepicker.navigation

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.maksonic.imagepicker.R
import com.maksonic.imagepicker.feature_picker.navigation.Communication

/**
 * @Author: maksonic on 23.12.2021
 */
class CommunicationImpl : Communication {
    override fun showPickerSheet(fragment: Fragment) {
        fragment.findNavController()
            .navigate(R.id.action_imagePickerScreen_to_imagePickerBottomSheet)
    }

    override fun selectImage(fragment: Fragment, image: Uri?, imageData: Bundle?) {
        fragment.findNavController()
            .navigate(R.id.action_imagePickerBottomSheet_to_imagePickerScreen, imageData)
    }
}