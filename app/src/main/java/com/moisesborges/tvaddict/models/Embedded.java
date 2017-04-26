package com.moisesborges.tvaddict.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.moisesborges.tvaddict.data.AppDatabase;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

/**
 * Created by Moisés on 21/04/2017.
 */

public class Embedded implements Parcelable {

    @SerializedName("episodes")
    @Expose
    private List<Episode> episodes = null;

    @SerializedName("seasons")
    @Expose
    private List<Season> seasons = null;

    @SerializedName("cast")
    @Expose
    private List<CastMember> cast = null;

    public Embedded() {
    }


    protected Embedded(Parcel in) {
        episodes = in.createTypedArrayList(Episode.CREATOR);
        seasons = in.createTypedArrayList(Season.CREATOR);
        cast = in.createTypedArrayList(CastMember.CREATOR);
    }

    public static final Creator<Embedded> CREATOR = new Creator<Embedded>() {
        @Override
        public Embedded createFromParcel(Parcel in) {
            return new Embedded(in);
        }

        @Override
        public Embedded[] newArray(int size) {
            return new Embedded[size];
        }
    };

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "episodes", isVariablePrivate = true)
    public List<Episode> getEpisodes() {
        if (episodes == null) {
            // TODO: 24/04/2017 fetch Episodes
        }
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
    }

    public List<CastMember> getCast() {
        return cast;
    }

    public void setCast(List<CastMember> cast) {
        this.cast = cast;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(episodes);
        dest.writeTypedList(seasons);
        dest.writeTypedList(cast);
    }
}
