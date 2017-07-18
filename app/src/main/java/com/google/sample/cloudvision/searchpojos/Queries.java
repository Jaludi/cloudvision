
package com.google.sample.cloudvision.searchpojos;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Queries implements Parcelable
{

    @SerializedName("request")
    @Expose
    private List<Request> request = null;
    @SerializedName("nextPage")
    @Expose
    private List<NextPage> nextPage = null;
    public final static Creator<Queries> CREATOR = new Creator<Queries>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Queries createFromParcel(Parcel in) {
            Queries instance = new Queries();
            in.readList(instance.request, Request.class.getClassLoader());
            in.readList(instance.nextPage, NextPage.class.getClassLoader());
            return instance;
        }

        public Queries[] newArray(int size) {
            return (new Queries[size]);
        }

    }
    ;

    public List<Request> getRequest() {
        return request;
    }

    public void setRequest(List<Request> request) {
        this.request = request;
    }

    public List<NextPage> getNextPage() {
        return nextPage;
    }

    public void setNextPage(List<NextPage> nextPage) {
        this.nextPage = nextPage;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(request);
        dest.writeList(nextPage);
    }

    public int describeContents() {
        return  0;
    }

}
