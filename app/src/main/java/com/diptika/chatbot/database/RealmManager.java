package com.diptika.chatbot.database;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Manager for setting config, and getting realm instance.
 */
public class RealmManager {
    private static final String DB_NAME = "com.diptika.chatbot.realm";
    private static final int DB_VERSION = 1;
    private static RealmManager sInstance;

    private RealmManager() {
        // required default constructor.
    }

    public static RealmManager getInstance() {
        if (sInstance == null) {
            sInstance = new RealmManager();
        }
        return sInstance;
    }

    /**
     * init realm with configurations.
     *
     * @param context application context
     */
    public void init(Context context) {
        Realm.init(context);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name(DB_NAME)
                .schemaVersion(DB_VERSION)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
        Realm.compactRealm(realmConfiguration);
    }

    public Realm getRealm() {
        return Realm.getDefaultInstance();
    }


}
