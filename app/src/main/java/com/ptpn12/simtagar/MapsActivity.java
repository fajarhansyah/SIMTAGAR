package com.ptpn12.simtagar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.maps.android.collections.GroundOverlayManager;
import com.google.maps.android.collections.MarkerManager;
import com.google.maps.android.collections.PolygonManager;
import com.google.maps.android.collections.PolylineManager;
import com.google.maps.android.data.Feature;
import com.google.maps.android.data.kml.KmlLayer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;


@SuppressWarnings("deprecation")
public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener {
    FloatingActionButton menu_aktifitas, menu_kejadian, menu_patok;
    FloatingActionButton menu_jalan, menu_jembatan, menu_tanaman;
    FloatingActionButton menu_saluran, menu_geotag;
    ProgressDialog progressDialog;
    Dialog pindah_kebun;


    private static final String TAG = "MapsActivity";
    private GoogleMap mMap;
    MySQLLiteHelper sws_db;
    DB_Setting dbsetting;
    TextView tv_nama;
    TextView tv_jab;
    TextView tv_koord;
    TextView header_nama;
    TextView header_jab;
    ImageButton btn_myloc, btn_goto;
    String glob_sekitar_mulai = "0";
    String glob_sekitar_jarak = "5";
    Toolbar toolbar;

    Button peta_pilih_jenis;
    RadioButton peta_jenis1, peta_jenis2, peta_jenis3, peta_jenis4;
    TableLayout peta_tl;
    TableRow peta_tr1;

    private Location currentLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int LOCATION_REQUEST_CODE = 101;

    private double wayLatitude = 0.0, wayLongitude = 0.0;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    private int requestingLocationUpdates = 1;

    Marker myposition = null;

    double sws_x = 0f;
    double sws_y = 0f;

    ArrayList<Marker> markerlist = new ArrayList<Marker>();
    ArrayList<String> markertaglist = new ArrayList<String>();

    Marker marker_circle = null;

    ArrayList<KmlLayer> kmllayerlist = new ArrayList<KmlLayer>();
    ArrayList<String> kmlnama = new ArrayList<String>();
    ArrayList<Integer> kmltampil = new ArrayList<Integer>();
    ArrayList<Integer> kmlidlist = new ArrayList<Integer>();

    double[] glob_xy = {0.0, 0.0};

    int forthefirsttime = 1;

    /**
     * Noted :
     * Adding new onCreate, onBackPressed, onNavigationItemSelected
     * Adding implement NavigationView.OnNavigationItemSelectedListener
     * Comment oncreate, onBackPressed, onCreateOptionsMenu, onPause, onResume
     * Move all code activity_maps to content_main
     * More changing layout view activity_map
     * reference : https://dedykuncoro.com/2016/03/membuat-navigation-drawer-menu-android.html
     */

    MarkerManager markerManager;
    GroundOverlayManager groundOverlayManager;
    PolygonManager polygonManager;
    PolylineManager polylineManager;

    DrawerLayout drawer;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(this, "Membutuhkan Izin Lokasi", Toast.LENGTH_SHORT).show();
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        1);
            }
        } else {
            // Permission has already been granted
            Toast.makeText(this, "Izin Lokasi diberikan", Toast.LENGTH_SHORT).show();
        }

        sws_db = new MySQLLiteHelper(getApplicationContext());
        dbsetting = sws_db.DBSetting_Get();

        tv_nama = (TextView) findViewById(R.id.peta_nama);
        tv_jab = (TextView) findViewById(R.id.peta_jab);
        tv_koord = (TextView) findViewById(R.id.peta_koord);
        btn_myloc = (ImageButton) findViewById(R.id.map_myloc);
        btn_goto = (ImageButton) findViewById(R.id.map_searchloc);

        menu_aktifitas = (FloatingActionButton) findViewById(R.id.peta_fab_aktifitas);
        menu_kejadian = (FloatingActionButton) findViewById(R.id.peta_fab_kejadian);
        menu_patok = (FloatingActionButton) findViewById(R.id.peta_fab_patok);
        menu_jalan = (FloatingActionButton) findViewById(R.id.peta_fab_jalan);
        menu_jembatan = (FloatingActionButton) findViewById(R.id.peta_fab_jembatan);
        menu_tanaman = (FloatingActionButton) findViewById(R.id.peta_fab_tanaman);
        menu_saluran = (FloatingActionButton) findViewById(R.id.peta_fab_saluran);
        menu_geotag = (FloatingActionButton) findViewById(R.id.peta_fab_geotag);

        peta_pilih_jenis = (Button) findViewById(R.id.peta_pilih_jenis);
        peta_tl = (TableLayout) findViewById(R.id.peta_tbl_layout);
        peta_tr1 = (TableRow) findViewById(R.id.peta_tr_01);
        peta_jenis1 = (RadioButton) findViewById(R.id.peta_peta1);
        peta_jenis2 = (RadioButton) findViewById(R.id.peta_peta2);
        peta_jenis3 = (RadioButton) findViewById(R.id.peta_peta3);
        peta_jenis4 = (RadioButton) findViewById(R.id.peta_peta4);

        if (dbsetting.getJMap() == 1) { peta_jenis1.setChecked(true); }
        if (dbsetting.getJMap() == 2) { peta_jenis2.setChecked(true); }
        if (dbsetting.getJMap() == 3) { peta_jenis3.setChecked(true); }
        if (dbsetting.getJMap() == 4) { peta_jenis4.setChecked(true); }
        peta_tr1.setVisibility(View.GONE);


        DB_User auser = sws_db.DBUSer_Get();
        tv_nama.setText(auser.getNama());
        tv_jab.setText(auser.getJabatan());

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        header_jab = (TextView) headerView.findViewById(R.id.peta_jab);
        header_nama = (TextView) headerView.findViewById(R.id.peta_nama);
        header_jab.setText(auser.getJabatan());
        header_nama.setText(auser.getNama());

        Menu menuNav=navigationView.getMenu();
        MenuItem nav_item2 = menuNav.findItem(R.id.menu_absensi);
        if( auser.getTingkat() > 3) { nav_item2.setEnabled(true); } else { nav_item2.setEnabled(false); }



        List<DB_Ref_Kebun> kaktif = new LinkedList<DB_Ref_Kebun>();
        Log.e("MapsOnCreate", "Kebun Aktif --> " + String.valueOf(dbsetting.getActiveKebun()));
        if (dbsetting.getActiveKebun() == 0) {
            tv_jab.setText("Kebun : [Belum didefinisikan]");
        } else {
            kaktif = sws_db.DBRefKebun_Get(dbsetting.getActiveKebun());
            if (kaktif.size() == 0) {
                tv_jab.setText("Kebun : [Belum didefinisikan]");
            } else {
                tv_jab.setText("Kebun : " + kaktif.get(0).getNama());
            }
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10 * 1000); // 10 seconds
        locationRequest.setFastestInterval(5 * 1000); // 5 seconds

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    dbsetting = sws_db.DBSetting_Get();
                    sws_x = location.getLongitude();
                    sws_y = location.getLatitude();

                    if (dbsetting.getJKoord() == 1) {
                        tv_koord.setText("Koord: " + String.format("%.3f", sws_x) + ", " + String.format("%.3f", sws_y));
                    }
                    if (dbsetting.getJKoord() == 2) {
                        //"\u00B0"
                        int dum1x = (int) sws_x;
                        double sisax = Math.abs((sws_x - dum1x) * 60f);

                        int dum1y = (int) sws_y;
                        double sisay = Math.abs((sws_y - dum1y) * 60f);
                        tv_koord.setText("Koord: " + String.valueOf(dum1x) + "\u00B0 " + String.format("%.3f", sisax) + "', " +
                                String.valueOf(dum1y) + "\u00B0 " + String.format("%.3f", sisay) + "'");
                    }
                    if (dbsetting.getJKoord() == 3) {
                        int dum1x = (int) sws_x;
                        double sisax = Math.abs((sws_x - dum1x) * 60f);
                        int dumd1x = (int) sisax;
                        double sisadx = Math.abs((sisax - dumd1x) * 60f);
                        int dum1y = (int) sws_y;
                        double sisay = Math.abs((sws_y - dum1y) * 60f);
                        int dumd1y = (int) sisay;
                        double sisady = Math.abs((sisay - dumd1y) * 60f);
                        tv_koord.setText("Koord: " + String.valueOf(dum1x) + "\u00B0 " + String.valueOf(dumd1x) + "' " + String.format("%.3f", sisadx) + "\", " +
                                String.valueOf(dum1y) + "\u00B0 " + String.valueOf(dumd1y) + "' " + String.format("%.3f", sisady) + "\"");
                    }
                    if (dbsetting.getJKoord() == 4) {
                        String szone;
                        int zone = (int) Math.floor((sws_x + 180.0) / 6f) + 1;
                        zone = LatLonToUTMXY(DegToRad(sws_y), DegToRad(sws_x), zone);
                        szone = "Koord: " + String.format("%.3f", glob_xy[0]) + ", " + String.format("%.3f", glob_xy[1]) + " " + String.valueOf(zone);
                        if (sws_y < 0) {
                            szone = szone + "S";
                        } else {
                            szone = szone + "N";
                        }
                        tv_koord.setText(szone);
                    }

                    //Toast.makeText(getApplicationContext(), String.format("%.3f", sws_x) + ", " + String.format("%.3f", sws_y), Toast.LENGTH_SHORT).show();

                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    //MarkerOptions are used to create a new Marker.You can specify location, title etc with MarkerOptions
                    MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Lokasi Anda");
                    //Toast.makeText(getApplicationContext(), "PL --> " + String.valueOf(dbsetting.getPl()) + "::: PZ --> " + String.valueOf(dbsetting.getPz()), Toast.LENGTH_SHORT).show();
                    if (forthefirsttime == 1) {
                        forthefirsttime = 0;
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));
                    } else {

                        if (dbsetting.getPl() == 1) {
                            if (dbsetting.getPz() == 1) {
                                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                            } else {
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20f));
                            }
                        }
                    }

                    //Adding the created the marker on the map
                    if (myposition != null) { myposition.remove(); }
                    myposition = mMap.addMarker(markerOptions);
                }
            };
        };


        if (ActivityCompat.checkSelfPermission(MapsActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            return;
        }

        peta_pilih_jenis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (peta_tr1.getVisibility() == View.VISIBLE) {
                    peta_tr1.setVisibility(View.GONE);
                } else {
                    peta_tr1.setVisibility(View.VISIBLE);
                }
            }
        });

        peta_jenis1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mMap.setMapType(1);
            }
        });
        peta_jenis2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mMap.setMapType(2);
            }
        });
        peta_jenis3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mMap.setMapType(3);
            }
        });
        peta_jenis4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mMap.setMapType(4);
            }
        });

        btn_goto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //tampilkan dialog

                pindah_kebun = new Dialog(MapsActivity.this);
                // Set GUI of login screen
                pindah_kebun.setContentView(R.layout.frm_map_to_loc);
                pindah_kebun.setTitle("Tunjukkan Lokasi");

                // Init button of login GUI
                Button btnProses = (Button) pindah_kebun.findViewById(R.id.map_to_proses);
                Button btnCancel = (Button) pindah_kebun.findViewById(R.id.map_to_batal);
                final EditText tox = (EditText) pindah_kebun.findViewById(R.id.map_tox);
                final EditText toy = (EditText) pindah_kebun.findViewById(R.id.map_toy);
                // Attached listener for login GUI button
                btnProses.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String dum = tox.getText().toString().trim();
                        if (dum.equals("")) {
                            Toast.makeText(MapsActivity.this, "Isikan dahulu koordinat X", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (!isDouble(dum)) {
                            Toast.makeText(MapsActivity.this, "Nilai koordinat X bukan numerik", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        double kex, key;
                        kex = Double.parseDouble(dum);
                        if (kex < -180.0 || kex > 180.0) {
                            Toast.makeText(MapsActivity.this, "Nilai koordinat X tidak valid", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        dum = toy.getText().toString().trim();
                        if (dum.equals("")) {
                            Toast.makeText(MapsActivity.this, "Isikan dahulu koordinat Y", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (!isDouble(dum)) {
                            Toast.makeText(MapsActivity.this, "Nilai koordinat Y bukan numerik", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        key = Double.parseDouble(dum);
                        if (key < -90.0 || key > 90.0) {
                            Toast.makeText(MapsActivity.this, "Nilai koordinat Y tidak valid", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        LatLng latLng = new LatLng(key, kex);
                        //Toast.makeText(getApplicationContext(), "PL --> " + String.valueOf(dbsetting.getPl()) + "::: PZ --> " + String.valueOf(dbsetting.getPz()), Toast.LENGTH_SHORT).show();
                        if (forthefirsttime == 1) {
                            forthefirsttime = 0;
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));
                        } else {
                            dbsetting = sws_db.DBSetting_Get();
                            if (dbsetting.getPl() == 1) {
                                if (dbsetting.getPz() == 1) {
                                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                                } else {
                                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20f));
                                }
                            } else {
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20f));
                            }
                        }
                        pindah_kebun.dismiss();
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pindah_kebun.dismiss();
                    }
                });
                // Make dialog box visible.
                pindah_kebun.show();
            }
        });

        btn_myloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                LatLng latLng = new LatLng(sws_y, sws_x);
                if (forthefirsttime == 1) {
                    forthefirsttime = 0;
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));
                } else {
                    dbsetting = sws_db.DBSetting_Get();
                    if (dbsetting.getPl() == 1) {
                        if (dbsetting.getPz() == 1) {
                            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                        } else {
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20f));
                        }
                    } else {
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20f));
                    }
                }
            }
        });
        //setup listener for FAB menu
        menu_aktifitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(getApplicationContext(), frm_aktifitas.class);
                intent.putExtra("cx", String.valueOf(sws_x));
                intent.putExtra("cy", String.valueOf(sws_y));
                startActivityForResult(intent, 1101);
                //Toast.makeText(MapsActivity.this , " Alarm Icon clicked ", Toast.LENGTH_LONG).show();
            }
        });
        menu_kejadian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(getApplicationContext(), frm_kejadian.class);
                intent.putExtra("cx", String.valueOf(sws_x));
                intent.putExtra("cy", String.valueOf(sws_y));
                startActivityForResult(intent, 1102);
            }
        });
        menu_patok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(getApplicationContext(), frm_patok.class);
                intent.putExtra("cx", String.valueOf(sws_x));
                intent.putExtra("cy", String.valueOf(sws_y));
                startActivityForResult(intent, 1103);
            }
        });
        menu_jalan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(getApplicationContext(), frm_jalan.class);
                intent.putExtra("cx", String.valueOf(sws_x));
                intent.putExtra("cy", String.valueOf(sws_y));
                startActivityForResult(intent, 1104);
            }
        });
        menu_jembatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MapsActivity.this , " Alarm Icon clicked ", Toast.LENGTH_LONG).show();
                Intent intent;
                intent = new Intent(getApplicationContext(), frm_jembatan.class);
                intent.putExtra("cx", String.valueOf(sws_x));
                intent.putExtra("cy", String.valueOf(sws_y));
                startActivityForResult(intent, 1105);
            }
        });
        menu_tanaman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(getApplicationContext(), frm_tanaman.class);
                intent.putExtra("cx", String.valueOf(sws_x));
                intent.putExtra("cy", String.valueOf(sws_y));
                startActivityForResult(intent, 1106);
            }
        });
        menu_saluran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(getApplicationContext(), frm_saluran.class);
                intent.putExtra("cx", String.valueOf(sws_x));
                intent.putExtra("cy", String.valueOf(sws_y));
                startActivityForResult(intent, 1107);
            }
        });
        menu_geotag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(getApplicationContext(), frm_camera.class);
                intent.putExtra("cx", String.valueOf(sws_x));
                intent.putExtra("cy", String.valueOf(sws_y));
                startActivityForResult(intent, 1108);
            }
        });


    }

    double pi = 3.14159265358979;
    /* Ellipsoid model constants (actual values here are for WGS84) */
    double sm_a = 6378137.0;
    double sm_b = 6356752.314;
    double sm_EccSquared = 6.69437999013e-03;
    double UTMScaleFactor = 0.9996;

    private double DegToRad(double deg) {
        return (deg / 180.0 * pi);
    }

    private double UTMCentralMeridian(int zone) {
        double cmeridian;
        cmeridian = DegToRad(-183.0 + (zone * 6.0));
        return cmeridian;
    }

    private double ArcLengthOfMeridian(double phi) {
        double alpha, beta, gamma, delta, epsilon, n;
        double result;

        /* Precalculate n */
        n = (sm_a - sm_b) / (sm_a + sm_b);
        /* Precalculate alpha */
        alpha = ((sm_a + sm_b) / 2.0) * (1.0 + (Math.pow(n, 2.0) / 4.0) + (Math.pow(n, 4.0) / 64.0));
        /* Precalculate beta */
        beta = (-3.0 * n / 2.0) + (9.0 * Math.pow(n, 3.0) / 16.0) + (-3.0 * Math.pow(n, 5.0) / 32.0);
        /* Precalculate gamma */
        gamma = (15.0 * Math.pow(n, 2.0) / 16.0) + (-15.0 * Math.pow(n, 4.0) / 32.0);
        /* Precalculate delta */
        delta = (-35.0 * Math.pow(n, 3.0) / 48.0) + (105.0 * Math.pow(n, 5.0) / 256.0);
        /* Precalculate epsilon */
        epsilon = (315.0 * Math.pow(n, 4.0) / 512.0);
        /* Now calculate the sum of the series and return */
        result = alpha
                * (phi + (beta * Math.sin(2.0 * phi))
                + (gamma * Math.sin(4.0 * phi))
                + (delta * Math.sin(6.0 * phi))
                + (epsilon * Math.sin(8.0 * phi)));

        return result;
    }

    private void MapLatLonToXY(double phi, double lambda, double lambda0) {
        double N, nu2, ep2, t, t2, l;
        double l3coef, l4coef, l5coef, l6coef, l7coef, l8coef;
        double tmp;

        /* Precalculate ep2 */
        ep2 = (Math.pow(sm_a, 2.0) - Math.pow(sm_b, 2.0)) / Math.pow(sm_b, 2.0);
        /* Precalculate nu2 */
        nu2 = ep2 * Math.pow(Math.cos(phi), 2.0);
        /* Precalculate N */
        N = Math.pow(sm_a, 2.0) / (sm_b * Math.sqrt(1 + nu2));
        /* Precalculate t */
        t = Math.tan(phi);
        t2 = t * t;
        tmp = (t2 * t2 * t2) - Math.pow(t, 6.0);
        /* Precalculate l */
        l = lambda - lambda0;
        /* Precalculate coefficients for l**n in the equations below
           so a normal human being can read the expressions for easting
           and northing
           -- l**1 and l**2 have coefficients of 1.0 */
        l3coef = 1.0 - t2 + nu2;
        l4coef = 5.0 - t2 + 9 * nu2 + 4.0 * (nu2 * nu2);
        l5coef = 5.0 - 18.0 * t2 + (t2 * t2) + 14.0 * nu2 - 58.0 * t2 * nu2;
        l6coef = 61.0 - 58.0 * t2 + (t2 * t2) + 270.0 * nu2 - 330.0 * t2 * nu2;
        l7coef = 61.0 - 479.0 * t2 + 179.0 * (t2 * t2) - (t2 * t2 * t2);
        l8coef = 1385.0 - 3111.0 * t2 + 543.0 * (t2 * t2) - (t2 * t2 * t2);

        /* Calculate easting (x) */
        glob_xy[0] = N * Math.cos(phi) * l
                + (N / 6.0 * Math.pow(Math.cos(phi), 3.0) * l3coef * Math.pow(l, 3.0))
                + (N / 120.0 * Math.pow(Math.cos(phi), 5.0) * l5coef * Math.pow(l, 5.0))
                + (N / 5040.0 * Math.pow(Math.cos(phi), 7.0) * l7coef * Math.pow(l, 7.0));

        /* Calculate northing (y) */
        glob_xy[1] = ArcLengthOfMeridian(phi)
                + (t / 2.0 * N * Math.pow(Math.cos(phi), 2.0) * Math.pow(l, 2.0))
                + (t / 24.0 * N * Math.pow(Math.cos(phi), 4.0) * l4coef * Math.pow(l, 4.0))
                + (t / 720.0 * N * Math.pow(Math.cos(phi), 6.0) * l6coef * Math.pow(l, 6.0))
                + (t / 40320.0 * N * Math.pow(Math.cos(phi), 8.0) * l8coef * Math.pow(l, 8.0));
    }


    private int LatLonToUTMXY(double lat, double lon, int zone) {
        MapLatLonToXY(lat, lon, UTMCentralMeridian(zone));

        /* Adjust easting and northing for UTM system. */
        glob_xy[0] = glob_xy[0] * UTMScaleFactor + 500000.0;
        glob_xy[1] = glob_xy[1] * UTMScaleFactor;
        if (glob_xy[1] < 0.0) {
            glob_xy[1] = glob_xy[1] + 10000000.0;
        }
        return zone;
    }


    @Override
    protected void onResume() {
        super.onResume();
        List<DB_Ref_Kebun> kaktif = new LinkedList<DB_Ref_Kebun>();
        if (dbsetting.getActiveKebun() == 0) {
            tv_jab.setText("Kebun : [Belum didefinisikan]");
        } else {
            kaktif = sws_db.DBRefKebun_Get(dbsetting.getActiveKebun());
            if (kaktif.size() == 0) {
                tv_jab.setText("Kebun : [Belum didefinisikan]");
            } else {
                tv_jab.setText("Kebun : " + kaktif.get(0).getNama());
            }
        }
        if (requestingLocationUpdates == 1) {
            startLocationUpdates();
        }
    }

    private void startLocationUpdates() {
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper());
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    private void stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        dbsetting = sws_db.DBSetting_Get();
        mMap = googleMap;
        mMap.setMapType(dbsetting.getJMap());
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);

        Double x = 112.7330868;
        Double y = -7.2348727;
        LatLng latLng = new LatLng(y, x);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));

        markerManager = new MarkerManager(mMap);
        groundOverlayManager = new GroundOverlayManager(mMap);
        polygonManager = new PolygonManager(mMap);
        polylineManager = new PolylineManager(mMap);

        //sws_x = currentLocation.getLongitude();
        //sws_y = currentLocation.getLatitude();
        //tv_koord.setText("Koord: " + String.format("%.3f", sws_x) + String.format("%.3f", sws_y));
        //LatLng latLng = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
        //MarkerOptions are used to create a new Marker.You can specify location, title etc with MarkerOptions
        //MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Lokasi Anda");
        //if( dbsetting.getPl() == 1 ) {
        //    if( dbsetting.getPz() == 1) {
        //        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        //    } else {
        //        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,20f));
        //    }
        //}

        //Adding the created the marker on the map
        //mMap.addMarker(markerOptions);
        try {
            Log.e("MR", "Tampilkan KML");
            tampilkan_kml();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.mapsactivitymenu, menu);
