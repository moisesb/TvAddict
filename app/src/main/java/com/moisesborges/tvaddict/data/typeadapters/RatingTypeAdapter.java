package com.moisesborges.tvaddict.data.typeadapters;

import com.google.gson.Gson;
import com.moisesborges.tvaddict.models.Rating;
import com.raizlabs.android.dbflow.converter.TypeConverter;

/**
 * Created by moises.anjos on 22/08/2017.
 */

public class RatingTypeAdapter  extends TypeConverter<String, Rating> {

    private final Gson mGson = new Gson();

    @Override
    public String getDBValue(Rating rating) {
        return mGson.toJson(rating);
    }

    @Override
    public Rating getModelValue(String data) {
        return mGson.fromJson(data, Rating.class);
    }
}
