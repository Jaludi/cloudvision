
package com.google.sample.cloudvision.searchpojos;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NextPage implements Parcelable
{

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("totalResults")
    @Expose
    private String totalResults;
    @SerializedName("searchTerms")
    @Expose
    private String searchTerms;
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("startIndex")
    @Expose
    private Integer startIndex;
    @SerializedName("inputEncoding")
    @Expose
    private String inputEncoding;
    @SerializedName("outputEncoding")
    @Expose
    private String outputEncoding;
    @SerializedName("safe")
    @Expose
    private String safe;
    @SerializedName("cx")
    @Expose
    private String cx;
    @SerializedName("searchType")
    @Expose
    private String searchType;
    public final static Creator<NextPage> CREATOR = new Creator<NextPage>() {


        @SuppressWarnings({
            "unchecked"
        })
        public NextPage createFromParcel(Parcel in) {
            NextPage instance = new NextPage();
            instance.title = ((String) in.readValue((String.class.getClassLoader())));
            instance.totalResults = ((String) in.readValue((String.class.getClassLoader())));
            instance.searchTerms = ((String) in.readValue((String.class.getClassLoader())));
            instance.count = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.startIndex = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.inputEncoding = ((String) in.readValue((String.class.getClassLoader())));
            instance.outputEncoding = ((String) in.readValue((String.class.getClassLoader())));
            instance.safe = ((String) in.readValue((String.class.getClassLoader())));
            instance.cx = ((String) in.readValue((String.class.getClassLoader())));
            instance.searchType = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public NextPage[] newArray(int size) {
            return (new NextPage[size]);
        }

    }
    ;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public String getSearchTerms() {
        return searchTerms;
    }

    public void setSearchTerms(String searchTerms) {
        this.searchTerms = searchTerms;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public String getInputEncoding() {
        return inputEncoding;
    }

    public void setInputEncoding(String inputEncoding) {
        this.inputEncoding = inputEncoding;
    }

    public String getOutputEncoding() {
        return outputEncoding;
    }

    public void setOutputEncoding(String outputEncoding) {
        this.outputEncoding = outputEncoding;
    }

    public String getSafe() {
        return safe;
    }

    public void setSafe(String safe) {
        this.safe = safe;
    }

    public String getCx() {
        return cx;
    }

    public void setCx(String cx) {
        this.cx = cx;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(title);
        dest.writeValue(totalResults);
        dest.writeValue(searchTerms);
        dest.writeValue(count);
        dest.writeValue(startIndex);
        dest.writeValue(inputEncoding);
        dest.writeValue(outputEncoding);
        dest.writeValue(safe);
        dest.writeValue(cx);
        dest.writeValue(searchType);
    }

    public int describeContents() {
        return  0;
    }

}
