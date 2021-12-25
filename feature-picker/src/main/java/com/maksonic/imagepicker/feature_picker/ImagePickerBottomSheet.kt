package com.maksonic.imagepicker.feature_picker

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.maksonic.imagepicker.core.Communication
import com.maksonic.imagepicker.core.base.BaseBottomSheet
import com.maksonic.imagepicker.feature_picker.adapter.GalleryAdapter
import com.maksonic.imagepicker.feature_picker.databinding.BottomSheetImagePickerBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * @Author: maksonic on 23.12.2021
 */
class ImagePickerBottomSheet : BaseBottomSheet<BottomSheetImagePickerBinding>() {

    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> BottomSheetImagePickerBinding
        get() = BottomSheetImagePickerBinding::inflate
    private val viewModel: ImagePickerViewModel by viewModel()

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<*>

    private val communication by inject<Communication>()

    private val galleryAdapter: GalleryAdapter by lazy {
        GalleryAdapter { image ->
            communication.selectImage(this, image)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener {
            initBottomSheetDialog(dialog as BottomSheetDialog)
        }
        return dialog
    }

    override fun prepareView(savedInstanceState: Bundle?) {
        viewModel.loadImages()
        initRecyclerView()
        toolbarBackPressed()
    }

    private fun getScreenHeight(): Int {
        return requireContext().resources.displayMetrics.heightPixels

    }

    private fun initBottomSheetDialog(dialog: BottomSheetDialog) {
        dialog.let {
            val bottomSheet = with(binding.root.rootView) {
                findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            }
            bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            val density = requireContext().resources.displayMetrics
            bottomSheetBehavior.peekHeight = (density.heightPixels * 0.6).toInt()

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
                        BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                            binding.bottomSheetLayout.transitionToStart()
                        }
                        BottomSheetBehavior.STATE_EXPANDED -> {
                            binding.bottomSheetLayout.transitionToEnd()
                        }
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    if (slideOffset > 0) {
                        binding.bottomSheetLayout.progress = slideOffset
                    }
                }
            })
        }

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

    private fun toolbarBackPressed() {
        binding.toolBar.setNavigationOnClickListener {
            requireDialog().onBackPressed()
        }
    }
}
