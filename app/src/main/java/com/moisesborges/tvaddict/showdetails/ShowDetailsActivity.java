package com.moisesborges.tvaddict.showdetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.moisesborges.tvaddict.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Moisés on 17/04/2017.
 */

public class ShowDetailsActivity extends AppCompatActivity {

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);
        mUnbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    public static void start(@NonNull Context context) {
        Intent intent = new Intent(context, ShowDetailsActivity.class);
        context.startActivity(intent);
    }
}
