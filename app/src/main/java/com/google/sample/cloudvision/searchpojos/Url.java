
package com.google.sample.cloudvision.searchpojos;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Url implements Parcelable
{

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("template")
    @Expose
    private String template;
    public final static Creator<Url> CREATOR = new Creator<Url>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Url createFromParcel(Parcel in) {
            Url instance = new Url();
            instance.type = ((String) in.readValue((String.class.getClassLoader())));
            instance.template = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Url[] newArray(int size) {
            return (new Url[size]);
        }

    }
    ;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(type);
        dest.writeValue(template);
    }

    public int describeContents() {
        return  0;
    }

}
