package com.ptpn12.simtagar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class frm_ld_aktifitas extends Activity {
    MySQLLiteHelper sws_db;
    ListView list;
    frm_ld_aktifitas_IA adapter;
    public  frm_ld_aktifitas CustomListView = null;
    public ArrayList<frm_ld_aktifitas_list> CustomListViewValuesArr = new ArrayList<frm_ld_aktifitas_list>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_ld_aktifitas);

        sws_db = new MySQLLiteHelper(getApplicationContext());
        CustomListView = this;

        /******** Take some data in Arraylist ( CustomListViewValuesArr ) ***********/
        setListData();

        list= ( ListView )findViewById( R.id.ld_aktifitas_list );  // List defined in XML ( See Below )

        /**************** Create Custom Adapter *********/
        adapter=new frm_ld_aktifitas_IA( CustomListView, CustomListViewValuesArr );
        list.setAdapter( adapter );

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                // TODO Auto-generated method stub
                if( CustomListViewValuesArr.size() == 0 ) { return; }
                frm_ld_aktifitas_list clv = CustomListViewValuesArr.get(position);
                ((frm_lihatdata)getParent()).pindahLokasi(clv.getCx(), clv.getCy());
                //Toast.makeText(MainActivity.this, listItemsValue[position], Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        sws_db = new MySQLLiteHelper(getApplicationContext());
        setListData();
        adapter=new frm_ld_aktifitas_IA( CustomListView, CustomListViewValuesArr );
        list.setAdapter( adapter );
    }

    private void setListData() {
        List<DB_Aktifitas> setaktifitas = new LinkedList<DB_Aktifitas>();
        setaktifitas = sws_db.DBAktifitas_Get(0,0, 99, "", 0);
        DB_Aktifitas jal;
        String dumket = "";
        CustomListViewValuesArr.clear();
        for( int i = 0; i < setaktifitas.size(); i++ ) {
            jal = setaktifitas.get(i);
            final frm_ld_aktifitas_list fsl = new frm_ld_aktifitas_list();

            fsl.setUrut(jal.getUrut());
            fsl.setKebun(jal.getKebun());
            fsl.setJenis(jal.getJenis());
            fsl.setElama(jal.getElama());
            fsl.setEbiaya(jal.getEbiaya());
            fsl.setEsdm(jal.getEsdm());
            fsl.setKet(jal.getKet());
            fsl.setCx(jal.getCX());
            fsl.setCy(jal.getCY());
            fsl.setWaktu(jal.getWaktu());
            fsl.setUserId(jal.getUserID());
            fsl.setKirim(jal.getKirim());
            fsl.setDariServer(jal.getDariServer());
            fsl.setIdServer(jal.getIDServer());
            fsl.setAsli(jal.getAsli());
            List<DB_Ref_Aktifitas> refaktifitas = new LinkedList<DB_Ref_Aktifitas>();
            refaktifitas = sws_db.DBRefAktifitas_Get(jal.getJenis());
            if( refaktifitas.size() == 0 ) {
                dumket = "Tidak diketahui";
            } else {
                dumket = refaktifitas.get(0).getNama();
            }
            fsl.setKetJenis(dumket);
            fsl.setKetKebun("");
            fsl.setKetUser("");
            if( jal.getKirim() == 0 ) {
                fsl.setKetKirim("Belum");
            } else {
                fsl.setKetKirim("Sudah");
            }
            if( jal.getDariServer() == 1 ) {
                fsl.setKetDariServer("Data dari server");
            } else {
                fsl.setKetDariServer("Data lokal di HP");
            }
            if( jal.getAsli() == 1) {
                fsl.setKetAsli("Data asli");
            } else {
                fsl.setKetAsli("Data sudah dirubah");
            }
            CustomListViewValuesArr.add( fsl );
        }
    }
}
