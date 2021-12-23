package com.maksonic.imagepicker.navigation

import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.maksonic.imagepicker.R
import com.maksonic.imagepicker.feature_picker.navigation.Communication

/**
 * @Author: maksonic on 23.12.2021
 */

 class SafeNavigation {

    companion object {
        private const val PASSED_DATA = "passed data"
    }

    fun safeNavigate(
        fragment: Fragment,
        actionId: Int,
        hostId: Int? = null,
        data: Parcelable? = null
    ) {
        val navController = if (hostId == null) {
            fragment.findNavController()
        } else {
            Navigation.findNavController(fragment.requireActivity(), hostId)
        }
        val bundle = Bundle().apply { putParcelable(PASSED_DATA, data) }
        val action = navController.currentDestination?.getAction(actionId)

        if (action != null && navController.currentDestination?.id != action.destinationId) {
            navController.navigate(actionId, bundle)
        }
    }
}

class CommunicationImpl(private val safeNavigation: SafeNavigation) : Communication {
    override fun showPickerSheet(fragment: Fragment) {
        safeNavigation.safeNavigate(
            fragment,
            R.id.action_imagePickerScreen_to_imagePickerBottomSheet
        )
    }

    override fun selectImage(fragment: Fragment, image: Uri?, imageData: Parcelable?) {
        safeNavigation.safeNavigate(
            fragment,
            R.id.action_imagePickerBottomSheet_to_imagePickerScreen,
            data = imageData
        )
    }
}