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

public class frm_ld_kejadian extends Activity {
    MySQLLiteHelper sws_db;
    ListView list;
    frm_ld_kejadian_IA adapter;
    public  frm_ld_kejadian CustomListView = null;
    public ArrayList<frm_ld_kejadian_list> CustomListViewValuesArr = new ArrayList<frm_ld_kejadian_list>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_ld_kejadian);

        sws_db = new MySQLLiteHelper(getApplicationContext());
        CustomListView = this;

        /******** Take some data in Arraylist ( CustomListViewValuesArr ) ***********/
        setListData();

        list= ( ListView )findViewById( R.id.ld_kejadian_list );  // List defined in XML ( See Below )

        /**************** Create Custom Adapter *********/
        adapter=new frm_ld_kejadian_IA( CustomListView, CustomListViewValuesArr );
        list.setAdapter( adapter );

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                // TODO Auto-generated method stub
                if( CustomListViewValuesArr.size() == 0 ) { return; }
                frm_ld_kejadian_list clv = CustomListViewValuesArr.get(position);
                ((frm_lihatdata)getParent()).pindahLokasi(clv.getCx(), clv.getCy());
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        sws_db = new MySQLLiteHelper(getApplicationContext());
        setListData();
        adapter=new frm_ld_kejadian_IA( CustomListView, CustomListViewValuesArr );
        list.setAdapter( adapter );
    }

    private void setListData() {
        List<DB_Kejadian> setkejadian = new LinkedList<DB_Kejadian>();
        setkejadian = sws_db.DBKejadian_Get(0,0,99,"",0);
        DB_Kejadian jal;
        String dumket = "";
        CustomListViewValuesArr.clear();
        for( int i = 0; i < setkejadian.size(); i++ ) {
            jal = setkejadian.get(i);
            final frm_ld_kejadian_list fsl = new frm_ld_kejadian_list();
            fsl.setUrut(jal.getUrut());
            fsl.setJenis(jal.getJenis());
            fsl.setKebun(jal.getKebun());
            fsl.setWaktu(jal.getWaktu());
            fsl.setUserId(jal.getUserID());
            fsl.setKet(jal.getKet());
            fsl.setCx(jal.getCX());
            fsl.setCy(jal.getCY());
            fsl.setKirim(jal.getKirim());
            fsl.setDariServer(jal.getDariServer());
            fsl.setIdServer(jal.getIDServer());
            fsl.setAsli(jal.getAsli());
            List<DB_Ref_Kejadian> refkejadian = new LinkedList<DB_Ref_Kejadian>();
            refkejadian = sws_db.DBRefKejadian_Get(jal.getJenis());
            if( refkejadian.size() == 0 ) {
                dumket = "Tidak diketahui";
            } else {
                dumket = refkejadian.get(0).getNama();
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
