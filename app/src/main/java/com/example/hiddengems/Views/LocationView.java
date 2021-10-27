package com.example.hiddengems.Views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.view.ViewCompat;

import com.example.hiddengems.R;
import com.example.hiddengems.dataModels.location;

public class LocationView extends LinearLayout {
    //Text views
    private TextView mlocationName, mdistanceAway;
    private location mlocation;
    private double mdistance;

    public LocationView(Context context) {
        this(context, null);
    }

    public LocationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LocationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LocationView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {

        inflate(getContext(), R.layout.location_view, this);

        mlocationName = (TextView) findViewById(R.id.locationName);
        mdistanceAway = (TextView) findViewById(R.id.distanceAway);

        mdistanceAway.setVisibility(GONE);
        mlocationName.setVisibility(GONE);

        mdistanceAway.setAlpha(0);
        mlocationName.setAlpha(0);

        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                ViewCompat.animate(mlocationName).alpha(1).setDuration(500);
                ViewCompat.animate(mdistanceAway).alpha(1).setDuration(500);

                return false;
            }
        });
    }

    public void setLocation(location loc, double distance) {
        mlocation = loc;
        mdistance = distance;
        setupView();
    }

    private void setupView() {
        mlocationName.setVisibility(VISIBLE);
        mlocationName.setText(mlocation.Name);
        mdistanceAway.setVisibility(VISIBLE);
        mdistanceAway.setText(mdistance + " Miles Away");
    }

}
