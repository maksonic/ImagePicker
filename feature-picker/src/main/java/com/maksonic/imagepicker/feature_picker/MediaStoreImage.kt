package com.maksonic.imagepicker.feature_picker

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import java.util.*

/**
 * @Author: maksonic on 23.12.2021
 */
data class MediaStoreImage(
    val id: Long,
    val name: String?,
    val dateAdded: Date,
    val uri: Uri?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readValue(Date::class.java.classLoader) as Date,
        parcel.readParcelable(Uri::class.java.classLoader) as? Uri
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeValue(dateAdded)
        parcel.writeParcelable(uri, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MediaStoreImage> {
        override fun createFromParcel(parcel: Parcel): MediaStoreImage {
            return MediaStoreImage(parcel)
        }

        override fun newArray(size: Int): Array<MediaStoreImage?> {
            return arrayOfNulls(size)
        }
    }
}