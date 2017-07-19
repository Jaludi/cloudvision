package com.google.sample.cloudvision.searchpojos

import android.os.Parcel
import android.os.Parcelable

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Queries : Parcelable {

    @SerializedName("request")
    @Expose
    var request: List<Request>? = null
    @SerializedName("nextPage")
    @Expose
    var nextPage: List<NextPage>? = null

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeList(request)
        dest.writeList(nextPage)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        val CREATOR: Parcelable.Creator<Queries> = object : Parcelable.Creator<Queries> {


            override fun createFromParcel(`in`: Parcel): Queries {
                val instance = Queries()
                `in`.readList(instance.request, Request::class.java.classLoader)
                `in`.readList(instance.nextPage, NextPage::class.java.classLoader)
                return instance
            }

            override fun newArray(size: Int): Array<Queries?> {
                return arrayOfNulls(size)
            }

        }
    }

}
