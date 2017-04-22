package me.androidbox.mymapapp.main;

import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.androidbox.mymapapp.R;
import timber.log.Timber;

public class MainActivity
        extends AppCompatActivity
        implements
        OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {

    private Unbinder mUnbinder;
    private GoogleMap mGoogleMap;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    @BindView(R.id.drawerLayout) DrawerLayout mDrawerLayout;
    @BindView(R.id.navigationView) NavigationView mNavigationView;
    @BindView(R.id.main_toolbar) Toolbar mToolbar;
    private ActionBarDrawerToggle mActionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUnbinder = ButterKnife.bind(MainActivity.this);

        setupToolbar();
        setupNavigationDrawer();

        mGoogleApiClient = new GoogleApiClient.Builder(MainActivity.this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(MainActivity.this)
                .addOnConnectionFailedListener(MainActivity.this)
                .build();

        final MapFragment mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MainActivity.this);
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setupNavigationDrawer() {
        mActionBarDrawerToggle = new ActionBarDrawerToggle(
                MainActivity.this,
                mDrawerLayout,
                R.string.drawer_open,
                R.string.drawer_close);

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectMenuOption(item);
                return true;
            }
        });
    }

    private void selectMenuOption(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.muTaxi:
                Toast.makeText(MainActivity.this, "Taxi service", Toast.LENGTH_SHORT).show();
                break;

            case R.id.muAvailable:
                Toast.makeText(MainActivity.this, "Available taxis", Toast.LENGTH_SHORT).show();
                break;

            case R.id.muDisplay:
                Toast.makeText(MainActivity.this, "Display all taxis", Toast.LENGTH_SHORT).show();
                break;

            case R.id.muNormal:
                switchMap();
                break;

            case R.id.muHybrid:
                switchHybrid();
                break;

            case R.id.muSatellite:
                switchSatellite();
                break;

            case R.id.muTerrain:
                switchTerrain();
                break;

            case R.id.muNone:
                switchNone();
                break;
        }

        /* Highlight the selected item has been done by NavigationView */
        item.setChecked(true);
        /* Set action bar title */
        setTitle(item.getTitle());
        /* Close the navigation drawer */
        mDrawerLayout.closeDrawer(GravityCompat.START, true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START, true);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mActionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
    }

    public void switchMap() {
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    public void switchHybrid() {
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }

    public void switchSatellite() {
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }

    public void switchNone() {
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NONE);
    }

    public void switchTerrain() {
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
    }

    public void goToDublin() {

/* 53.3478 6.2597 */

        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(updateCameraPosition(53.3478, 6.2597)), 5000, new GoogleMap.CancelableCallback() {
            @Override
            public void onFinish() {
                Timber.d("Dublin onFinish");
            }

            @Override
            public void onCancel() {
                Timber.d("Dublin onCancel");
            }
        });
    }

    public void goToSeattle() {

/* 47.6204 -122.3491 */

        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(updateCameraPosition(47.6204, -122.3491)), 5000, new GoogleMap.CancelableCallback() {
            @Override
            public void onFinish() {
                Timber.d("Seattle onFinish");
            }

            @Override
            public void onCancel() {
                Timber.d("Seattle onCancel");
            }
        });
    }

    public void gotToNewYork() {
        /* 40.7127 74.0059 */
        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(updateCameraPosition(40.7127, 74.0059)), 5000, new GoogleMap.CancelableCallback() {
            @Override
            public void onFinish() {
                Timber.d(" New York onFinish");
            }

            @Override
            public void onCancel() {
                Timber.d("New York onCancel");
            }
        });
    }

    private CameraPosition updateCameraPosition(double lat, double lng) {
        LatLng latLng = new LatLng(lat, lng);
        return CameraPosition.builder()
                .target(latLng)
                .zoom(14)
                .bearing(0L)
                .tilt(20L)
                .build();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Timber.d("onRequestPermissionResult requestCode %d grantResult", requestCode);

        switch(requestCode) {
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    try {
                        mGoogleMap.setMyLocationEnabled(true);
                    } catch (SecurityException e) {
                        Timber.e("onRequestPermissions %s", e.getMessage());
                    }

                    mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
                    mGoogleMap.getUiSettings().setCompassEnabled(true);
                    mGoogleMap.getUiSettings().setZoomControlsEnabled(true);

                    LatLng myHome = new LatLng(40.7478, -73.9857);
                    CameraPosition target = CameraPosition.builder().target(myHome).zoom(14).build();
                    mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(target));
                }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Timber.d("onMapReady");

        mGoogleMap = googleMap;
        int hasAccessFineLocation = ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        int hasAccessCoarseLocation = ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION);

        if(hasAccessFineLocation == PackageManager.PERMISSION_GRANTED && hasAccessCoarseLocation == PackageManager.PERMISSION_GRANTED) {
            mGoogleMap.setMyLocationEnabled(true);

            mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
            mGoogleMap.getUiSettings().setCompassEnabled(true);
            mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
            mGoogleMap.getUiSettings().setMapToolbarEnabled(true);

            LatLng myHome = new LatLng(40.7478, -73.9857);
            CameraPosition target = CameraPosition.builder().target(myHome).zoom(14).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(target));
        }
        else {
            /* Request permission */
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

/*
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(myHome);
        markerOptions.title("Steve Home");
*/
   //     mGoogleMap.addMarker(markerOptions);


/*
        PolygonOptions polygonOptions = new PolygonOptions();
        polygonOptions.geodesic(true)
                .add(new LatLng(40.7668, -73.9960))
                .add(new LatLng(40.7558, -73.9850))
                .add(new LatLng(40.7348, -73.9740))
                .add(new LatLng(40.7238, -73.9430));
*/

  //      mGoogleMap.addPolygon(polygonOptions);

/*
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(myHome).fillColor(R.color.colorAccent);
        circleOptions.radius(1000L);
*/
//        mGoogleMap.addCircle(circleOptions);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Timber.d("onConnected");
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000L);

        try {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    Timber.d("onLocationChanged: %f", location.getLatitude());
                }
            });
        }
        catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Timber.d("onConnectionSuspended: i %d", i);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Timber.d("onConnectionFailed %d", connectionResult.getErrorCode());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