//        DB_User auser = sws_db.DBUSer_Get();
//        Log.e("MENU CREATE", "TINGKAT --> " + String.valueOf(auser.getTingkat()));
//        if( auser.getTingkat() == 4 || auser.getTingkat() == 5 ) {
//            menu.getItem(0).setVisible(false);
//            menu.getItem(1).setVisible(true);
//        } else {
//            if( auser.getTingkat() == 3 ) {
//                menu.getItem(0).setVisible(false);
//            } else {
//                menu.getItem(0).setVisible(true);
//            }
//            menu.getItem(1).setVisible(false);
//        }
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AlertDialog.Builder alertDialogBuilder;
        AlertDialog alertDialog;
        //Toast.makeText(this, "Selected Item: " +item.getTitle(), Toast.LENGTH_SHORT).show();
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_pindah:
                pindah_kebun = new Dialog(this);
                // Set GUI of login screen
                pindah_kebun.setContentView(R.layout.frm_pindah_kebun);
                pindah_kebun.setTitle("Pindah Kebun");

                // Init button of login GUI
                Button btnProses = (Button) pindah_kebun.findViewById(R.id.kaktif_proses);
                Button btnCancel = (Button) pindah_kebun.findViewById(R.id.kaktif_batal);
                final Spinner spinkebun = (Spinner) pindah_kebun.findViewById(R.id.kaktif_kebun);
                List<DB_Ref_Kebun> setkebun = new LinkedList<DB_Ref_Kebun>();
                final ArrayList<String> kode_kebun = new ArrayList<String>();
                ArrayList<String> nama_kebun = new ArrayList<String>();
                setkebun = sws_db.DBRefKebun_Get(0);
                for (int iix = 0; iix < setkebun.size(); iix++) {
                    kode_kebun.add(String.valueOf(setkebun.get(iix).getKode()));
                    nama_kebun.add(setkebun.get(iix).getNama());
                }
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, nama_kebun);
                spinkebun.setAdapter(adapter1);
                // Attached listener for login GUI button
                btnProses.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pilihan = spinkebun.getSelectedItemPosition();
                        String kebun_terpilih = kode_kebun.get(pilihan);
                        dbsetting = sws_db.DBSetting_Get();
                        sws_db.DBSetting_UpdateUser(dbsetting.getActiveUser(), Integer.parseInt(kebun_terpilih));
                        doGetData(kebun_terpilih);
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pindah_kebun.dismiss();
                    }
                });
                // Make dialog box visible.
                pindah_kebun.show();

                return true;
            case R.id.menu_absensi:
                //absensi
                intent = new Intent(getApplicationContext(), frm_absensi.class);
                startActivityForResult(intent, 7009);
                return true;
            case R.id.menu_info:
                //menu info
                intent = new Intent(getApplicationContext(), frm_info.class);
                startActivityForResult(intent, 7008);
                return true;
            case R.id.menu_kml:
                // do your code
                intent = new Intent(getApplicationContext(), frm_daftar_peta.class);
                startActivityForResult(intent, 7002);
                return true;
            case R.id.toggle_kml:
                intent = new Intent(getApplicationContext(), frm_toggle_peta.class);
                startActivityForResult(intent, 7003);
                return true;
            case R.id.menu_sekitar:
                intent = new Intent(getApplicationContext(), frm_sekitar.class);
                intent.putExtra("cx", String.valueOf(sws_x));
                intent.putExtra("cy", String.valueOf(sws_y));
                intent.putExtra("mulai", glob_sekitar_mulai);
                intent.putExtra("jarak", glob_sekitar_jarak);
                startActivityForResult(intent, 7004);
                return true;
            case R.id.menu_lihatdata:
                intent = new Intent(getApplicationContext(), frm_lihatdata.class);
                intent.putExtra("cx", String.valueOf(sws_x));
                intent.putExtra("cy", String.valueOf(sws_y));
                intent.putExtra("mulai", glob_sekitar_mulai);
                intent.putExtra("jarak", glob_sekitar_jarak);
                startActivityForResult(intent, 7010);
                return true;
            case R.id.menu_gallery:
                // do your code
                intent = new Intent(getApplicationContext(), frm_gallery.class);
                startActivityForResult(intent, 7005);
                return true;
            case R.id.menu_download:
                intent = new Intent(getApplicationContext(), frm_download.class);
                startActivityForResult(intent, 7006);
                return true;
            case R.id.menu_upload:
                intent = new Intent(getApplicationContext(), frm_upload.class);
                startActivityForResult(intent, 7007);
                return true;
            case R.id.menu_setting:
                //setting
                intent = new Intent(getApplicationContext(), frm_setting.class);
                startActivityForResult(intent, 7001);
                return true;
            case R.id.menu_logout:
                // do your code
                alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("SIMTAGAR");
                alertDialogBuilder.setMessage("Tekan Ya untuk Log-Out");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //map_main.this.finish();
                        sws_db.DBUser_Delete_All();
                        sws_db.DBSetting_UpdateUser(0, 0);
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                alertDialogBuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                return true;
            case R.id.menu_keluar:
                // do your code
                alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("SIMTAGAR");
                alertDialogBuilder.setMessage("Tekan Ya untuk keluar");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //map_main.this.finish();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("SWS_EXIT", true);
                        startActivity(intent);
                        finish();
                    }
                });
                alertDialogBuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//                super.onBackPressed();
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("SIMTAGAR");
            alertDialogBuilder.setMessage("Tekan Ya untuk keluar");
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //map_main.this.finish();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("SWS_EXIT", true);
                    startActivity(intent);
                    finish();
                }
            });
            alertDialogBuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("OAR", String.valueOf(requestCode));
        if (requestCode == 7002) {
            try {
                tampilkan_kml();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == 7003) {
            if (resultCode == 1105) {
                try {
                    tampilkan_kml();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
            }
        }
        if( requestCode == 7010 ) {
            //dari lihat data
            Log.e("MAINACT", "Masuk sini");
            if( data == null ) {
                Log.e("MAINACT", "DATA ISNULL");
                return;
            }
            String return_x = data.getExtras().getString("cx");
            String return_y = data.getExtras().getString("cy");
            Log.e("MAINACT", "CX, CY --> " + return_x + ", " + return_y);
            int anicon = R.drawable.red_circle;
            if( marker_circle != null ) {
                //remove dulu
                marker_circle.remove();
            }
            LatLng point_terpilih = new LatLng(Double.parseDouble(return_y), Double.parseDouble(return_x));
            marker_circle = mMap.addMarker(new MarkerOptions()
                    .position(point_terpilih)
                    .title("Obyek terpilih")
                    .anchor(0.5f, 1.0f)
                    .draggable(false)
                    .icon(BitmapDescriptorFactory.fromResource(anicon)));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(point_terpilih, 18f));
        }
        if (requestCode == 7004) {
            if( data == null ) { return; }
            glob_sekitar_jarak = data.getExtras().getString("jarak");
            glob_sekitar_mulai = data.getExtras().getString("mulai");
            if (resultCode == 7004) {
                //ada yang terpilih nih --> zoom ke lokasi dan tampilkan lingkaran
                String return_x = data.getExtras().getString("cx");
                String return_y = data.getExtras().getString("cy");
                int anicon = R.drawable.red_circle;
                if (marker_circle != null) {
                    //remove dulu
                    marker_circle.remove();
                }
                LatLng point_terpilih = new LatLng(Double.parseDouble(return_y), Double.parseDouble(return_x));
                marker_circle = mMap.addMarker(new MarkerOptions()
                        .position(point_terpilih)
                        .title("Obyek terpilih")
                        .anchor(0.5f, 1.0f)
                        .draggable(false)
                        .icon(BitmapDescriptorFactory.fromResource(anicon)));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(point_terpilih, 15f));
            }
        }
        if (requestCode == 7001 || requestCode == 7006) {
            //dari setting
            if (resultCode == 1) {
                //ada perubahan
                dbsetting = sws_db.DBSetting_Get();
                //Toast.makeText(getApplicationContext(),String.valueOf(dbsetting.getTampilGeotag()), Toast.LENGTH_SHORT).show();
                if (dbsetting.getJMap() == 1) { peta_jenis1.setChecked(true); }
                if (dbsetting.getJMap() == 2) { peta_jenis2.setChecked(true); }
                if (dbsetting.getJMap() == 3) { peta_jenis3.setChecked(true); }
                if (dbsetting.getJMap() == 4) { peta_jenis4.setChecked(true); }
                mMap.setMapType(dbsetting.getJMap());

                if( dbsetting.getTampilKejadian() == 1 ) { tampilkan_kejadian(); } else { sembunyi_kejadian(); }
                if( dbsetting.getTampilPatok() == 1 ) { tampilkan_patok(); } else { sembunyi_patok(); }
                if( dbsetting.getTampilPekerjaan() == 1 ) { tampilkan_pekerjaan(); } else { sembunyi_pekerjaan(); }
                if( dbsetting.getTampilAktifitas() == 1 ) { tampilkan_aktifitas(); } else { sembunyi_aktifitas(); }
                if( dbsetting.getTampilJalan() == 1 ) { tampilkan_jalan(); } else { sembunyi_jalan(); }
                if( dbsetting.getTampilJembatan() == 1 ) { tampilkan_jembatan(); } else { sembunyi_jembatan(); }
                if( dbsetting.getTampilTanaman() == 1 ) { tampilkan_tanaman(); } else { sembunyi_tanaman(); }
                if( dbsetting.getTampilObyek() == 1 ) { tampilkan_obyek(); } else { sembunyi_obyek(); }
                if( dbsetting.getTampilSaluran() == 1 ) { tampilkan_saluran(); } else { sembunyi_saluran(); }
                if( dbsetting.getTampilGeotag() == 1 ) { tampilkan_geotag(); } else { sembunyi_geotag(); }

            }
        }
        if (requestCode == 1101) {
            if (resultCode == 1101) {
                dbsetting = sws_db.DBSetting_Get();
                if (dbsetting.getTampilAktifitas() == 1) {
                    tampilkan_aktifitas();
                }
            }
        }
        if (requestCode == 1102) {
            if (resultCode == 1102) {
                dbsetting = sws_db.DBSetting_Get();
                if (dbsetting.getTampilKejadian() == 1) {
                    tampilkan_kejadian();
                }
            }
        }
        if (requestCode == 1103) {
            if (resultCode == 1103) {
                dbsetting = sws_db.DBSetting_Get();
                if (dbsetting.getTampilPatok() == 1) {
                    tampilkan_patok();
                }
            }
        }
        if (requestCode == 1104) {
            if (resultCode == 1104) {
                dbsetting = sws_db.DBSetting_Get();
                if (dbsetting.getTampilJalan() == 1) {
                    tampilkan_jalan();
                }
            }
        }
        if (requestCode == 1105) {
            if (resultCode == 1105) {
                dbsetting = sws_db.DBSetting_Get();
                if (dbsetting.getTampilJembatan() == 1) {
                    tampilkan_jembatan();
                }
            }
        }
        if (requestCode == 1106) {
            if (resultCode == 1106) {
                dbsetting = sws_db.DBSetting_Get();
                if (dbsetting.getTampilTanaman() == 1) {
                    tampilkan_tanaman();
                }
            }
        }
        if (requestCode == 1107) {
            if (resultCode == 1107) {
                dbsetting = sws_db.DBSetting_Get();
                if (dbsetting.getTampilSaluran() == 1) {
                    tampilkan_saluran();
                }
            }
        }
        if (requestCode == 1108) {
            if (resultCode == 1108) {
                dbsetting = sws_db.DBSetting_Get();
                if (dbsetting.getTampilGeotag() == 1) {
                    tampilkan_geotag();
                }
            }
        }
    }

    private void tampilkan_kejadian() {
        sembunyi_kejadian();

        LatLng pointGPS;
        int anicon = R.drawable.kejadian;
        List<DB_Kejadian> setaktif = new LinkedList<DB_Kejadian>();
        setaktif = sws_db.DBKejadian_Get(0, 0, 99, "", 0);
        DB_Kejadian anaktif;
        String jaktif;
        Log.e("MA Kejadian", String.valueOf(setaktif.size()));
        for (int iix = 0; iix < setaktif.size(); iix++) {
            anaktif = setaktif.get(iix);
            if (isDouble(anaktif.getCX()) && isDouble(anaktif.getCY())) {
                pointGPS = new LatLng(Double.parseDouble(anaktif.getCY()), Double.parseDouble(anaktif.getCX()));
                Marker TP = mMap.addMarker(new MarkerOptions()
                        .position(pointGPS)
                        .title("Tag kejadian")
                        .anchor(0.5f, 1.0f)
                        .draggable(false)
                        .icon(BitmapDescriptorFactory.fromResource(anicon)));
                markerlist.add(TP);
                markertaglist.add("KEJADIAN");
            }
        }
    }

    private void sembunyi_kejadian() {
        Marker marker;
        for (int i = markertaglist.size() - 1; i >= 0; i--) {
            if (markertaglist.get(i).equals("KEJADIAN")) {
                marker = markerlist.get(i);
                marker.remove();
                markerlist.remove(i);
                markertaglist.remove(i);
            }
        }
    }

    private void tampilkan_patok() {
        sembunyi_patok();

        LatLng pointGPS;
        int anicon = R.drawable.patok;
        List<DB_Patok> setaktif = new LinkedList<DB_Patok>();
        setaktif = sws_db.DBPatok_Get(0, 0, 99);
        DB_Patok anaktif;
        String jaktif;

        for (int iix = 0; iix < setaktif.size(); iix++) {
            anaktif = setaktif.get(iix);
            if (isDouble(anaktif.getCX()) && isDouble(anaktif.getCY())) {
                pointGPS = new LatLng(Double.parseDouble(anaktif.getCY()), Double.parseDouble(anaktif.getCX()));
                Marker TP = mMap.addMarker(new MarkerOptions()
                        .position(pointGPS)
                        .title("Tag patok")
                        .anchor(0.5f, 1.0f)
                        .draggable(false)
                        .icon(BitmapDescriptorFactory.fromResource(anicon)));
                markerlist.add(TP);
                markertaglist.add("PATOK");
            }
        }
    }

    private void sembunyi_patok() {
        Marker marker;
        for (int i = markertaglist.size() - 1; i >= 0; i--) {
            if (markertaglist.get(i).equals("PATOK")) {
                marker = markerlist.get(i);
                marker.remove();
                markerlist.remove(i);
                markertaglist.remove(i);
            }
        }
    }

    private void tampilkan_pekerjaan() {
    }

    private void sembunyi_pekerjaan() {
    }

    private void tampilkan_aktifitas() {
        sembunyi_aktifitas();

        LatLng pointGPS;
        int anicon = R.drawable.aktifitas;
        List<DB_Aktifitas> setaktif = new LinkedList<DB_Aktifitas>();
        setaktif = sws_db.DBAktifitas_Get(0, 0, 99, "", 0);
        DB_Aktifitas anaktif;
        String jaktif;

        for (int iix = 0; iix < setaktif.size(); iix++) {
            anaktif = setaktif.get(iix);
            if (isDouble(anaktif.getCX()) && isDouble(anaktif.getCY())) {
                pointGPS = new LatLng(Double.parseDouble(anaktif.getCY()), Double.parseDouble(anaktif.getCX()));
                Marker TP = mMap.addMarker(new MarkerOptions()
                        .position(pointGPS)
                        .title("Tag aktifitas")
                        .anchor(0.5f, 1.0f)
                        .draggable(false)
                        .icon(BitmapDescriptorFactory.fromResource(anicon)));
                markerlist.add(TP);
                markertaglist.add("AKTIFITAS");
            }
        }
    }

    private void sembunyi_aktifitas() {
        Marker marker;
        for (int i = markertaglist.size() - 1; i >= 0; i--) {
            if (markertaglist.get(i).equals("AKTIFITAS")) {
                marker = markerlist.get(i);
                marker.remove();
                markerlist.remove(i);
                markertaglist.remove(i);
            }
        }
    }

    private void tampilkan_jalan() {
        sembunyi_jalan();

        LatLng pointGPS;
        int anicon = R.drawable.jalan;
        List<DB_Jalan> setaktif = new LinkedList<DB_Jalan>();
        setaktif = sws_db.DBJalan_Get(0, 99);
        DB_Jalan anaktif;
        String jaktif;

        for (int iix = 0; iix < setaktif.size(); iix++) {
            anaktif = setaktif.get(iix);
            if (isDouble(anaktif.getCX()) && isDouble(anaktif.getCY())) {
                pointGPS = new LatLng(Double.parseDouble(anaktif.getCY()), Double.parseDouble(anaktif.getCX()));
                Marker TP = mMap.addMarker(new MarkerOptions()
                        .position(pointGPS)
                        .title("Tag jalan")
                        .anchor(0.5f, 1.0f)
                        .draggable(false)
                        .icon(BitmapDescriptorFactory.fromResource(anicon)));
                markerlist.add(TP);
                markertaglist.add("JALAN");
            }
        }
    }

    private void sembunyi_jalan() {
        Marker marker;
        for (int i = markertaglist.size() - 1; i >= 0; i--) {
            if (markertaglist.get(i).equals("JALAN")) {
                marker = markerlist.get(i);
                marker.remove();
                markerlist.remove(i);
                markertaglist.remove(i);
            }
        }
    }

    private void tampilkan_jembatan() {
        sembunyi_jembatan();

        LatLng pointGPS;
        int anicon = R.drawable.jembatan;
        List<DB_Jembatan> setaktif = new LinkedList<DB_Jembatan>();
        setaktif = sws_db.DBJembatan_Get(0, 99);
        DB_Jembatan anaktif;
        String jaktif;

        for (int iix = 0; iix < setaktif.size(); iix++) {
            anaktif = setaktif.get(iix);
            if (isDouble(anaktif.getCX()) && isDouble(anaktif.getCY())) {
                pointGPS = new LatLng(Double.parseDouble(anaktif.getCY()), Double.parseDouble(anaktif.getCX()));
                Marker TP = mMap.addMarker(new MarkerOptions()
                        .position(pointGPS)
                        .title("Tag jembatan")
                        .anchor(0.5f, 1.0f)
                        .draggable(false)
                        .icon(BitmapDescriptorFactory.fromResource(anicon)));
                markerlist.add(TP);
                markertaglist.add("JEMBATAN");
            }
        }
    }

    private void sembunyi_jembatan() {
        Marker marker;
        for (int i = markertaglist.size() - 1; i >= 0; i--) {
            if (markertaglist.get(i).equals("JEMBATAN")) {
                marker = markerlist.get(i);
                marker.remove();
                markerlist.remove(i);
                markertaglist.remove(i);
            }
        }
    }

    private void tampilkan_tanaman() {
        sembunyi_tanaman();

        LatLng pointGPS;
        int anicon = R.drawable.tanaman;
        List<DB_Tanaman> setaktif = new LinkedList<DB_Tanaman>();
        setaktif = sws_db.DBTanaman_Get(0, 99);
        DB_Tanaman anaktif;
        String jaktif;

        for (int iix = 0; iix < setaktif.size(); iix++) {
            anaktif = setaktif.get(iix);
            if (isDouble(anaktif.getCX()) && isDouble(anaktif.getCY())) {
                pointGPS = new LatLng(Double.parseDouble(anaktif.getCY()), Double.parseDouble(anaktif.getCX()));
                Marker TP = mMap.addMarker(new MarkerOptions()
                        .position(pointGPS)
                        .title("Tag komoditi")
                        .anchor(0.5f, 1.0f)
                        .draggable(false)
                        .icon(BitmapDescriptorFactory.fromResource(anicon)));
                markerlist.add(TP);
                markertaglist.add("KOMODITI");
            }
        }
    }

    private void sembunyi_tanaman() {
        Marker marker;
        for (int i = markertaglist.size() - 1; i >= 0; i--) {
            if (markertaglist.get(i).equals("KOMODITI")) {
                marker = markerlist.get(i);
                marker.remove();
                markerlist.remove(i);
                markertaglist.remove(i);
            }
        }
    }

    private void tampilkan_obyek() {
        sembunyi_obyek();

        LatLng pointGPS;
        int anicon = R.drawable.saluran;
        List<DB_Obyek> setaktif = new LinkedList<DB_Obyek>();
        setaktif = sws_db.DBObyek_Get(0, 99);
        DB_Obyek anaktif;
        String jaktif;

        for (int iix = 0; iix < setaktif.size(); iix++) {
            anaktif = setaktif.get(iix);
            if (isDouble(anaktif.getCX()) && isDouble(anaktif.getCY())) {
                pointGPS = new LatLng(Double.parseDouble(anaktif.getCY()), Double.parseDouble(anaktif.getCX()));
                Marker TP = mMap.addMarker(new MarkerOptions()
                        .position(pointGPS)
                        .title("Tag obyek")
                        .anchor(0.5f, 1.0f)
                        .draggable(false)
                        .icon(BitmapDescriptorFactory.fromResource(anicon)));
                markerlist.add(TP);
                markertaglist.add("OBYEK");
            }
        }
    }

    private void sembunyi_obyek() {
        Marker marker;
        for (int i = markertaglist.size() - 1; i >= 0; i--) {
            if (markertaglist.get(i).equals("OBYEK")) {
                marker = markerlist.get(i);
                marker.remove();
                markerlist.remove(i);
                markertaglist.remove(i);
            }
        }
    }

    private void tampilkan_saluran() {
        sembunyi_saluran();

        LatLng pointGPS;
        int anicon = R.drawable.saluran;
        List<DB_Saluran> setaktif = new LinkedList<DB_Saluran>();
        setaktif = sws_db.DBSaluran_Get(0, 99);
        DB_Saluran anaktif;
        String jaktif;

        for (int iix = 0; iix < setaktif.size(); iix++) {
            anaktif = setaktif.get(iix);
            if (isDouble(anaktif.getCX()) && isDouble(anaktif.getCY())) {
                pointGPS = new LatLng(Double.parseDouble(anaktif.getCY()), Double.parseDouble(anaktif.getCX()));
                Marker TP = mMap.addMarker(new MarkerOptions()
                        .position(pointGPS)
                        .title("Tag Saluran")
                        .anchor(0.5f, 1.0f)
                        .draggable(false)
                        .icon(BitmapDescriptorFactory.fromResource(anicon)));
                markerlist.add(TP);
                markertaglist.add("SALURAN");
            }
        }
    }

    private void sembunyi_saluran() {
        Marker marker;
        for (int i = markertaglist.size() - 1; i >= 0; i--) {
            if (markertaglist.get(i).equals("SALURAN")) {
                marker = markerlist.get(i);
                marker.remove();
                markerlist.remove(i);
                markertaglist.remove(i);
            }
        }
    }

    private void tampilkan_geotag() {
        sembunyi_geotag();

        LatLng pointGPS;
        int anicon = R.drawable.foto;
        List<DB_Image> setaktif = new LinkedList<DB_Image>();
        setaktif = sws_db.DBImage_Get(0, 99, 1, 99);
        DB_Image anaktif;
        String jaktif;

        Log.e("TGEO", String.valueOf(setaktif.size()));
        for (int iix = 0; iix < setaktif.size(); iix++) {
            anaktif = setaktif.get(iix);
            if (isDouble(anaktif.getCx()) && isDouble(anaktif.getCy())) {
                pointGPS = new LatLng(Double.parseDouble(anaktif.getCy()), Double.parseDouble(anaktif.getCx()));
                Marker TP = mMap.addMarker(new MarkerOptions()
                        .position(pointGPS)
                        .title("Tag Obyek")
                        .anchor(0.5f, 1.0f)
                        .draggable(false)
                        .icon(BitmapDescriptorFactory.fromResource(anicon)));
                markerlist.add(TP);
                markertaglist.add("OBYEK");
            }
        }
    }

    private void sembunyi_geotag() {
        Marker marker;
        for (int i = markertaglist.size() - 1; i >= 0; i--) {
            if (markertaglist.get(i).equals("GEOTAG")) {
                marker = markerlist.get(i);
                marker.remove();
                markerlist.remove(i);
                markertaglist.remove(i);
            }
        }
    }

    private boolean isDouble(String str) {
        String dum1 = String.valueOf(1 / 4);
        String dum2 = "";
        Boolean titik = false;
        if (dum1.contains(".")) {
            titik = true;
        } else {
            titik = false;
        }
        dum2 = str;
        //if( titik == false ) { dum2 = str.replace(".", ","); } else { dum2 = str; }
        Log.e("ISDOUBLE", str + " --> " + dum2);
        try {
            double d = Double.parseDouble(dum2);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private void sembunyikan_kml() {
        KmlLayer alayer;
        Log.e("SEMBUNYIKAN", "Jumlah layer --> " + String.valueOf(kmllayerlist.size()));
        int i = kmllayerlist.size()-1;
        while ( i >= 0 ) {
            alayer = kmllayerlist.get(i);
            alayer.removeLayerFromMap();
            kmllayerlist.remove(i);
            kmlnama.remove(i);
            kmlidlist.remove(i);
            i = i - 1;
        }
        mMap.clear();
        kmllayerlist.clear();
        kmlnama.clear();
        kmltampil.clear();
        kmlidlist.clear();
    }

    private void sembunyikan(String nama) {
        KmlLayer alayer;
        Log.e("SEMBUNYIKAN", "Jumlah layer awal --> " + String.valueOf(kmllayerlist.size()));
        boolean ketemu = false;
        int i = kmllayerlist.size()-1;
        while( i >= 0 && !ketemu ) {
            if( kmlnama.get(i).equals(nama)) {
                ketemu = true;
            } else {
                i = i - 1;
            }
        }
        if( ketemu ) {
            alayer = kmllayerlist.get(i);
            alayer.removeLayerFromMap();
            kmllayerlist.remove(i);
            kmlnama.remove(i);
            kmlidlist.remove(i);
        }
        Log.e("SEMBUNYIKAN", "Jumlah layer akhir --> " + String.valueOf(kmllayerlist.size()));
    }

    private void tampilkan_kml() throws IOException, XmlPullParserException {
        sembunyikan_kml();
        List<DB_KML> setkml = new LinkedList<DB_KML>();
        setkml = sws_db.DBKML_Get(0,"",1);
        for( int i = 0; i < setkml.size(); i++ ) {
            Log.e("MA DAFTAR KML", String.valueOf(setkml.get(i).getID() + ":::" + setkml.get(i).getNama() + ":::" + String.valueOf(setkml.get(i).getTampil())));
        }
        DB_KML dbkml;
        Log.e("MA Tampil KML", String.valueOf(setkml.size()));
        for( int i = 0; i < setkml.size(); i++ ) {
            dbkml = setkml.get(i);
            int idkml = dbkml.getID();
            String dum = dbkml.getFolder();
            String namalayer = dbkml.getNama();
            String[] sep = dum.split("/");
            String namaasli = sep[sep.length-1];
            //Log.e("MA TAMBAH", "ID KML --> " + String.valueOf(idkml));
            //Log.e("MA TAMBAH", "dum --> " + dum);
            //Log.e("MA TAMBAH", "namalayer --> " + namalayer);
            //Log.e("MA TAMBAH", "nama asli --> " + namaasli);
            //Log.e("MA TAMBAH", "SUDAH --> " + String.valueOf(dbkml.getSudah()));
            Log.e("MA TAMBAH", "Mau ADD 1 --> " + namaasli + ":::Jumlah layer --> " + String.valueOf(kmllayerlist.size()));
            if( dbkml.getSudah() == 1) {
                File file=new File(getExternalFilesDir(null), namaasli);
                Log.e("MA TAMBAH", "Mau ADD 2 --> " + String.valueOf(file) + ":::Jumlah layer --> " + String.valueOf(kmllayerlist.size()));
                if( file.exists()) {
                    Log.e("MA TAMBAH", "Mau ADD 3 --> " + String.valueOf(file) + ":::Jumlah layer --> " + String.valueOf(kmllayerlist.size()));
                    InputStream kmlInputStream = new FileInputStream(file);
                    //KmlLayer layer = new KmlLayer(mMap, kmlInputStream, getApplicationContext());
                    KmlLayer layer = new KmlLayer(mMap, kmlInputStream, getApplicationContext(), markerManager, polygonManager,
                            polylineManager, groundOverlayManager, null);
                    layer.addLayerToMap();
                    sws_db.DBKML_UpdateTampil(idkml, 1);
                    kmllayerlist.add(layer);
                    kmlnama.add(namalayer);
                    kmlidlist.add(idkml);
                    layer.setOnFeatureClickListener(new KmlLayer.OnFeatureClickListener() {
                        @Override
                        public void onFeatureClick(Feature feature) {
                            if( feature.getProperty("description") != null ) {
                                String atext = feature.getProperty("description");
                                atext = atext.replace("<![CDATA[", "");
                                atext = atext.replace("]]>", "");
                                Dialog info_window;
                                info_window = new Dialog(MapsActivity.this);
                                // Set GUI of login screen
                                info_window.setContentView(R.layout.frm_info_map);
                                info_window.setTitle("Info Peta");
                                WebView iwtv;
                                iwtv = (WebView) info_window.findViewById(R.id.info_map_tv);
                                iwtv.loadData(atext, "text/html; charset=UTF-8", null);
                                info_window.show();
                            }

                        }
                    });
                    Log.e("MA TAMBAH", "ID KML --> " + String.valueOf(idkml) + " :::Jumlah layer --> " + String.valueOf(kmllayerlist.size()));
                }
            }
        }
    }

    private void doGetData(String kebun) {
        class doGetDataAsync extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // Showing progress dialog
                progressDialog = new ProgressDialog(MapsActivity.this);
                progressDialog.setMessage("Sedang mengunduh data"); // Setting Message
                progressDialog.setTitle("SIMTAGAR"); // Setting Title
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                progressDialog.show(); // Display Progress Dialog
                progressDialog.setCancelable(true);
            }

            @Override
            protected String doInBackground(String... params) {
                String jsonStr = null;
                int user = 0;
                int kebun = Integer.parseInt(params[0]);
                dbsetting = sws_db.DBSetting_Get();
                String alamat = "http://" + dbsetting.getWebip();
                if (dbsetting.getVd().equals("")) {
                    alamat = alamat + "/mob_download.php";
                } else {
                    alamat = alamat + "/" + dbsetting.getVd() + "/mob_download.php";
                }
                user = dbsetting.getActiveUser();

                try {
                    URL url = new URL(alamat);
                    Map<String, Object> params1 = new LinkedHashMap<>();
                    params1.put("user", String.valueOf(user));
                    params1.put("idkebun", String.valueOf(kebun));
                    params1.put("akt", "1");
                    params1.put("kej", "1");
                    params1.put("pat", "1");
                    params1.put("jal", "1");
                    params1.put("jem", "1");
                    params1.put("tan", "1");
                    params1.put("sal", "1");
                    params1.put("pek", "1");

                    StringBuilder postData = new StringBuilder();
                    for (Map.Entry<String, Object> param : params1.entrySet()) {
                        if (postData.length() != 0) postData.append('&');
                        postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                        postData.append('=');
                        postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                    }
                    byte[] postDataBytes = postData.toString().getBytes("UTF-8");

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.getOutputStream().write(postDataBytes);

                    // read the response
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb = new StringBuilder();

                    String line;
                    try {
                        while ((line = reader.readLine()) != null) {
                            sb.append(line).append('\n');
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    jsonStr = sb.toString();
                } catch (MalformedURLException e) {
                    Log.e(TAG, "MalformedURLException: " + e.getMessage());
                } catch (ProtocolException e) {
                    Log.e(TAG, "ProtocolException: " + e.getMessage());
                } catch (IOException e) {
                    Log.e(TAG, "IOException: " + e.getMessage());
                } catch (Exception e) {
                    Log.e(TAG, "Exception: " + e.getMessage());
                }

                if (jsonStr != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(jsonStr);
                        // Getting JSON Array node
                        JSONArray sws_data = jsonObj.getJSONArray("data");
                        for (int i = 0; i < sws_data.length(); i++) {
                            JSONObject c = sws_data.getJSONObject(i);
                            JSONArray js_akt = c.getJSONArray("aktifitas");
                            if (js_akt != null) {
                                //sws_db.DBAktifitas_Add(kebun,jenis,alama,abiaya,asdm,aket,acx, acy, logdate,userid,0,0,0);
                                for (int j = 0; j < js_akt.length(); j++) {
                                    JSONObject d = js_akt.getJSONObject(j);
                                    String akt_jenis = d.getString("jenis");
                                    String akt_lama = d.getString("lama");
                                    String akt_biaya = d.getString("biaya");
                                    String akt_sdm = d.getString("sdm");
                                    String akt_cx = d.getString("x");
                                    String akt_cy = d.getString("y");
                                    String akt_waktu = d.getString("waktu");
                                    String akt_userid = d.getString("userid");
                                    String akt_id_server = d.getString("id");
                                    String akt_ket = d.getString("ket");
                                    List<DB_Aktifitas> setobj = new LinkedList<DB_Aktifitas>();
                                    setobj = sws_db.DBAktifitas_CheckDariServer(Integer.parseInt(akt_id_server));
                                    if (setobj.size() > 0) {
                                        int akt_urut = setobj.get(0).getUrut();
                                        sws_db.DBAktifitas_Delete(akt_urut);
                                    }
                                    sws_db.DBAktifitas_Add(kebun, Integer.parseInt(akt_jenis), akt_lama, akt_biaya, akt_sdm, akt_ket,
                                            akt_cx, akt_cy, akt_waktu, Integer.parseInt(akt_userid), 1, 1, Integer.parseInt(akt_id_server), 2);
                                }
                            }
                            JSONArray js_kej = c.getJSONArray("kejadian");
                            if (js_kej != null) {
                                for (int j = 0; j < js_kej.length(); j++) {
                                    JSONObject d = js_kej.getJSONObject(j);
                                    String kej_jenis = d.getString("jenis");
                                    String kej_ket = d.getString("ket");
                                    String kej_cx = d.getString("x");
                                    String kej_cy = d.getString("y");
                                    String kej_waktu = d.getString("waktu");
                                    String kej_userid = d.getString("userid");
                                    String kej_id_server = d.getString("id");
                                    List<DB_Kejadian> setobj = new LinkedList<DB_Kejadian>();
                                    setobj = sws_db.DBKejadian_CheckDariServer(Integer.parseInt(kej_id_server));
                                    if (setobj.size() > 0) {
                                        int kej_urut = setobj.get(0).getUrut();
                                        sws_db.DBKejadian_Delete(kej_urut);
                                    }
                                    //kebun, jenis, aket, acx, acy, logdate, userid, 0,0,0);
                                    sws_db.DBKejadian_Add(kebun, Integer.parseInt(kej_jenis), kej_ket, kej_cx, kej_cy, kej_waktu,
                                            Integer.parseInt(kej_userid), 1, 1, Integer.parseInt(kej_id_server), 2);
                                }
                            }

                            JSONArray js_patok = c.getJSONArray("patok");
                            if (js_patok != null) {
                                for (int j = 0; j < js_patok.length(); j++) {
                                    JSONObject d = js_patok.getJSONObject(j);
                                    String pat_jenis = d.getString("jenis");
                                    String pat_nama = d.getString("nama");
                                    String pat_cx = d.getString("x");
                                    String pat_cy = d.getString("y");
                                    String pat_waktu = d.getString("waktu");
                                    String pat_ket = d.getString("ket");
                                    String pat_userid = d.getString("userid");
                                    String pat_id_server = d.getString("id");
                                    List<DB_Patok> setobj = new LinkedList<DB_Patok>();
                                    setobj = sws_db.DBPatok_CheckDariServer(Integer.parseInt(pat_id_server));
                                    if (setobj.size() > 0) {
                                        int pat_urut = setobj.get(0).getUrut();
                                        sws_db.DBPatok_Delete(pat_urut);
                                    }
                                    //(anama, jenis, kebun, acx, acy, logdate, userid, 0, aket,0,0);
                                    sws_db.DBPatok_Add(pat_nama, Integer.parseInt(pat_jenis), kebun, pat_cx, pat_cy, pat_waktu,
                                            Integer.parseInt(pat_userid), 1, pat_ket, 1, Integer.parseInt(pat_id_server), 2);
                                }
                            }
                            JSONArray js_jalan = c.getJSONArray("jalan");
                            if (js_jalan != null) {
                                for (int j = 0; j < js_jalan.length(); j++) {
                                    JSONObject d = js_jalan.getJSONObject(j);
                                    String jal_jenis = d.getString("jenis");
                                    String jal_kondisi = d.getString("kondisi");
                                    String jal_waktu = d.getString("waktu");
                                    String jal_userid = d.getString("userid");
                                    String jal_ket = d.getString("ket");
                                    String jal_cx = d.getString("x");
                                    String jal_cy = d.getString("y");
                                    String jal_lebar = d.getString("lebar");
                                    String jal_id_server = d.getString("id");
                                    List<DB_Jalan> setobj = new LinkedList<DB_Jalan>();
                                    setobj = sws_db.DBJalan_CheckDariServer(Integer.parseInt(jal_id_server));
                                    if (setobj.size() > 0) {
                                        int jal_urut = setobj.get(0).getUrut();
                                        sws_db.DBJalan_Delete(jal_urut, 99, 0);
                                    }
                                    //jenis, kondisi, kebun, logdate, userid, aket, acx, acy, 0, alebar,0,0
                                    sws_db.DBJalan_Add(
                                            Integer.parseInt(jal_jenis), Integer.parseInt(jal_kondisi), kebun, jal_waktu,
                                            Integer.parseInt(jal_userid), jal_ket, jal_cx, jal_cy, 1, jal_lebar,
                                            1, Integer.parseInt(jal_id_server), 2);
                                }
                            }
                            JSONArray js_jembatan = c.getJSONArray("jembatan");
                            if (js_jembatan != null) {
                                for (int j = 0; j < js_jembatan.length(); j++) {
                                    JSONObject d = js_jembatan.getJSONObject(j);
                                    String jem_jenis = d.getString("jenis");
                                    String jem_kondisi = d.getString("kondisi");
                                    String jem_waktu = d.getString("waktu");
                                    String jem_userid = d.getString("userid");
                                    String jem_ket = d.getString("ket");
                                    String jem_cx = d.getString("x");
                                    String jem_cy = d.getString("y");
                                    String jem_lebar = d.getString("lebar");
                                    String jem_id_server = d.getString("id");
                                    List<DB_Jembatan> setobj = new LinkedList<DB_Jembatan>();
                                    setobj = sws_db.DBJembatan_CheckDariServer(Integer.parseInt(jem_id_server));
                                    if (setobj.size() > 0) {
                                        int jem_urut = setobj.get(0).getUrut();
                                        sws_db.DBJalan_Delete(jem_urut, 99, 0);
                                    }
                                    //jenis, kondisi, kebun, logdate, userid, aket, acx, acy, 0, alebar,0,0
                                    sws_db.DBJembatan_Add(
                                            Integer.parseInt(jem_jenis), Integer.parseInt(jem_kondisi), kebun, jem_waktu,
                                            Integer.parseInt(jem_userid), jem_ket, jem_cx, jem_cy, 1, jem_lebar,
                                            1, Integer.parseInt(jem_id_server), 2);
                                }
                            }

                            JSONArray js_tanaman = c.getJSONArray("tanaman");
                            if (js_tanaman != null) {
                                for (int j = 0; j < js_tanaman.length(); j++) {
                                    JSONObject d = js_tanaman.getJSONObject(j);
                                    String tan_jenis = d.getString("jenis");
                                    String tan_lingkar = d.getString("lingkar");
                                    String tan_jarak = d.getString("jarak");
                                    String tan_tinggi = d.getString("tinggi");
                                    String tan_banyaki = d.getString("banyak");
                                    String tan_waktu = d.getString("waktu");
                                    String tan_userid = d.getString("userid");
                                    String tan_afd = d.getString("afd");
                                    String tan_blok = d.getString("blok");
                                    String tan_ttanami = d.getString("ttanam");
                                    String tan_tpaneni = d.getString("tpanen");
                                    String tan_vapanen = d.getString("apanen");
                                    String tan_vbpanen = d.getString("bpanen");
                                    String tan_vspanen = d.getString("spanen");
                                    String tan_ket = d.getString("ket");
                                    String tan_cx = d.getString("x");
                                    String tan_cy = d.getString("y");
                                    String tan_id_server = d.getString("id");
                                    List<DB_Tanaman> setobj = new LinkedList<DB_Tanaman>();
                                    setobj = sws_db.DBTanaman_CheckDariServer(Integer.parseInt(tan_id_server));
                                    if (setobj.size() > 0) {
                                        int tan_urut = setobj.get(0).getUrut();
                                        sws_db.DBJalan_Delete(tan_urut, 99, 0);
                                    }
                                    //jenis, vlingkar, vjarak, vtinggi, vbanyaki, kebun, logdate, userid, vafdeling, vblok,
                                    //vttanami, vtpaneni, vapanen, vbpanen, vspanen, aket, acx, acy, 0, 0, 0
                                    sws_db.DBTanaman_Add(Integer.parseInt(tan_jenis), tan_lingkar, tan_jarak, tan_tinggi, Integer.parseInt(tan_banyaki),
                                            kebun, tan_waktu, Integer.parseInt(tan_userid), tan_afd, tan_blok, Integer.parseInt(tan_ttanami),
                                            Integer.parseInt(tan_tpaneni), tan_vapanen, tan_vbpanen, tan_vspanen, tan_ket, tan_cx, tan_cy,
                                            0, 1, Integer.parseInt(tan_id_server), 2);
                                }
                            }
                            JSONArray js_saluran = c.getJSONArray("saluran");
                            if (js_saluran != null) {
                                for (int j = 0; j < js_saluran.length(); j++) {
                                    JSONObject d = js_saluran.getJSONObject(j);
                                    String sal_jenis = d.getString("jenis");
                                    String sal_kondisi = d.getString("kondisi");
                                    String sal_waktu = d.getString("waktu");
                                    String sal_userid = d.getString("userid");
                                    String sal_ket = d.getString("ket");
                                    String sal_cx = d.getString("x");
                                    String sal_cy = d.getString("y");
                                    String sal_lebar = d.getString("lebar");
                                    String sal_id_server = d.getString("id");
                                    List<DB_Saluran> setobj = new LinkedList<DB_Saluran>();
                                    setobj = sws_db.DBSaluran_CheckDariServer(Integer.parseInt(sal_id_server));
                                    if (setobj.size() > 0) {
                                        int sal_urut = setobj.get(0).getUrut();
                                        sws_db.DBJalan_Delete(sal_urut, 99, 0);
                                    }
                                    //jenis, kondisi, kebun, logdate, userid, aket, acx, acy, 0, alebar,0,0);
                                    sws_db.DBSaluran_Add(Integer.parseInt(sal_jenis), Integer.parseInt(sal_kondisi), kebun, sal_waktu,
                                            Integer.parseInt(sal_userid), sal_ket, sal_cx, sal_cy, 1, sal_lebar, 1,
                                            Integer.parseInt(sal_id_server), 2);
                                }
                            }
                            JSONArray js_refpekerja = c.getJSONArray("pekerja");
                            if (js_refpekerja != null) {
                                for (int j = 0; j < js_refpekerja.length(); j++) {
                                    JSONObject d = js_refpekerja.getJSONObject(j);
                                    int pekerja_urut = Integer.parseInt(d.getString("urut"));
                                    int pekerja_idkebun = Integer.parseInt(d.getString("idkebun"));
                                    int pekerja_idafd = Integer.parseInt(d.getString("idafd"));
                                    String pekerja_idpeg = d.getString("idpeg");
                                    int pekerja_idmandor = Integer.parseInt(d.getString("idmandor"));
                                    String pekerja_nama = d.getString("nama");
                                    int pekerja_jab = Integer.parseInt(d.getString("jab"));

                                    List<DB_Pekerja> setobj = new LinkedList<DB_Pekerja>();
                                    setobj = sws_db.DBPekerja_Get(pekerja_urut, pekerja_idkebun, pekerja_idafd, pekerja_idpeg, pekerja_idmandor);
                                    if (setobj.size() > 0) {
                                        //int pekerja_urut = setobj.get(0).getUrut();
                                        sws_db.DBPekerja_Update(pekerja_urut, pekerja_idkebun, pekerja_idafd, pekerja_idmandor, pekerja_idpeg, pekerja_nama, pekerja_jab);
                                    } else {
                                        sws_db.DBPekerja_Add(pekerja_urut, pekerja_idkebun, pekerja_idafd, pekerja_idmandor, pekerja_idpeg, pekerja_nama, pekerja_jab);
                                    }
                                }
                            }

                            dbsetting.setActiveKebun(kebun);
                            List<DB_Ref_Kebun> kaktif = new LinkedList<DB_Ref_Kebun>();
                            if (dbsetting.getActiveKebun() == 0) {
                                tv_jab.setText("Kebun : [Belum didefinisikan]");
                            } else {
                                kaktif = sws_db.DBRefKebun_Get(dbsetting.getActiveKebun());
                                if (kaktif.size() == 0) {
                                    tv_jab.setText("Kebun : [Belum didefinisikan]");
                                } else {
                                    tv_jab.setText("Kebun : " + kaktif.get(0).getNama());
                                }
                            }
                            pindah_kebun.dismiss();
                            //progressDialog.dismiss();
                            //Intent intent = new Intent(frm_download.this,MapsActivity.class);
                            //setResult(1, intent);
                            //startActivity(intent);
                            //finish();
                        }
                    } catch (final JSONException e) {
                        Log.e(TAG, "Json parsing error: " + e.getMessage());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),
                                        "Json parsing error: " + e.getMessage(),
                                        Toast.LENGTH_LONG)
                                        .show();
                            }
                        });

                    }
                } else {
                    Log.e(TAG, "Gagal dalam mengambil data dari servere");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Gagal dalam mengambil data dari server",
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }

                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                progressDialog.dismiss();
            }
        }

        doGetDataAsync dogetdataasync = new doGetDataAsync();
        dogetdataasync.execute(kebun);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //TODO 3 set while your menu selected
        AlertDialog.Builder alertDialogBuilder;
        AlertDialog alertDialog;
        //Toast.makeText(this, "Selected Item: " +item.getTitle(), Toast.LENGTH_SHORT).show();
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_pindah:
                pindah_kebun = new Dialog(this);
                // Set GUI of login screen
                pindah_kebun.setContentView(R.layout.frm_pindah_kebun);
                pindah_kebun.setTitle("Pindah Kebun");

                // Init button of login GUI
                Button btnProses = (Button) pindah_kebun.findViewById(R.id.kaktif_proses);
                Button btnCancel = (Button) pindah_kebun.findViewById(R.id.kaktif_batal);
                final Spinner spinkebun = (Spinner) pindah_kebun.findViewById(R.id.kaktif_kebun);
                List<DB_Ref_Kebun> setkebun = new LinkedList<DB_Ref_Kebun>();
                final ArrayList<String> kode_kebun = new ArrayList<String>();
                ArrayList<String> nama_kebun = new ArrayList<String>();
                setkebun = sws_db.DBRefKebun_Get(0);
                for (int iix = 0; iix < setkebun.size(); iix++) {
                    kode_kebun.add(String.valueOf(setkebun.get(iix).getKode()));
                    nama_kebun.add(setkebun.get(iix).getNama());
                }
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, nama_kebun);
                spinkebun.setAdapter(adapter1);
                // Attached listener for login GUI button
                btnProses.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pilihan = spinkebun.getSelectedItemPosition();
                        String kebun_terpilih = kode_kebun.get(pilihan);
                        dbsetting = sws_db.DBSetting_Get();
                        sws_db.DBSetting_UpdateUser(dbsetting.getActiveUser(), Integer.parseInt(kebun_terpilih));
                        doGetData(kebun_terpilih);
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pindah_kebun.dismiss();
                    }
                });
                // Make dialog box visible.
                pindah_kebun.show();

                return true;
            case R.id.menu_absensi:
                //absensi
                intent = new Intent(getApplicationContext(), frm_absensi.class);
                startActivityForResult(intent, 7009);
                return true;
            case R.id.menu_info:
                //menu info
                intent = new Intent(getApplicationContext(), frm_info.class);
                startActivityForResult(intent, 7008);
                return true;
            case R.id.menu_kml:
                // do your code
                intent = new Intent(getApplicationContext(), frm_daftar_peta.class);
                startActivityForResult(intent, 7002);
                return true;
            case R.id.toggle_kml:
                intent = new Intent(getApplicationContext(), frm_toggle_peta.class);
                startActivityForResult(intent, 7003);
                return true;
            case R.id.menu_sekitar:
                intent = new Intent(getApplicationContext(), frm_sekitar.class);
                intent.putExtra("cx", String.valueOf(sws_x));
                intent.putExtra("cy", String.valueOf(sws_y));
                intent.putExtra("mulai", glob_sekitar_mulai);
                intent.putExtra("jarak", glob_sekitar_jarak);
                startActivityForResult(intent, 7004);
                return true;
            case R.id.menu_lihatdata:
                intent = new Intent(getApplicationContext(), frm_lihatdata.class);
                intent.putExtra("cx", String.valueOf(sws_x));
                intent.putExtra("cy", String.valueOf(sws_y));
                intent.putExtra("mulai", glob_sekitar_mulai);
                intent.putExtra("jarak", glob_sekitar_jarak);
                startActivityForResult(intent, 7010);
                return true;
            case R.id.menu_gallery:
                // do your code
                intent = new Intent(getApplicationContext(), frm_gallery.class);
                startActivityForResult(intent, 7005);
                return true;
            case R.id.menu_download:
                intent = new Intent(getApplicationContext(), frm_download.class);
                startActivityForResult(intent, 7006);
                return true;
            case R.id.menu_upload:
                intent = new Intent(getApplicationContext(), frm_upload.class);
                startActivityForResult(intent, 7007);
                return true;
            case R.id.menu_setting:
                //setting
                intent = new Intent(getApplicationContext(), frm_setting.class);
                startActivityForResult(intent, 7001);
                return true;
            case R.id.menu_logout:
                // do your code
                alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("SIMTAGAR");
                alertDialogBuilder.setMessage("Tekan Ya untuk Log-Out");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //map_main.this.finish();
                        sws_db.DBUser_Delete_All();
                        sws_db.DBSetting_UpdateUser(0, 0);
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                alertDialogBuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                return true;
            case R.id.menu_keluar:
                // do your code
                alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("SIMTAGAR");
                alertDialogBuilder.setMessage("Tekan Ya untuk keluar");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //map_main.this.finish();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("SWS_EXIT", true);
                        startActivity(intent);
                        finish();
                    }
                });
                alertDialogBuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                return true;
            default:
                return false;
        }

    }
}
