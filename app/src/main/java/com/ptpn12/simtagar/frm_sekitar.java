package com.ptpn12.simtagar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;


public class frm_sekitar extends AppCompatActivity {
    Button btn_ok, btn_cancel;
    EditText spin_jarak;
    MySQLLiteHelper sws_db;
    String acx, acy;
    Double xcoord, ycoord;
    ListView hasil;
    frm_sekitar_IA adapter;
    String dumjarak, mulai;
    public frm_sekitar CustomListView = null;
    public ArrayList<frm_sekitar_list> CustomListViewValuesArr = new ArrayList<frm_sekitar_list>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_sekitar);
        sws_db = new MySQLLiteHelper(getApplicationContext());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            acx = extras.getString("cx");
            acy = extras.getString("cy");
            dumjarak = extras.getString("jarak");
            mulai = extras.getString("mulai");
        }
        xcoord = Double.parseDouble(acx);
        ycoord = Double.parseDouble(acy);

        spin_jarak = (EditText) findViewById(R.id.sekitar_jarak);
        btn_ok = (Button) findViewById(R.id.sekitar_proses);
        btn_cancel = (Button) findViewById(R.id.sekitar_batal);
        hasil = (ListView) findViewById(R.id.sekitar_list);
        spin_jarak.setText(dumjarak);
        CustomListView = this;
        update_hasil();

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                update_hasil();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent1 = new Intent();
                intent1.putExtra("mulai", "1");
                intent1.putExtra("jarak", spin_jarak.getText().toString().trim());
                setResult(113, intent1);
                finish();
            }
        });

        hasil.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                // TODO Auto-generated method stub
                if( CustomListViewValuesArr == null ) { return; }
                frm_sekitar_list clv = CustomListViewValuesArr.get(position);
                Log.e("FS", String.valueOf(clv.getJarak()));
                sekitarZoom(clv.getCX(), clv.getCY());
                //Toast.makeText(MainActivity.this, listItemsValue[position], Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void sekitarZoom(String x, String y) {
        Intent intent1 = new Intent();
        intent1.putExtra("mulai", "1");
        intent1.putExtra("jarak", spin_jarak.getText().toString().trim());
        intent1.putExtra("cx", x);
        intent1.putExtra("cy", y);
        setResult(7004, intent1);
        finish();
    }

    //@Override
    //public void onItemClick(AdapterView adapterView, View view, int position, long id) {
    //    Log.e("FS", String.valueOf(position));
    //}

    private double hitung_jarak(Double awalx, Double awaly, Double akhirx, Double akhiry) {
        return Math.pow(Math.pow((awalx - akhirx),2) + Math.pow((awaly - akhiry),2),0.5) * 111.3199;
    }

    private void update_hasil() {
        CustomListViewValuesArr.clear();
        //mulai melakukan pencarian
        String dumket;
        int dumkode;
        String dum = spin_jarak.getText().toString().trim();
        Double jarak;
        if( dum.equals("")) {
            Toast.makeText(getApplicationContext(), "Isikan jarak maksimum pencarian", Toast.LENGTH_SHORT).show();
            return;
        }
        Double jarakmax = Double.parseDouble(dum);
        List<DB_Aktifitas> setaktifitas = new LinkedList<DB_Aktifitas>();
        setaktifitas = sws_db.DBAktifitas_Get(0,0,99,"",0);
        DB_Aktifitas akt;
        Log.e("FS", "Banyak record aktifitas --> " + String.valueOf(setaktifitas.size()));
        for( int i = 0; i < setaktifitas.size(); i++ ) {
            akt = setaktifitas.get(i);
            jarak = hitung_jarak(xcoord, ycoord, Double.parseDouble(akt.getCX()), Double.parseDouble(akt.getCY()));
            Log.e("FS", "Jarak --> " + String.valueOf(jarak) + " ::: Jarak maks --> " + String.valueOf(jarakmax));
            if( jarak <= jarakmax ) {
                //catat
                final frm_sekitar_list fsl = new frm_sekitar_list();
                fsl.setImg(1);
                fsl.setJenis("Aktifitas");
                fsl.setCX(akt.getCX());
                fsl.setCY(akt.getCY());
                fsl.setKet(akt.getKet());
                fsl.setJarak(jarak);
                CustomListViewValuesArr.add( fsl );
            }
        }
        List<DB_Kejadian> setkejadian = new LinkedList<DB_Kejadian>();
        setkejadian = sws_db.DBKejadian_Get(0,0,99,"",0);
        DB_Kejadian kej;
        for( int i = 0; i < setkejadian.size(); i++ ) {
            kej = setkejadian.get(i);
            jarak = hitung_jarak(xcoord, ycoord, Double.parseDouble(kej.getCX()), Double.parseDouble(kej.getCY()));
            if( jarak <= jarakmax ) {
                //catat
                final frm_sekitar_list fsl = new frm_sekitar_list();
                fsl.setImg(2);
                fsl.setJenis("Kejadian");
                fsl.setCX(kej.getCX());
                fsl.setCY(kej.getCY());
                fsl.setKet(kej.getKet());
                fsl.setJarak(jarak);
                CustomListViewValuesArr.add( fsl );
            }
        }
        List<DB_Patok> setpatok = new LinkedList<DB_Patok>();
        setpatok = sws_db.DBPatok_Get(0,99,99);
        DB_Patok pat;
        for( int i = 0; i < setpatok.size(); i++ ) {
            pat = setpatok.get(i);
            jarak = hitung_jarak(xcoord, ycoord, Double.parseDouble(pat.getCX()), Double.parseDouble(pat.getCY()));
            if( jarak <= jarakmax ) {
                //catat
                final frm_sekitar_list fsl = new frm_sekitar_list();
                fsl.setImg(4);
                fsl.setJenis("Patok Pembatas");
                fsl.setCX(pat.getCX());
                fsl.setCY(pat.getCY());
                dumkode = pat.getJenis();
                List<DB_Ref_Patok> refpatok = new LinkedList<DB_Ref_Patok>();
                refpatok = sws_db.DBRefPatok_Get(dumkode);
                if( refpatok.size() == 0 ) {
                    dumket = "Tidak diketahui";
                } else {
                    dumket = refpatok.get(0).getNama();
                }
                fsl.setKet(dumket);
                fsl.setJarak(jarak);
                CustomListViewValuesArr.add( fsl );
            }
        }

        List<DB_Jalan> setjalan = new LinkedList<DB_Jalan>();
        setjalan = sws_db.DBJalan_Get(0,99);
        DB_Jalan jal;
        for( int i = 0; i < setjalan.size(); i++ ) {
            jal = setjalan.get(i);
            jarak = hitung_jarak(xcoord, ycoord, Double.parseDouble(jal.getCX()), Double.parseDouble(jal.getCY()));
            if( jarak <= jarakmax ) {
                //catat
                final frm_sekitar_list fsl = new frm_sekitar_list();
                fsl.setImg(4);
                fsl.setJenis("Kondisi Jalan");
                fsl.setCX(jal.getCX());
                fsl.setCY(jal.getCY());
                dumkode = jal.getJenis();
                List<DB_Ref_Jalan> refjalan = new LinkedList<DB_Ref_Jalan>();
                refjalan = sws_db.DBRefJalan_Get(dumkode);
                if( refjalan.size() == 0 ) {
                    dumket = "Tidak diketahui";
                } else {
                    dumket = refjalan.get(0).getNama();
                }
                fsl.setKet(dumket);
                fsl.setJarak(jarak);
                CustomListViewValuesArr.add( fsl );
            }
        }
        List<DB_Jembatan> setjembatan = new LinkedList<DB_Jembatan>();
        setjembatan = sws_db.DBJembatan_Get(0,99);
        DB_Jembatan jem;
        for( int i = 0; i < setjembatan.size(); i++ ) {
            jem = setjembatan.get(i);
            jarak = hitung_jarak(xcoord, ycoord, Double.parseDouble(jem.getCX()), Double.parseDouble(jem.getCY()));
            if( jarak <= jarakmax ) {
                //catat
                final frm_sekitar_list fsl = new frm_sekitar_list();
                fsl.setImg(5);
                fsl.setJenis("Kondisi Jembatan");
                fsl.setCX(jem.getCX());
                fsl.setCY(jem.getCY());
                dumkode = jem.getJenis();
                List<DB_Ref_Jembatan> refjem = new LinkedList<DB_Ref_Jembatan>();
                refjem = sws_db.DBRefJembatan_Get(dumkode);
                if( refjem.size() == 0 ) {
                    dumket = "Tidak diketahui";
                } else {
                    dumket = refjem.get(0).getNama();
                }
                fsl.setKet(dumket);
                fsl.setJarak(jarak);
                CustomListViewValuesArr.add( fsl );
            }
        }
        List<DB_Tanaman> settanaman = new LinkedList<DB_Tanaman>();
        settanaman = sws_db.DBTanaman_Get(0,99);
        DB_Tanaman tan;
        for( int i = 0; i < settanaman.size(); i++ ) {
            tan = settanaman.get(i);
            jarak = hitung_jarak(xcoord, ycoord, Double.parseDouble(tan.getCX()), Double.parseDouble(tan.getCY()));
            if( jarak <= jarakmax ) {
                //catat
                final frm_sekitar_list fsl = new frm_sekitar_list();
                fsl.setImg(6);
                fsl.setJenis("Komoditi");
                fsl.setCX(tan.getCX());
                fsl.setCY(tan.getCY());
                dumkode = tan.getJenis();
                List<DB_Ref_Komoditi> refkom = new LinkedList<DB_Ref_Komoditi>();
                refkom = sws_db.DBRefKomoditi_Get(dumkode);
                if( refkom.size() == 0 ) {
                    dumket = "Tidak diketahui";
                } else {
                    dumket = refkom.get(0).getNama();
                }
                fsl.setKet(dumket);
                fsl.setJarak(jarak);
                CustomListViewValuesArr.add( fsl );
            }
        }
        List<DB_Saluran> setsaluran = new LinkedList<DB_Saluran>();
        setsaluran = sws_db.DBSaluran_Get(0,99);
        DB_Saluran sal;
        for( int i = 0; i < setsaluran.size(); i++ ) {
            sal = setsaluran.get(i);
            jarak = hitung_jarak(xcoord, ycoord, Double.parseDouble(sal.getCX()), Double.parseDouble(sal.getCY()));
            if( jarak <= jarakmax ) {
                //catat
                final frm_sekitar_list fsl = new frm_sekitar_list();
                fsl.setImg(7);
                fsl.setJenis("Kondisi Saluran");
                fsl.setCX(sal.getCX());
                fsl.setCY(sal.getCY());
                dumkode = sal.getJenis();
                List<DB_Ref_Saluran> refsal = new LinkedList<DB_Ref_Saluran>();
                refsal = sws_db.DBRefSaluran_Get(dumkode);
                if( refsal.size() == 0 ) {
                    dumket = "Tidak diketahui";
                } else {
                    dumket = refsal.get(0).getNama();
                }
                fsl.setKet(dumket);
                fsl.setJarak(jarak);
                CustomListViewValuesArr.add( fsl );
            }
        }
        List<DB_Image> setimage = new LinkedList<DB_Image>();
        setimage = sws_db.DBImage_Get(0,99,0,99);
        DB_Image ima;
        for( int i = 0; i < setimage.size(); i++ ) {
            ima = setimage.get(i);
            jarak = hitung_jarak(xcoord, ycoord, Double.parseDouble(ima.getCx()), Double.parseDouble(ima.getCy()));
            if( jarak <= jarakmax ) {
                //catat
                final frm_sekitar_list fsl = new frm_sekitar_list();
                fsl.setImg(8);
                fsl.setJenis("Foto Geotag");
                fsl.setCX(ima.getCx());
                fsl.setCY(ima.getCy());
                fsl.setKet("Hasil foto");
                fsl.setJarak(jarak);
                CustomListViewValuesArr.add( fsl );
            }
        }

        adapter=new frm_sekitar_IA( CustomListView, CustomListViewValuesArr );
        hasil.setAdapter( adapter );
    }
}
