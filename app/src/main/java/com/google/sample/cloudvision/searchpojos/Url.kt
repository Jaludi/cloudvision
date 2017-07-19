package com.google.sample.cloudvision.searchpojos

import android.os.Parcel
import android.os.Parcelable

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Url : Parcelable {

    @SerializedName("type")
    @Expose
    var type: String? = null
    @SerializedName("template")
    @Expose
    var template: String? = null

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeValue(type)
        dest.writeValue(template)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        val CREATOR: Parcelable.Creator<Url> = object : Parcelable.Creator<Url> {


            override fun createFromParcel(`in`: Parcel): Url {
                val instance = Url()
                instance.type = `in`.readValue(String::class.java.classLoader) as String
                instance.template = `in`.readValue(String::class.java.classLoader) as String
                return instance
            }

            override fun newArray(size: Int): Array<Url?> {
                return arrayOfNulls(size)
            }

        }
    }

}
