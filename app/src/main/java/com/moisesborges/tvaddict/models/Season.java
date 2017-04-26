
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
public class Season implements Parcelable {


    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Integer id;

    @Column
    @SerializedName("url")
    @Expose
    private String url;

    @Column
    @SerializedName("number")
    @Expose
    private Integer number;

    @Column
    @SerializedName("name")
    @Expose
    private String name;

    @Column
    @SerializedName("episodeOrder")
    @Expose
    private Integer episodeOrder;

    @Column
    @SerializedName("premiereDate")
    @Expose
    private String premiereDate;

    @Column
    @SerializedName("endDate")
    @Expose
    private String endDate;

    @ForeignKey(saveForeignKeyModel = true)
    @SerializedName("network")
    @Expose
    private Network network;

    @SerializedName("webChannel")
    @Expose
    private Object webChannel;

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

    @ForeignKey(tableClass = Show.class)
    private Integer showId;

    public Season() {
    }


    protected Season(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        url = in.readString();
        if (in.readByte() == 0) {
            number = null;
        } else {
            number = in.readInt();
        }
        name = in.readString();
        if (in.readByte() == 0) {
            episodeOrder = null;
        } else {
            episodeOrder = in.readInt();
        }
        premiereDate = in.readString();
        endDate = in.readString();
        network = in.readParcelable(Network.class.getClassLoader());
        image = in.readParcelable(Image.class.getClassLoader());
        summary = in.readString();
        links = in.readParcelable(Links.class.getClassLoader());
        if (in.readByte() == 0) {
            showId = null;
        } else {
            showId = in.readInt();
        }
    }

    public static final Creator<Season> CREATOR = new Creator<Season>() {
        @Override
        public Season createFromParcel(Parcel in) {
            return new Season(in);
        }

        @Override
        public Season[] newArray(int size) {
            return new Season[size];
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

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getEpisodeOrder() {
        return episodeOrder;
    }

    public void setEpisodeOrder(Integer episodeOrder) {
        this.episodeOrder = episodeOrder;
    }

    public String getPremiereDate() {
        return premiereDate;
    }

    public void setPremiereDate(String premiereDate) {
        this.premiereDate = premiereDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public Object getWebChannel() {
        return webChannel;
    }

    public void setWebChannel(Object webChannel) {
        this.webChannel = webChannel;
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

    public Integer getShowId() {
        return showId;
    }

    public void setShowId(Integer showId) {
        this.showId = showId;
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
        if (number == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(number);
        }
        dest.writeString(name);
        if (episodeOrder == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(episodeOrder);
        }
        dest.writeString(premiereDate);
        dest.writeString(endDate);
        dest.writeParcelable(network, flags);
        dest.writeParcelable(image, flags);
        dest.writeString(summary);
        dest.writeParcelable(links, flags);
        if (showId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(showId);
        }
    }
}
