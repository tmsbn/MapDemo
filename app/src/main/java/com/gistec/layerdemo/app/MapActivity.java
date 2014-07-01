package com.gistec.layerdemo.app;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISDynamicMapServiceLayer;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.android.map.ogc.WMSLayer;
import com.github.johnpersano.supertoasts.SuperActivityToast;
import com.github.johnpersano.supertoasts.SuperToast;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MapActivity extends Activity implements OnStatusChangedListener {

    String baseMapURL, featureLayerURL, userName, password;

    @InjectView(R.id.map)
    MapView mapView;

    SuperActivityToast progressToast;

    WMSLayer wmsLayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.inject(this);

        setUpProgressToast();
        setupMap();

    }


    private void setupMap() {

        mapView.setOnStatusChangedListener(this);
    }

    private void setUpProgressToast() {

        progressToast = new SuperActivityToast(this, SuperToast.Type.PROGRESS);
        progressToast.setIndeterminate(true);
        progressToast.setProgressIndeterminate(true);
        progressToast.setTouchToDismiss(true);
        progressToast.setText("Loading map...");
        progressToast.show();

    }


    private void addWMSLayer() {

        wmsLayer = new WMSLayer("http://mesonet.agron.iastate.edu/cgi-bin/wms/us/mrms.cgi?");
       // wmsLayer.setVisibleLayer(new String[]{"basic"});
        mapView.addLayer(wmsLayer);
        wmsLayer.setOnStatusChangedListener(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();
        switch (id) {

            case R.id.refreshItem:
                wmsLayer.refresh();
               // progressToast.show();

                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    private void restorePreviousSettings() {

        SharedPreferences preferences = getSharedPreferences(Globals.SETTINGS_FILE, Context.MODE_PRIVATE);
        baseMapURL = preferences.getString(Globals.BASEMAP_KEY, "");
        featureLayerURL = preferences.getString(Globals.FLAYER_KEY, "");

        userName = preferences.getString(Globals.USERNAME_KEY, "");
        password = preferences.getString(Globals.PASSWORD_KEY, "");

        if (baseMapURL.equals(""))
            baseMapURL = Globals.BASEMAP_URL;
        if (featureLayerURL.equals(""))
            featureLayerURL = Globals.FLAYER_URL;

        if (userName.equals(""))
            userName = Globals.USERNAME;

        if (password.equals(""))
            password = Globals.PASSWORD;


    }

    @Override
    public void onStatusChanged(Object object, STATUS status) {

        if (object == mapView) {

            if (status == STATUS.INITIALIZED) {

                addWMSLayer();
                progressToast.setText("Loading WMS Layer...");

            } else if (status == STATUS.INITIALIZATION_FAILED) {

                progressToast.dismiss();
                Toast.makeText(this, "Cannot load map", Toast.LENGTH_SHORT).show();

            }

        } else if (object instanceof WMSLayer) {
            if (status == STATUS.INITIALIZED) {

                progressToast.dismiss();

            } else if (status == STATUS.INITIALIZATION_FAILED) {

                progressToast.dismiss();
                Toast.makeText(this, "Failed to LOAD WMS Layer", Toast.LENGTH_SHORT).show();

            }


        } else if (object instanceof ArcGISDynamicMapServiceLayer) {
            if (status == STATUS.INITIALIZED) {

                progressToast.dismiss();

            } else if (status == STATUS.INITIALIZATION_FAILED) {

                progressToast.dismiss();
                Toast.makeText(this, getString(R.string.FailedToLoadFeatureLayer), Toast.LENGTH_SHORT).show();

            }


        }

    }

}
