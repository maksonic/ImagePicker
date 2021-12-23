package com.maksonic.imagepicker.feature_picker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.maksonic.imagepicker.core.base.BaseRecyclerAdapter
import com.maksonic.imagepicker.feature_picker.MediaStoreImage
import com.maksonic.imagepicker.feature_picker.databinding.ItemImageBinding

/**
 * @Author: maksonic on 23.12.2021
 */
class GalleryAdapter constructor(
    private val onItemClick: ((MediaStoreImage?) -> Unit)? = null,
) : BaseRecyclerAdapter<MediaStoreImage, ItemImageBinding, ImageViewHolder>(CountryItemDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
        ImageViewHolder(
            ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onClick = onItemClick
        )
}

class CountryItemDiffUtil : DiffUtil.ItemCallback<MediaStoreImage>() {
    override fun areItemsTheSame(oldItem: MediaStoreImage, newItem: MediaStoreImage): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MediaStoreImage, newItem: MediaStoreImage): Boolean {
        return oldItem == newItem
    }
}
