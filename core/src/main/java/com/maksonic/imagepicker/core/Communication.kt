package com.maksonic.imagepicker.core

import android.os.Parcelable
import androidx.fragment.app.Fragment

/**
 * @Author: maksonic on 23.12.2021
 */
interface Communication {

    fun showPickerSheet(fragment: Fragment)
    fun selectImage(fragment: Fragment, imageData: Parcelable?)
}

const val PASSED_DATA = "passed data"
val Fragment.navigationData: Parcelable?
    get() = arguments?.getParcelable(PASSED_DATA)
