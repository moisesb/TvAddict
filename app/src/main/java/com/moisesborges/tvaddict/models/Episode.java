
package com.moisesborges.tvaddict.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.moisesborges.tvaddict.data.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

@Table(database = AppDatabase.class)
public class Episode implements Parcelable {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Integer id;

    @Column
    @SerializedName("url")
    @Expose
    private String url;

    @Column
    @SerializedName("name")
    @Expose
    private String name;

    @ForeignKey(tableClass = Show.class)
    private Integer showId;

    @Column
    @SerializedName("season")
    @Expose
    private Integer season;

    @Column
    @SerializedName("number")
    @Expose
    private Integer number;

    @Column
    @SerializedName("airdate")
    @Expose
    private String airdate;

    @Column
    @SerializedName("airtime")
    @Expose
    private String airtime;

    @Column
    @SerializedName("airstamp")
    @Expose
    private String airstamp;

    @Column
    @SerializedName("runtime")
    @Expose
    private Integer runtime;

    @ForeignKey(saveForeignKeyModel = true)
    @SerializedName("image")
    @Expose
    private Image image;

    @Column
    @SerializedName("summary")
    @Expose
    private String summary;

    @ForeignKey(saveForeignKeyModel = true)
    @SerializedName("_links")
    @Expose
    private Links links;

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
            showId = null;
        } else {
            showId = in.readInt();
        }
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

    public Integer getShowId() {
        return showId;
    }

    public void setShowId(Integer showId) {
        this.showId = showId;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(url);
        dest.writeString(name);
        if (showId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(showId);
        }
        if (season == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(season);
        }
        if (number == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(number);
        }
        dest.writeString(airdate);
        dest.writeString(airtime);
        dest.writeString(airstamp);
        if (runtime == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(runtime);
        }
        dest.writeParcelable(image, flags);
        dest.writeString(summary);
        dest.writeParcelable(links, flags);
    }
}
