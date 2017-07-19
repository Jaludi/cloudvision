package com.google.sample.cloudvision.searchpojos

import android.os.Parcel
import android.os.Parcelable

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SearchInformation : Parcelable {

    @SerializedName("searchTime")
    @Expose
    var searchTime: Double? = null
    @SerializedName("formattedSearchTime")
    @Expose
    var formattedSearchTime: String? = null
    @SerializedName("totalResults")
    @Expose
    var totalResults: String? = null
    @SerializedName("formattedTotalResults")
    @Expose
    var formattedTotalResults: String? = null

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeValue(searchTime)
        dest.writeValue(formattedSearchTime)
        dest.writeValue(totalResults)
        dest.writeValue(formattedTotalResults)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        val CREATOR: Parcelable.Creator<SearchInformation> = object : Parcelable.Creator<SearchInformation> {


            override fun createFromParcel(`in`: Parcel): SearchInformation {
                val instance = SearchInformation()
                instance.searchTime = `in`.readValue(Double::class.java.classLoader) as Double
                instance.formattedSearchTime = `in`.readValue(String::class.java.classLoader) as String
                instance.totalResults = `in`.readValue(String::class.java.classLoader) as String
                instance.formattedTotalResults = `in`.readValue(String::class.java.classLoader) as String
                return instance
            }

            override fun newArray(size: Int): Array<SearchInformation?> {
                return arrayOfNulls(size)
            }

        }
    }

}
