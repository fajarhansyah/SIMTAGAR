package com.ptpn12.simtagar;

import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class frm_toggle_peta extends AppCompatActivity {
    MySQLLiteHelper sws_db;
    DB_Setting dbsetting;
    DB_User dbuser;
    TableLayout ll;
    Button btn_ok, btn_cancel;
    CheckBox checkBox;
    TextView tv;

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

    private ProgressDialog pDialog;
    public static final int progress_bar_type = 0;
    int banyak_data = 0;
    int active_kebun = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_toggle_peta);
        sws_db = new MySQLLiteHelper(getApplicationContext());
        TableLayout ll = (TableLayout) findViewById(R.id.toggle_peta_tl);
        btn_ok = (Button) findViewById(R.id.toggle_peta_proses);
        btn_cancel = (Button) findViewById(R.id.toggle_peta_batal);
        downloadedid.clear();

        dbsetting = sws_db.DBSetting_Get();
        dbuser = sws_db.DBUSer_Get();
        if( dbuser.getTingkat() < 3) {
            setkml = sws_db.DBKML_Get(0, "", 99);
        } else {
            setkml = sws_db.DBKML_GetKebun(dbuser.getIDKebun());
        }

        int bbaris = 0;
        for( int i=0; i < setkml.size(); i++ ) {
            akml = setkml.get(i);
            if( akml.getSudah() == 1) {
                TableRow row= new TableRow(this);
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(lp);
                checkBox = new CheckBox(this);
                tv = new TextView(this);
                checkBox.setText("");
                anid = View.generateViewId();
                checkBox.setId(anid);
                if( akml.getTampil() == 1) {
                    checkBox.setChecked(true);
                } else {
                    checkBox.setChecked(false);
                }
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
                ll.addView(row,bbaris);
                bbaris += 1;
            }
        }

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                DB_Setting asetting;
                CheckBox cb;
                int jml_tampil = 0;
                for( int i = 0; i < listid.size(); i++ ) {
                    int dum = (int) listid.get(i);
                    cb = (CheckBox) findViewById(dum);
                    glob_iddb = (int) listiddb.get(i);
                    if (cb.isChecked()) {
                        jml_tampil = jml_tampil + 1;
                    }
                }
                if( jml_tampil > 2 ) {
                    Toast.makeText(frm_toggle_peta.this, "Untuk kenyamanan, peta yang ditampilkan sekaligus dibatasi hanya 2 peta saja", Toast.LENGTH_SHORT).show();
                    return;
                }
                for( int i = 0; i < listid.size(); i++ ) {
                    int dum = (int) listid.get(i);
                    cb = (CheckBox) findViewById(dum);
                    glob_iddb = (int) listiddb.get(i);
                    if( cb.isChecked()) {
                        sws_db.DBKML_UpdateTampil(glob_iddb, 1);
                    } else {
                        sws_db.DBKML_UpdateTampil(glob_iddb, 0);
                    }
                }
                Intent intent1 = new Intent();
                setResult(1105, intent1);
                finish();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent1 = new Intent();
                setResult(1106, intent1);
                finish();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
