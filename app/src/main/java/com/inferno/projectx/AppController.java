package com.inferno.projectx;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by saravana.subramanian on 8/21/17.
 */

public class AppController extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        config.getMigration();

    }


    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
