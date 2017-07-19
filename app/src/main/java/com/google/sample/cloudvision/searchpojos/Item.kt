package com.google.sample.cloudvision.searchpojos

import android.os.Parcel
import android.os.Parcelable

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Item : Parcelable {

    @SerializedName("kind")
    @Expose
    var kind: String? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("htmlTitle")
    @Expose
    var htmlTitle: String? = null
    @SerializedName("link")
    @Expose
    var link: String? = null
    @SerializedName("displayLink")
    @Expose
    var displayLink: String? = null
    @SerializedName("snippet")
    @Expose
    var snippet: String? = null
    @SerializedName("htmlSnippet")
    @Expose
    var htmlSnippet: String? = null
    @SerializedName("mime")
    @Expose
    var mime: String? = null
    @SerializedName("image")
    @Expose
    var image: Image? = null

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeValue(kind)
        dest.writeValue(title)
        dest.writeValue(htmlTitle)
        dest.writeValue(link)
        dest.writeValue(displayLink)
        dest.writeValue(snippet)
        dest.writeValue(htmlSnippet)
        dest.writeValue(mime)
        dest.writeValue(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        val CREATOR: Parcelable.Creator<Item> = object : Parcelable.Creator<Item> {


            override fun createFromParcel(`in`: Parcel): Item {
                val instance = Item()
                instance.kind = `in`.readValue(String::class.java.classLoader) as String
                instance.title = `in`.readValue(String::class.java.classLoader) as String
                instance.htmlTitle = `in`.readValue(String::class.java.classLoader) as String
                instance.link = `in`.readValue(String::class.java.classLoader) as String
                instance.displayLink = `in`.readValue(String::class.java.classLoader) as String
                instance.snippet = `in`.readValue(String::class.java.classLoader) as String
                instance.htmlSnippet = `in`.readValue(String::class.java.classLoader) as String
                instance.mime = `in`.readValue(String::class.java.classLoader) as String
                instance.image = `in`.readValue(Image::class.java.classLoader) as Image
                return instance
            }

            override fun newArray(size: Int): Array<Item?> {
                return arrayOfNulls(size)
            }

        }
    }

}
