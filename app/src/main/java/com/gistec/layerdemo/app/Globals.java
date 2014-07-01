package com.gistec.layerdemo.app;

import com.esri.core.io.UserCredentials;

/**
 * Created by tmsbn on 5/15/14.
 */
public class Globals {

    public static final String SETTINGS_FILE="settings_file";
    public static final String BASEMAP_KEY="basemap";
    public static final String FLAYER_KEY="flayer";
    public static final String USERNAME_KEY="mdarbuser";
    public static final String PASSWORD_KEY="md4r6us3r@321";


    public static final String BASEMAP_URL="http://geoportal.abudhabi.ae/rest/services/BaseMapEnglish/MapServer";
    public static final String FLAYER_URL="http://www.darb.ae/ArcGIS/rest/services/MDARB/GeofeedBack10/MapServer";

    public static final String USERNAME="mdarbuser";
    public static final String PASSWORD="md4r6us3r@321";

    public static UserCredentials getWebDarbUserCredentials(String userName,String password) {
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setUserAccount(userName, password);
        return userCredentials;
    }

}
