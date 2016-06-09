package voidream.vcontroller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ryvaldie on 21/10/15.
 */

public class SQLiteAdapter extends SQLiteOpenHelper {

    private SQLiteDatabase db;
    private static final String database_name = "VcontrollerDB";
    private static final int database_version = 1;//naikin setiap ada perubahan

    public SQLiteAdapter(Context context) {
        super(context, database_name, null, database_version);
    }

    private static final String key_id = "id", tabel_controller = "controller"
            , tabel_timer = "timer", tabel_log = "log", tabel_setting_mqtt = "mqtt_settings"
            , tabel_setting_tcp = "tcp_settings", tabel_io_command = "io_command";

    private static final String nama = "nama", posisi = "posisi", power = "power"
            , timer = "timer", id_image = "id_image", status = "status";
    private static final String create_tabel_controller =
            "create table " + tabel_controller + " (" + key_id + " INTEGER PRIMARY KEY,"
                    + nama + " TEXT," + posisi + " TEXT,"
                    + power + " TEXT," + status + " TEXT," + id_image + " TEXT" + ")";

    private static final String id_controller = "id_controller", action = "action", time = "time";
    private static final String create_tabel_timer =
            "create table " + tabel_timer + " (" + key_id + " INTEGER PRIMARY KEY,"
                    + id_controller + " TEXT," + action + " TEXT," + time + " TEXT" + ")";

    private static final String timestamp = "timestamp";
    private static final String create_tabel_log =
            "create table " + tabel_log + " (" + key_id + " INTEGER PRIMARY KEY,"
                    + id_controller + " TEXT," +  timestamp + "TEXT" + ")";

    private static final String broker_url = "broker_url", port = "port", username = "username"
            , password = "password", topic = "topic";
    private static final String create_tabel_setting_mqtt =
            "create table " + tabel_setting_mqtt + " (" + key_id + " INTEGER PRIMARY KEY,"
                    + broker_url + " TEXT," + port + " TEXT," + username + " TEXT,"
                    + password + " TEXT,"+ topic + " TEXT" + ")";

    private static final String ip_or_domain = "ip_domain";
    private static final String create_tabel_setting_tcp =
            "create table " + tabel_setting_tcp+ " (" + key_id + " INTEGER PRIMARY KEY,"
                    + ip_or_domain + " TEXT," + port + " TEXT" + ")";

    private static final String on_command = "on_command", off_command = "off_command"
            , timer_command = "timer_command";
    private static final String create_tabel_io_command =
            "create table " + tabel_io_command + " (" + key_id + " INTEGER PRIMARY KEY,"
                    + id_controller + " TEXT," + on_command + " TEXT,"
                    + off_command + " TEXT," + timer_command + " TEXT" + ")";

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(create_tabel_controller);
        db.execSQL(create_tabel_io_command);
        db.execSQL(create_tabel_log);
        db.execSQL(create_tabel_setting_mqtt);
        db.execSQL(create_tabel_setting_tcp);
        db.execSQL(create_tabel_timer);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + tabel_controller);
        db.execSQL("DROP TABLE IF EXIST " + tabel_io_command);
        db.execSQL("DROP TABLE IF EXIST " + tabel_log);
        db.execSQL("DROP TABLE IF EXIST " + tabel_setting_tcp);
        db.execSQL("DROP TABLE IF EXIST " + tabel_timer);
        db.execSQL("DROP TABLE IF EXIST " + tabel_setting_mqtt);
        onCreate(db);
    }


    public void AddController(String nama_, String posisi_, String power_, int id_image_){
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        if(nama_!=null & posisi_!=null & power_!=null & id_image_!= 0){
            values.put(nama, nama_);
            values.put(posisi, posisi_);
            values.put(power, power_);
            values.put(status, "Waiting Response");
            values.put(id_image, id_image_);
        }

        db.insert(tabel_controller, null, values);
    }

    public String[][] getController(){
        db = this.getReadableDatabase();

        String[] columns = new String[]{nama, posisi, power, status, id_image};
        Cursor cursor = db.query(tabel_controller, columns,
                null, null, null, null, null);

        int size = (int)getRowCount(tabel_controller);
        String[][] result = new String[5][size];

        int a = 0;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            result[0][a] = cursor.getString(0);//nama
            result[1][a] = cursor.getString(1);//posisi
            result[2][a] = cursor.getString(2);//power
            result[3][a] = cursor.getString(3);//status
            result[4][a] = cursor.getString(4);//id_image
            a++;
            cursor.moveToNext();
        }
        cursor.close();

        return result;
    }

    public void addControllerStatus(String nama_, String status_){
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        if(status_!=null){
            values.put(status, status_);
        }

        db.insert(tabel_controller, nama + "='" + nama_ + "'" , values);
    }

    public void deleteController(String nama_) {
        db = this.getWritableDatabase();
        db.delete(tabel_controller, nama + "='" + nama_ + "'", null);
    }

    public void deleteAll(){
        db = this.getWritableDatabase();
        db.delete(tabel_controller, null, null);
        db.delete(tabel_io_command, null, null);
        db.delete(tabel_log, null, null);
        db.delete(tabel_setting_mqtt, null, null);
        db.delete(tabel_setting_tcp, null, null);
        db.delete(tabel_timer, null, null);
    }

    public long getRowCount(String tabel) {
        SQLiteDatabase db = this.getReadableDatabase();
        return DatabaseUtils.queryNumEntries(db, tabel);
    }

}