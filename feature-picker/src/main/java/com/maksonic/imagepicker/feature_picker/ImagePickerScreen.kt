package com.maksonic.imagepicker.feature_picker

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import coil.load
import com.maksonic.imagepicker.core.Communication
import com.maksonic.imagepicker.core.base.BaseScreen
import com.maksonic.imagepicker.core.navigationData
import com.maksonic.imagepicker.feature_picker.databinding.ScreenPickerImageBinding
import org.koin.android.ext.android.inject

/**
 * @Author: maksonic on 23.12.2021
 */
class ImagePickerScreen : BaseScreen<ScreenPickerImageBinding>() {
    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> ScreenPickerImageBinding
        get() = ScreenPickerImageBinding::inflate

    private val communication by inject<Communication>()

    override fun prepareView(savedInstanceState: Bundle?) {
        initClicks()
        setImage(binding.image)
    }

    private fun initClicks() {
        with(binding) {
            btnPickImage.setOnClickListener(clickListener)
            btnClearImage.setOnClickListener(clickListener)
        }
    }

    private val clickListener = View.OnClickListener {
        with(binding) {
            when (it.id) {
                btnPickImage.id -> checkedPermission
                    .launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                btnClearImage.id -> clearImage(image)
            }
        }
    }

    private fun setImage(image: ImageView) {
        val data = navigationData as? MediaStoreImage
        image.load(data?.uri) {
            build()
        }
    }

    private fun clearImage(image: ImageView) {
        image.setImageDrawable(
            AppCompatResources.getDrawable(
                requireContext(),
                R.drawable.placeholder
            )
        )
    }

    private val checkedPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (!isGranted) {
                Manifest.permission.READ_EXTERNAL_STORAGE.checkPermission()
            } else {
                communication.showPickerSheet(this)
            }
        }

    private fun String.checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(), this
        ) == PackageManager.PERMISSION_GRANTED
    }
}