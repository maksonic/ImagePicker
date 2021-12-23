package com.maksonic.imagepicker.feature_picker.adapter

import coil.load
import com.maksonic.imagepicker.core.base.BaseViewHolder
import com.maksonic.imagepicker.feature_picker.MediaStoreImage
import com.maksonic.imagepicker.feature_picker.R
import com.maksonic.imagepicker.feature_picker.databinding.ItemImageBinding

/**
 * @Author: maksonic on 23.12.2021
 */
class ImageViewHolder(
    private val binding: ItemImageBinding,
    private val onClick: ((MediaStoreImage?) -> Unit)? = null
) : BaseViewHolder<MediaStoreImage, ItemImageBinding>(binding) {
    init {
        binding.image.setOnClickListener {
            onClick?.invoke(getRowItem())
        }
    }

    override fun bind() {
        getRowItem()?.let {
            with(binding) {
                image.load(it.uri) {
                    crossfade(false)
                    placeholder(R.drawable.placeholder)
                        .build()
                }
            }
        }
    }
}
