package com.moisesborges.tvaddict.data.typeadapters;

import com.google.gson.Gson;
import com.raizlabs.android.dbflow.converter.TypeConverter;

import java.util.List;

/**
 * Created by moises.anjos on 24/04/2017.
 */
@com.raizlabs.android.dbflow.annotation.TypeConverter
public class StringListTypeAdapter extends TypeConverter<String, List> {

    private Gson gson = new Gson();

    @Override
    public String getDBValue(List list) {
        return gson.toJson(list);
    }

    @Override
    public List getModelValue(String s) {
        return gson.fromJson(s, List.class);
    }
}
