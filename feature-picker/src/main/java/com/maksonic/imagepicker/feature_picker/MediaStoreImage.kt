package com.maksonic.imagepicker.feature_picker

import android.net.Uri
import androidx.recyclerview.widget.DiffUtil
import java.util.*

/**
 * @Author: maksonic on 23.12.2021
 */
data class MediaStoreImage(
    val id: Long,
    val name: String,
    val dateAdded: Date,
    val uri: Uri
) {
    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<MediaStoreImage>() {
            override fun areItemsTheSame(oldItem: MediaStoreImage, newItem: MediaStoreImage) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: MediaStoreImage, newItem: MediaStoreImage) =
                oldItem == newItem
        }
    }
}