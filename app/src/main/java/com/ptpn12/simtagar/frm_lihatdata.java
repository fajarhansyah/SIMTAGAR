package com.ptpn12.simtagar;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;

public class frm_lihatdata extends TabActivity {
    TabHost tabHost;
    int banyak_tab = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_lihatdata);

        tabHost = getTabHost();

        TabSpec tab1 = tabHost.newTabSpec("Jalan");
        tab1.setIndicator("Jalan");
        Intent tab1Intent = new Intent(frm_lihatdata.this, frm_ld_jalan.class);
        tab1.setContent(tab1Intent);
        tab1.setContent(tab1Intent);

        TabSpec tab2 = tabHost.newTabSpec("Jembatan");
        tab2.setIndicator("Jembatan");
        Intent tab2Intent = new Intent(frm_lihatdata.this, frm_ld_jembatan.class);
        tab2.setContent(tab2Intent);

        TabSpec tab3 = tabHost.newTabSpec("Patok");
        tab3.setIndicator("Patok");
        Intent tab3Intent = new Intent(frm_lihatdata.this, frm_ld_patok.class);
        tab3.setContent(tab3Intent);

        TabSpec tab4 = tabHost.newTabSpec("Saluran");
        tab4.setIndicator("Saluran");
        Intent tab4Intent = new Intent(frm_lihatdata.this, frm_ld_saluran.class);
        tab4.setContent(tab4Intent);

        TabSpec tab5 = tabHost.newTabSpec("Aktifitas");
        tab5.setIndicator("Aktifitas");
        Intent tab5Intent = new Intent(frm_lihatdata.this, frm_ld_aktifitas.class);
        tab5.setContent(tab5Intent);

        TabSpec tab6 = tabHost.newTabSpec("Kejadian");
        tab6.setIndicator("Kejadian");
        Intent tab6Intent = new Intent(frm_lihatdata.this, frm_ld_kejadian.class);
        tab6.setContent(tab6Intent);

        TabSpec tab7 = tabHost.newTabSpec("Komoditi");
        tab7.setIndicator("Komoditi");
        Intent tab7Intent = new Intent(frm_lihatdata.this, frm_ld_tanaman.class);
        tab7.setContent(tab7Intent);

        // Adding all TabSpec to TabHost
        tabHost.addTab(tab5);
        tabHost.addTab(tab6);
        tabHost.addTab(tab3);
        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab4);
        tabHost.addTab(tab7);

        banyak_tab = 7;
        tabHost.setCurrentTab(0);
        tabHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.parseColor("#00923F"));
        TextView tv;
        tv = (TextView) tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
        tv.setTextColor(Color.parseColor("#e3e0ee"));


        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                // TODO Auto-generated method stub
                int iix;
                TextView tv;
                for(iix = 0; iix < banyak_tab; iix++ ) {
                    tabHost.getTabWidget().getChildAt(iix).setBackgroundColor(0x000000);
                    tv = (TextView) tabHost.getTabWidget().getChildAt(iix).findViewById(android.R.id.title);
                    tv.setTextColor(Color.parseColor("#000000"));
                }
                int tab = tabHost.getCurrentTab();
                tabHost.getTabWidget().getChildAt(tab).setBackgroundColor(Color.parseColor("#00923F"));
                tv = (TextView) tabHost.getTabWidget().getChildAt(tab).findViewById(android.R.id.title);
                tv.setTextColor(Color.parseColor("#e3e0ee"));
            }
        });
    }
    
    public void pindahLokasi(String x, String y) {
        Intent intent1 = new Intent();
        intent1.putExtra("cx", x);
        intent1.putExtra("cy", y);
        setResult(7004, intent1);
        finish();
    }
}
