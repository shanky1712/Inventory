package com.inferno.projectx;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by saravana.subramanian on 8/21/17.
 */

public interface FragmentCallback {

    void startFrgament(FragmentManager fragmentManager, Fragment fragment, boolean addToBackstack, Bundle args);
}
