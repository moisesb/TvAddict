
package com.moisesborges.tvaddict.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Links {

    @SerializedName("self")
    @Expose
    private Self self;
    @SerializedName("previousepisode")
    @Expose
    private Previousepisode previousepisode;
    @SerializedName("nextepisode")
    @Expose
    private Nextepisode nextepisode;

    public Self getSelf() {
        return self;
    }

    public void setSelf(Self self) {
        this.self = self;
    }

    public Previousepisode getPreviousepisode() {
        return previousepisode;
    }

    public void setPreviousepisode(Previousepisode previousepisode) {
        this.previousepisode = previousepisode;
    }

    public Nextepisode getNextepisode() {
        return nextepisode;
    }

    public void setNextepisode(Nextepisode nextepisode) {
        this.nextepisode = nextepisode;
    }

}
