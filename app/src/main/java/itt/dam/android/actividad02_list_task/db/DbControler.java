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
        db.execSQL("CREATE TABLE TASKS (ID INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addTask(String s) {
        Log.i("App", "Pasa por addTask");
        ContentValues r = new ContentValues();
        r.put("NOMBRE", s);


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
        db.delete("TASKS", "NOMBRE=?", new String[]{taskName});
        db.close();
    }

    public void udpateTask(String taskName) {

        // Se abre la BD para escritura
        SQLiteDatabase db = this.getWritableDatabase();

        // Se añade el registra  a la tarea
        db.update("TASKS",null, "NOMBRE=?", new String[]{taskName});

        // Se cierra la BD
        db.close();
    }
}
