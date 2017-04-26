
package com.moisesborges.tvaddict.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.moisesborges.tvaddict.data.AppDatabase;
import com.moisesborges.tvaddict.data.typeadapters.StringListTypeAdapter;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

@Table(database = AppDatabase.class)
public class Show implements Parcelable{

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

    @Column
    @SerializedName("type")
    @Expose
    private String type;

    @Column
    @SerializedName("language")
    @Expose
    private String language;

    @Column(typeConverter = StringListTypeAdapter.class)
    @SerializedName("genres")
    @Expose
    private List genres = null;

    @Column
    @SerializedName("status")
    @Expose
    private String status;

    @Column
    @SerializedName("runtime")
    @Expose
    private Integer runtime;

    @Column
    @SerializedName("premiered")
    @Expose
    private String premiered;

    @ForeignKey(saveForeignKeyModel = true)
    @SerializedName("schedule")
    @Expose
    private Schedule schedule;

    @ForeignKey(saveForeignKeyModel = true)
    @SerializedName("rating")
    @Expose
    private Rating rating;

    @Column
    @SerializedName("weight")
    @Expose
    private Integer weight;

    @ForeignKey(saveForeignKeyModel = true)
    @SerializedName("network")
    @Expose
    private Network network;

    @SerializedName("webChannel")
    @Expose
    private Object webChannel; // TODO: 24/04/2017 Fix this field
    @SerializedName("externals")
    @Expose
    private Externals externals;

    @ForeignKey(saveForeignKeyModel = true)
    @SerializedName("image")
    @Expose
    private Image image;

    @Column
    @SerializedName("summary")
    @Expose
    private String summary;

    @Column
    @SerializedName("updated")
    @Expose
    private Integer updated;

    @ForeignKey(saveForeignKeyModel = true)
    @SerializedName("_links")
    @Expose
    private Links links;

    @SerializedName("_embedded")
    @Expose
    private Embedded embedded;

    public Show() {
    }

    protected Show(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        url = in.readString();
        name = in.readString();
        type = in.readString();
        language = in.readString();
        genres = in.createStringArrayList();
        status = in.readString();
        if (in.readByte() == 0) {
            runtime = null;
        } else {
            runtime = in.readInt();
        }
        premiered = in.readString();
        schedule = in.readParcelable(Schedule.class.getClassLoader());
        rating = in.readParcelable(Rating.class.getClassLoader());
        if (in.readByte() == 0) {
            weight = null;
        } else {
            weight = in.readInt();
        }
        network = in.readParcelable(Network.class.getClassLoader());
        externals = in.readParcelable(Externals.class.getClassLoader());
        image = in.readParcelable(Image.class.getClassLoader());
        summary = in.readString();
        if (in.readByte() == 0) {
            updated = null;
        } else {
            updated = in.readInt();
        }
        links = in.readParcelable(Links.class.getClassLoader());
    }

    public static final Creator<Show> CREATOR = new Creator<Show>() {
        @Override
        public Show createFromParcel(Parcel in) {
            return new Show(in);
        }

        @Override
        public Show[] newArray(int size) {
            return new Show[size];
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public String getPremiered() {
        return premiered;
    }

    public void setPremiered(String premiered) {
        this.premiered = premiered;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
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

    public Externals getExternals() {
        return externals;
    }

    public void setExternals(Externals externals) {
        this.externals = externals;
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

    public Integer getUpdated() {
        return updated;
    }

    public void setUpdated(Integer updated) {
        this.updated = updated;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    @Nullable
    public Embedded getEmbedded() {
        return embedded;
    }

    public void setEmbedded(Embedded embedded) {
        this.embedded = embedded;
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
        dest.writeString(type);
        dest.writeString(language);
        dest.writeStringList(genres);
        dest.writeString(status);
        if (runtime == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(runtime);
        }
        dest.writeString(premiered);
        dest.writeParcelable(schedule, flags);
        dest.writeParcelable(rating, flags);
        if (weight == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(weight);
        }
        dest.writeParcelable(network, flags);
        dest.writeParcelable(externals, flags);
        dest.writeParcelable(image, flags);
        dest.writeString(summary);
        if (updated == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(updated);
        }
        dest.writeParcelable(links, flags);
    }
}
