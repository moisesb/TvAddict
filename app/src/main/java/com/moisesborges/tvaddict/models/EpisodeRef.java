
package com.moisesborges.tvaddict.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EpisodeRef implements Parcelable {

    @SerializedName("href")
    @Expose
    private String href;

    public EpisodeRef() {
    }

    protected EpisodeRef(Parcel in) {
        href = in.readString();
    }

    public static final Creator<EpisodeRef> CREATOR = new Creator<EpisodeRef>() {
        @Override
        public EpisodeRef createFromParcel(Parcel in) {
            return new EpisodeRef(in);
        }

        @Override
        public EpisodeRef[] newArray(int size) {
            return new EpisodeRef[size];
        }
    };

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(href);
    }
}
