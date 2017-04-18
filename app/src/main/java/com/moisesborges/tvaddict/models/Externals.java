
package com.moisesborges.tvaddict.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Externals implements Parcelable {

    @SerializedName("tvrage")
    @Expose
    private Integer tvrage;
    @SerializedName("thetvdb")
    @Expose
    private Integer thetvdb;
    @SerializedName("imdb")
    @Expose
    private String imdb;

    public Externals() {
    }

    protected Externals(Parcel in) {
        if (in.readByte() == 0) {
            tvrage = null;
        } else {
            tvrage = in.readInt();
        }
        if (in.readByte() == 0) {
            thetvdb = null;
        } else {
            thetvdb = in.readInt();
        }
        imdb = in.readString();
    }

    public static final Creator<Externals> CREATOR = new Creator<Externals>() {
        @Override
        public Externals createFromParcel(Parcel in) {
            return new Externals(in);
        }

        @Override
        public Externals[] newArray(int size) {
            return new Externals[size];
        }
    };

    public Integer getTvrage() {
        return tvrage;
    }

    public void setTvrage(Integer tvrage) {
        this.tvrage = tvrage;
    }

    public Integer getThetvdb() {
        return thetvdb;
    }

    public void setThetvdb(Integer thetvdb) {
        this.thetvdb = thetvdb;
    }

    public String getImdb() {
        return imdb;
    }

    public void setImdb(String imdb) {
        this.imdb = imdb;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (tvrage == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(tvrage);
        }
        if (thetvdb == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(thetvdb);
        }
        dest.writeString(imdb);
    }
}
