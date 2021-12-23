package com.maksonic.imagepicker.feature_picker

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.maksonic.imagepicker.core.ImagePicker
import com.maksonic.imagepicker.core.Visibility.invisible
import com.maksonic.imagepicker.core.Visibility.visible
import com.maksonic.imagepicker.feature_picker.adapter.GalleryAdapter
import com.maksonic.imagepicker.core.base.BaseBottomSheet
import com.maksonic.imagepicker.feature_picker.databinding.BottomSheetImagePickerBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import kotlin.math.absoluteValue


/**
 * @Author: maksonic on 23.12.2021
 */
const val READ_EXTERNAL_STORAGE_REQUEST = 0x1045

class ImagePickerBottomSheet : BaseBottomSheet<BottomSheetImagePickerBinding>(), ImagePicker {

    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> BottomSheetImagePickerBinding
        get() = BottomSheetImagePickerBinding::inflate
    private val viewModel: ImagePickerViewModel by viewModel()

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<*>

    private val galleryAdapter: GalleryAdapter by lazy {
        GalleryAdapter {
            Toast.makeText(context, it?.name, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener {
            initBottomSheetDialog()
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
        }
        return dialog
    }

    override fun prepareView(savedInstanceState: Bundle?) {
        viewModel.loadImages()
        initRecyclerView()
        toolbarBackPressed()

    }

    private fun getScreenHeight(): Int {
        return (Resources.getSystem().displayMetrics.heightPixels).times(1)
    }

    private fun initBottomSheetDialog() {
        val bottomSheet = with(binding.root.rootView) {
            findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
        }
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)


        val params = bottomSheet.layoutParams
        params.height = getScreenHeight()
        bottomSheet.layoutParams = params
        bottomSheet.background = requireContext().let {
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.bg_sheet,
                requireContext().theme
            )
        }
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> binding.bottomSheetLayout.transitionToStart()
                    BottomSheetBehavior.STATE_EXPANDED -> binding.bottomSheetLayout.transitionToEnd()
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                if (slideOffset > 0) {
                    binding.bottomSheetLayout.progress = slideOffset
                }
            }
        })

    }

    private fun initRecyclerView() {
        with(binding) {
            recyclerGallery.adapter = galleryAdapter
        }

        lifecycleScope.launch {
            viewModel.images.collect {
                galleryAdapter.submitList(it)
            }
        }
    }

    override val pickImage =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { permission ->
            when (permission) {
                true -> viewModel.loadImages()
                false -> {
                    val showRationale =
                        ActivityCompat.shouldShowRequestPermissionRationale(
                            requireActivity(),
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        )

                    if (!showRationale) {
                        goToSettings()
                    }
                }
            }
        }

    private fun toolbarBackPressed() {
        binding.toolBar.setNavigationOnClickListener {
            requireDialog().onBackPressed()
        }
    }

    private fun goToSettings() {
        Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.parse("package:${requireActivity().packageName}")
        ).apply {
            addCategory(Intent.CATEGORY_DEFAULT)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }.also { intent ->
            startActivity(intent)
        }
    }

}
