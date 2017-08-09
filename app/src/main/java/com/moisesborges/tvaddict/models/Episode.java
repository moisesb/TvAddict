
package com.moisesborges.tvaddict.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.moisesborges.tvaddict.data.AppDatabase;
import com.moisesborges.tvaddict.utils.DateUtils;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

public class Episode implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("season")
    @Expose
    private Integer season;

    @SerializedName("number")
    @Expose
    private Integer number;

    @SerializedName("airdate")
    @Expose
    private String airdate;

    @SerializedName("airtime")
    @Expose
    private String airtime;

    @SerializedName("airstamp")
    @Expose
    private String airstamp;

    @SerializedName("runtime")
    @Expose
    private Integer runtime;

    @SerializedName("image")
    @Expose
    private Image image;

    @SerializedName("summary")
    @Expose
    private String summary;

    @SerializedName("_links")
    @Expose
    private Links links;

    private boolean watched;

    public Episode() {
    }

    protected Episode(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        url = in.readString();
        name = in.readString();
        if (in.readByte() == 0) {
            season = null;
        } else {
            season = in.readInt();
        }
        if (in.readByte() == 0) {
            number = null;
        } else {
            number = in.readInt();
        }
        airdate = in.readString();
        airtime = in.readString();
        airstamp = in.readString();
        if (in.readByte() == 0) {
            runtime = null;
        } else {
            runtime = in.readInt();
        }
        image = in.readParcelable(Image.class.getClassLoader());
        summary = in.readString();
        links = in.readParcelable(Links.class.getClassLoader());
        watched = in.readByte() != 0;
    }

    public static final Creator<Episode> CREATOR = new Creator<Episode>() {
        @Override
        public Episode createFromParcel(Parcel in) {
            return new Episode(in);
        }

        @Override
        public Episode[] newArray(int size) {
            return new Episode[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getAirdate() {
        return airdate;
    }

    public void setAirdate(String airdate) {
        this.airdate = airdate;
    }

    public String getAirtime() {
        return airtime;
    }

    public void setAirtime(String airtime) {
        this.airtime = airtime;
    }

    public String getAirstamp() {
        return airstamp;
    }

    public void setAirstamp(String airstamp) {
        this.airstamp = airstamp;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public boolean wasWatched() {
        return watched;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }

    public boolean wasAired() {
        return !DateUtils.timeInTheFuture(airdate + " " + airtime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        parcel.writeString(url);
        parcel.writeString(name);
        if (season == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(season);
        }
        if (number == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(number);
        }
        parcel.writeString(airdate);
        parcel.writeString(airtime);
        parcel.writeString(airstamp);
        if (runtime == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(runtime);
        }
        parcel.writeParcelable(image, i);
        parcel.writeString(summary);
        parcel.writeParcelable(links, i);
        parcel.writeByte((byte) (watched ? 1 : 0));
    }
}
