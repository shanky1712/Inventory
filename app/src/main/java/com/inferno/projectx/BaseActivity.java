package com.inferno.projectx;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by saravana.subramanian on 8/21/17.
 */

public class BaseActivity extends AppCompatActivity implements ActivityCallback,FragmentCallback {

    @Override
    public void startActivity(Class<? extends AppCompatActivity> activity, Bundle extras, int requestCode) {

        Intent intent = new Intent(this,activity);
        if(extras!=null)intent.putExtras(extras);
        startActivityForResult(intent,requestCode);

    }


    @Override
    public void startFrgament(FragmentManager fragmentManager, Fragment fragment, boolean addToBackstack, Bundle args) {
        if(fragment!=null){
            try{
                if(fragment.isAdded()){
                    return; //or return false/true, based on where you are calling from
                }
                String backStateName = fragment.getClass().getName();
                try{
                    if (args != null && !fragment.isAdded()) fragment.setArguments(args);
                }catch (Exception e){
                    e.printStackTrace();
                }


                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.container, fragment, backStateName);
                if (addToBackstack) transaction.addToBackStack(backStateName);
                transaction.commit();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
