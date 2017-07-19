package com.google.sample.cloudvision.searchpojos

import android.os.Parcel
import android.os.Parcelable

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Request : Parcelable {

    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("totalResults")
    @Expose
    var totalResults: String? = null
    @SerializedName("searchTerms")
    @Expose
    var searchTerms: String? = null
    @SerializedName("count")
    @Expose
    var count: Int? = null
    @SerializedName("startIndex")
    @Expose
    var startIndex: Int? = null
    @SerializedName("inputEncoding")
    @Expose
    var inputEncoding: String? = null
    @SerializedName("outputEncoding")
    @Expose
    var outputEncoding: String? = null
    @SerializedName("safe")
    @Expose
    var safe: String? = null
    @SerializedName("cx")
    @Expose
    var cx: String? = null
    @SerializedName("searchType")
    @Expose
    var searchType: String? = null

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeValue(title)
        dest.writeValue(totalResults)
        dest.writeValue(searchTerms)
        dest.writeValue(count)
        dest.writeValue(startIndex)
        dest.writeValue(inputEncoding)
        dest.writeValue(outputEncoding)
        dest.writeValue(safe)
        dest.writeValue(cx)
        dest.writeValue(searchType)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        val CREATOR: Parcelable.Creator<Request> = object : Parcelable.Creator<Request> {


            override fun createFromParcel(`in`: Parcel): Request {
                val instance = Request()
                instance.title = `in`.readValue(String::class.java.classLoader) as String
                instance.totalResults = `in`.readValue(String::class.java.classLoader) as String
                instance.searchTerms = `in`.readValue(String::class.java.classLoader) as String
                instance.count = `in`.readValue(Int::class.java.classLoader) as Int
                instance.startIndex = `in`.readValue(Int::class.java.classLoader) as Int
                instance.inputEncoding = `in`.readValue(String::class.java.classLoader) as String
                instance.outputEncoding = `in`.readValue(String::class.java.classLoader) as String
                instance.safe = `in`.readValue(String::class.java.classLoader) as String
                instance.cx = `in`.readValue(String::class.java.classLoader) as String
                instance.searchType = `in`.readValue(String::class.java.classLoader) as String
                return instance
            }

            override fun newArray(size: Int): Array<Request?> {
                return arrayOfNulls(size)
            }

        }
    }

}
