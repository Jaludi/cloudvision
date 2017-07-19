package com.google.sample.cloudvision.searchpojos

import android.os.Parcel
import android.os.Parcelable

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Context : Parcelable {

    @SerializedName("title")
    @Expose
    var title: String? = null

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeValue(title)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        val CREATOR: Parcelable.Creator<Context> = object : Parcelable.Creator<Context> {


            override fun createFromParcel(`in`: Parcel): Context {
                val instance = Context()
                instance.title = `in`.readValue(String::class.java.classLoader) as String
                return instance
            }

            override fun newArray(size: Int): Array<Context?> {
                return arrayOfNulls(size)
            }

        }
    }

}
