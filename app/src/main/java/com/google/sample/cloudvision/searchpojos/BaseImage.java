
package com.google.sample.cloudvision.searchpojos;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BaseImage implements Parcelable
{

    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("url")
    @Expose
    private Url url;
    @SerializedName("queries")
    @Expose
    private Queries queries;
    @SerializedName("context")
    @Expose
    private Context context;
    @SerializedName("searchInformation")
    @Expose
    private SearchInformation searchInformation;
    @SerializedName("items")
    @Expose
    private List<Item> items = null;
    public final static Creator<BaseImage> CREATOR = new Creator<BaseImage>() {


        @SuppressWarnings({
            "unchecked"
        })
        public BaseImage createFromParcel(Parcel in) {
            BaseImage instance = new BaseImage();
            instance.kind = ((String) in.readValue((String.class.getClassLoader())));
            instance.url = ((Url) in.readValue((Url.class.getClassLoader())));
            instance.queries = ((Queries) in.readValue((Queries.class.getClassLoader())));
            instance.context = ((Context) in.readValue((Context.class.getClassLoader())));
            instance.searchInformation = ((SearchInformation) in.readValue((SearchInformation.class.getClassLoader())));
            in.readList(instance.items, Item.class.getClassLoader());
            return instance;
        }

        public BaseImage[] newArray(int size) {
            return (new BaseImage[size]);
        }

    }
    ;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Url getUrl() {
        return url;
    }

    public void setUrl(Url url) {
        this.url = url;
    }

    public Queries getQueries() {
        return queries;
    }

    public void setQueries(Queries queries) {
        this.queries = queries;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public SearchInformation getSearchInformation() {
        return searchInformation;
    }

    public void setSearchInformation(SearchInformation searchInformation) {
        this.searchInformation = searchInformation;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(kind);
        dest.writeValue(url);
        dest.writeValue(queries);
        dest.writeValue(context);
        dest.writeValue(searchInformation);
        dest.writeList(items);
    }

    public int describeContents() {
        return  0;
    }

}
