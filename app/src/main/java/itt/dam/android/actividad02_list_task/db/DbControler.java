package itt.dam.android.actividad02_list_task.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DbControler extends SQLiteOpenHelper {

    public DbControler(@Nullable Context context) {
        super(context, "itt.dam.android.actividad02_list_task.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("App", "Se crea la bd");

        db.execSQL("CREATE TABLE USERS (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "USERNAME TEXT NOT NULL, " +
                "PASS TEXT NOT NULL, " +
                "NUMTASK INTEGER DEFAULT 0)");

        db.execSQL("CREATE TABLE TASKS (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT NOT NULL, " +
                "ACTIVE INTEGER DEFAULT 1, " +
                "USER INTEGER, " +
                "FOREIGN KEY(USER) REFERENCES USERS(ID))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addUser(String user, int pass) {

    }

    public void addTask(String s) {
        //Log.i("App", "Pasa por addTask");
        ContentValues r = new ContentValues();
        r.put("NAME", s);

        // Se abre la BD para escritura
        SQLiteDatabase db = this.getWritableDatabase();

        // Se añade el registra  a la tarea
        db.insert("TASKS", null, r);


        // Se cierra la BD
        db.close();
    }

    public String[] getTasks() {
        // Log.i("App", "Pasa por getTasks");
        // Se abre la BD para lectura
        SQLiteDatabase db = this.getReadableDatabase();

        // Se añade el registra  a la tarea
        Cursor cursor = db.rawQuery("SELECT * FROM TASKS", null);

        if (cursor.getCount() > 0){
            String[] tasks = new String[cursor.getCount()];

            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i++) {
                tasks[i] = cursor.getString(1);
                cursor.moveToNext();
            }

            db.close();
            return tasks;

        } else {
            db.close();
            return null;
        }

    }

    public boolean isEmptyDb() {
        Log.i("App", "Pasa por isEmpyDb");
        // Se abre la BD para lectura
        SQLiteDatabase db = this.getReadableDatabase();

        // Se añade el registra  a la tarea
        Cursor cursor = db.rawQuery("SELECT * FROM TASKS", null);

        if (cursor.getCount() > 0){
            Log.i("APP", "Version Click:" + cursor.getCount());
            return false;
        } else {
            Log.i("App", "BD vacia");
            return true;
        }
    }

    public void deleteTask(String taskName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("TASKS", "NAME=?", new String[]{taskName});
        db.close();
    }

    public void udpateTask(String taskOld, String taskNew) {
        //Se establece el campo a actualizar
        ContentValues v = new ContentValues();

        // Se indica el campo y el nuevo valor
        v.put("NAME", taskNew);

        // Se abre la BD para escritura
        SQLiteDatabase db = this.getWritableDatabase();

        // Se actualiza el valor del registro al nuevo.
        db.update("TASKS", v, "NAME=?", new String[]{taskOld});

        // Se cierra la BD
        db.close();
    }

    public void userRegister(String user, String pass) {
        //Log.i("App", "Pasa por addTask");
        ContentValues r = new ContentValues();
        r.put("USERNAME", user);
        r.put("PASS", pass);

        // Se abre la BD para escritura
        SQLiteDatabase db = this.getWritableDatabase();

        // Se añade el registra  a la tarea
        db.insert("USERS", null, r);

        // Se cierra la BD
        db.close();
    }

    public int login(String user, String pass) {
        // Se abre la BD para escritura
        SQLiteDatabase db = this.getReadableDatabase();

        String[] args = new String[] {user, pass};

        // Se añade el registra  a la tarea
        Cursor cursor = db.rawQuery("SELECT ID FROM USER " +
                "WHERE USERNAME=? AND PASS=?", args);

        if (cursor.getCount() > 0){
            Log.i("APP", "Usuario existe id:" + cursor.getString(0));
            return Integer.parseInt(cursor.getString(0));
        } else {
            Log.i("App", "BD vacia");
            return 0;
        }
    }
}
