package com.maksonic.imagepicker.feature_picker.navigation

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment

/**
 * @Author: maksonic on 23.12.2021
 */
interface Communication {

    fun showPickerSheet(fragment: Fragment)
    fun selectImage(fragment: Fragment, image: Uri?, imageData: Bundle?)
}