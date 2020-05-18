package com.ptpn12.simtagar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class frm_daftar_peta extends AppCompatActivity {
    MySQLLiteHelper sws_db;
    DB_Setting dbsetting;
    DB_User dbuser;
    TableLayout ll;
    Button btn_ok, btn_cancel;
    CheckBox checkBox;
    TextView tv;
    Spinner skebun;

    EditText ket, lebar;
    List<DB_KML> setkml = new LinkedList<DB_KML>();
    DB_KML akml;
    int anid = 0;
    LinkedList listid = new LinkedList();
    LinkedList listidrow = new LinkedList();
    LinkedList listidtoc = new LinkedList();
    LinkedList listidpeta = new LinkedList();
    LinkedList listiddb = new LinkedList();
    LinkedList listnama = new LinkedList();
    LinkedList downloadedid = new LinkedList();
    LinkedList downloadediddb = new LinkedList();
    LinkedList listfolder = new LinkedList();
    LinkedList listfolderkompit = new LinkedList();
    LinkedList downloadedfolder = new LinkedList();
    int glob_idrow, glob_idtoc, glob_idpeta, glob_iddb;
    String glob_folder, glob_nama, glob_folder_komplit;
    ArrayList<String> idkebun = new ArrayList<String>();
    ArrayList<String> nmkebun = new ArrayList<String>();

    private ProgressDialog pDialog;
    public static final int progress_bar_type = 0;
    int banyak_data = 0;
    int active_kebun = 0;
    int tingkat = 0;
    ProgressDialog mProgressDialog;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_daftar_peta);
        sws_db = new MySQLLiteHelper(getApplicationContext());
        TableLayout ll = (TableLayout) findViewById(R.id.daftar_peta_tl);
        btn_ok = (Button) findViewById(R.id.daftar_peta_proses);
        btn_cancel = (Button) findViewById(R.id.daftar_peta_batal);
        skebun = (Spinner) findViewById(R.id.daftar_peta_kebun_spinner);
        downloadedid.clear();

        mProgressDialog = new ProgressDialog(frm_daftar_peta.this);
        mProgressDialog.setMessage("Sedang melakukan pengunduhan peta. Mohon tunggu...");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);

        idkebun.clear();
        nmkebun.clear();
        dbsetting = sws_db.DBSetting_Get();
        active_kebun = dbsetting.getActiveKebun();
        if( active_kebun == 0 ) {
            Toast.makeText( frm_daftar_peta.this, "Definisikan dulu kebun yang aktif", Toast.LENGTH_SHORT).show();
        } else {
            List<DB_Ref_Kebun> setkebun = new LinkedList<DB_Ref_Kebun>();
            setkebun = sws_db.DBRefKebun_Get(active_kebun);
            for (int iix = 0; iix < setkebun.size(); iix++) {
                idkebun.add(String.valueOf(setkebun.get(iix).getKode()));
                nmkebun.add(setkebun.get(iix).getNama());
            }
            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, nmkebun);
            skebun.setAdapter(adapter1);
            populatepeta(Integer.parseInt(idkebun.get(0)));
        }

        skebun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int akode = Integer.parseInt(idkebun.get(position));
                //populate checkbox
                populatepeta(akode);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                DB_Setting asetting;
                CheckBox cb;
                for( int i = 0; i < listid.size(); i++ ) {
                    int dum = (int) listid.get(i);
                    cb = (CheckBox) findViewById(dum);
                    if( cb.isChecked()) {
                        //berarti harus download
                        String anama = (String) listnama.get(i);
                        glob_iddb = (int) listiddb.get(i);
                        glob_idrow = (int) listidrow.get(i);
                        glob_idtoc = (int) listidtoc.get(i);
                        glob_idpeta = (int) listidpeta.get(i);
                        glob_nama = (String) listnama.get(i);
                        glob_folder = (String) listfolder.get(i);
                        glob_folder_komplit = (String) listfolderkompit.get(i);

                        dbsetting = sws_db.DBSetting_Get();
                        //beginDownload(glob_iddb, glob_idrow, glob_idtoc, glob_idpeta, glob_nama, glob_folder, glob_folder_komplit);
                        String alamat = "http://" + dbsetting.getWebip();
                        if( dbsetting.getVd().equals("")) {
                            alamat = alamat + "/mob_peta.php";
                        } else {
                            alamat = alamat + "/" + dbsetting.getVd() + "/mob_peta.php";
                        }

                        //check file dulu
                        final File file=new File(getExternalFilesDir(null), glob_folder);
                        if( file.exists()) {
                            AlertDialog.Builder alertDialogBuilder;
                            AlertDialog alertDialog;
                            alertDialogBuilder = new AlertDialog.Builder(frm_daftar_peta.this);
                            alertDialogBuilder.setTitle("SIMTAGAR");
                            alertDialogBuilder.setMessage("Berkas KML sudah ada. Overwrite berkas KML?");
                            alertDialogBuilder.setCancelable(false);
                            alertDialogBuilder.setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    //map_main.this.finish();
                                    file.delete();
                                    try {

                                        doGetPeta(String.valueOf(glob_iddb), String.valueOf(glob_idrow), String.valueOf(glob_idtoc),
                                                String.valueOf(glob_idpeta), glob_nama, glob_folder, glob_folder_komplit);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    //beginDownload(glob_iddb, glob_idrow, glob_idtoc, glob_idpeta, glob_nama, glob_folder, glob_folder_komplit);
                                }
                            });
                            alertDialogBuilder.setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    dialog.cancel();
                                }
                            });
                            alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        } else {
                            try {
                                doGetPeta(String.valueOf(glob_iddb), String.valueOf(glob_idrow), String.valueOf(glob_idtoc),
                                        String.valueOf(glob_idpeta), glob_nama, glob_folder, glob_folder_komplit);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            //beginDownload(glob_iddb, glob_idrow, glob_idtoc, glob_idpeta, glob_nama, glob_folder, glob_folder_komplit);
                        }
                    }
                }

                //Intent intent1 = new Intent();
                //setResult(1103, intent1);
                //finish();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent1 = new Intent();
                setResult(1104, intent1);
                finish();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void populatepeta(int akebun) {
        ll = (TableLayout) findViewById(R.id.daftar_peta_tl);
        Log.e("POPULATE PETA", "Kebun -- >" + String.valueOf(akebun));
        setkml = sws_db.DBKML_GetKebun(akebun);
        listid.clear();
        listidrow.clear();
        listidpeta.clear();
        listidtoc.clear();
        listiddb.clear();
        listnama.clear();
        listfolder.clear();
        listfolderkompit.clear();


        if( ll != null ) {
            int childCount = ll.getChildCount();
            ll.removeViews(0, childCount);
        }
        for( int i=0; i < setkml.size(); i++ ) {
            akml = setkml.get(i);
            TableRow row= new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            checkBox = new CheckBox(this);
            tv = new TextView(this);
            checkBox.setText("");
            anid = View.generateViewId();
            checkBox.setId(anid);
            listid.add(anid);
            listidrow.add(akml.getIDRow());
            listidpeta.add(akml.getIDPeta());
            listidtoc.add(akml.getIDTOC());
            listiddb.add(akml.getID());
            listnama.add(akml.getNama());
            String[] separated = akml.getFolder().split("/");
            listfolder.add(separated[separated.length-1]);
            listfolderkompit.add(akml.getFolder());

            tv.setText(akml.getNama());
            row.addView(checkBox);
            row.addView(tv);
            ll.addView(row,i);
        }
    }


    private void beginDownload(int iddb, int idrow, int idtoc, int idpeta, String nama, String folder, String folderkomplit){
        File file=new File(getExternalFilesDir(null), folder);
        Log.e("DP", String.valueOf(file));
        /*
        Create a DownloadManager.Request with all the information necessary to start the download
         */
        dbsetting = sws_db.DBSetting_Get();
        String alamat = "http://" + dbsetting.getWebip();
        if( dbsetting.getVd().equals("")) {
            alamat = alamat + "/mob_peta.php";
        } else {
            alamat = alamat + "/" + dbsetting.getVd() + "/mob_peta.php";
        }
        alamat = alamat + "?id=" + String.valueOf(idrow) + "&idtoc=" + String.valueOf(idtoc) + "&idpeta=" + String.valueOf(idpeta);
        DownloadManager.Request request=new DownloadManager.Request(Uri.parse(alamat))
                .setTitle(nama)// Title of the Download Notification
                .setDescription("Sedang melakukan pengunduhan")// Description of the Download Notification
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)// Visibility of the download Notification
                .setDestinationUri(Uri.fromFile(file))// Uri of the destination file
                .setAllowedOverMetered(true)// Set if download is allowed on Mobile network
                .setAllowedOverRoaming(true);// Set if download is allowed on roaming network
        DownloadManager downloadManager= (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        long downloadID = downloadManager.enqueue(request);// enqueue puts the download request in the queue.
        Log.e("FDP DownloadID", String.valueOf(downloadID));
        downloadedid.add(downloadID);
        downloadediddb.add(iddb);
        downloadedfolder.add(folder);
        banyak_data = banyak_data + 1;
    }

    private PowerManager.WakeLock mWakeLock;

    private void doGetPeta(String iddb, String idrow, String idtoc, String idpeta, String nama, String folder, String folderkomplit) throws InterruptedException {

        class doGetPetaAsync extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                String iddb = params[0];
                String idrow = params[1];
                String idtoc = params[2];
                String idpeta = params[3];
                String nama = params[4];
                String folder = params[5];
                String folderkomplit = params[6];
                dbsetting = sws_db.DBSetting_Get();
                String alamat = "http://" + dbsetting.getWebip();
                if( dbsetting.getVd().equals("")) {
                    alamat = alamat + "/mob_peta.php";
                } else {
                    alamat = alamat + "/" + dbsetting.getVd() + "/mob_peta.php";
                }
                try {
                    URL url = new URL(alamat);
                    Map<String, Object> params1 = new LinkedHashMap<>();
                    params1.put("id", idrow);
                    params1.put("idtoc", idtoc);
                    params1.put("idpeta", idpeta);

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

                    File file=new File(getExternalFilesDir(null), folder);
                    if (!file.exists()) {
                        file.createNewFile();
                        Log.e("FDP", "File Created");
                    }

                    FileOutputStream fos = new FileOutputStream(file);//Get OutputStream for NewFile Location
                    InputStream is = conn.getInputStream();//Get InputStream for connection

                    byte[] buffer = new byte[1024];//Set buffer type
                    int len1 = 0;//init length
                    while ((len1 = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, len1);//Write new file
                    }

                    //Close all connection after doing task
                    fos.close();
                    is.close();
                    List<DB_KML> dumsetkml = new LinkedList<DB_KML>();
                    dumsetkml = sws_db.DBKML_Get(Integer.parseInt(iddb),"",99);
                    for( int j = 0; j < dumsetkml.size(); j++ ) {
                        DB_KML dk = dumsetkml.get(j);
                        sws_db.DBKML_Update(Integer.parseInt(iddb), dk.getNama(), dk.getIDKebun(), dk.getIDRow(),
                                dk.getIDTOC(), dk.getIDPeta(), dk.getFolder(), dk.getTampil(), 1);
                        File file1=new File(getExternalFilesDir(null), folder);
                        long asize = file1.length();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Peta " + dk.getNama() + " selesai diunduh (" + String.valueOf(asize) + " byte)", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                } catch (MalformedURLException e) {
                    Log.e("FDP", "MalformedURLException: " + e.getMessage());
                } catch (ProtocolException e) {
                    Log.e("FDP", "ProtocolException: " + e.getMessage());
                } catch (IOException e) {
                    Log.e("FDP", "IOException: " + e.getMessage());
                } catch (Exception e) {
                    Log.e("FDP", "Exception: " + e.getMessage());
                }



                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                PowerManager pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
                mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                        getClass().getName());
                mWakeLock.acquire();
                banyak_data = banyak_data + 1;
                if( !mProgressDialog.isShowing() ) {
                    mProgressDialog.show();
                }
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                banyak_data = banyak_data - 1;
                if( banyak_data <= 0) {
                    mProgressDialog.dismiss();
                    finish();
                }
            }
        }

        doGetPetaAsync doGetPetaAsync = new doGetPetaAsync();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            doGetPetaAsync.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, iddb, idrow, idtoc, idpeta, nama, folder, folderkomplit);
        } else {
            doGetPetaAsync.execute(iddb, idrow, idtoc, idpeta, nama, folder, folderkomplit);
        }
    }



}
