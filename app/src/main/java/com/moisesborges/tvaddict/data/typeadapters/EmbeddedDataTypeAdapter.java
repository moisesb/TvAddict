package com.moisesborges.tvaddict.data.typeadapters;

import com.google.gson.Gson;
import com.moisesborges.tvaddict.models.Embedded;
import com.raizlabs.android.dbflow.converter.TypeConverter;

/**
 * Created by moises.anjos on 24/05/2017.
 */

public class EmbeddedDataTypeAdapter extends TypeConverter<String, Embedded> {

    private Gson mGson;

    public EmbeddedDataTypeAdapter() {
        mGson = new Gson();
    }

    @Override
    public String getDBValue(Embedded embedded) {
        return mGson.toJson(embedded);
    }

    @Override
    public Embedded getModelValue(String s) {
        return mGson.fromJson(s, Embedded.class);
    }
}
