
package com.google.sample.cloudvision.searchpojos;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image implements Parcelable
{

    @SerializedName("contextLink")
    @Expose
    private String contextLink;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("byteSize")
    @Expose
    private Integer byteSize;
    @SerializedName("thumbnailLink")
    @Expose
    private String thumbnailLink;
    @SerializedName("thumbnailHeight")
    @Expose
    private Integer thumbnailHeight;
    @SerializedName("thumbnailWidth")
    @Expose
    private Integer thumbnailWidth;
    public final static Creator<Image> CREATOR = new Creator<Image>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Image createFromParcel(Parcel in) {
            Image instance = new Image();
            instance.contextLink = ((String) in.readValue((String.class.getClassLoader())));
            instance.height = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.width = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.byteSize = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.thumbnailLink = ((String) in.readValue((String.class.getClassLoader())));
            instance.thumbnailHeight = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.thumbnailWidth = ((Integer) in.readValue((Integer.class.getClassLoader())));
            return instance;
        }

        public Image[] newArray(int size) {
            return (new Image[size]);
        }

    }
    ;

    public String getContextLink() {
        return contextLink;
    }

    public void setContextLink(String contextLink) {
        this.contextLink = contextLink;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getByteSize() {
        return byteSize;
    }

    public void setByteSize(Integer byteSize) {
        this.byteSize = byteSize;
    }

    public String getThumbnailLink() {
        return thumbnailLink;
    }

    public void setThumbnailLink(String thumbnailLink) {
        this.thumbnailLink = thumbnailLink;
    }

    public Integer getThumbnailHeight() {
        return thumbnailHeight;
    }

    public void setThumbnailHeight(Integer thumbnailHeight) {
        this.thumbnailHeight = thumbnailHeight;
    }

    public Integer getThumbnailWidth() {
        return thumbnailWidth;
    }

    public void setThumbnailWidth(Integer thumbnailWidth) {
        this.thumbnailWidth = thumbnailWidth;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(contextLink);
        dest.writeValue(height);
        dest.writeValue(width);
        dest.writeValue(byteSize);
        dest.writeValue(thumbnailLink);
        dest.writeValue(thumbnailHeight);
        dest.writeValue(thumbnailWidth);
    }

    public int describeContents() {
        return  0;
    }

}
