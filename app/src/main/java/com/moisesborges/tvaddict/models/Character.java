
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
public class Character implements Parcelable {

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

    @ForeignKey(saveForeignKeyModel = true)
    @SerializedName("image")
    @Expose
    private Image image;

    @ForeignKey(saveForeignKeyModel = true)
    @SerializedName("_links")
    @Expose
    private Links links;

    public Character() {
    }


    protected Character(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        url = in.readString();
        name = in.readString();
        image = in.readParcelable(Image.class.getClassLoader());
        links = in.readParcelable(Links.class.getClassLoader());
    }

    public static final Creator<Character> CREATOR = new Creator<Character>() {
        @Override
        public Character createFromParcel(Parcel in) {
            return new Character(in);
        }

        @Override
        public Character[] newArray(int size) {
            return new Character[size];
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
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
        dest.writeParcelable(image, flags);
        dest.writeParcelable(links, flags);
    }
}
