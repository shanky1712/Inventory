package com.inferno.projectx;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by saravana.subramanian on 8/21/17.
 */

public class BaseFragment extends Fragment implements ActivityCallback,FragmentCallback{

    ActivityCallback activityCallback;
    FragmentCallback fragmentCallback;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activityCallback = (ActivityCallback)context;
        fragmentCallback = (FragmentCallback)context;
    }

    @Override
    public void startActivity(Class<? extends AppCompatActivity> activity, Bundle extras, int requestCode) {
        activityCallback.startActivity(activity,extras,requestCode);
    }

    @Override
    public void startFrgament(FragmentManager fragmentManager, Fragment fragment, boolean addToBackstack, Bundle args) {
        fragmentCallback.startFrgament(fragmentManager,fragment,addToBackstack,args);
    }
}
