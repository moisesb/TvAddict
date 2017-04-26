
package com.moisesborges.tvaddict.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.moisesborges.tvaddict.data.AppDatabase;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

@Table(database = AppDatabase.class)
public class CastMember implements Parcelable {

    @PrimaryKey(autoincrement = true)
    private long id;

    @ForeignKey(saveForeignKeyModel = true)
    @SerializedName("person")
    @Expose
    private Person person;

    @ForeignKey(saveForeignKeyModel = true)
    @SerializedName("character")
    @Expose
    private Character character;

    @ForeignKey(tableClass = Show.class)
    private Integer showId;

    public CastMember() {
    }


    protected CastMember(Parcel in) {
        id = in.readLong();
        person = in.readParcelable(Person.class.getClassLoader());
        character = in.readParcelable(Character.class.getClassLoader());
        if (in.readByte() == 0) {
            showId = null;
        } else {
            showId = in.readInt();
        }
    }

    public static final Creator<CastMember> CREATOR = new Creator<CastMember>() {
        @Override
        public CastMember createFromParcel(Parcel in) {
            return new CastMember(in);
        }

        @Override
        public CastMember[] newArray(int size) {
            return new CastMember[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
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
        dest.writeLong(id);
        dest.writeParcelable(person, flags);
        dest.writeParcelable(character, flags);
        if (showId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(showId);
        }
    }
}
