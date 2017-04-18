
package com.moisesborges.tvaddict.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Links implements Parcelable{


    @SerializedName("self")
    @Expose
    private Self self;
    @SerializedName("previousepisode")
    @Expose
    private EpisodeRef previousepisode;
    @SerializedName("nextepisode")
    @Expose
    private EpisodeRef nextepisode;

    public Links() {
    }

    protected Links(Parcel in) {
        self = in.readParcelable(Self.class.getClassLoader());
        previousepisode = in.readParcelable(EpisodeRef.class.getClassLoader());
        nextepisode = in.readParcelable(EpisodeRef.class.getClassLoader());
    }

    public static final Creator<Links> CREATOR = new Creator<Links>() {
        @Override
        public Links createFromParcel(Parcel in) {
            return new Links(in);
        }

        @Override
        public Links[] newArray(int size) {
            return new Links[size];
        }
    };

    public Self getSelf() {
        return self;
    }

    public void setSelf(Self self) {
        this.self = self;
    }

    public EpisodeRef getPreviousepisode() {
        return previousepisode;
    }

    public void setPreviousepisode(EpisodeRef previousepisode) {
        this.previousepisode = previousepisode;
    }

    public EpisodeRef getNextepisode() {
        return nextepisode;
    }

    public void setNextepisode(EpisodeRef nextepisode) {
        this.nextepisode = nextepisode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(self, flags);
        dest.writeParcelable(previousepisode, flags);
        dest.writeParcelable(nextepisode, flags);
    }
}
