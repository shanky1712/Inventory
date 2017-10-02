package com.inferno.projectx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by saravana.subramanian on 8/21/17.
 */

public interface ActivityCallback {

    void startActivity(Class<? extends AppCompatActivity> activity, Bundle extras, int requestCode);
}
