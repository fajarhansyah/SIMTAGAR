package com.ptpn12.simtagar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "simtagar.db";
    private static final int DATABASE_VERSION = 1;

    //01. tbl_aktifitas
    private static final String TBL_AKTIFITAS = "tbl_aktifitas";
    private static final String[] TBL_AKTIFITAS_COL = {"urut", "kebun", "jenis", "elama", "ebiaya", "esdm", "ket",
            "cx", "cy", "waktu", "userid", "kirim", "dari_server", "id_server", "asli"};
    private static final String TBL_AKTIFITAS_DB = "create table tbl_aktifitas ("
            + "urut integer primary key autoincrement, kebun integer, jenis integer, elama text, ebiaya text, esdm text, "
            + " ket text, cx text, cy text, waktu text, userid integer, kirim integer, dari_server integer, id_server integer, asli integer);";

    //02. tbl_image
    private static final String TBL_IMAGE = "tbl_image";
    private static final String[] TBL_IMAGE_COL = {"id", "userid", "nfile", "cx", "cy", "kirim", "waktu", "ket", "tampil",
            "jenis", "dari_server", "id_server", "asli"};
    private static final String TBL_IMAGE_DB = "create table tbl_image ("
            + "id integer primary key autoincrement, userid integer, nfile text, cx text, cy text, "
            + "kirim integer, waktu text, ket text, tampil integer, jenis integer, dari_server integer, id_server integer, asli integer);";

    //03. tbl_jalan
    private static final String TBL_JALAN = "tbl_jalan";
    private static final String[] TBL_JALAN_COL = {"urut", "jenis", "kondisi", "kebun", "waktu", "userid", "ket",
            "cx", "cy", "kirim", "lebar", "dari_server", "id_server", "asli"};
    private static final String TBL_JALAN_DB = "create table tbl_jalan (urut integer primary key autoincrement, jenis integer, "
            + "kondisi integer, kebun integer, waktu text, userid integer, ket text, cx text, cy text, kirim integer, "
            + "lebar text, dari_server integer, id_server integer, asli integer);";

    //04. tbl_jembatan
    private static final String TBL_JEMBATAN = "tbl_jembatan";
    private static final String[] TBL_JEMBATAN_COL = {"urut", "jenis", "kondisi", "kebun", "waktu", "userid",
            "ket", "cx", "cy", "kirim", "lebar", "dari_server", "id_server", "asli"};
    private static final String TBL_JEMBATAN_DB = "create table tbl_jembatan (urut integer primary key autoincrement, jenis integer, "
            + "kondisi integer, kebun integer, waktu text, userid integer, ket text, cx text, cy text, kirim integer, "
            + "lebar text, dari_server integer, id_server integer, asli integer);";

    //05. tbl_kejadian
    private static final String TBL_KEJADIAN = "tbl_kejadian";
    private static final String[] TBL_KEJADIAN_COL = {"urut", "kebun", "jenis", "ket", "cx", "cy", "waktu",
            "userid", "kirim", "dari_server", "id_server", "asli"};
    private static final String TBL_KEJADIAN_DB = "create table tbl_kejadian ("
            + "urut integer primary key autoincrement, kebun integer, jenis integer, ket text, cx text, cy text, "
            + "waktu text, userid integer, kirim integer, dari_server integer, id_server integer, asli integer);";

    //06. tbl_kml
    private static final String TBL_KML = "tbl_kml";
    private static final String[] TBL_KML_COL = {"id", "nama", "idkebun", "idrow", "idtoc", "idpeta",
            "folder", "tampil", "sudah"};
    private static final String TBL_KML_DB = "create table tbl_kml ("
            + "id integer primary key autoincrement, nama text, idkebun integer, idrow integer, "
            + "idtoc integer, idpeta integer, folder text, tampil integer, sudah integer);";

    //07. tbl_obyek
    private static final String TBL_OBYEK = "tbl_obyek";
    private static final String[] TBL_OBYEK_COL = {"urut", "jenis", "cx", "cy", "waktu", "userid", "ket", "kirim",
            "dari_server", "id_server", "asli"};
    private static final String TBL_OBYEK_DB = "create table tbl_obyek (urut integer primary key autoincrement, jenis integer, "
            + "cx text, cy text, waktu text, userid integer, ket text, kirim integer, dari_server integer, id_server integer, asli integer);";

    //08. tbl_patok
    private static final String TBL_PATOK = "tbl_patok";
    private static final String[] TBL_PATOK_COL = {"urut", "nama", "jenis", "kebun", "cx", "cy", "waktu",
            "userid", "ket", "kirim", "dari_server", "id_server", "asli"};
    private static final String TBL_PATOK_DB = "create table tbl_patok (urut integer primary key autoincrement, nama text, "
        + " jenis integer, kebun integer, cx text, cy text, waktu text, userid integer, ket text, "
        + "kirim integer, dari_server integer, id_server integer, asli integer);";

    //09. tbl_ref_afdeling
    private static final String TBL_REF_AFDELING = "tbl_ref_afdeling";
    private static final String[] TBL_REF_AFDELING_COL = {"idkebun", "kode", "nama"};
    private static final String TBL_REF_AFDELING_DB = "create table tbl_ref_afdeling (idkebun integer, kode integer, nama text);";

    //10. tbl_ref_aktifitas
    private static final String TBL_REF_AKTIFITAS = "tbl_ref_aktifitas";
    private static final String[] TBL_REF_AKTIFITAS_COL = {"kode", "nama"};
    private static final String TBL_REF_AKTIFITAS_DB = "create table tbl_ref_aktifitas (kode integer, nama text);";

    //11. tbl_ref_jalan
    private static final String TBL_REF_JALAN = "tbl_ref_jalan";
    private static final String[] TBL_REF_JALAN_COL = {"kode", "nama"};
    private static final String TBL_REF_JALAN_DB = "create table tbl_ref_jalan (kode integer, nama text);";

    //12. tbl_ref_jalan_kondisi
    private static final String TBL_REF_JALAN_KONDISI = "tbl_ref_jalan_kondisi";
    private static final String[] TBL_REF_JALAN_KONDISI_COL = {"kode", "nama"};
    private static final String TBL_REF_JALAN_KONDISI_DB = "create table tbl_ref_jalan_kondisi (kode integer, nama text);";

    //13. tbl_ref_jembatan
    private static final String TBL_REF_JEMBATAN = "tbl_ref_jembatan";
    private static final String[] TBL_REF_JEMBATAN_COL = {"kode", "nama"};
    private static final String TBL_REF_JEMBATAN_DB = "create table tbl_ref_jembatan (kode integer, nama text);";

    //29. tbl_ref_jembatan_kondisi
    private static final String TBL_REF_JEMBATAN_KONDISI = "tbl_ref_jembatan_kondisi";
    private static final String[] TBL_REF_JEMBATAN_KONDISI_COL = {"kode", "nama"};
    private static final String TBL_REF_JEMBATAN_KONDISI_DB = "create table tbl_ref_jembatan_kondisi (kode integer, nama text);";

    //14. tbl_ref_kebun
    private static final String TBL_REF_KEBUN = "tbl_ref_kebun";
    private static final String[] TBL_REF_KEBUN_COL = {"kode", "nama"};
    private static final String TBL_REF_KEBUN_DB = "create table tbl_ref_kebun (kode integer, nama text);";

    //15. tbl_ref_keg
    private static final String TBL_REF_KEG = "tbl_ref_keg";
    private static final String[] TBL_REF_KEG_COL = {"kode", "nama"};
    private static final String TBL_REF_KEG_DB = "create table tbl_ref_keg (kode integer, nama text);";

    //16. tbl_ref_kejadian
    private static final String TBL_REF_KEJADIAN = "tbl_ref_kejadian";
    private static final String[] TBL_REF_KEJADIAN_COL = {"kode", "nama"};
    private static final String TBL_REF_KEJADIAN_DB = "create table tbl_ref_kejadian (kode integer, nama text);";

    //17. tbl_ref_komoditi
    private static final String TBL_REF_KOMODITI = "tbl_ref_komoditi";
    private static final String[] TBL_REF_KOMODITI_COL = {"kode", "nama"};
    private static final String TBL_REF_KOMODITI_DB = "create table tbl_ref_komoditi (kode integer, nama text);";

    //18. tbl_ref_obyek
    private static final String TBL_REF_OBYEK = "tbl_ref_obyek";
    private static final String[] TBL_REF_OBYEK_COL = {"kode", "nama"};
    private static final String TBL_REF_OBYEK_DB = "create table tbl_ref_obyek (kode integer, nama text);";

    //19. tbl_ref_patok
    private static final String TBL_REF_PATOK = "tbl_ref_patok";
    private static final String[] TBL_REF_PATOK_COL = {"kode", "nama"};
    private static final String TBL_REF_PATOK_DB = "create table tbl_ref_patok (kode integer, nama text);";

    //20. tbl_ref_pekerjaan
    private static final String TBL_REF_PEKERJAAN = "tbl_ref_pekerjaan";
    private static final String[] TBL_REF_PEKERJAAN_COL = {"kode", "nama"};
    private static final String TBL_REF_PEKERJAAN_DB = "create table tbl_ref_pekerjaan (kode integer, nama text);";

    //21. tbl_ref_saluran
    private static final String TBL_REF_SALURAN = "tbl_ref_saluran";
    private static final String[] TBL_REF_SALURAN_COL = {"kode", "nama"};
    private static final String TBL_REF_SALURAN_DB = "create table tbl_ref_saluran (kode integer, nama text);";

    //22. tbl_ref_saluran_kondisi
    private static final String TBL_REF_SALURAN_KONDISI = "tbl_ref_saluran_kondisi";
    private static final String[] TBL_REF_SALURAN_KONDISI_COL = {"kode", "nama"};
    private static final String TBL_REF_SALURAN_KONDISI_DB = "create table tbl_ref_saluran_kondisi (kode integer, nama text);";

    //23. tbl_ref_tanaman
    private static final String TBL_REF_TANAMAN = "tbl_ref_tanaman";
    private static final String[] TBL_REF_TANAMAN_COL = {"kode", "nama"};
    private static final String TBL_REF_TANAMAN_DB = "create table tbl_ref_tanaman (kode integer, nama text);";

    //24. tbl_saluran
    private static final String TBL_SALURAN = "tbl_saluran";
    private static final String[] TBL_SALURAN_COL = {"urut", "jenis", "kondisi", "kebun", "waktu", "userid",
            "ket", "cx", "cy", "kirim", "lebar", "dari_server", "id_server", "asli"};
    private static final String TBL_SALURAN_DB = "create table tbl_saluran (urut integer primary key autoincrement, "
            + " jenis integer, kondisi integer, kebun integer, waktu text, userid integer, ket text, cx text, cy text, "
            + " kirim integer, lebar text, dari_server integer, id_server integer, asli integer);";

    //25. tbl_setting
    private static final String TBL_SETTING = "tbl_setting";
    private static final String[] TBL_SETTING_COL = {"webip", "vd", "ikirim", "dver", "pz", "pl", "jmap", "activeuser", "tfoto", "taktif",
            "tinstruksi", "ttrack", "tkml", "tteman", "alarm", "jam", "menit", "pesan", "int_inst", "int_outbound", "int_setting",
            "int_teman", "int_gps", "int_internet", "int_track", "int_lateins", "tampil_kejadian", "tampil_patok",
            "tampil_pekerjaan", "tampil_aktifitas", "tampil_jalan", "tampil_jembatan", "tampil_tanaman",
            "tampil_obyek", "tampil_saluran", "tampil_geotag", "activekebun", "jkoord"};
    private static final String TBL_SETTING_DB = "create table tbl_setting ("
            + "webip text, vd text, ikirim integer, dver integer, pz integer, pl integer, jmap integer, activeuser integer, "
            + "tfoto integer, taktif integer, tinstruksi integer, ttrack integer, tkml integer, tteman integer, "
            + "alarm integer, jam integer, menit integer, pesan text, int_inst text, int_outbound text, int_setting text, int_teman text, "
            + "int_gps text, int_internet text, int_track text, int_lateins text, tampil_kejadian integer, "
            + "tampil_patok integer, tampil_pekerjaan integer, tampil_aktifitas integer, tampil_jalan integer, "
            + "tampil_jembatan integer, tampil_tanaman integer, tampil_obyek integer, tampil_saluran integer, "
            + "tampil_geotag integer, activekebun integer, jkoord integer);";

    //26. tbl_tanaman
    private static final String TBL_TANAMAN = "tbl_tanaman";
    private static final String[] TBL_TANAMAN_COL = {"urut", "jenis", "lingkar", "tinggi", "jarak", "banyak", "kebun",
            "waktu", "userid", "afdeling", "blok", "ttanam", "tpanen", "apanen", "bpanen", "spanen", "ket", "cx",
            "cy", "kirim", "dari_server", "id_server", "asli"};
    private static final String TBL_TANAMAN_DB = "create table tbl_tanaman ("
            + "urut integer primary key autoincrement, jenis integer, lingkar text, tinggi text, jarak text, banyak text, "
            + "kebun integer, waktu text, userid integer, afdeling int, blok text, ttanam text, tpanen text, apanen text, "
            + "bpanen text, spanen text, ket text, cx text, cy text, kirim integer, dari_server integer, id_server integer, asli integer);";

    //27. tbl_track
    private static final String TBL_TRACK = "tbl_track";
    private static final String[] TBL_TRACK_COL = {"urut", "cx", "cy", "waktu", "userid", "kirim"};
    private static final String TBL_TRACK_DB = "create table tbl_track ("
            + "urut integer primary key autoincrement, cx text, cy text, waktu text, userid integer, kirim integer);";

    //28. tbl_user
    private static final String TBL_USER = "tbl_user";
    private static final String[] TBL_USER_COL = {"id", "idpeg", "nama", "devid", "nohp", "email",
            "idkebun", "kebunkml", "tingkat", "jabatan"};
    private static final String TBL_USER_DB = "create table tbl_user ("
            + "id integer, idpeg text, nama text, devid text, nohp text, email text, idkebun integer,"
            + "kebunkml text, tingkat integer, jabatan text);";

    //29. tbl_ref_jabatan;
    private static final String TBL_REF_JABATAN = "tbl_ref_jabatan";
    private static final String[] TBL_REF_JABATAN_COL = {"id", "nama"};
    private static final String TBL_REF_JABATAN_DB = "create table tbl_ref_jabatan (id integer, nama text);";

    //30. tbl_pekerja;
    private static final String TBL_PEKERJA = "tbl_pekerja";
    private static final String[] TBL_PEKERJA_COL = {"urut", "idkebun", "idafd", "idmandor", "idpeg", "nama", "jab"};
    private static final String TBL_PEKERJA_DB = "create table tbl_pekerja (urut integer, idkebun integer, idafd integer, "
        + "idmandor integer, idpeg text, nama text, jab integer);";

    //31. tbl_absensi
    private static final String TBL_ABSENSI = "tbl_absensi";
    private static final String[] TBL_ABSENSI_COL = {"idrow", "idpek", "idmandor", "tgl", "jamb", "jamp", "lokasi", "fotob", "fotop",
            "adab", "adap", "cxb", "cyb", "cxp", "cyp", "waktub", "waktup", "kirim"};
    private static final String TBL_ABSENSI_DB = "create table tbl_absensi (idrow integer, idpek text, idmandor integer, tgl text, "
        + "jamb text, jamp text, lokasi text, fotob text, fotop text, adab integer, adap integer, cxb text, cyb text, cxp text, "
        + "cyp text, waktub text, waktup text, kirim integer);";


    public MySQLLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(TBL_AKTIFITAS_DB);
        database.execSQL(TBL_IMAGE_DB);
        database.execSQL(TBL_JALAN_DB);
        database.execSQL(TBL_JEMBATAN_DB);
        database.execSQL(TBL_KEJADIAN_DB);
        database.execSQL(TBL_KML_DB);
        database.execSQL(TBL_OBYEK_DB);
        database.execSQL(TBL_PATOK_DB);
        database.execSQL(TBL_REF_AFDELING_DB);
        database.execSQL(TBL_REF_AKTIFITAS_DB);
        database.execSQL(TBL_REF_JALAN_DB);
        database.execSQL(TBL_REF_JALAN_KONDISI_DB);
        database.execSQL(TBL_REF_JEMBATAN_DB);
        database.execSQL(TBL_REF_JEMBATAN_KONDISI_DB);
        database.execSQL(TBL_REF_KEBUN_DB);
        database.execSQL(TBL_REF_KEG_DB);
        database.execSQL(TBL_REF_KEJADIAN_DB);
        database.execSQL(TBL_REF_KOMODITI_DB);
        database.execSQL(TBL_REF_OBYEK_DB);
        database.execSQL(TBL_REF_PATOK_DB);
        database.execSQL(TBL_REF_PEKERJAAN_DB);
        database.execSQL(TBL_REF_SALURAN_DB);
        database.execSQL(TBL_REF_SALURAN_KONDISI_DB);
        database.execSQL(TBL_REF_TANAMAN_DB);
        database.execSQL(TBL_SALURAN_DB);
        database.execSQL(TBL_SETTING_DB);
        database.execSQL(TBL_TANAMAN_DB);
        database.execSQL(TBL_TRACK_DB);
        database.execSQL(TBL_USER_DB);
        database.execSQL(TBL_REF_JABATAN_DB);
        database.execSQL(TBL_PEKERJA_DB);
        database.execSQL(TBL_ABSENSI_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TBL_AKTIFITAS);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_IMAGE);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_JALAN);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_JEMBATAN);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_KEJADIAN);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_KML);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_OBYEK);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_PATOK);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_REF_AFDELING);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_REF_AKTIFITAS);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_REF_JALAN);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_REF_JALAN_KONDISI);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_REF_JEMBATAN);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_REF_KEBUN);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_REF_KEG);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_REF_KEJADIAN);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_REF_KOMODITI);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_REF_OBYEK);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_REF_PATOK);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_REF_PEKERJAAN);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_REF_SALURAN);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_REF_SALURAN_KONDISI);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_REF_TANAMAN);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_SALURAN);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_SETTING);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_TANAMAN);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_TRACK);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_REF_JABATAN);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_PEKERJA);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_ABSENSI);
        onCreate(db);
    }

    //01. tbl_aktifitas - starts
    //public DB_Aktifitas (int urut, int kebun, int jenis, String elama, String ebiaya, String esdm, String ket,
    //String cx, String cy, String waktu, int userid, int kirim) {
    public Boolean DBAktifitas_Add
        (int kebun, int jenis, String elama, String ebiaya, String esdm, String ket,
         String cx, String cy, String waktu, int userid, int kirim, int dari_server, int id_server, int asli) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("kebun", String.valueOf(kebun));
        cv.put("jenis", String.valueOf(jenis));
        cv.put("elama", elama);
        cv.put("ebiaya", ebiaya);
        cv.put("esdm", esdm);
        cv.put("ket", ket);
        cv.put("cx", cx);
        cv.put("cy", cy);
        cv.put("waktu", waktu);
        cv.put("userid", String.valueOf(userid));
        cv.put("kirim", String.valueOf(kirim));
        cv.put("dari_server", String.valueOf(dari_server));
        cv.put("id_server", String.valueOf(id_server));
        cv.put("asli", String.valueOf(asli));
        db.insert(TBL_AKTIFITAS, null, cv);
        return true;
    }
    public List<DB_Aktifitas> DBAktifitas_CheckDariServer(int id_server) {
        List<DB_Aktifitas> aset = new LinkedList<DB_Aktifitas>();
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( id_server != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and id_server= ? "; } else { crit = "id_server= ? "; }
            mStringList.add(String.valueOf(id_server));
        }
        crit = crit + " and dari_server= ? ";
        mStringList.add("1");
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_AKTIFITAS, TBL_AKTIFITAS_COL, crit, critval, null, null, null);
        DB_Aktifitas anitem = null;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                anitem = new DB_Aktifitas();
                anitem.setUrut(Integer.parseInt(cursor.getString(0)));
                aset.add(anitem);
            } while (cursor.moveToNext());
        }
        return aset;
    }


    public List<DB_Aktifitas> DBAktifitas_Get(int urut, int jenis, int kirim, String waktu, int jwaktu) {
        List<DB_Aktifitas> aset = new LinkedList<DB_Aktifitas>();
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( urut != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and urut= ? "; } else { crit = "urut= ? "; }
            mStringList.add(String.valueOf(urut));
        }
        if( jenis != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and jenis= ? "; } else { crit = "jenis= ? "; }
            mStringList.add(String.valueOf(jenis));
        }
        if( kirim != 99 ) {
            if( !crit.equals("") ) { crit = crit + " and kirim= ? "; } else { crit = "kirim= ? "; }
            mStringList.add(String.valueOf(kirim));
        }
        if( !waktu.equals("") ) {
            String[] separated = waktu.split("-");
            String dummy = "";
            if( !crit.equals("") ) { crit = crit + " and waktu like ? "; } else { crit = "waktu like ? "; }
            if( jwaktu == 1 ) { dummy = waktu;}
            if( jwaktu == 2 ) { dummy = separated[0] + "-" + separated[1] + "-%"; }
            if( jwaktu == 3 ) { dummy = separated[0] + "-%"; }
            mStringList.add(dummy);
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }
        Log.e("DB AKT", "CRIT --> " + String.valueOf(crit));
        Log.e("DB AKT", "CRITVAL --> " + String.valueOf(critval));

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_AKTIFITAS, TBL_AKTIFITAS_COL, crit, critval, null, null, null);
        DB_Aktifitas anitem = null;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                anitem = new DB_Aktifitas();
                anitem.setUrut(Integer.parseInt(cursor.getString(0)));
                anitem.setKebun(Integer.parseInt(cursor.getString(1)));
                anitem.setJenis(Integer.parseInt(cursor.getString(2)));
                anitem.setElama(cursor.getString(3));
                anitem.setEBiaya(cursor.getString(4));
                anitem.setEsdm(cursor.getString(5));
                anitem.setKet(cursor.getString(6));
                anitem.setCX(cursor.getString(7));
                anitem.setCY(cursor.getString(8));
                anitem.setWaktu(cursor.getString(9));
                anitem.setUserID(Integer.parseInt(cursor.getString(10)));
                anitem.setKirim(Integer.parseInt(cursor.getString(11)));
                anitem.setDariServer(Integer.parseInt(cursor.getString(12)));
                anitem.setIDServer(Integer.parseInt(cursor.getString(13)));
                anitem.setAsli(Integer.parseInt(cursor.getString(14)));
                aset.add(anitem);
            } while (cursor.moveToNext());
        }
        return aset;
    }

    public Boolean DBAktifitas_Delete(int urut) {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( urut != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and urut= ? "; } else { crit = "urut= ? "; }
            mStringList.add(String.valueOf(urut));
        }

        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }
        db.delete(TBL_AKTIFITAS, crit, critval);
        return true;
    }

    public Boolean DBAktifitas_UpdateKirim(int urut) {

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( urut != 0 ) {
            crit = "urut = ? ";
            mStringList.add(String.valueOf(urut));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("kirim", "1");
        db.update(TBL_AKTIFITAS, cv, crit, critval);
        return true;
    }


    public Boolean DBAktifitas_Update
            (int urut, int kebun, int jenis, String elama, String ebiaya, String esdm, String ket, String cx, String cy,
             String waktu, int userid, int kirim, int dari_server, int id_server, int asli) {

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( urut != 0 ) {
            crit = "urut = ? ";
            mStringList.add(String.valueOf(urut));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("kebun", String.valueOf(kebun));
        cv.put("jenis", String.valueOf(jenis));
        cv.put("elama", elama);
        cv.put("ebiaya", ebiaya);
        cv.put("esdm", esdm);
        cv.put("ket", ket);
        cv.put("cx", cx);
        cv.put("cy", cy);
        cv.put("waktu", waktu);
        cv.put("userid", String.valueOf(userid));
        cv.put("kirim", String.valueOf(kirim));
        cv.put("dari_server", String.valueOf(dari_server));
        cv.put("id_server", String.valueOf(id_server));
        cv.put("asli", String.valueOf(asli));
        db.update(TBL_AKTIFITAS, cv, crit, critval);
        return true;
    }
    //01. tbl_aktifitas - ends

    //02. tbl_image - starts
    public Boolean DBImage_Add(
            int userid, String nfile, String cx, String cy, int kirim, String waktu, String ket,
            int tampil, int jenis, int dari_server, int id_server, int asli) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("userid", String.valueOf(userid));
        cv.put("nfile", nfile);
        cv.put("cx", cx);
        cv.put("cy", cy);
        cv.put("kirim", String.valueOf(kirim));
        cv.put("waktu", waktu);
        cv.put("ket", ket);
        cv.put("tampil", String.valueOf(tampil));
        cv.put("jenis", String.valueOf(jenis));
        cv.put("dari_server", String.valueOf(dari_server));
        cv.put("id_server", String.valueOf(id_server));
        cv.put("asli", String.valueOf(asli));
        db.insert(TBL_IMAGE, null, cv);
        return true;
    }

    public boolean DBImage_Del(int id, String nfile, int kirim, int jenis) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( id != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and id= ? "; } else { crit = "id= ? "; }
            mStringList.add(String.valueOf(id));
        }
        if( !nfile.equals("") ) {
            if( !crit.equals("") ) { crit = crit + " and nfile = ? "; } else { crit = "nfile = ? "; }
            mStringList.add(nfile);
        }
        if( kirim != 99 ) {
            if( !crit.equals("") ) { crit = crit + " and kirim= ? "; } else { crit = "kirim= ? "; }
            mStringList.add(String.valueOf(kirim));
        }
        if( jenis != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and jenis= ? "; } else { crit = "jenis= ? "; }
            mStringList.add(String.valueOf(jenis));
        }

        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }
        db.delete(TBL_IMAGE, crit, critval);
        return true;
    }

    public Boolean DBImage_UpdateKirim(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( id != 0 ) {
            crit = "id= ? ";
            mStringList.add(String.valueOf(id));
        }

        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }
        cv.put("kirim", "1");
        db.update(TBL_IMAGE, cv, crit, critval);
        return true;
    }

    public Boolean DBImage_Update(int id, int kirim, int tampil, int dari_server, int id_server) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( id != 0 ) {
            crit = "id= ? ";
            mStringList.add(String.valueOf(id));
        }

        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }
        cv.put("kirim", String.valueOf(kirim));
        cv.put("tampil", String.valueOf(tampil));
        cv.put("dari_server", String.valueOf(dari_server));
        cv.put("id_server", String.valueOf(id_server));
        db.update(TBL_IMAGE, cv, crit, critval);
        return true;
    }

    public List<DB_Image> DBImage_GetUpload(int id, int tampil, int jenis, int kirim, String waktu, int jwaktu) {
        List<DB_Image> aset = new LinkedList<DB_Image>();
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( id != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and id= ? "; } else { crit = "id= ? "; }
            mStringList.add(String.valueOf(id));
        }
        if( kirim != 99 ) {
            if( !crit.equals("") ) { crit = crit + " and kirim= ? "; } else { crit = "kirim= ? "; }
            mStringList.add(String.valueOf(kirim));
        }
        if( tampil != 99 ) {
            if( !crit.equals("") ) { crit = crit + " and tampil= ? "; } else { crit = "tampil= ? "; }
            mStringList.add(String.valueOf(tampil));
        }
        if( jenis != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and jenis= ? "; } else { crit = "jenis= ? "; }
            mStringList.add(String.valueOf(jenis));
        }
        if( !waktu.equals("") ) {
            String[] separated = waktu.split("-");
            String dummy = "";
            if( !crit.equals("") ) { crit = crit + " and waktu like ? "; } else { crit = "waktu like ? "; }
            if( jwaktu == 1 ) { dummy = waktu;}
            if( jwaktu == 2 ) { dummy = separated[0] + "-" + separated[1] + "-%"; }
            if( jwaktu == 3 ) { dummy = separated[0] + "-%"; }
            mStringList.add(dummy);
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_IMAGE, TBL_IMAGE_COL, crit, critval, null, null, null);
        DB_Image anitem = null;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                anitem = new DB_Image();
                anitem.setID(Integer.parseInt(cursor.getString(0)));
                anitem.setUserid(Integer.parseInt(cursor.getString(1)));
                anitem.setNfile(cursor.getString(2));
                anitem.setCx(cursor.getString(3));
                anitem.setCy(cursor.getString(4));
                anitem.setKirim(Integer.parseInt(cursor.getString(5)));
                anitem.setWaktu(cursor.getString(6));
                anitem.setKet(cursor.getString(7));
                anitem.setTampil(Integer.parseInt(cursor.getString(8)));
                anitem.setJenis(Integer.parseInt(cursor.getString(9)));
                anitem.setDariServer(Integer.parseInt(cursor.getString(10)));
                anitem.setIDServer(Integer.parseInt(cursor.getString(11)));
                anitem.setAsli(Integer.parseInt(cursor.getString(12)));
                aset.add(anitem);
            } while (cursor.moveToNext());
        }
        return aset;
    }

    public List<DB_Image> DBImage_Get(int id, int tampil, int jenis, int kirim) {
        List<DB_Image> aset = new LinkedList<DB_Image>();
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( id != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and id= ? "; } else { crit = "id= ? "; }
            mStringList.add(String.valueOf(id));
        }
        if( kirim != 99 ) {
            if( !crit.equals("") ) { crit = crit + " and kirim= ? "; } else { crit = "kirim= ? "; }
            mStringList.add(String.valueOf(kirim));
        }
        if( tampil != 99 ) {
            if( !crit.equals("") ) { crit = crit + " and tampil= ? "; } else { crit = "tampil= ? "; }
            mStringList.add(String.valueOf(tampil));
        }
        if( jenis != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and jenis= ? "; } else { crit = "jenis= ? "; }
            mStringList.add(String.valueOf(jenis));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_IMAGE, TBL_IMAGE_COL, crit, critval, null, null, null);
        DB_Image anitem = null;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                anitem = new DB_Image();
                anitem.setID(Integer.parseInt(cursor.getString(0)));
                anitem.setUserid(Integer.parseInt(cursor.getString(1)));
                anitem.setNfile(cursor.getString(2));
                anitem.setCx(cursor.getString(3));
                anitem.setCy(cursor.getString(4));
                anitem.setKirim(Integer.parseInt(cursor.getString(5)));
                anitem.setWaktu(cursor.getString(6));
                anitem.setKet(cursor.getString(7));
                anitem.setTampil(Integer.parseInt(cursor.getString(8)));
                anitem.setJenis(Integer.parseInt(cursor.getString(9)));
                anitem.setDariServer(Integer.parseInt(cursor.getString(10)));
                anitem.setIDServer(Integer.parseInt(cursor.getString(11)));
                anitem.setAsli(Integer.parseInt(cursor.getString(12)));
                aset.add(anitem);
            } while (cursor.moveToNext());
        }
        return aset;
    }
    //02. tbl_image - ends

    //03. tbl_jalan - starts
    public Boolean DBJalan_Add(
            int jenis, int kondisi, int kebun, String waktu, int userid, String ket, String cx, String cy,
            int kirim, String lebar, int dari_server, int id_server, int asli) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("jenis", String.valueOf(jenis));
        cv.put("kondisi", String.valueOf(kondisi));
        cv.put("kebun", String.valueOf(kebun));
        cv.put("waktu", waktu);
        cv.put("userid", String.valueOf(userid));
        cv.put("ket", ket);
        cv.put("cx", cx);
        cv.put("cy", cy);
        cv.put("kirim", String.valueOf(kirim));
        cv.put("lebar", lebar);
        cv.put("dari_server", String.valueOf(dari_server));
        cv.put("id_server", String.valueOf(id_server));
        cv.put("asli", String.valueOf(asli));
        db.insert(TBL_JALAN, null, cv);
        return true;
    }

    public List<DB_Jalan> DBJalan_CheckDariServer(int id_server) {
        List<DB_Jalan> aset = new LinkedList<DB_Jalan>();
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( id_server != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and id_server= ? "; } else { crit = "id_server= ? "; }
            mStringList.add(String.valueOf(id_server));
        }
        crit = crit + " and dari_server= ? ";
        mStringList.add("1");
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_JALAN, TBL_JALAN_COL, crit, critval, null, null, null);
        DB_Jalan anitem = null;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                anitem = new DB_Jalan();
                anitem.setUrut(Integer.parseInt(cursor.getString(0)));
                aset.add(anitem);
            } while (cursor.moveToNext());
        }
        return aset;
    }

    public Boolean DBJalan_Delete(int urut, int kirim, int userid) {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( urut != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and urut= ? "; } else { crit = "urut= ? "; }
            mStringList.add(String.valueOf(urut));
        }
        if( kirim != 99 ) {
            if( !crit.equals("") ) { crit = crit + " and kirim= ? "; } else { crit = "kirim= ? "; }
            mStringList.add(String.valueOf(kirim));
        }
        if( userid != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and userid= ? "; } else { crit = "userid= ? "; }
            mStringList.add(String.valueOf(urut));
        }

        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }
        db.delete(TBL_JALAN, crit, critval);
        return true;
    }

    public Boolean DBJalan_UpdateKirim(int urut) {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( urut != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and urut= ? "; } else { crit = "urut= ? "; }
            mStringList.add(String.valueOf(urut));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }
        ContentValues cv = new ContentValues();
        cv.put("kirim", "1");
        db.update(TBL_JALAN, cv, crit, critval);
        return true;
    }

    public Boolean DBJalan_Update(int urut, int jenis, int kondisi, int kebun, String waktu, int userid, String ket,
                                  String cx, String cy, int kirim, String lebar, int dari_server, int id_server) {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( urut != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and urut= ? "; } else { crit = "urut= ? "; }
            mStringList.add(String.valueOf(urut));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }
        ContentValues cv = new ContentValues();
        cv.put("jenis", String.valueOf(jenis));
        cv.put("kondisi", String.valueOf(kondisi));
        cv.put("kebun", String.valueOf(kebun));
        cv.put("waktu", waktu);
        cv.put("userid", String.valueOf(userid));
        cv.put("ket", ket);
        cv.put("cx", cx);
        cv.put("cy", cy);
        cv.put("kirim", String.valueOf(kirim));
        cv.put("lebar", lebar);
        cv.put("dari_server", String.valueOf(dari_server));
        cv.put("id_server", String.valueOf(id_server));
        db.update(TBL_JALAN, cv, crit, critval);
        return true;
    }

    public List<DB_Jalan> DBJalan_GetUpload(int urut, int kirim, String waktu, int jwaktu) {
        List<DB_Jalan> aset = new LinkedList<DB_Jalan>();
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( urut != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and urut=" + " ? "; } else { crit = "urut=" + " ? "; }
            mStringList.add(String.valueOf(urut));
        }
        if( kirim != 99 ) {
            if( !crit.equals("") ) { crit = crit + " and kirim=" + " ? "; } else { crit = "kirim=" + " ? "; }
            mStringList.add(String.valueOf(kirim));
        }
        if( !waktu.equals("") ) {
            String[] separated = waktu.split("-");
            String dummy = "";
            if( !crit.equals("") ) { crit = crit + " and waktu like ? "; } else { crit = "waktu like ? "; }
            if( jwaktu == 1 ) { dummy = waktu;}
            if( jwaktu == 2 ) { dummy = separated[0] + "-" + separated[1] + "-%"; }
            if( jwaktu == 3 ) { dummy = separated[0] + "-%"; }
            mStringList.add(dummy);
        }

        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_JALAN, TBL_JALAN_COL, crit, critval, null, null, null);
        DB_Jalan anitem = null;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                anitem = new DB_Jalan();
                anitem.setUrut(Integer.parseInt(cursor.getString(0)));
                anitem.setJenis(Integer.parseInt(cursor.getString(1)));
                anitem.setKondisi(Integer.parseInt(cursor.getString(2)));
                anitem.setKebun(Integer.parseInt(cursor.getString(3)));
                anitem.setWaktu(cursor.getString(4));
                anitem.setUserID(Integer.parseInt(cursor.getString(5)));
                anitem.setKet(cursor.getString(6));
                anitem.setCX(cursor.getString(7));
                anitem.setCY(cursor.getString(8));
                anitem.setKirim(Integer.parseInt(cursor.getString(9)));
                anitem.setLebar(cursor.getString(10));
                anitem.setDariServer(Integer.parseInt(cursor.getString(11)));
                anitem.setIDServer(Integer.parseInt(cursor.getString(12)));
                anitem.setAsli(Integer.parseInt(cursor.getString(13)));
                aset.add(anitem);
            } while (cursor.moveToNext());
        }
        return aset;
    }

    public List<DB_Jalan> DBJalan_Get(int urut, int kirim) {
        List<DB_Jalan> aset = new LinkedList<DB_Jalan>();
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( urut != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and urut=" + " ? "; } else { crit = "urut=" + " ? "; }
            mStringList.add(String.valueOf(urut));
        }
        if( kirim != 99 ) {
            if( !crit.equals("") ) { crit = crit + " and kirim=" + " ? "; } else { crit = "kirim=" + " ? "; }
            mStringList.add(String.valueOf(kirim));
        }

        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_JALAN, TBL_JALAN_COL, crit, critval, null, null, null);
        DB_Jalan anitem = null;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                anitem = new DB_Jalan();
                anitem.setUrut(Integer.parseInt(cursor.getString(0)));
                anitem.setJenis(Integer.parseInt(cursor.getString(1)));
                anitem.setKondisi(Integer.parseInt(cursor.getString(2)));
                anitem.setKebun(Integer.parseInt(cursor.getString(3)));
                anitem.setWaktu(cursor.getString(4));
                anitem.setUserID(Integer.parseInt(cursor.getString(5)));
                anitem.setKet(cursor.getString(6));
                anitem.setCX(cursor.getString(7));
                anitem.setCY(cursor.getString(8));
                anitem.setKirim(Integer.parseInt(cursor.getString(9)));
                anitem.setLebar(cursor.getString(10));
                anitem.setDariServer(Integer.parseInt(cursor.getString(11)));
                anitem.setIDServer(Integer.parseInt(cursor.getString(12)));
                anitem.setAsli(Integer.parseInt(cursor.getString(13)));
                aset.add(anitem);
            } while (cursor.moveToNext());
        }
        return aset;
    }
    //03. tbl_jalan - ends

    //04. tbl_jembatan - starts
    public Boolean DBJembatan_Add(
            int jenis, int kondisi, int kebun, String waktu, int userid, String ket, String cx, String cy,
            int kirim, String lebar, int dari_server, int id_server, int asli) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("jenis", String.valueOf(jenis));
        cv.put("kondisi", String.valueOf(kondisi));
        cv.put("kebun", String.valueOf(kebun));
        cv.put("waktu", waktu);
        cv.put("userid", String.valueOf(userid));
        cv.put("ket", ket);
        cv.put("cx", cx);
        cv.put("cy", cy);
        cv.put("kirim", String.valueOf(kirim));
        cv.put("lebar", lebar);
        cv.put("dari_server", String.valueOf(dari_server));
        cv.put("id_server", String.valueOf(id_server));
        cv.put("asli", String.valueOf(asli));
        db.insert(TBL_JEMBATAN, null, cv);
        return true;
    }
    public List<DB_Jembatan> DBJembatan_CheckDariServer(int id_server) {
        List<DB_Jembatan> aset = new LinkedList<DB_Jembatan>();
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( id_server != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and id_server= ? "; } else { crit = "id_server= ? "; }
            mStringList.add(String.valueOf(id_server));
        }
        crit = crit + " and dari_server= ? ";
        mStringList.add("1");
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_JEMBATAN, TBL_JEMBATAN_COL, crit, critval, null, null, null);
        DB_Jembatan anitem = null;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                anitem = new DB_Jembatan();
                anitem.setUrut(Integer.parseInt(cursor.getString(0)));
                aset.add(anitem);
            } while (cursor.moveToNext());
        }
        return aset;
    }

    public Boolean DBJembatan_Delete(int urut, int kirim, int userid) {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( urut != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and urut= ? "; } else { crit = "urut= ? "; }
            mStringList.add(String.valueOf(urut));
        }
        if( kirim != 99 ) {
            if( !crit.equals("") ) { crit = crit + " and kirim= ? "; } else { crit = "kirim= ? "; }
            mStringList.add(String.valueOf(kirim));
        }
        if( userid != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and userid= ? "; } else { crit = "userid= ? "; }
            mStringList.add(String.valueOf(urut));
        }

        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }
        db.delete(TBL_JEMBATAN, crit, critval);
        return true;
    }

    public Boolean DBJembatan_UpdateKirim(int urut) {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( urut != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and urut= ? "; } else { crit = "urut= ? "; }
            mStringList.add(String.valueOf(urut));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }
        ContentValues cv = new ContentValues();
        cv.put("kirim", "1");
        db.update(TBL_JEMBATAN, cv, crit, critval);
        return true;
    }

    public Boolean DBJembatan_Update(
            int urut, int jenis, int kondisi, int kebun, String waktu, int userid, String ket, String cx, String cy,
            int kirim, String lebar, int dari_server, int id_server) {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( urut != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and urut= ? "; } else { crit = "urut= ? "; }
            mStringList.add(String.valueOf(urut));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }
        ContentValues cv = new ContentValues();
        cv.put("jenis", String.valueOf(jenis));
        cv.put("kondisi", String.valueOf(kondisi));
        cv.put("kebun", String.valueOf(kebun));
        cv.put("waktu", waktu);
        cv.put("userid", String.valueOf(userid));
        cv.put("ket", ket);
        cv.put("cx", cx);
        cv.put("cy", cy);
        cv.put("kirim", String.valueOf(kirim));
        cv.put("lebar", lebar);
        cv.put("dari_server", String.valueOf(dari_server));
        cv.put("id_server", String.valueOf(id_server));
        db.update(TBL_JEMBATAN, cv, crit, critval);
        return true;
    }
    public List<DB_Jembatan> DBJembatan_GetUpload(int urut, int kirim, String waktu, int jwaktu) {
        List<DB_Jembatan> aset = new LinkedList<DB_Jembatan>();
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( urut != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and urut=" + " ? "; } else { crit = "urut=" + " ? "; }
            mStringList.add(String.valueOf(urut));
        }
        if( kirim != 99 ) {
            if( !crit.equals("") ) { crit = crit + " and kirim=" + " ? "; } else { crit = "kirim=" + " ? "; }
            mStringList.add(String.valueOf(kirim));
        }
        if( !waktu.equals("") ) {
            String[] separated = waktu.split("-");
            String dummy = "";
            if( !crit.equals("") ) { crit = crit + " and waktu like ? "; } else { crit = "waktu like ? "; }
            if( jwaktu == 1 ) { dummy = waktu;}
            if( jwaktu == 2 ) { dummy = separated[0] + "-" + separated[1] + "-%"; }
            if( jwaktu == 3 ) { dummy = separated[0] + "-%"; }
            mStringList.add(dummy);
        }

        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_JEMBATAN, TBL_JEMBATAN_COL, crit, critval, null, null, null);
        DB_Jembatan anitem = null;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                anitem = new DB_Jembatan();
                anitem.setUrut(Integer.parseInt(cursor.getString(0)));
                anitem.setJenis(Integer.parseInt(cursor.getString(1)));
                anitem.setKondisi(Integer.parseInt(cursor.getString(2)));
                anitem.setKebun(Integer.parseInt(cursor.getString(3)));
                anitem.setWaktu(cursor.getString(4));
                anitem.setUserID(Integer.parseInt(cursor.getString(5)));
                anitem.setKet(cursor.getString(6));
                anitem.setCX(cursor.getString(7));
                anitem.setCY(cursor.getString(8));
                anitem.setKirim(Integer.parseInt(cursor.getString(9)));
                anitem.setLebar(cursor.getString(10));
                anitem.setDariServer(Integer.parseInt(cursor.getString(11)));
                anitem.setIDServer(Integer.parseInt(cursor.getString(12)));
                anitem.setAsli(Integer.parseInt(cursor.getString(13)));
                aset.add(anitem);
            } while (cursor.moveToNext());
        }
        return aset;
    }

    public List<DB_Jembatan> DBJembatan_Get(int urut, int kirim) {
        List<DB_Jembatan> aset = new LinkedList<DB_Jembatan>();
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( urut != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and urut=" + " ? "; } else { crit = "urut=" + " ? "; }
            mStringList.add(String.valueOf(urut));
        }
        if( kirim != 99 ) {
            if( !crit.equals("") ) { crit = crit + " and kirim=" + " ? "; } else { crit = "kirim=" + " ? "; }
            mStringList.add(String.valueOf(kirim));
        }

        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_JEMBATAN, TBL_JEMBATAN_COL, crit, critval, null, null, null);
        DB_Jembatan anitem = null;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                anitem = new DB_Jembatan();
                anitem.setUrut(Integer.parseInt(cursor.getString(0)));
                anitem.setJenis(Integer.parseInt(cursor.getString(1)));
                anitem.setKondisi(Integer.parseInt(cursor.getString(2)));
                anitem.setKebun(Integer.parseInt(cursor.getString(3)));
                anitem.setWaktu(cursor.getString(4));
                anitem.setUserID(Integer.parseInt(cursor.getString(5)));
                anitem.setKet(cursor.getString(6));
                anitem.setCX(cursor.getString(7));
                anitem.setCY(cursor.getString(8));
                anitem.setKirim(Integer.parseInt(cursor.getString(9)));
                anitem.setLebar(cursor.getString(10));
                anitem.setDariServer(Integer.parseInt(cursor.getString(11)));
                anitem.setIDServer(Integer.parseInt(cursor.getString(12)));
                anitem.setAsli(Integer.parseInt(cursor.getString(13)));
                aset.add(anitem);
            } while (cursor.moveToNext());
        }
        return aset;
    }
    //04. tbl_jembatan - ends

    //05. tbl_kejadian - starts
    public Boolean DBKejadian_Add(
            int kebun, int jenis, String ket, String cx, String cy, String waktu, int userid,
            int kirim, int dari_server, int id_server, int asli) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("kebun", String.valueOf(kebun));
        cv.put("jenis", String.valueOf(jenis));
        cv.put("ket", ket);
        cv.put("cx", cx);
        cv.put("cy", cy);
        cv.put("waktu", waktu);
        cv.put("userid", String.valueOf(userid));
        cv.put("kirim", String.valueOf(kirim));
        cv.put("dari_server", String.valueOf(dari_server));
        cv.put("id_server", String.valueOf(id_server));
        cv.put("asli", String.valueOf(asli));
        db.insert(TBL_KEJADIAN, null, cv);
        return true;
    }

    public List<DB_Kejadian> DBKejadian_CheckDariServer(int id_server) {
        List<DB_Kejadian> aset = new LinkedList<DB_Kejadian>();
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( id_server != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and id_server= ? "; } else { crit = "id_server= ? "; }
            mStringList.add(String.valueOf(id_server));
        }
        crit = crit + " and dari_server= ? ";
        mStringList.add("1");
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_KEJADIAN, TBL_KEJADIAN_COL, crit, critval, null, null, null);
        DB_Kejadian anitem = null;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                anitem = new DB_Kejadian();
                anitem.setUrut(Integer.parseInt(cursor.getString(0)));
                aset.add(anitem);
            } while (cursor.moveToNext());
        }
        return aset;
    }

    public List<DB_Kejadian> DBKejadian_Get(int urut, int jenis, int kirim, String waktu, int jwaktu) {
        List<DB_Kejadian> aset = new LinkedList<DB_Kejadian>();
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( urut != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and urut= ? "; } else { crit = "urut= ? "; }
            mStringList.add(String.valueOf(urut));
        }
        if( jenis != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and jenis= ? "; } else { crit = "jenis= ? "; }
            mStringList.add(String.valueOf(jenis));
        }
        if( kirim != 99 ) {
            if( !crit.equals("") ) { crit = crit + " and kirim= ? "; } else { crit = "kirim= ? "; }
            mStringList.add(String.valueOf(kirim));
        }
        if( !waktu.equals("") ) {
            String[] separated = waktu.split("-");
            String dummy = "";
            if( !crit.equals("") ) { crit = crit + " and waktu like ? "; } else { crit = "waktu like ? "; }
            if( jwaktu == 1 ) { dummy = waktu;}
            if( jwaktu == 2 ) { dummy = separated[0] + "-" + separated[1] + "-%"; }
            if( jwaktu == 3 ) { dummy = separated[0] + "-%"; }
            mStringList.add(dummy);
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_KEJADIAN, TBL_KEJADIAN_COL, crit, critval, null, null, null);
        DB_Kejadian anitem = null;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                anitem = new DB_Kejadian();
                anitem.setUrut(Integer.parseInt(cursor.getString(0)));
                anitem.setKebun(Integer.parseInt(cursor.getString(1)));
                anitem.setJenis(Integer.parseInt(cursor.getString(2)));
                anitem.setKet(cursor.getString(3));
                anitem.setCX(cursor.getString(4));
                anitem.setCY(cursor.getString(5));
                anitem.setWaktu(cursor.getString(6));
                anitem.setUserID(Integer.parseInt(cursor.getString(7)));
                anitem.setKirim(Integer.parseInt(cursor.getString(8)));
                anitem.setDariServer(Integer.parseInt(cursor.getString(9)));
                anitem.setIDServer(Integer.parseInt(cursor.getString(10)));
                anitem.setAsli(Integer.parseInt(cursor.getString(11)));
                aset.add(anitem);
            } while (cursor.moveToNext());
        }
        return aset;
    }

    public Boolean DBKejadian_Delete(int urut) {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( urut != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and urut= ? "; } else { crit = "urut= ? "; }
            mStringList.add(String.valueOf(urut));
        }

        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }
        db.delete(TBL_KEJADIAN, crit, critval);
        return true;
    }

    public Boolean DBKejadian_UpdateKirim(int urut) {
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( urut != 0 ) {
            crit = "urut = ? ";
            mStringList.add(String.valueOf(urut));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("kirim", "1");
        db.update(TBL_KEJADIAN, cv, crit, critval);
        return true;
    }

    public Boolean DBKejadian_Update
            (int urut, int kebun, int jenis, String ket, String cx, String cy, String waktu, int userid,
             int kirim, int dari_server, int id_server) {

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( urut != 0 ) {
            crit = "urut = ? ";
            mStringList.add(String.valueOf(urut));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("kebun", String.valueOf(kebun));
        cv.put("jenis", String.valueOf(jenis));
        cv.put("ket", ket);
        cv.put("cx", cx);
        cv.put("cy", cy);
        cv.put("waktu", waktu);
        cv.put("userid", String.valueOf(userid));
        cv.put("kirim", String.valueOf(kirim));
        cv.put("dari_server", String.valueOf(dari_server));
        cv.put("id_server", String.valueOf(id_server));
        db.update(TBL_KEJADIAN, cv, crit, critval);
        return true;
    }
    //05. tbl_kejadian - ends

    //06. tbl_kml - starts
    public Boolean DBKML_Add(String nama, int idkebun, int idrow, int idtoc, int idpeta, String folder, int tampil, int sudah) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nama", nama);
        cv.put("idkebun", String.valueOf(idkebun));
        cv.put("idrow", String.valueOf(idrow));
        cv.put("idtoc", String.valueOf(idtoc));
        cv.put("idpeta", String.valueOf(idpeta));
        cv.put("folder", folder);
        cv.put("tampil", String.valueOf(tampil));
        cv.put("sudah", String.valueOf(sudah));
        db.insert(TBL_KML, null, cv);
        return true;
    }

    public Boolean DBKML_Update(
            int id, String nama, int idkebun, int idrow, int idtoc, int idpeta, String folder, int tampil, int sudah) {
        List<DB_KML> aset = new LinkedList<DB_KML>();
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( id != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and id = ? "; } else { crit = "id = ? "; }
            mStringList.add(String.valueOf(id));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("nama", nama);
        cv.put("idkebun", String.valueOf(idkebun));
        cv.put("idrow", String.valueOf(idrow));
        cv.put("idtoc", String.valueOf(idtoc));
        cv.put("idpeta", String.valueOf(idpeta));
        cv.put("folder", folder);
        cv.put("tampil", String.valueOf(tampil));
        cv.put("sudah", String.valueOf(sudah));
        db.update(TBL_KML, cv, crit, critval);
        return true;
    }

    public Boolean DBKML_UpdateTampil(int id, int tampil) {
        List<DB_KML> aset = new LinkedList<DB_KML>();
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( id != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and id = ? "; } else { crit = "id = ? "; }
            mStringList.add(String.valueOf(id));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("tampil", String.valueOf(tampil));
        db.update(TBL_KML, cv, crit, critval);
        return true;
    }

    public List<DB_KML> DBKML_Get(int id, String nama, int tampil) {
        List<DB_KML> aset = new LinkedList<DB_KML>();
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( id != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and id = ? "; } else { crit = "id = ? "; }
            mStringList.add(String.valueOf(id));
        }
        if( !nama.equals("") ) {
            if( !crit.equals("") ) { crit = crit + " and nama= ? "; } else { crit = "nama = ? "; }
            mStringList.add(nama);
        }
        if( tampil != 99 ) {
            if( !crit.equals("") ) { crit = crit + " and tampil = ? "; } else { crit = "tampil = ? "; }
            mStringList.add(String.valueOf(tampil));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_KML, TBL_KML_COL, crit, critval, null, null, null);
        DB_KML anitem = null;
        ////int id, String nama, int idkebun, int idrow, int idtoc, int idpeta, String folder
        if (cursor != null && cursor.moveToFirst()) {
            do {
                anitem = new DB_KML();
                anitem.setID(Integer.parseInt(cursor.getString(0)));
                anitem.setNama(cursor.getString(1));
                anitem.setIDKebun(Integer.parseInt(cursor.getString(2)));
                anitem.setIDRow(Integer.parseInt(cursor.getString(3)));
                anitem.setIDTOC(Integer.parseInt(cursor.getString(4)));
                anitem.setIDPeta(Integer.parseInt(cursor.getString(5)));
                anitem.setFolder(cursor.getString(6));
                anitem.setTampil(Integer.parseInt(cursor.getString(7)));
                anitem.setSudah(Integer.parseInt(cursor.getString(8)));
                aset.add(anitem);
            } while (cursor.moveToNext());
        }
        return aset;
    }

    public List<DB_KML> DBKML_GetFromDB(int idtoc, int idpeta) {
        List<DB_KML> aset = new LinkedList<DB_KML>();
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( idtoc != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and idtoc = ? "; } else { crit = "idtoc = ? "; }
            mStringList.add(String.valueOf(idtoc));
        }
        if( idpeta != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and idpeta= ? "; } else { crit = "idpeta = ? "; }
            mStringList.add(String.valueOf(idpeta));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_KML, TBL_KML_COL, crit, critval, null, null, null);
        DB_KML anitem = null;
        ////int id, String nama, int idkebun, int idrow, int idtoc, int idpeta, String folder
        if (cursor != null && cursor.moveToFirst()) {
            do {
                anitem = new DB_KML();
                anitem.setID(Integer.parseInt(cursor.getString(0)));
                anitem.setNama(cursor.getString(1));
                anitem.setIDKebun(Integer.parseInt(cursor.getString(2)));
                anitem.setIDRow(Integer.parseInt(cursor.getString(3)));
                anitem.setIDTOC(Integer.parseInt(cursor.getString(4)));
                anitem.setIDPeta(Integer.parseInt(cursor.getString(5)));
                anitem.setFolder(cursor.getString(6));
                anitem.setTampil(Integer.parseInt(cursor.getString(7)));
                anitem.setSudah(Integer.parseInt(cursor.getString(8)));
                aset.add(anitem);
            } while (cursor.moveToNext());
        }
        return aset;
    }

    public Boolean DBKML_Del(int id, String nama) {
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( id != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and id = ? "; } else { crit = "id = ? "; }
            mStringList.add(String.valueOf(id));
        }
        if( !nama.equals("") ) {
            crit = "nama = ? ";
            mStringList.add(nama);
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TBL_KML, crit, critval);
        return true;
    }
    //06. tbl_kml - ends

    //07. tbl_obyek - starts
    public Boolean DBObyek_Add(
            int jenis, String waktu, int userid, String ket, String cx, String cy,
            int kirim, int dari_server, int id_server, int asli) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("jenis", String.valueOf(jenis));
        cv.put("cx", cx);
        cv.put("cy", cy);
        cv.put("waktu", waktu);
        cv.put("userid", String.valueOf(userid));
        cv.put("ket", ket);
        cv.put("kirim", String.valueOf(kirim));
        cv.put("dari_server", String.valueOf(dari_server));
        cv.put("id_server", String.valueOf(id_server));
        cv.put("asli", String.valueOf(asli));
        db.insert(TBL_OBYEK, null, cv);
        return true;
    }

    public Boolean DBObyek_Delete(int urut, int kirim, int userid) {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( urut != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and urut= ? "; } else { crit = "urut= ? "; }
            mStringList.add(String.valueOf(urut));
        }
        if( kirim != 99 ) {
            if( !crit.equals("") ) { crit = crit + " and kirim= ? "; } else { crit = "kirim= ? "; }
            mStringList.add(String.valueOf(kirim));
        }
        if( userid != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and userid= ? "; } else { crit = "userid= ? "; }
            mStringList.add(String.valueOf(urut));
        }

        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }
        db.delete(TBL_OBYEK, crit, critval);
        return true;
    }

    public Boolean DBObyek_Update(
            int urut, int jenis, String waktu, int userid, String ket, String cx, String cy,
            int kirim, int dari_server, int id_server) {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( urut != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and urut= ? "; } else { crit = "urut= ? "; }
            mStringList.add(String.valueOf(urut));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }
        ContentValues cv = new ContentValues();
        cv.put("jenis", String.valueOf(jenis));
        cv.put("cx", cx);
        cv.put("cy", cy);
        cv.put("waktu", waktu);
        cv.put("userid", String.valueOf(userid));
        cv.put("ket", ket);
        cv.put("kirim", String.valueOf(kirim));
        cv.put("dari_server", String.valueOf(dari_server));
        cv.put("id_server", String.valueOf(id_server));
        db.update(TBL_OBYEK, cv, crit, critval);
        return true;
    }

    //{"urut", "jenis", "cx", "cy", "waktu", "userid", "ket", "kirim"};
    public List<DB_Obyek> DBObyek_Get(int urut, int kirim) {
        List<DB_Obyek> aset = new LinkedList<DB_Obyek>();
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( urut != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and urut=" + " ? "; } else { crit = "urut=" + " ? "; }
            mStringList.add(String.valueOf(urut));
        }
        if( kirim != 99 ) {
            if( !crit.equals("") ) { crit = crit + " and kirim=" + " ? "; } else { crit = "kirim=" + " ? "; }
            mStringList.add(String.valueOf(kirim));
        }

        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_OBYEK, TBL_OBYEK_COL, crit, critval, null, null, null);
        DB_Obyek anitem = null;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                anitem = new DB_Obyek();
                anitem.setUrut(Integer.parseInt(cursor.getString(0)));
                anitem.setJenis(Integer.parseInt(cursor.getString(1)));
                anitem.setCX(cursor.getString(2));
                anitem.setCY(cursor.getString(3));
                anitem.setWaktu(cursor.getString(4));
                anitem.setUserID(Integer.parseInt(cursor.getString(5)));
                anitem.setKet(cursor.getString(6));
                anitem.setKirim(Integer.parseInt(cursor.getString(7)));
                anitem.setDariServer(Integer.parseInt(cursor.getString(8)));
                anitem.setIDServer(Integer.parseInt(cursor.getString(9)));
                anitem.setAsli(Integer.parseInt(cursor.getString(10)));
                aset.add(anitem);
            } while (cursor.moveToNext());
        }
        return aset;
    }
    //07. tbl_obyek - ends

    //08. tbl_patok - starts
    public Boolean DBPatok_Add
    (String nama, int jenis, int kebun, String cx, String cy, String waktu, int userid, int kirim,
     String ket, int dari_server, int id_server, int asli) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nama", nama);
        cv.put("jenis", String.valueOf(jenis));
        cv.put("kebun", String.valueOf(kebun));
        cv.put("cx", cx);
        cv.put("cy", cy);
        cv.put("waktu", waktu);
        cv.put("userid", String.valueOf(userid));
        cv.put("ket", ket);
        cv.put("kirim", String.valueOf(kirim));
        cv.put("dari_server", String.valueOf(dari_server));
        cv.put("id_server", String.valueOf(id_server));
        cv.put("asli", String.valueOf(asli));
        db.insert(TBL_PATOK, null, cv);
        return true;
    }

    public List<DB_Patok> DBPatok_CheckDariServer(int id_server) {
        List<DB_Patok> aset = new LinkedList<DB_Patok>();
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( id_server != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and id_server= ? "; } else { crit = "id_server= ? "; }
            mStringList.add(String.valueOf(id_server));
        }
        crit = crit + " and dari_server= ? ";
        mStringList.add("1");
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_PATOK, TBL_PATOK_COL, crit, critval, null, null, null);
        DB_Patok anitem = null;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                anitem = new DB_Patok();
                anitem.setUrut(Integer.parseInt(cursor.getString(0)));
                aset.add(anitem);
            } while (cursor.moveToNext());
        }
        return aset;
    }

    public List<DB_Patok> DBPatok_GetUpload(int urut, int jenis, int kirim, String waktu, int jwaktu ) {
        List<DB_Patok> aset = new LinkedList<DB_Patok>();
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( urut != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and urut= ? "; } else { crit = "urut= ? "; }
            mStringList.add(String.valueOf(urut));
        }
        if( jenis != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and jenis= ? "; } else { crit = "jenis= ? "; }
            mStringList.add(String.valueOf(jenis));
        }
        if( kirim != 99 ) {
            if( !crit.equals("") ) { crit = crit + " and kirim= ? "; } else { crit = "kirim= ? "; }
            mStringList.add(String.valueOf(kirim));
        }
        if( !waktu.equals("") ) {
            String[] separated = waktu.split("-");
            String dummy = "";
            if( !crit.equals("") ) { crit = crit + " and waktu like ? "; } else { crit = "waktu like ? "; }
            if( jwaktu == 1 ) { dummy = waktu;}
            if( jwaktu == 2 ) { dummy = separated[0] + "-" + separated[1] + "-%"; }
            if( jwaktu == 3 ) { dummy = separated[0] + "-%"; }
            mStringList.add(dummy);
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_PATOK, TBL_PATOK_COL, crit, critval, null, null, null);
        DB_Patok anitem = null;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                anitem = new DB_Patok();
                anitem.setUrut(Integer.parseInt(cursor.getString(0)));
                anitem.setNama(cursor.getString(1));
                anitem.setJenis(Integer.parseInt(cursor.getString(2)));
                anitem.setKebun(Integer.parseInt(cursor.getString(3)));
                anitem.setCX(cursor.getString(4));
                anitem.setCY(cursor.getString(5));
                anitem.setWaktu(cursor.getString(6));
                anitem.setUserID(Integer.parseInt(cursor.getString(7)));
                anitem.setKet(cursor.getString(8));
                anitem.setKirim(Integer.parseInt(cursor.getString(9)));
                anitem.setDariServer(Integer.parseInt(cursor.getString(10)));
                anitem.setIDServer(Integer.parseInt(cursor.getString(11)));
                anitem.setAsli(Integer.parseInt(cursor.getString(12)));
                aset.add(anitem);
            } while (cursor.moveToNext());
        }
        return aset;
    }

    public List<DB_Patok> DBPatok_Get(int urut, int jenis, int kirim) {
        List<DB_Patok> aset = new LinkedList<DB_Patok>();
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( urut != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and urut= ? "; } else { crit = "urut= ? "; }
            mStringList.add(String.valueOf(urut));
        }
        if( jenis != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and jenis= ? "; } else { crit = "jenis= ? "; }
            mStringList.add(String.valueOf(jenis));
        }
        if( kirim != 99 ) {
            if( !crit.equals("") ) { crit = crit + " and kirim= ? "; } else { crit = "kirim= ? "; }
            mStringList.add(String.valueOf(kirim));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_PATOK, TBL_PATOK_COL, crit, critval, null, null, null);
        DB_Patok anitem = null;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                anitem = new DB_Patok();
                anitem.setUrut(Integer.parseInt(cursor.getString(0)));
                anitem.setNama(cursor.getString(1));
                anitem.setJenis(Integer.parseInt(cursor.getString(2)));
                anitem.setKebun(Integer.parseInt(cursor.getString(3)));
                anitem.setCX(cursor.getString(4));
                anitem.setCY(cursor.getString(5));
                anitem.setWaktu(cursor.getString(6));
                anitem.setUserID(Integer.parseInt(cursor.getString(7)));
                anitem.setKet(cursor.getString(8));
                anitem.setKirim(Integer.parseInt(cursor.getString(9)));
                anitem.setDariServer(Integer.parseInt(cursor.getString(10)));
                anitem.setIDServer(Integer.parseInt(cursor.getString(11)));
                anitem.setAsli(Integer.parseInt(cursor.getString(12)));
                aset.add(anitem);
            } while (cursor.moveToNext());
        }
        return aset;
    }

    public Boolean DBPatok_Delete(int urut) {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( urut != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and urut= ? "; } else { crit = "urut= ? "; }
            mStringList.add(String.valueOf(urut));
        }

        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }
        db.delete(TBL_PATOK, crit, critval);
        return true;
    }

    public Boolean DBPatok_UpdateKirim(int urut) {

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( urut != 0 ) {
            crit = "urut = ? ";
            mStringList.add(String.valueOf(urut));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("kirim", "1");
        db.update(TBL_PATOK, cv, crit, critval);
        return true;
    }

    public Boolean DBPatok_Update
            (int urut, String nama, int jenis, int kebun, String cx, String cy, String waktu, int userid,
             String ket, int kirim, int dari_server, int id_server) {

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( urut != 0 ) {
            crit = "urut = ? ";
            mStringList.add(String.valueOf(urut));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nama", nama);
        cv.put("jenis", String.valueOf(jenis));
        cv.put("kebun", String.valueOf(kebun));
        cv.put("cx", cx);
        cv.put("cy", cy);
        cv.put("waktu", waktu);
        cv.put("userid", String.valueOf(userid));
        cv.put("ket", ket);
        cv.put("kirim", String.valueOf(kirim));
        cv.put("dari_server", String.valueOf(dari_server));
        cv.put("id_server", String.valueOf(id_server));
        db.update(TBL_PATOK, cv, crit, critval);
        return true;
    }
    //08. tbl_patok - ends

    //09. tbl_ref_afdeling - starts
    public Boolean DBRefAfdeling_Add(int idkebun, int kode, String nama) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("idkebun", String.valueOf(idkebun));
        cv.put("kode", String.valueOf(kode));
        cv.put("nama", nama);
        db.insert(TBL_REF_AFDELING, null, cv);
        return true;
    }

    public List<DB_Ref_Afdeling> DBRefAfdeling_Get(int idkebun, int kode) {
        List<DB_Ref_Afdeling> aset = new LinkedList<DB_Ref_Afdeling>();
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( idkebun != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and idkebun= ? "; } else { crit = "idkebun= ? "; }
            mStringList.add(String.valueOf(idkebun));
        }
        if( kode != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and kode= ? "; } else { crit = "kode= ? "; }
            mStringList.add(String.valueOf(kode));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_REF_AFDELING, TBL_REF_AFDELING_COL, crit, critval, null, null, null);
        DB_Ref_Afdeling anitem = null;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                anitem = new DB_Ref_Afdeling();
                anitem.setIdkebun(Integer.parseInt(cursor.getString(0)));
                anitem.setKode(Integer.parseInt(cursor.getString(1)));
                anitem.setNama(cursor.getString(2));
                aset.add(anitem);
            } while (cursor.moveToNext());
        }
        return aset;
    }

    public Boolean DBRefAfdeling_Delete(int idkebun, int kode) {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( idkebun != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and idkebun= ? "; } else { crit = "idkebun= ? "; }
            mStringList.add(String.valueOf(idkebun));
        }
        if( kode != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and kode= ? "; } else { crit = "kode= ? "; }
            mStringList.add(String.valueOf(kode));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }
        db.delete(TBL_REF_AFDELING, crit, critval);
        return true;
    }

    public Boolean DBRefAfdeling_Update(int idkebun, int kode, String nama) {

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( idkebun != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and idkebun= ? "; } else { crit = "idkebun= ? "; }
            mStringList.add(String.valueOf(idkebun));
        }
        if( kode != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and kode= ? "; } else { crit = "kode= ? "; }
            mStringList.add(String.valueOf(kode));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nama", nama);
        db.update(TBL_REF_AFDELING, cv, crit, critval);
        return true;
    }
    //09. tbl_ref_afdeling - ends

    //10. tbl_ref_aktifitas - starts
    public Boolean DBRefAktifitas_Add(int kode, String nama) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("kode", String.valueOf(kode));
        cv.put("nama", nama);
        db.insert(TBL_REF_AKTIFITAS, null, cv);
        return true;
    }

    public List<DB_Ref_Aktifitas> DBRefAktifitas_Get(int kode) {
        List<DB_Ref_Aktifitas> aset = new LinkedList<DB_Ref_Aktifitas>();
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( kode != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and kode= ? "; } else { crit = "kode= ? "; }
            mStringList.add(String.valueOf(kode));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_REF_AKTIFITAS, TBL_REF_AKTIFITAS_COL, crit, critval, null, null, null);
        DB_Ref_Aktifitas anitem = null;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                anitem = new DB_Ref_Aktifitas();
                anitem.setKode(Integer.parseInt(cursor.getString(0)));
                anitem.setNama(cursor.getString(1));
                aset.add(anitem);
            } while (cursor.moveToNext());
        }
        return aset;
    }

    public Boolean DBRefAktifitas_Delete(int kode) {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( kode != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and kode= ? "; } else { crit = "kode= ? "; }
            mStringList.add(String.valueOf(kode));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }
        db.delete(TBL_REF_AKTIFITAS, crit, critval);
        return true;
    }

    public Boolean DBRefAktifitas_Update(int kode, String nama) {

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( kode != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and kode= ? "; } else { crit = "kode= ? "; }
            mStringList.add(String.valueOf(kode));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nama", nama);
        db.update(TBL_REF_AKTIFITAS, cv, crit, critval);
        return true;
    }
    //10. tbl_ref_aktifitas - ends

    //11. tbl_ref_jalan - starts
    public Boolean DBRefJalan_Add(int kode, String nama) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("kode", String.valueOf(kode));
        cv.put("nama", nama);
        db.insert(TBL_REF_JALAN, null, cv);
        return true;
    }

    public List<DB_Ref_Jalan> DBRefJalan_Get(int kode) {
        List<DB_Ref_Jalan> aset = new LinkedList<DB_Ref_Jalan>();
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( kode != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and kode= ? "; } else { crit = "kode= ? "; }
            mStringList.add(String.valueOf(kode));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_REF_JALAN, TBL_REF_JALAN_COL, crit, critval, null, null, null);
        DB_Ref_Jalan anitem = null;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                anitem = new DB_Ref_Jalan();
                anitem.setKode(Integer.parseInt(cursor.getString(0)));
                anitem.setNama(cursor.getString(1));
                aset.add(anitem);
            } while (cursor.moveToNext());
        }
        return aset;
    }

    public Boolean DBRefJalan_Delete(int kode) {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( kode != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and kode= ? "; } else { crit = "kode= ? "; }
            mStringList.add(String.valueOf(kode));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }
        db.delete(TBL_REF_JALAN, crit, critval);
        return true;
    }

    public Boolean DBRefJalan_Update(int kode, String nama) {

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( kode != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and kode= ? "; } else { crit = "kode= ? "; }
            mStringList.add(String.valueOf(kode));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nama", nama);
        db.update(TBL_REF_JALAN, cv, crit, critval);
        return true;
    }
    //11. tbl_ref_jalan - ends

    //12. tbl_ref_jalan_kondisi - starts
    public Boolean DBRefJalanKondisi_Add(int kode, String nama) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("kode", String.valueOf(kode));
        cv.put("nama", nama);
        db.insert(TBL_REF_JALAN_KONDISI, null, cv);
        return true;
    }

    public List<DB_Ref_Jalan_Kondisi> DBRefJalanKondisi_Get(int kode) {
        List<DB_Ref_Jalan_Kondisi> aset = new LinkedList<DB_Ref_Jalan_Kondisi>();
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( kode != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and kode= ? "; } else { crit = "kode= ? "; }
            mStringList.add(String.valueOf(kode));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_REF_JALAN_KONDISI, TBL_REF_JALAN_KONDISI_COL, crit, critval, null, null, null);
        DB_Ref_Jalan_Kondisi anitem = null;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                anitem = new DB_Ref_Jalan_Kondisi();
                anitem.setKode(Integer.parseInt(cursor.getString(0)));
                anitem.setNama(cursor.getString(1));
                aset.add(anitem);
            } while (cursor.moveToNext());
        }
        return aset;
    }

    public Boolean DBRefJalanKondisi_Delete(int kode) {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( kode != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and kode= ? "; } else { crit = "kode= ? "; }
            mStringList.add(String.valueOf(kode));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }
        db.delete(TBL_REF_JALAN_KONDISI, crit, critval);
        return true;
    }

    public Boolean DBRefJalanKondisi_Update(int kode, String nama) {

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( kode != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and kode= ? "; } else { crit = "kode= ? "; }
            mStringList.add(String.valueOf(kode));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nama", nama);
        db.update(TBL_REF_JALAN_KONDISI, cv, crit, critval);
        return true;
    }
    //12. tbl_ref_jalan_kondisi - ends

    //13. tbl_ref_jembatan - starts
    public Boolean DBRefJembatan_Add(int kode, String nama) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("kode", String.valueOf(kode));
        cv.put("nama", nama);
        db.insert(TBL_REF_JEMBATAN, null, cv);
        return true;
    }

    public List<DB_Ref_Jembatan> DBRefJembatan_Get(int kode) {
        List<DB_Ref_Jembatan> aset = new LinkedList<DB_Ref_Jembatan>();
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( kode != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and kode= ? "; } else { crit = "kode= ? "; }
            mStringList.add(String.valueOf(kode));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_REF_JEMBATAN, TBL_REF_JEMBATAN_COL, crit, critval, null, null, null);
        DB_Ref_Jembatan anitem = null;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                anitem = new DB_Ref_Jembatan();
                anitem.setKode(Integer.parseInt(cursor.getString(0)));
                anitem.setNama(cursor.getString(1));
                aset.add(anitem);
            } while (cursor.moveToNext());
        }
        return aset;
    }

    public Boolean DBRefJembatan_Delete(int kode) {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( kode != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and kode= ? "; } else { crit = "kode= ? "; }
            mStringList.add(String.valueOf(kode));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }
        db.delete(TBL_REF_JEMBATAN, crit, critval);
        return true;
    }

    public Boolean DBRefJembatan_Update(int kode, String nama) {

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( kode != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and kode= ? "; } else { crit = "kode= ? "; }
            mStringList.add(String.valueOf(kode));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nama", nama);
        db.update(TBL_REF_JEMBATAN, cv, crit, critval);
        return true;
    }
    //13. tbl_ref_jembatan - ends

    //14. tbl_kebun - starts
    public Boolean DBRefKebun_Add(int kode, String nama) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("kode", String.valueOf(kode));
        cv.put("nama", nama);
        db.insert(TBL_REF_KEBUN, null, cv);
        return true;
    }

    public List<DB_Ref_Kebun> DBRefKebun_Get(int kode) {
        List<DB_Ref_Kebun> aset = new LinkedList<DB_Ref_Kebun>();
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( kode != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and kode= ? "; } else { crit = "kode= ? "; }
            mStringList.add(String.valueOf(kode));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_REF_KEBUN, TBL_REF_KEBUN_COL, crit, critval, null, null, null);
        DB_Ref_Kebun anitem = null;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                anitem = new DB_Ref_Kebun();
                anitem.setKode(Integer.parseInt(cursor.getString(0)));
                anitem.setNama(cursor.getString(1));
                aset.add(anitem);
            } while (cursor.moveToNext());
        }
        return aset;
    }

    public Boolean DBRefKebun_Delete(int kode) {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( kode != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and kode= ? "; } else { crit = "kode= ? "; }
            mStringList.add(String.valueOf(kode));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }
        db.delete(TBL_REF_KEBUN, crit, critval);
        return true;
    }

    public Boolean DBRefKebun_Update(int kode, String nama) {

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( kode != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and kode= ? "; } else { crit = "kode= ? "; }
            mStringList.add(String.valueOf(kode));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nama", nama);
        db.update(TBL_REF_KEBUN, cv, crit, critval);
        return true;
    }
    //14. tbl_kebun - ends

    //15. tbl_keg - starts
    public Boolean DBRefKeg_Add(int kode, String nama) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("kode", String.valueOf(kode));
        cv.put("nama", nama);
        db.insert(TBL_REF_KEG, null, cv);
        return true;
    }

    public List<DB_Ref_Keg> DBRefKeg_Get(int idkebun, int kode) {
        List<DB_Ref_Keg> aset = new LinkedList<DB_Ref_Keg>();
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( kode != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and kode= ? "; } else { crit = "kode= ? "; }
            mStringList.add(String.valueOf(kode));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_REF_KEG, TBL_REF_KEG_COL, crit, critval, null, null, null);
        DB_Ref_Keg anitem = null;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                anitem = new DB_Ref_Keg();
                anitem.setKode(Integer.parseInt(cursor.getString(0)));
                anitem.setNama(cursor.getString(1));
                aset.add(anitem);
            } while (cursor.moveToNext());
        }
        return aset;
    }

    public Boolean DBRefKeg_Delete(int kode) {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( kode != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and kode= ? "; } else { crit = "kode= ? "; }
            mStringList.add(String.valueOf(kode));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }
        db.delete(TBL_REF_KEG, crit, critval);
        return true;
    }

    public Boolean DBRefKeg_Update(int kode, String nama) {

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( kode != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and kode= ? "; } else { crit = "kode= ? "; }
            mStringList.add(String.valueOf(kode));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nama", nama);
        db.update(TBL_REF_KEG, cv, crit, critval);
        return true;
    }
    //15. tbl_ref_keg - ends

    //16. tbl_kejadian - starts
    public Boolean DBRefKejadian_Add(int kode, String nama) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("kode", String.valueOf(kode));
        cv.put("nama", nama);
        db.insert(TBL_REF_KEJADIAN, null, cv);
        return true;
    }

    public List<DB_Ref_Kejadian> DBRefKejadian_Get(int kode) {
        List<DB_Ref_Kejadian> aset = new LinkedList<DB_Ref_Kejadian>();
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( kode != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and kode= ? "; } else { crit = "kode= ? "; }
            mStringList.add(String.valueOf(kode));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_REF_KEJADIAN, TBL_REF_KEJADIAN_COL, crit, critval, null, null, null);
        DB_Ref_Kejadian anitem = null;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                anitem = new DB_Ref_Kejadian();
                anitem.setKode(Integer.parseInt(cursor.getString(0)));
                anitem.setNama(cursor.getString(1));
                aset.add(anitem);
            } while (cursor.moveToNext());
        }
        return aset;
    }

    public Boolean DBRefKejadian_Delete(int kode) {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( kode != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and kode= ? "; } else { crit = "kode= ? "; }
            mStringList.add(String.valueOf(kode));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }
        db.delete(TBL_REF_KEJADIAN, crit, critval);
        return true;
    }

    public Boolean DBRefKejadian_Update(int kode, String nama) {

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( kode != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and kode= ? "; } else { crit = "kode= ? "; }
            mStringList.add(String.valueOf(kode));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nama", nama);
        db.update(TBL_REF_KEJADIAN, cv, crit, critval);
        return true;
    }
    //16. tbl_ref_kejadian - ends

    //17. tbl_komoditi - starts
    public Boolean DBRefKomoditi_Add(int kode, String nama) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("kode", String.valueOf(kode));
        cv.put("nama", nama);
        db.insert(TBL_REF_KOMODITI, null, cv);
        return true;
    }

    public List<DB_Ref_Komoditi> DBRefKomoditi_Get(int kode) {
        List<DB_Ref_Komoditi> aset = new LinkedList<DB_Ref_Komoditi>();
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( kode != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and kode= ? "; } else { crit = "kode= ? "; }
            mStringList.add(String.valueOf(kode));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_REF_KOMODITI, TBL_REF_KOMODITI_COL, crit, critval, null, null, null);
        DB_Ref_Komoditi anitem = null;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                anitem = new DB_Ref_Komoditi();
                anitem.setKode(Integer.parseInt(cursor.getString(0)));
                anitem.setNama(cursor.getString(1));
                aset.add(anitem);
            } while (cursor.moveToNext());
        }
        return aset;
    }

    public Boolean DBRefKomoditi_Delete(int kode) {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( kode != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and kode= ? "; } else { crit = "kode= ? "; }
            mStringList.add(String.valueOf(kode));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }
        db.delete(TBL_REF_KOMODITI, crit, critval);
        return true;
    }

    public Boolean DBRefKomoditi_Update(int kode, String nama) {

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( kode != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and kode= ? "; } else { crit = "kode= ? "; }
            mStringList.add(String.valueOf(kode));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nama", nama);
        db.update(TBL_REF_KOMODITI, cv, crit, critval);
        return true;
    }
    //17. tbl_ref_komoditi - ends

    //18. tbl_obyek - starts
    public Boolean DBRefObyek_Add(int kode, String nama) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("kode", String.valueOf(kode));
        cv.put("nama", nama);
        db.insert(TBL_REF_OBYEK, null, cv);
        return true;
    }

    public List<DB_Ref_Obyek> DBRefObyek_Get(int kode) {
        List<DB_Ref_Obyek> aset = new LinkedList<DB_Ref_Obyek>();
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( kode != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and kode= ? "; } else { crit = "kode= ? "; }
            mStringList.add(String.valueOf(kode));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_REF_OBYEK, TBL_REF_OBYEK_COL, crit, critval, null, null, null);
        DB_Ref_Obyek anitem = null;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                anitem = new DB_Ref_Obyek();
                anitem.setKode(Integer.parseInt(cursor.getString(0)));
                anitem.setNama(cursor.getString(1));
                aset.add(anitem);
            } while (cursor.moveToNext());
        }
        return aset;
    }

    public Boolean DBRefObyek_Delete(int kode) {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( kode != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and kode= ? "; } else { crit = "kode= ? "; }
            mStringList.add(String.valueOf(kode));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }
        db.delete(TBL_REF_OBYEK, crit, critval);
        return true;
    }

    public Boolean DBRefObyek_Update(int kode, String nama) {

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( kode != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and kode= ? "; } else { crit = "kode= ? "; }
            mStringList.add(String.valueOf(kode));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nama", nama);
        db.update(TBL_REF_OBYEK, cv, crit, critval);
        return true;
    }
    //18. tbl_ref_obyek - ends

    //19. tbl_ref_patok - starts
    public Boolean DBRefPatok_Add(int kode, String nama) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("kode", String.valueOf(kode));
        cv.put("nama", nama);
        db.insert(TBL_REF_PATOK, null, cv);
        return true;
    }

    public List<DB_Ref_Patok> DBRefPatok_Get(int kode) {
        List<DB_Ref_Patok> aset = new LinkedList<DB_Ref_Patok>();
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( kode != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and kode= ? "; } else { crit = "kode= ? "; }
            mStringList.add(String.valueOf(kode));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_REF_PATOK, TBL_REF_PATOK_COL, crit, critval, null, null, null);
        DB_Ref_Patok anitem = null;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                anitem = new DB_Ref_Patok();
                anitem.setKode(Integer.parseInt(cursor.getString(0)));
                anitem.setNama(cursor.getString(1));
                aset.add(anitem);
            } while (cursor.moveToNext());
        }
        return aset;
    }

    public Boolean DBRefPatok_Delete(int kode) {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( kode != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and kode= ? "; } else { crit = "kode= ? "; }
            mStringList.add(String.valueOf(kode));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }
        db.delete(TBL_REF_PATOK, crit, critval);
        return true;
    }

    public Boolean DBRefPatok_Update(int kode, String nama) {

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( kode != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and kode= ? "; } else { crit = "kode= ? "; }
            mStringList.add(String.valueOf(kode));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nama", nama);
        db.update(TBL_REF_PATOK, cv, crit, critval);
        return true;
    }
    //19. tbl_ref_patok - ends

    //20. tbl_ref_pekerjaan - starts
    public Boolean DBRefPekerjaan_Add(int kode, String nama) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("kode", String.valueOf(kode));
        cv.put("nama", nama);
        db.insert(TBL_REF_PEKERJAAN, null, cv);
        return true;
    }

    public List<DB_Ref_Pekerjaan> DBRefPekerjaan_Get(int kode) {
        List<DB_Ref_Pekerjaan> aset = new LinkedList<DB_Ref_Pekerjaan>();
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( kode != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and kode= ? "; } else { crit = "kode= ? "; }
            mStringList.add(String.valueOf(kode));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_REF_PEKERJAAN, TBL_REF_PEKERJAAN_COL, crit, critval, null, null, null);
        DB_Ref_Pekerjaan anitem = null;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                anitem = new DB_Ref_Pekerjaan();
                anitem.setKode(Integer.parseInt(cursor.getString(0)));
                anitem.setNama(cursor.getString(1));
                aset.add(anitem);
            } while (cursor.moveToNext());
        }
        return aset;
    }

    public Boolean DBRefPekerjaan_Delete(int kode) {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( kode != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and kode= ? "; } else { crit = "kode= ? "; }
            mStringList.add(String.valueOf(kode));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }
        db.delete(TBL_REF_PEKERJAAN, crit, critval);
        return true;
    }

    public Boolean DBRefPekerjaan_Update(int kode, String nama) {

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( kode != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and kode= ? "; } else { crit = "kode= ? "; }
            mStringList.add(String.valueOf(kode));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nama", nama);
        db.update(TBL_REF_PEKERJAAN, cv, crit, critval);
        return true;
    }
    //20. tbl_ref_pekerjaan - ends

    //21. tbl_ref_saluran - starts
    public Boolean DBRefSaluran_Add(int kode, String nama) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("kode", String.valueOf(kode));
        cv.put("nama", nama);
        db.insert(TBL_REF_SALURAN, null, cv);
        return true;
    }

    public List<DB_Ref_Saluran> DBRefSaluran_Get(int kode) {
        List<DB_Ref_Saluran> aset = new LinkedList<DB_Ref_Saluran>();
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( kode != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and kode= ? "; } else { crit = "kode= ? "; }
            mStringList.add(String.valueOf(kode));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_REF_SALURAN, TBL_REF_SALURAN_COL, crit, critval, null, null, null);
        DB_Ref_Saluran anitem = null;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                anitem = new DB_Ref_Saluran();
                anitem.setKode(Integer.parseInt(cursor.getString(0)));
                anitem.setNama(cursor.getString(1));
                aset.add(anitem);
            } while (cursor.moveToNext());
        }
        return aset;
    }

    public Boolean DBRefSaluran_Delete(int kode) {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( kode != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and kode= ? "; } else { crit = "kode= ? "; }
            mStringList.add(String.valueOf(kode));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }
        db.delete(TBL_REF_SALURAN, crit, critval);
        return true;
    }

    public Boolean DBRefSaluran_Update(int kode, String nama) {

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( kode != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and kode= ? "; } else { crit = "kode= ? "; }
            mStringList.add(String.valueOf(kode));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nama", nama);
        db.update(TBL_REF_SALURAN, cv, crit, critval);
        return true;
    }
    //21. tbl_ref_saluran - ends

    //22. tbl_ref_saluran_kondisi - starts
    public Boolean DBRefSaluranKondisi_Add(int kode, String nama) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("kode", String.valueOf(kode));
        cv.put("nama", nama);
        db.insert(TBL_REF_SALURAN_KONDISI, null, cv);
        return true;
    }

    public List<DB_Ref_Saluran_Kondisi> DBRefSaluranKondisi_Get(int kode) {
        List<DB_Ref_Saluran_Kondisi> aset = new LinkedList<DB_Ref_Saluran_Kondisi>();
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( kode != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and kode= ? "; } else { crit = "kode= ? "; }
            mStringList.add(String.valueOf(kode));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_REF_SALURAN_KONDISI, TBL_REF_SALURAN_KONDISI_COL, crit, critval, null, null, null);
        DB_Ref_Saluran_Kondisi anitem = null;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                anitem = new DB_Ref_Saluran_Kondisi();
                anitem.setKode(Integer.parseInt(cursor.getString(0)));
                anitem.setNama(cursor.getString(1));
                aset.add(anitem);
            } while (cursor.moveToNext());
        }
        return aset;
    }

    public Boolean DBRefSaluranKondisi_Delete(int kode) {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( kode != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and kode= ? "; } else { crit = "kode= ? "; }
            mStringList.add(String.valueOf(kode));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }
        db.delete(TBL_REF_SALURAN_KONDISI, crit, critval);
        return true;
    }

    public Boolean DBRefSaluranKondisi_Update(int kode, String nama) {

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( kode != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and kode= ? "; } else { crit = "kode= ? "; }
            mStringList.add(String.valueOf(kode));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nama", nama);
        db.update(TBL_REF_SALURAN_KONDISI, cv, crit, critval);
        return true;
    }
    //22. tbl_ref_saluran_kondisi - ends

    //23. tbl_ref_tanaman - starts
    public Boolean DBRefTanaman_Add(int kode, String nama) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("kode", String.valueOf(kode));
        cv.put("nama", nama);
        db.insert(TBL_REF_TANAMAN, null, cv);
        return true;
    }

    public List<DB_Ref_Tanaman> DBRefTanaman_Get(int idkebun, int kode) {
        List<DB_Ref_Tanaman> aset = new LinkedList<DB_Ref_Tanaman>();
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( kode != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and kode= ? "; } else { crit = "kode= ? "; }
            mStringList.add(String.valueOf(kode));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_REF_TANAMAN, TBL_REF_TANAMAN_COL, crit, critval, null, null, null);
        DB_Ref_Tanaman anitem = null;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                anitem = new DB_Ref_Tanaman();
                anitem.setKode(Integer.parseInt(cursor.getString(0)));
                anitem.setNama(cursor.getString(1));
                aset.add(anitem);
            } while (cursor.moveToNext());
        }
        return aset;
    }

    public Boolean DBRefTanaman_Delete(int kode) {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( kode != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and kode= ? "; } else { crit = "kode= ? "; }
            mStringList.add(String.valueOf(kode));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }
        db.delete(TBL_REF_TANAMAN, crit, critval);
        return true;
    }

    public Boolean DBRefTanaman_Update(int kode, String nama) {

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( kode != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and kode= ? "; } else { crit = "kode= ? "; }
            mStringList.add(String.valueOf(kode));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nama", nama);
        db.update(TBL_REF_TANAMAN, cv, crit, critval);
        return true;
    }
    //23. tbl_ref_tanaman - ends

    //24. tbl_saluran - starts
    public Boolean DBSaluran_Add(
            int jenis, int kondisi, int kebun, String waktu, int userid, String ket, String cx, String cy,
            int kirim, String lebar, int dari_server, int id_server, int asli) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("jenis", String.valueOf(jenis));
        cv.put("kondisi", String.valueOf(kondisi));
        cv.put("kebun", String.valueOf(kebun));
        cv.put("waktu", waktu);
        cv.put("userid", String.valueOf(userid));
        cv.put("ket", ket);
        cv.put("cx", cx);
        cv.put("cy", cy);
        cv.put("kirim", String.valueOf(kirim));
        cv.put("lebar", lebar);
        cv.put("dari_server", String.valueOf(dari_server));
        cv.put("id_server", String.valueOf(id_server));
        cv.put("asli", String.valueOf(asli));
        db.insert(TBL_SALURAN, null, cv);
        return true;
    }
    public List<DB_Saluran> DBSaluran_CheckDariServer(int id_server) {
        List<DB_Saluran> aset = new LinkedList<DB_Saluran>();
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( id_server != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and id_server= ? "; } else { crit = "id_server= ? "; }
            mStringList.add(String.valueOf(id_server));
        }
        crit = crit + " and dari_server= ? ";
        mStringList.add("1");
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_SALURAN, TBL_SALURAN_COL, crit, critval, null, null, null);
        DB_Saluran anitem = null;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                anitem = new DB_Saluran();
                anitem.setUrut(Integer.parseInt(cursor.getString(0)));
                aset.add(anitem);
            } while (cursor.moveToNext());
        }
        return aset;
    }

    public Boolean DBSaluran_Delete(int urut, int kirim, int userid) {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( urut != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and urut= ? "; } else { crit = "urut= ? "; }
            mStringList.add(String.valueOf(urut));
        }
        if( kirim != 99 ) {
            if( !crit.equals("") ) { crit = crit + " and kirim= ? "; } else { crit = "kirim= ? "; }
            mStringList.add(String.valueOf(kirim));
        }
        if( userid != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and userid= ? "; } else { crit = "userid= ? "; }
            mStringList.add(String.valueOf(urut));
        }

        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }
        db.delete(TBL_SALURAN, crit, critval);
        return true;
    }

    public Boolean DBSaluran_UpdateKirim(int urut) {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( urut != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and urut= ? "; } else { crit = "urut= ? "; }
            mStringList.add(String.valueOf(urut));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }
        ContentValues cv = new ContentValues();
        cv.put("kirim", "1");
        db.update(TBL_SALURAN, cv, crit, critval);
        return true;
    }

    public Boolean DBSaluran_Update(
            int urut, int jenis, int kondisi, int kebun, String waktu, int userid, String ket,
            String cx, String cy, int kirim, String lebar, int dari_server, int id_server) {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( urut != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and urut= ? "; } else { crit = "urut= ? "; }
            mStringList.add(String.valueOf(urut));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }
        ContentValues cv = new ContentValues();
        cv.put("jenis", String.valueOf(jenis));
        cv.put("kondisi", String.valueOf(kondisi));
        cv.put("kebun", String.valueOf(kebun));
        cv.put("waktu", waktu);
        cv.put("userid", String.valueOf(userid));
        cv.put("ket", ket);
        cv.put("cx", cx);
        cv.put("cy", cy);
        cv.put("kirim", String.valueOf(kirim));
        cv.put("lebar", lebar);
        cv.put("dari_server", String.valueOf(dari_server));
        cv.put("id_server", String.valueOf(id_server));
        db.update(TBL_SALURAN, cv, crit, critval);
        return true;
    }

    public List<DB_Saluran> DBSaluran_GetUpload(int urut, int kirim, String waktu, int jwaktu) {
        List<DB_Saluran> aset = new LinkedList<DB_Saluran>();
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( urut != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and urut=" + " ? "; } else { crit = "urut=" + " ? "; }
            mStringList.add(String.valueOf(urut));
        }
        if( kirim != 99 ) {
            if( !crit.equals("") ) { crit = crit + " and kirim=" + " ? "; } else { crit = "kirim=" + " ? "; }
            mStringList.add(String.valueOf(kirim));
        }
        if( !waktu.equals("") ) {
            String[] separated = waktu.split("-");
            String dummy = "";
            if( !crit.equals("") ) { crit = crit + " and waktu like ? "; } else { crit = "waktu like ? "; }
            if( jwaktu == 1 ) { dummy = waktu;}
            if( jwaktu == 2 ) { dummy = separated[0] + "-" + separated[1] + "-%"; }
            if( jwaktu == 3 ) { dummy = separated[0] + "-%"; }
            mStringList.add(dummy);
        }

        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_SALURAN, TBL_SALURAN_COL, crit, critval, null, null, null);
        DB_Saluran anitem = null;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                anitem = new DB_Saluran();
                anitem.setUrut(Integer.parseInt(cursor.getString(0)));
                anitem.setJenis(Integer.parseInt(cursor.getString(1)));
                anitem.setKondisi(Integer.parseInt(cursor.getString(2)));
                anitem.setKebun(Integer.parseInt(cursor.getString(3)));
                anitem.setWaktu(cursor.getString(4));
                anitem.setUserID(Integer.parseInt(cursor.getString(5)));
                anitem.setKet(cursor.getString(6));
                anitem.setCX(cursor.getString(7));
                anitem.setCY(cursor.getString(8));
                anitem.setKirim(Integer.parseInt(cursor.getString(9)));
                anitem.setLebar(cursor.getString(10));
                anitem.setDariServer(Integer.parseInt(cursor.getString(11)));
                anitem.setIDServer(Integer.parseInt(cursor.getString(12)));
                anitem.setAsli(Integer.parseInt(cursor.getString(13)));
                aset.add(anitem);
            } while (cursor.moveToNext());
        }
        return aset;
    }

    public List<DB_Saluran> DBSaluran_Get(int urut, int kirim) {
        List<DB_Saluran> aset = new LinkedList<DB_Saluran>();
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( urut != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and urut=" + " ? "; } else { crit = "urut=" + " ? "; }
            mStringList.add(String.valueOf(urut));
        }
        if( kirim != 99 ) {
            if( !crit.equals("") ) { crit = crit + " and kirim=" + " ? "; } else { crit = "kirim=" + " ? "; }
            mStringList.add(String.valueOf(kirim));
        }

        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_SALURAN, TBL_SALURAN_COL, crit, critval, null, null, null);
        DB_Saluran anitem = null;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                anitem = new DB_Saluran();
                anitem.setUrut(Integer.parseInt(cursor.getString(0)));
                anitem.setJenis(Integer.parseInt(cursor.getString(1)));
                anitem.setKondisi(Integer.parseInt(cursor.getString(2)));
                anitem.setKebun(Integer.parseInt(cursor.getString(3)));
                anitem.setWaktu(cursor.getString(4));
                anitem.setUserID(Integer.parseInt(cursor.getString(5)));
                anitem.setKet(cursor.getString(6));
                anitem.setCX(cursor.getString(7));
                anitem.setCY(cursor.getString(8));
                anitem.setKirim(Integer.parseInt(cursor.getString(9)));
                anitem.setLebar(cursor.getString(10));
                anitem.setDariServer(Integer.parseInt(cursor.getString(11)));
                anitem.setIDServer(Integer.parseInt(cursor.getString(12)));
                anitem.setAsli(Integer.parseInt(cursor.getString(13)));
                aset.add(anitem);
            } while (cursor.moveToNext());
        }
        return aset;
    }
    //24. tbl_saluran - ends

    //25. tbl_setting - starts
    //{"webip", "vd", "ikirim", "dver", "pz", "pl", "jmap", "activeuser", "tfoto", "taktif", "tinstruksi", "ttrack", "tkml"};
    public Boolean DBSetting_Add
        (String webip, String vd, int ikirim, int dver, int pz, int pl, int jmap, int activeuser, int tfoto,
         int taktif, int tinstruksi, int ttrack, int tkml, int tteman, int alarm, int jam, int menit, String pesan, String int_inst,
         String int_outbound, String int_setting, String int_teman, String int_GPS, String int_internet,
         String int_track, String int_lateins, int tampil_kejadian, int tampil_patok, int tampil_pekerjaan,
         int tampil_aktifitas, int tampil_jalan, int tampil_jembatan, int tampil_tanaman, int tampil_obyek,
         int tampil_saluran, int tampil_geotag, int activekebun, int jkoord) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("webip", webip);
        cv.put("vd", vd);
        cv.put("ikirim", String.valueOf(ikirim));
        cv.put("dver", String.valueOf(dver));
        cv.put("pz", String.valueOf(pz));
        cv.put("pl", String.valueOf(pl));
        cv.put("jmap", String.valueOf(jmap));
        cv.put("activeuser", String.valueOf(activeuser));
        cv.put("tfoto", String.valueOf(tfoto));
        cv.put("taktif", String.valueOf(taktif));
        cv.put("tinstruksi", String.valueOf(tinstruksi));
        cv.put("ttrack", String.valueOf(ttrack));
        cv.put("tkml", String.valueOf(tkml));
        cv.put("tteman", String.valueOf(tteman));
        cv.put("alarm", String.valueOf(alarm));
        cv.put("jam", String.valueOf(jam));
        cv.put("menit", String.valueOf(menit));
        cv.put("pesan", pesan);
        cv.put("int_inst", int_inst);
        cv.put("int_outbound", int_outbound);
        cv.put("int_setting", int_setting);
        cv.put("int_teman", int_teman);
        cv.put("int_gps", int_GPS);
        cv.put("int_internet", int_internet);
        cv.put("int_track", int_track);
        cv.put("int_lateins", int_lateins);
        cv.put("tampil_kejadian",String.valueOf(tampil_kejadian));
        cv.put("tampil_patok",String.valueOf(tampil_patok));
        cv.put("tampil_pekerjaan",String.valueOf(tampil_pekerjaan));
        cv.put("tampil_aktifitas",String.valueOf(tampil_aktifitas));
        cv.put("tampil_jalan",String.valueOf(tampil_jalan));
        cv.put("tampil_jembatan",String.valueOf(tampil_jembatan));
        cv.put("tampil_tanaman",String.valueOf(tampil_tanaman));
        cv.put("tampil_obyek",String.valueOf(tampil_obyek));
        cv.put("tampil_saluran",String.valueOf(tampil_saluran));
        cv.put("tampil_geotag",String.valueOf(tampil_geotag));
        cv.put("activekebun",String.valueOf(activekebun));
        cv.put("jkoord",String.valueOf(jkoord));
        db.insert(TBL_SETTING, null, cv);
        return true;
    }

    public Boolean DBSetting_UpdateUser(int activeuser, int activekebun) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("activeuser", String.valueOf(activeuser));
        cv.put("activekebun",String.valueOf(activekebun));
        db.update(TBL_SETTING, cv, null, null);
        return true;
    }

    public Boolean DBSetting_Update
            (String webip, String vd, int ikirim, int dver, int pz, int pl, int jmap, int activeuser, int tfoto,
             int taktif, int tinstruksi, int ttrack, int tkml, int tteman, int alarm, int jam, int menit, String pesan, String int_inst,
             String int_outbound, String int_setting, String int_teman, String int_GPS, String int_internet,
             String int_track, String int_lateins, int tampil_kejadian, int tampil_patok, int tampil_pekerjaan,
             int tampil_aktifitas, int tampil_jalan, int tampil_jembatan, int tampil_tanaman, int tampil_obyek,
             int tampil_saluran, int tampil_geotag, int activekebun, int jkoord) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("webip", webip);
        cv.put("vd", vd);
        cv.put("ikirim", String.valueOf(ikirim));
        cv.put("dver", String.valueOf(dver));
        cv.put("pz", String.valueOf(pz));
        cv.put("pl", String.valueOf(pl));
        cv.put("jmap", String.valueOf(jmap));
        cv.put("activeuser", String.valueOf(activeuser));
        cv.put("tfoto", String.valueOf(tfoto));
        cv.put("taktif", String.valueOf(taktif));
        cv.put("tinstruksi", String.valueOf(tinstruksi));
        cv.put("ttrack", String.valueOf(ttrack));
        cv.put("tkml", String.valueOf(tkml));
        cv.put("tteman", String.valueOf(tteman));
        cv.put("alarm", String.valueOf(alarm));
        cv.put("jam", String.valueOf(jam));
        cv.put("menit", String.valueOf(menit));
        cv.put("pesan", pesan);
        cv.put("int_inst", int_inst);
        cv.put("int_outbound", int_outbound);
        cv.put("int_setting", int_setting);
        cv.put("int_teman", int_teman);
        cv.put("int_gps", int_GPS);
        cv.put("int_internet", int_internet);
        cv.put("int_track", int_track);
        cv.put("int_lateins", int_lateins);
        cv.put("tampil_kejadian",String.valueOf(tampil_kejadian));
        cv.put("tampil_patok",String.valueOf(tampil_patok));
        cv.put("tampil_pekerjaan",String.valueOf(tampil_pekerjaan));
        cv.put("tampil_aktifitas",String.valueOf(tampil_aktifitas));
        cv.put("tampil_jalan",String.valueOf(tampil_jalan));
        cv.put("tampil_jembatan",String.valueOf(tampil_jembatan));
        cv.put("tampil_tanaman",String.valueOf(tampil_tanaman));
        cv.put("tampil_obyek",String.valueOf(tampil_obyek));
        cv.put("tampil_saluran",String.valueOf(tampil_saluran));
        cv.put("tampil_geotag",String.valueOf(tampil_geotag));
        cv.put("activekebun",String.valueOf(activekebun));
        cv.put("jkoord",String.valueOf(jkoord));
        db.update(TBL_SETTING, cv, null, null);
        return true;
    }

    public void DBSetting_Delete() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TBL_SETTING, null , null);
    }

    public DB_Setting DBSetting_Get() {
        DB_Setting asetting = new DB_Setting();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_SETTING, TBL_SETTING_COL, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            //{"id", "webip", "vd", "ftpip", "ftpuser", "ftppw", "ikirim"};
            asetting.setWebip(cursor.getString(0));
            asetting.setVd(cursor.getString(1));
            asetting.setIkirim(Integer.parseInt(cursor.getString(2)));
            asetting.setDver(Integer.parseInt(cursor.getString(3)));
            asetting.setPz(Integer.parseInt(cursor.getString(4)));
            asetting.setPl(Integer.parseInt(cursor.getString(5)));
            asetting.setJMap(Integer.parseInt(cursor.getString(6)));
            asetting.setActiveUser(Integer.parseInt(cursor.getString(7)));
            asetting.setTFoto(Integer.parseInt(cursor.getString(8)));
            asetting.setTAktif(Integer.parseInt(cursor.getString(9)));
            asetting.setTInstruksi(Integer.parseInt(cursor.getString(10)));
            asetting.setTTrack(Integer.parseInt(cursor.getString(11)));
            asetting.setTKML(Integer.parseInt(cursor.getString(12)));
            asetting.setTTeman(Integer.parseInt(cursor.getString(13)));
            asetting.setAlarm(Integer.parseInt(cursor.getString(14)));
            asetting.setJam(Integer.parseInt(cursor.getString(15)));
            asetting.setMenit(Integer.parseInt(cursor.getString(16)));
            asetting.setPesan(cursor.getString(17));
            asetting.setIntInstruksi(cursor.getString(18));
            asetting.setIntOutbound(cursor.getString(19));
            asetting.setIntSetting(cursor.getString(20));
            asetting.setIntTeman(cursor.getString(21));
            asetting.setIntGPS(cursor.getString(22));
            asetting.setIntInternet(cursor.getString(23));
            asetting.setIntTrack(cursor.getString(24));
            asetting.setIntLateIns(cursor.getString(25));
            asetting.setTampilKejadian(Integer.parseInt(cursor.getString(26)));
            asetting.setTampilPatok(Integer.parseInt(cursor.getString(27)));
            asetting.setTampilPekerjaan(Integer.parseInt(cursor.getString(28)));
            asetting.setTampilAktifitas(Integer.parseInt(cursor.getString(29)));
            asetting.setTampilJalan(Integer.parseInt(cursor.getString(30)));
            asetting.setTampilJembatan(Integer.parseInt(cursor.getString(31)));
            asetting.setTampilTanaman(Integer.parseInt(cursor.getString(32)));
            asetting.setTampilObyek(Integer.parseInt(cursor.getString(33)));
            asetting.setTampilSaluran(Integer.parseInt(cursor.getString(34)));
            asetting.setTampilGeotag(Integer.parseInt(cursor.getString(35)));
            asetting.setActiveKebun(Integer.parseInt(cursor.getString(36)));
            asetting.setJKoord(Integer.parseInt(cursor.getString(37)));
        } else {
            asetting.setWebip("103.253.107.79");
            asetting.setVd("mobile");
            asetting.setIkirim(5);
            asetting.setDver(0);
            asetting.setPz(0);
            asetting.setPl(0);
            asetting.setJMap(1);
            asetting.setActiveUser(0);
            asetting.setTFoto(0);
            asetting.setTAktif(0);
            asetting.setTInstruksi(0);
            asetting.setTTrack(0);
            asetting.setTKML(0);
            asetting.setTTeman(0);
            asetting.setAlarm(1);
            asetting.setJam(7);
            asetting.setMenit(0);
            asetting.setPesan("Sebuah pesan");
            asetting.setIntInstruksi("10000");
            asetting.setIntOutbound("10000");
            asetting.setIntSetting("10000");
            asetting.setIntTeman("10000");
            asetting.setIntGPS("10000");
            asetting.setIntInternet("10000");
            asetting.setIntTrack("10000");
            asetting.setIntLateIns("10000");
            asetting.setTampilKejadian(0);
            asetting.setTampilPatok(0);
            asetting.setTampilPekerjaan(0);
            asetting.setTampilAktifitas(0);
            asetting.setTampilJalan(0);
            asetting.setTampilJembatan(0);
            asetting.setTampilTanaman(0);
            asetting.setTampilObyek(0);
            asetting.setTampilSaluran(0);
            asetting.setTampilGeotag(0);
            asetting.setActiveKebun(0);
            asetting.setJKoord(1);
            DBSetting_Add("103.253.107.79", "mobile", 5, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 7, 0, "Jangan lupa mengaktifkan Activity Tracking",
                    "10000", "10000", "10000", "10000",
                    "10000", "10000", "10000", "10000", 0, 0,
                    0, 0, 0, 0, 0, 0,
                    0, 0, 0, 1);
        }
        cursor.close();
        return asetting;
    }

    public int DB_Setting_Count() {
        int banyak = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_SETTING, TBL_SETTING_COL, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                banyak = banyak + 1;
            } while (cursor.moveToNext());
        }
        return banyak;
    }
    //25. tbl_setting - ends

    //26. tbl_tanaman - starts
    public Boolean DBTanaman_Add
        (int jenis, String lingkar, String jarak, String tinggi, int banyak, int kebun, String waktu, int userid,
         String afdeling, String blok, int ttanam, int tpanen, String apanen, String bpanen, String spanen,
         String ket, String cx, String cy, int kirim, int dari_server, int id_server, int asli) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("jenis", String.valueOf(jenis));
        cv.put("lingkar", lingkar);
        cv.put("jarak", jarak);
        cv.put("tinggi", tinggi);
        cv.put("banyak", String.valueOf(banyak));
        cv.put("kebun", String.valueOf(kebun));
        cv.put("waktu", waktu);
        cv.put("userid", String.valueOf(userid));
        cv.put("afdeling", afdeling);
        cv.put("blok", blok);
        cv.put("ttanam", String.valueOf(ttanam));
        cv.put("tpanen", String.valueOf(tpanen));
        cv.put("apanen", apanen);
        cv.put("bpanen", bpanen);
        cv.put("spanen", spanen);
        cv.put("ket", ket);
        cv.put("cx", cx);
        cv.put("cy", cy);
        cv.put("kirim", String.valueOf(kirim));
        cv.put("dari_server", String.valueOf(dari_server));
        cv.put("id_server", String.valueOf(id_server));
        cv.put("asli", String.valueOf(asli));
        db.insert(TBL_TANAMAN, null, cv);
        return true;
    }

    public List<DB_Tanaman> DBTanaman_CheckDariServer(int id_server) {
        List<DB_Tanaman> aset = new LinkedList<DB_Tanaman>();
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( id_server != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and id_server= ? "; } else { crit = "id_server= ? "; }
            mStringList.add(String.valueOf(id_server));
        }
        crit = crit + " and dari_server= ? ";
        mStringList.add("1");
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_TANAMAN, TBL_TANAMAN_COL, crit, critval, null, null, null);
        DB_Tanaman anitem = null;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                anitem = new DB_Tanaman();
                anitem.setUrut(Integer.parseInt(cursor.getString(0)));
                aset.add(anitem);
            } while (cursor.moveToNext());
        }
        return aset;
    }

    public Boolean DBTanaman_Delete(int urut, int kirim, int userid) {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( urut != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and urut= ? "; } else { crit = "urut= ? "; }
            mStringList.add(String.valueOf(urut));
        }
        if( kirim != 99 ) {
            if( !crit.equals("") ) { crit = crit + " and kirim= ? "; } else { crit = "kirim= ? "; }
            mStringList.add(String.valueOf(kirim));
        }
        if( userid != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and userid= ? "; } else { crit = "userid= ? "; }
            mStringList.add(String.valueOf(urut));
        }

        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }
        db.delete(TBL_TANAMAN, crit, critval);
        return true;
    }

    public Boolean DBTanaman_Update
            (int urut, int jenis, String lingkar, String jarak, String tinggi, int banyak, int kebun,
             String waktu, int userid, String afdeling, String blok, int ttanam, int tpanen, String apanen, String bpanen, String spanen,
             String ket, String cx, String cy, int kirim, int dari_server, int id_server) {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( urut != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and urut= ? "; } else { crit = "urut= ? "; }
            mStringList.add(String.valueOf(urut));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }
        ContentValues cv = new ContentValues();
        cv.put("jenis", String.valueOf(jenis));
        cv.put("lingkar", lingkar);
        cv.put("jarak", jarak);
        cv.put("tinggi", tinggi);
        cv.put("banyak", String.valueOf(banyak));
        cv.put("kebun", String.valueOf(kebun));
        cv.put("waktu", waktu);
        cv.put("userid", String.valueOf(userid));
        cv.put("afdeling", afdeling);
        cv.put("blok", blok);
        cv.put("ttanam", String.valueOf(ttanam));
        cv.put("tpanen", String.valueOf(tpanen));
        cv.put("apanen", apanen);
        cv.put("bpanen", bpanen);
        cv.put("spanen", spanen);
        cv.put("ket", ket);
        cv.put("cx", cx);
        cv.put("cy", cy);
        cv.put("kirim", String.valueOf(kirim));
        cv.put("dari_server", String.valueOf(dari_server));
        cv.put("id_server", String.valueOf(id_server));
        db.update(TBL_TANAMAN, cv, crit, critval);
        return true;
    }

    public List<DB_Tanaman> DBTanaman_GetUpload(int urut, int kirim, String waktu, int jwaktu) {
        List<DB_Tanaman> aset = new LinkedList<DB_Tanaman>();
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( urut != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and urut=" + " ? "; } else { crit = "urut=" + " ? "; }
            mStringList.add(String.valueOf(urut));
        }
        if( kirim != 99 ) {
            if( !crit.equals("") ) { crit = crit + " and kirim=" + " ? "; } else { crit = "kirim=" + " ? "; }
            mStringList.add(String.valueOf(kirim));
        }
        if( !waktu.equals("") ) {
            String[] separated = waktu.split("-");
            String dummy = "";
            if( !crit.equals("") ) { crit = crit + " and waktu like ? "; } else { crit = "waktu like ? "; }
            if( jwaktu == 1 ) { dummy = waktu;}
            if( jwaktu == 2 ) { dummy = separated[0] + "-" + separated[1] + "-%"; }
            if( jwaktu == 3 ) { dummy = separated[0] + "-%"; }
            mStringList.add(dummy);
        }

        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_TANAMAN, TBL_TANAMAN_COL, crit, critval, null, null, null);
        DB_Tanaman anitem = null;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                anitem = new DB_Tanaman();
                anitem.setUrut(Integer.parseInt(cursor.getString(0)));
                anitem.setJenis(Integer.parseInt(cursor.getString(1)));
                anitem.setLingkar(cursor.getString(2));
                anitem.setJarak(cursor.getString(3));
                anitem.setTinggi(cursor.getString(4));
                anitem.setBanyak(Integer.parseInt(cursor.getString(5)));
                anitem.setKebun(Integer.parseInt(cursor.getString(6)));
                anitem.setWaktu(cursor.getString(7));
                anitem.setUserID(Integer.parseInt(cursor.getString(8)));
                anitem.setAfdeling(cursor.getString(9));
                anitem.setBlok(cursor.getString(10));
                anitem.setTTanam(Integer.parseInt(cursor.getString(11)));
                anitem.setTPanen(Integer.parseInt(cursor.getString(12)));
                anitem.setAPanen(cursor.getString(13));
                anitem.setBPanen(cursor.getString(14));
                anitem.setSPanen(cursor.getString(15));
                anitem.setKet(cursor.getString(16));
                anitem.setCX(cursor.getString(17));
                anitem.setCY(cursor.getString(18));
                anitem.setKirim(Integer.parseInt(cursor.getString(19)));
                anitem.setDariServer(Integer.parseInt(cursor.getString(20)));
                anitem.setIDServer(Integer.parseInt(cursor.getString(21)));
                anitem.setAsli(Integer.parseInt(cursor.getString(22)));
                aset.add(anitem);
            } while (cursor.moveToNext());
        }
        return aset;
    }

    public List<DB_Tanaman> DBTanaman_Get(int urut, int kirim) {
        List<DB_Tanaman> aset = new LinkedList<DB_Tanaman>();
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( urut != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and urut=" + " ? "; } else { crit = "urut=" + " ? "; }
            mStringList.add(String.valueOf(urut));
        }
        if( kirim != 99 ) {
            if( !crit.equals("") ) { crit = crit + " and kirim=" + " ? "; } else { crit = "kirim=" + " ? "; }
            mStringList.add(String.valueOf(kirim));
        }

        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_TANAMAN, TBL_TANAMAN_COL, crit, critval, null, null, null);
        DB_Tanaman anitem = null;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                anitem = new DB_Tanaman();
                anitem.setUrut(Integer.parseInt(cursor.getString(0)));
                anitem.setJenis(Integer.parseInt(cursor.getString(1)));
                anitem.setLingkar(cursor.getString(2));
                anitem.setJarak(cursor.getString(3));
                anitem.setTinggi(cursor.getString(4));
                anitem.setBanyak(Integer.parseInt(cursor.getString(5)));
                anitem.setKebun(Integer.parseInt(cursor.getString(6)));
                anitem.setWaktu(cursor.getString(7));
                anitem.setUserID(Integer.parseInt(cursor.getString(8)));
                anitem.setAfdeling(cursor.getString(9));
                anitem.setBlok(cursor.getString(10));
                anitem.setTTanam(Integer.parseInt(cursor.getString(11)));
                anitem.setTPanen(Integer.parseInt(cursor.getString(12)));
                anitem.setAPanen(cursor.getString(13));
                anitem.setBPanen(cursor.getString(14));
                anitem.setSPanen(cursor.getString(15));
                anitem.setKet(cursor.getString(16));
                anitem.setCX(cursor.getString(17));
                anitem.setCY(cursor.getString(18));
                anitem.setKirim(Integer.parseInt(cursor.getString(19)));
                anitem.setDariServer(Integer.parseInt(cursor.getString(20)));
                anitem.setIDServer(Integer.parseInt(cursor.getString(21)));
                anitem.setAsli(Integer.parseInt(cursor.getString(22)));
                aset.add(anitem);
            } while (cursor.moveToNext());
        }
        return aset;
    }
    //26. tbl_tanaman - ends

    //27. tbl_track - starts
    public Boolean DBTrack_Add(String cx, String cy, String waktu, int userid, int kirim) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("userid", String.valueOf(userid));
        cv.put("waktu", waktu);
        cv.put("cx", cx);
        cv.put("cy", cy);
        cv.put("kirim", String.valueOf(kirim));
        db.insert(TBL_TRACK, null, cv);
        return true;
    }

    public Boolean DBTrack_Delete(int kirim) {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( kirim != 99 ) {
            if( !crit.equals("") ) { crit = crit + " and kirim= ? "; } else { crit = "kirim= ? "; }
            mStringList.add(String.valueOf(kirim));
        }

        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }
        db.delete(TBL_TRACK, crit, critval);
        return true;
    }

    public Boolean DBTrack_Update(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( !crit.equals("") ) { crit = crit + " and urut= ? "; } else { crit = "urut = ? "; }
        mStringList.add(String.valueOf(id));

        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }
        ContentValues cv = new ContentValues();
        cv.put("kirim", "1");
        db.update(TBL_TRACK, cv, crit, critval);
        return true;
    }

    public List<DB_Track> DBTrack_Get(int urut, int kirim, int tagnaik, String waktu) {
        //tagnaik
        //1 --> =
        //2 --> <
        //3 --> <=
        //4 --> >
        //5 --> >=
        String opr = "=";
        if( tagnaik == 1 ) { opr = "="; }
        if( tagnaik == 2 ) { opr = "<"; }
        if( tagnaik == 3 ) { opr = "<="; }
        if( tagnaik == 4 ) { opr = ">"; }
        if( tagnaik == 5 ) { opr = ">="; }
        List<DB_Track> aset = new LinkedList<DB_Track>();
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( urut != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and urut " + opr + " ? "; } else { crit = "urut " + opr + " ? "; }
            mStringList.add(String.valueOf(urut));
        }

        if( kirim != 99 ) {
            if( !crit.equals("") ) { crit = crit + " and kirim= ? "; } else { crit = "kirim= ? "; }
            mStringList.add(String.valueOf(kirim));
        }

        if( !waktu.equals("")) {
            if( !crit.equals("") ) { crit = crit + " and waktu like ? "; } else { crit = "waktu like ? "; }
            mStringList.add("%" + waktu + "%");
        }

        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_TRACK, TBL_TRACK_COL, crit, critval, null, null, "datetime(waktu)");
        DB_Track anitem = null;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                anitem = new DB_Track();
                anitem.setUrut(Integer.parseInt(cursor.getString(0)));
                anitem.setCX(cursor.getString(1));
                anitem.setCY(cursor.getString(2));
                anitem.setWaktu(cursor.getString(3));
                anitem.setUserID(Integer.parseInt(cursor.getString(4)));
                anitem.setKirim(Integer.parseInt(cursor.getString(5)));
                aset.add(anitem);
            } while (cursor.moveToNext());
        }
        return aset;
    }

    public List<DB_Track> DBTrack_GetAll() {

        List<DB_Track> aset = new LinkedList<DB_Track>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_TRACK, TBL_TRACK_COL, null, null, null, null, "datetime(waktu)");
        DB_Track anitem = null;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                anitem = new DB_Track();
                anitem.setUrut(Integer.parseInt(cursor.getString(0)));
                anitem.setCX(cursor.getString(1));
                anitem.setCY(cursor.getString(2));
                anitem.setWaktu(cursor.getString(3));
                anitem.setUserID(Integer.parseInt(cursor.getString(4)));
                anitem.setKirim(Integer.parseInt(cursor.getString(5)));
                aset.add(anitem);
            } while (cursor.moveToNext());
        }
        return aset;
    }
    //27. tbl_track - ends

    //28. tbl_user - starts
    public Boolean DBUser_Add
        (int id, String idpeg, String nama, String devid, String nohp, String email, int idkebun,
         String kebunkml, int tingkat, String jabatan) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id", String.valueOf(id));
        cv.put("idpeg", idpeg);
        cv.put("nama", nama);
        cv.put("devid", devid);
        cv.put("nohp", nohp);
        cv.put("email", email);
        cv.put("idkebun", String.valueOf(idkebun));
        cv.put("kebunkml", kebunkml);
        cv.put("tingkat", String.valueOf(tingkat));
        cv.put("jabatan", jabatan);
        db.insert(TBL_USER, null, cv);
        return true;
    }

    public int DBUser_Count() {
        int banyak = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_USER, TBL_USER_COL, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                banyak = banyak + 1;
            } while (cursor.moveToNext());
        }
        return banyak;
    }

    public DB_User DBUSer_Get() {
        DB_User anuser = new DB_User();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_USER, TBL_USER_COL, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            anuser.setID(Integer.parseInt(cursor.getString(0)));
            anuser.setIDPeg(cursor.getString(1));
            anuser.setNama(cursor.getString(2));
            anuser.setDevid(cursor.getString(3));
            anuser.setNoHP(cursor.getString(4));
            anuser.setEmail(cursor.getString(5));
            anuser.setIDKebun(Integer.parseInt(cursor.getString(6)));
            anuser.setKebunKML(cursor.getString(7));
            anuser.setTingkat(Integer.parseInt(cursor.getString(8)));
            anuser.setJabatan(cursor.getString(9));
        } else {
            return null;
        }
        return anuser;
    }

    public Boolean DBUser_Delete(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( id != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and id= ? "; } else { crit = "id= ? "; }
            mStringList.add(String.valueOf(id));
        }

        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }
        db.delete(TBL_USER, crit, critval);
        return true;
    }

    public Boolean DBUser_Delete_All() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TBL_USER, null, null);
        return true;
    }


    public Boolean DBUser_Update
            (int id, String idpeg, String nama, String devid, String nohp, String email, int idkebun,
             String kebunkml, int tingkat, String jabatan) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id", String.valueOf(id));
        cv.put("idpeg", idpeg);
        cv.put("nama", nama);
        cv.put("devid", devid);
        cv.put("nohp", nohp);
        cv.put("email", email);
        cv.put("idkebun", String.valueOf(idkebun));
        cv.put("kebunkml", kebunkml);
        cv.put("tingkat", String.valueOf(tingkat));
        cv.put("jabatan", jabatan);
        db.update(TBL_USER, cv, null, null);
        return true;
    }
    //28. tbl_user - ends

    //29. tbl_ref_jembatan_kondisi - starts
    public Boolean DBRefJembatanKondisi_Add(int kode, String nama) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("kode", String.valueOf(kode));
        cv.put("nama", nama);
        db.insert(TBL_REF_JEMBATAN_KONDISI, null, cv);
        return true;
    }

    public List<DB_Ref_Jembatan_Kondisi> DBRefJembatanKondisi_Get(int kode) {
        List<DB_Ref_Jembatan_Kondisi> aset = new LinkedList<DB_Ref_Jembatan_Kondisi>();
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( kode != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and kode= ? "; } else { crit = "kode= ? "; }
            mStringList.add(String.valueOf(kode));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_REF_JEMBATAN_KONDISI, TBL_REF_JEMBATAN_KONDISI_COL, crit, critval, null, null, null);
        DB_Ref_Jembatan_Kondisi anitem = null;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                anitem = new DB_Ref_Jembatan_Kondisi();
                anitem.setKode(Integer.parseInt(cursor.getString(0)));
                anitem.setNama(cursor.getString(1));
                aset.add(anitem);
            } while (cursor.moveToNext());
        }
        return aset;
    }

    public Boolean DBRefJembatanKondisi_Delete(int kode) {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( kode != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and kode= ? "; } else { crit = "kode= ? "; }
            mStringList.add(String.valueOf(kode));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }
        db.delete(TBL_REF_JEMBATAN_KONDISI, crit, critval);
        return true;
    }

    public Boolean DBRefJembatanKondisi_Update(int kode, String nama) {

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( kode != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and kode= ? "; } else { crit = "kode= ? "; }
            mStringList.add(String.valueOf(kode));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nama", nama);
        db.update(TBL_REF_JEMBATAN_KONDISI, cv, crit, critval);
        return true;
    }
    //29. tbl_ref_jembatan_kondisi - ends

    //29. tbl_ref_jabatan - starts;
    public Boolean DBRefJabatan_Add(int id, String nama) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id", String.valueOf(id));
        cv.put("nama", nama);
        db.insert(TBL_REF_JABATAN, null, cv);
        return true;
    }

    public List<DB_Ref_Jabatan> DBRefJabatan_Get(int id) {
        List<DB_Ref_Jabatan> aset = new LinkedList<DB_Ref_Jabatan>();
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( id != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and id= ? "; } else { crit = "id= ? "; }
            mStringList.add(String.valueOf(id));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_REF_JABATAN, TBL_REF_JABATAN_COL, crit, critval, null, null, null);
        DB_Ref_Jabatan anitem = null;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                anitem = new DB_Ref_Jabatan();
                anitem.setID(Integer.parseInt(cursor.getString(0)));
                anitem.setNama(cursor.getString(1));
                aset.add(anitem);
            } while (cursor.moveToNext());
        }
        return aset;
    }

    public Boolean DBRefJabatan_Delete(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( id != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and id= ? "; } else { crit = "id= ? "; }
            mStringList.add(String.valueOf(id));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }
        db.delete(TBL_REF_JABATAN, crit, critval);
        return true;
    }

    public Boolean DBRefJabatan_Update(int id, String nama) {

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( id != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and id= ? "; } else { crit = "id= ? "; }
            mStringList.add(String.valueOf(id));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nama", nama);
        db.update(TBL_REF_JABATAN, cv, crit, critval);
        return true;
    }
    //29. tbl_ref_jabatan - ends

    //30. tbl_pekerja - starts;
    public Boolean DBPekerja_Add(int urut, int idkebun, int idafd, int idmandor, String idpeg, String nama, int jab) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("urut", String.valueOf(urut));
        cv.put("idkebun", String.valueOf(idkebun));
        cv.put("idafd", String.valueOf(idafd));
        cv.put("idmandor", String.valueOf(idmandor));
        cv.put("idpeg", idpeg);
        cv.put("nama", nama);
        cv.put("jab", String.valueOf(jab));
        db.insert(TBL_PEKERJA, null, cv);
        return true;
    }

    public List<DB_Pekerja> DBPekerja_Get(int urut, int idkebun, int idafd, String idpeg, int idmandor) {
        List<DB_Pekerja> aset = new LinkedList<DB_Pekerja>();
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( urut != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and urut= ? "; } else { crit = "urut= ? "; }
            mStringList.add(String.valueOf(urut));
        }
        if( idkebun != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and idkebun= ? "; } else { crit = "idkebun= ? "; }
            mStringList.add(String.valueOf(idkebun));
        }
        if( idafd != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and idafd= ? "; } else { crit = "idafd= ? "; }
            mStringList.add(String.valueOf(idafd));
        }
        if( !idpeg.equals("")) {
            if( !crit.equals("") ) { crit = crit + " and idpeg= ? "; } else { crit = "idpeg= ? "; }
            mStringList.add(idpeg);
        }
        if( idmandor != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and idmandor= ? "; } else { crit = "idmandor= ? "; }
            mStringList.add(String.valueOf(idmandor));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_PEKERJA, TBL_PEKERJA_COL, crit, critval, null, null, null);
        DB_Pekerja anitem = null;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                anitem = new DB_Pekerja();
                anitem.setUrut(Integer.parseInt(cursor.getString(0)));
                anitem.setIDkebun(Integer.parseInt(cursor.getString(1)));
                anitem.setIDAfd(Integer.parseInt(cursor.getString(2)));
                anitem.setIDMandor(Integer.parseInt(cursor.getString(3)));
                anitem.setIDPeg(cursor.getString(4));
                anitem.setNama(cursor.getString(5));
                anitem.setJabatan(Integer.parseInt(cursor.getString(6)));
                aset.add(anitem);
            } while (cursor.moveToNext());
        }
        return aset;
    }

    public Boolean DBPekerja_Delete(int urut, int idkebun, int idafd, String idpeg, int idmandor) {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( urut != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and urut= ? "; } else { crit = "urut= ? "; }
            mStringList.add(String.valueOf(urut));
        }
        if( idkebun != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and idkebun= ? "; } else { crit = "idkebun= ? "; }
            mStringList.add(String.valueOf(idkebun));
        }
        if( idafd != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and idafd= ? "; } else { crit = "idafd= ? "; }
            mStringList.add(String.valueOf(idafd));
        }
        if( !idpeg.equals("")) {
            if( !crit.equals("") ) { crit = crit + " and idpeg= ? "; } else { crit = "idpeg= ? "; }
            mStringList.add(idpeg);
        }
        if( idmandor != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and idmandor= ? "; } else { crit = "idmandor= ? "; }
            mStringList.add(String.valueOf(idmandor));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }
        db.delete(TBL_PEKERJA, crit, critval);
        return true;
    }

    public Boolean DBPekerja_Update(int urut, int idkebun, int idafd, int idmandor, String idpeg, String nama, int jab) {
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( urut != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and urut= ? "; } else { crit = "urut= ? "; }
            mStringList.add(String.valueOf(urut));
        }
        if( idkebun != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and idkebun= ? "; } else { crit = "idkebun= ? "; }
            mStringList.add(String.valueOf(idkebun));
        }
        if( idafd != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and idafd= ? "; } else { crit = "idafd= ? "; }
            mStringList.add(String.valueOf(idafd));
        }
        if( !idpeg.equals("")) {
            if( !crit.equals("") ) { crit = crit + " and idpeg= ? "; } else { crit = "idpeg= ? "; }
            mStringList.add(idpeg);
        }
        if( idmandor != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and idmandor= ? "; } else { crit = "idmandor= ? "; }
            mStringList.add(String.valueOf(idmandor));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("urut", String.valueOf(urut));
        cv.put("idkebun", String.valueOf(idkebun));
        cv.put("idafd", String.valueOf(idafd));
        cv.put("idmandor", String.valueOf(idmandor));
        cv.put("idpeg", idpeg);
        cv.put("nama", nama);
        cv.put("jab", String.valueOf(jab));
        db.update(TBL_PEKERJA, cv, crit, critval);
        return true;
    }
    //30. tbl_pekerja - ends

    //31. tbl_absensi - starts
    public Boolean DBAbsensi_Add (
        int idrow, String idpek, int idmandor, String tgl, String jamb, String jamp, String lokasi, String fotob,
        String fotop, int adab, int adap, String cxb, String cyb, String cxp, String cyp, String waktub, String waktup, int kirim) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("idrow", String.valueOf(idrow));
        cv.put("idpek", idpek);
        cv.put("idmandor", String.valueOf(idmandor));
        cv.put("tgl", tgl);
        cv.put("jamb", jamb);
        cv.put("jamp", jamp);
        cv.put("lokasi", lokasi);
        cv.put("fotob", fotob);
        cv.put("fotop", fotop);
        cv.put("adab", String.valueOf(adab));
        cv.put("adap", String.valueOf(adap));
        cv.put("cxb", cxb);
        cv.put("cyb", cyb);
        cv.put("cxp", cxp);
        cv.put("cyp", cyp);
        cv.put("waktub", waktub);
        cv.put("waktup", waktup);
        cv.put("kirim", String.valueOf(kirim));
        db.insert(TBL_ABSENSI, null, cv);
        return true;
    }

    public List<DB_Absensi> DBAbsensi_GetUruttgl(int idrow, String idpek, int idmandor) {
        List<DB_Absensi> aset = new LinkedList<DB_Absensi>();
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( idrow != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and idrow= ? "; } else { crit = "idrow= ? "; }
            mStringList.add(String.valueOf(idrow));
        }
        if( !idpek.equals("")) {
            if( !crit.equals("") ) { crit = crit + " and idpek= ? "; } else { crit = "idpek= ? "; }
            mStringList.add(idpek);
        }
        if( idmandor != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and idmandor= ? "; } else { crit = "idmandor= ? "; }
            mStringList.add(String.valueOf(idmandor));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_ABSENSI, TBL_ABSENSI_COL, crit, critval, null, null, "tgl DESC");
        DB_Absensi anitem = null;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                anitem = new DB_Absensi();
                anitem.setIDRow(Integer.parseInt(cursor.getString(0)));
                anitem.setIDPek(cursor.getString(1));
                anitem.setIDMandor(Integer.parseInt(cursor.getString(2)));
                anitem.setTgl(cursor.getString(3));
                anitem.setJamb(cursor.getString(4));
                anitem.setJamp(cursor.getString(5));
                anitem.setLokasi(cursor.getString(6));
                anitem.setFotob(cursor.getString(7));
                anitem.setFotop(cursor.getString(8));
                anitem.setAdab(Integer.parseInt(cursor.getString(9)));
                anitem.setAdap(Integer.parseInt(cursor.getString(10)));
                anitem.setCXB(cursor.getString(11));
                anitem.setCYB(cursor.getString(12));
                anitem.setCXP(cursor.getString(13));
                anitem.setCYP(cursor.getString(14));
                anitem.setWaktub(cursor.getString(15));
                anitem.setWaktup(cursor.getString(16));
                anitem.setKirim(Integer.parseInt(cursor.getString(17)));
                aset.add(anitem);
            } while (cursor.moveToNext());
        }
        return aset;
    }

    public List<DB_Absensi> DBAbsensi_GetUpload(int idrow, String idpek, int idmandor, String tgl, int kirim) {
        List<DB_Absensi> aset = new LinkedList<DB_Absensi>();
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( idrow != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and idrow= ? "; } else { crit = "idrow= ? "; }
            mStringList.add(String.valueOf(idrow));
        }
        if( !idpek.equals("")) {
            if( !crit.equals("") ) { crit = crit + " and idpek= ? "; } else { crit = "idpek= ? "; }
            mStringList.add(idpek);
        }
        if( idmandor != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and idmandor= ? "; } else { crit = "idmandor= ? "; }
            mStringList.add(String.valueOf(idmandor));
        }
        if( !tgl.equals("") ) {
            String[] separated = tgl.split("-");
            String dummy = "";
            if( !crit.equals("") ) { crit = crit + " and tgl like ? "; } else { crit = "tgl like ? "; }
            dummy = separated[0] + "-" + separated[1] + "-%";
            mStringList.add(dummy);
        }
        if( kirim != 99 ) {
            if( !crit.equals("") ) { crit = crit + " and kirim= ? "; } else { crit = "kirim= ? "; }
            mStringList.add(String.valueOf(kirim));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_ABSENSI, TBL_ABSENSI_COL, crit, critval, null, null, null);
        DB_Absensi anitem = null;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                anitem = new DB_Absensi();
                anitem.setIDRow(Integer.parseInt(cursor.getString(0)));
                anitem.setIDPek(cursor.getString(1));
                anitem.setIDMandor(Integer.parseInt(cursor.getString(2)));
                anitem.setTgl(cursor.getString(3));
                anitem.setJamb(cursor.getString(4));
                anitem.setJamp(cursor.getString(5));
                anitem.setLokasi(cursor.getString(6));
                anitem.setFotob(cursor.getString(7));
                anitem.setFotop(cursor.getString(8));
                anitem.setAdab(Integer.parseInt(cursor.getString(9)));
                anitem.setAdap(Integer.parseInt(cursor.getString(10)));
                anitem.setCXB(cursor.getString(11));
                anitem.setCYB(cursor.getString(12));
                anitem.setCXP(cursor.getString(13));
                anitem.setCYP(cursor.getString(14));
                anitem.setWaktub(cursor.getString(15));
                anitem.setWaktup(cursor.getString(16));
                anitem.setKirim(Integer.parseInt(cursor.getString(17)));
                aset.add(anitem);
            } while (cursor.moveToNext());
        }
        return aset;
    }

    public List<DB_Absensi> DBAbsensi_Get(int idrow, String idpek, int idmandor, String tgl) {
        List<DB_Absensi> aset = new LinkedList<DB_Absensi>();
        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( idrow != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and idrow= ? "; } else { crit = "idrow= ? "; }
            mStringList.add(String.valueOf(idrow));
        }
        if( !idpek.equals("")) {
            if( !crit.equals("") ) { crit = crit + " and idpek= ? "; } else { crit = "idpek= ? "; }
            mStringList.add(idpek);
        }
        if( idmandor != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and idmandor= ? "; } else { crit = "idmandor= ? "; }
            mStringList.add(String.valueOf(idmandor));
        }
        if( !tgl.equals("")) {
            if( !crit.equals("") ) { crit = crit + " and tgl= ? "; } else { crit = "tgl= ? "; }
            mStringList.add(tgl);
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_ABSENSI, TBL_ABSENSI_COL, crit, critval, null, null, null);
        DB_Absensi anitem = null;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                anitem = new DB_Absensi();
                anitem.setIDRow(Integer.parseInt(cursor.getString(0)));
                anitem.setIDPek(cursor.getString(1));
                anitem.setIDMandor(Integer.parseInt(cursor.getString(2)));
                anitem.setTgl(cursor.getString(3));
                anitem.setJamb(cursor.getString(4));
                anitem.setJamp(cursor.getString(5));
                anitem.setLokasi(cursor.getString(6));
                anitem.setFotob(cursor.getString(7));
                anitem.setFotop(cursor.getString(8));
                anitem.setAdab(Integer.parseInt(cursor.getString(9)));
                anitem.setAdap(Integer.parseInt(cursor.getString(10)));
                anitem.setCXB(cursor.getString(11));
                anitem.setCYB(cursor.getString(12));
                anitem.setCXP(cursor.getString(13));
                anitem.setCYP(cursor.getString(14));
                anitem.setWaktub(cursor.getString(15));
                anitem.setWaktup(cursor.getString(16));
                anitem.setKirim(Integer.parseInt(cursor.getString(17)));
                aset.add(anitem);
            } while (cursor.moveToNext());
        }
        return aset;
    }

    public Boolean DBAbsensi_Delete(int urut, String idpek, int idmandor) {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( urut != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and urut= ? "; } else { crit = "urut= ? "; }
            mStringList.add(String.valueOf(urut));
        }
        if( !idpek.equals("")) {
            if( !crit.equals("") ) { crit = crit + " and idpek= ? "; } else { crit = "idpek= ? "; }
            mStringList.add(idpek);
        }
        if( idmandor != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and idmandor= ? "; } else { crit = "idmandor= ? "; }
            mStringList.add(String.valueOf(idmandor));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }
        db.delete(TBL_ABSENSI, crit, critval);
        return true;
    }

    public Boolean DBAbsensi_UpdateKirim(int idrow) {

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( idrow != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and idrow= ? "; } else { crit = "idrow= ? "; }
            mStringList.add(String.valueOf(idrow));
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("kirim", 1);
        db.update(TBL_ABSENSI, cv, crit, critval);
        return true;
    }

    public Boolean DBAbsensi_Update(
            int idrow, String idpek, int idmandor, String tgl, String jamb, String jamp, String lokasi, String fotob,
            String fotop, int adab, int adap, String cxb, String cyb, String cxp, String cyp, String waktub, String waktup, int kirim) {

        ArrayList<String>  mStringList= new ArrayList<String>();
        String crit = "";
        if( idrow != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and idrow= ? "; } else { crit = "idrow= ? "; }
            mStringList.add(String.valueOf(idrow));
        }
        if( !idpek.equals("")) {
            if( !crit.equals("") ) { crit = crit + " and idpek= ? "; } else { crit = "idpek= ? "; }
            mStringList.add(idpek);
        }
        if( idmandor != 0 ) {
            if( !crit.equals("") ) { crit = crit + " and idmandor= ? "; } else { crit = "idmandor= ? "; }
            mStringList.add(String.valueOf(idmandor));
        }
        if( !tgl.equals("")) {
            if( !crit.equals("") ) { crit = crit + " and tgl= ? "; } else { crit = "tgl= ? "; }
            mStringList.add(tgl);
        }
        String[] critval = new String[mStringList.size()];
        critval = mStringList.toArray(critval);
        if( crit.equals("") ) {
            crit = null;
            critval = null;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("idrow", String.valueOf(idrow));
        cv.put("idpek", idpek);
        cv.put("idmandor", String.valueOf(idmandor));
        cv.put("tgl", tgl);
        cv.put("jamb", jamb);
        cv.put("jamp", jamp);
        cv.put("lokasi", lokasi);
        cv.put("fotob", fotob);
        cv.put("fotop", fotop);
        cv.put("adab", String.valueOf(adab));
        cv.put("adap", String.valueOf(adap));
        cv.put("cxb", cxb);
        cv.put("cyb", cyb);
        cv.put("cxp", cxp);
        cv.put("cyp", cyp);
        cv.put("waktub", waktub);
        cv.put("waktup", waktup);
        cv.put("kirim", String.valueOf(kirim));
        db.update(TBL_ABSENSI, cv, crit, critval);
        return true;
    }
    //31. tbl_absensi - ends
}
