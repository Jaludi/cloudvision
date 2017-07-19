package com.google.sample.cloudvision.searchpojos

import android.os.Parcel
import android.os.Parcelable

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Image : Parcelable {

    @SerializedName("contextLink")
    @Expose
    var contextLink: String? = null
    @SerializedName("height")
    @Expose
    var height: Int? = null
    @SerializedName("width")
    @Expose
    var width: Int? = null
    @SerializedName("byteSize")
    @Expose
    var byteSize: Int? = null
    @SerializedName("thumbnailLink")
    @Expose
    var thumbnailLink: String? = null
    @SerializedName("thumbnailHeight")
    @Expose
    var thumbnailHeight: Int? = null
    @SerializedName("thumbnailWidth")
    @Expose
    var thumbnailWidth: Int? = null

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeValue(contextLink)
        dest.writeValue(height)
        dest.writeValue(width)
        dest.writeValue(byteSize)
        dest.writeValue(thumbnailLink)
        dest.writeValue(thumbnailHeight)
        dest.writeValue(thumbnailWidth)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        val CREATOR: Parcelable.Creator<Image> = object : Parcelable.Creator<Image> {


            override fun createFromParcel(`in`: Parcel): Image {
                val instance = Image()
                instance.contextLink = `in`.readValue(String::class.java.classLoader) as String
                instance.height = `in`.readValue(Int::class.java.classLoader) as Int
                instance.width = `in`.readValue(Int::class.java.classLoader) as Int
                instance.byteSize = `in`.readValue(Int::class.java.classLoader) as Int
                instance.thumbnailLink = `in`.readValue(String::class.java.classLoader) as String
                instance.thumbnailHeight = `in`.readValue(Int::class.java.classLoader) as Int
                instance.thumbnailWidth = `in`.readValue(Int::class.java.classLoader) as Int
                return instance
            }

            override fun newArray(size: Int): Array<Image?> {
                return arrayOfNulls(size)
            }

        }
    }

}
