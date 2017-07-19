package com.google.sample.cloudvision.searchpojos

import android.os.Parcel
import android.os.Parcelable

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BaseImage : Parcelable {

    @SerializedName("kind")
    @Expose
    var kind: String? = null
    @SerializedName("url")
    @Expose
    var url: Url? = null
    @SerializedName("queries")
    @Expose
    var queries: Queries? = null
    @SerializedName("context")
    @Expose
    var context: Context? = null
    @SerializedName("searchInformation")
    @Expose
    var searchInformation: SearchInformation? = null
    @SerializedName("items")
    @Expose
    var items: List<Item>? = null

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeValue(kind)
        dest.writeValue(url)
        dest.writeValue(queries)
        dest.writeValue(context)
        dest.writeValue(searchInformation)
        dest.writeList(items)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        val CREATOR: Parcelable.Creator<BaseImage> = object : Parcelable.Creator<BaseImage> {


            override fun createFromParcel(`in`: Parcel): BaseImage {
                val instance = BaseImage()
                instance.kind = `in`.readValue(String::class.java.classLoader) as String
                instance.url = `in`.readValue(Url::class.java.classLoader) as Url
                instance.queries = `in`.readValue(Queries::class.java.classLoader) as Queries
                instance.context = `in`.readValue(Context::class.java.classLoader) as Context
                instance.searchInformation = `in`.readValue(SearchInformation::class.java.classLoader) as SearchInformation
                `in`.readList(instance.items, Item::class.java.classLoader)
                return instance
            }

            override fun newArray(size: Int): Array<BaseImage?> {
                return arrayOfNulls(size)
            }

        }
    }

}
