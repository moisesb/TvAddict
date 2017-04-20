package com.moisesborges.tvaddict.utils;

import android.view.View;
import android.widget.ProgressBar;

/**
 * Created by moises.anjos on 19/04/2017.
 */

public abstract class ProgressBarHelper {

    private ProgressBarHelper() {
    }

    public static void show(ProgressBar progressBar, boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }
}
