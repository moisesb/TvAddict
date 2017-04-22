
package com.moisesborges.tvaddict.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CastMember implements Parcelable{

    @SerializedName("person")
    @Expose
    private Person person;
    @SerializedName("character")
    @Expose
    private Character character;

    public CastMember() {
    }

    protected CastMember(Parcel in) {
        person = in.readParcelable(Person.class.getClassLoader());
        character = in.readParcelable(Character.class.getClassLoader());
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(person, i);
        parcel.writeParcelable(character, i);
    }
}
