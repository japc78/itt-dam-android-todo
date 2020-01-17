package itt.dam.android.actividad02_list_task.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import itt.dam.android.actividad02_list_task.LoginActivity;

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
                "PASS TEXT NOT NULL)");

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

    public void addTask(String text, String userId) {
        //Log.i("App", "Pasa por addTask");
        ContentValues r = new ContentValues();
        r.put("NAME", text);
        r.put("USER", userId);

        // Se abre la BD para escritura
        SQLiteDatabase db = this.getWritableDatabase();

        // Se añade el registra  a la tarea
        db.insert("TASKS", null, r);


        // Se cierra la BD
        db.close();
    }

    /**
     * Metodo para el listado de tareas.
     * @param userId Id del usuario
     * @param state Tareas activas 1 / terminadas 0
     * @return Retonra un Array
     */

    public String[] getTasks(String userId, int state) {
        // Log.i("App", "Pasa por getTasks");
        // Se abre la BD para lectura
        SQLiteDatabase db = this.getReadableDatabase();

        String[] args = new String[]{String.valueOf(userId), String.valueOf(state)};

        // Se añade el registra  a la tarea
        Cursor cursor = db.rawQuery("SELECT * FROM TASKS " +
                "WHERE USER=? AND ACTIVE=?", args);

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

    public boolean isEmptyDb(String userId, int state) {
        System.out.println("App: Pasa por isEmpyDb");
        // Se abre la BD para lectura
        SQLiteDatabase db = this.getReadableDatabase();

        String[] args = new String[]{String.valueOf(userId), String.valueOf(state)};

        // Se añade el registra  a la tarea
        Cursor cursor = db.rawQuery("SELECT * FROM TASKS " +
                "WHERE USER=? AND ACTIVE=?", args);

        if (cursor.getCount() > 0){
            return false;
        } else {
            Log.i("App", "BD vacia");
            return true;
        }
    }

    public void deleteTask(String userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("TASKS", "USER=? AND ACTIVE=0", new String[]{userId});
        db.close();
    }

    public void deleteTask(String taskName, String userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("TASKS", "NAME=? AND USER=? AND ACTIVE=0", new String[]{taskName, userId});
        db.close();
    }

    public void disableTask(String task, String userId) {
        ContentValues v = new ContentValues();
        v.put("ACTIVE", 0);

        SQLiteDatabase db = this.getWritableDatabase();
        db.update("TASKS", v, "NAME=? AND USER=?", new String[]{task, userId});

        db.close();
    }

    public void udpateTask(String taskOld, String taskNew, String userId) {
        //Se establece el campo a actualizar
        ContentValues v = new ContentValues();

        // Se indica el campo y el nuevo valor
        v.put("NAME", taskNew);

        // Se abre la BD para escritura
        SQLiteDatabase db = this.getWritableDatabase();

        // Se actualiza el valor del registro al nuevo.
        db.update("TASKS", v, "NAME=? AND USER=?", new String[]{taskOld,userId});

        // Se cierra la BD
        db.close();
    }

    public void udpateTask(String task, String userId) {
        //Se establece el campo a actualizar
        ContentValues v = new ContentValues();

        // Se indica el campo y el nuevo valor
        v.put("ACTIVE", 1);

        // Se abre la BD para escritura
        SQLiteDatabase db = this.getWritableDatabase();

        // Se actualiza el valor del registro al nuevo.
        db.update("TASKS", v, "NAME=? AND USER=?", new String[]{task,userId});

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

        db.insert("USERS", null, r);

        // Se cierra la BD
        db.close();
    }

    public String userLogin(String user, String pass) {
        // Se abre la BD para escritura
        SQLiteDatabase db = this.getReadableDatabase();

        String[] args = new String[] {user, pass};

        // Se añade el registra  a la tarea
        Cursor cursor = db.rawQuery("SELECT ID FROM USERS " +
                "WHERE USERNAME=? AND PASS=?", args);

        cursor.moveToFirst();

        //System.out.println("UserLogin cursor: " + cursor.getString(1));


        if (cursor.getCount() > 0){
            Log.i("APP", "Usuario existe id:" + cursor.getString(0));
            return cursor.getString(0);
        } else {
            Log.i("App", "BD vacia");
            return null;
        }
    }

    public boolean isTaskExist (String task, String userId) {
        System.out.println("App: Para por isTaskExist");
        System.out.println("User - " + userId + ", task: " + task);
        SQLiteDatabase db = this.getReadableDatabase();

        String[] args = new String[] {task, userId};

        Cursor cursor = db.rawQuery("SELECT * FROM TASKS " +
                "WHERE NAME=? AND USER=?", args);

        System.out.println("User - " + userId + ", task: " + task);

        if (cursor.getCount() > 0){
            return true;
        } else {
            return false;
        }
    }

    public boolean isTaskExist (String task, String userId, int active) {
        System.out.println("App: Para por isTaskExist");
        System.out.println("User - " + userId + ", task: " + task);
        SQLiteDatabase db = this.getReadableDatabase();

        String[] args = new String[] {task, userId, String.valueOf(active)};

        Cursor cursor = db.rawQuery("SELECT * FROM TASKS " +
                "WHERE NAME=? AND USER=? AND ACTIVE=?", args);

        System.out.println("User - " + userId + ", task: " + task);

        if (cursor.getCount() > 0){
            return true;
        } else {
            return false;
        }
    }

    public boolean isUserExist (String userId) {
        System.out.println("App: Para por isUserExist");
        SQLiteDatabase db = this.getReadableDatabase();

        String[] args = new String[] {userId};

        System.out.println("Args: " + args[0]);

        Cursor cursor = db.rawQuery("SELECT * FROM USERS " +
                "WHERE USERNAME=?", args);

        if (cursor.getCount() > 0){
            return true;
        } else {
            return false;
        }
    }

    public void undoTaks(String task, String userId) {
        //Se establece el campo a actualizar
        ContentValues v = new ContentValues();

        // Se indica el campo y el nuevo valor
        v.put("ACTIVE", 1);

        // Se abre la BD para escritura
        SQLiteDatabase db = this.getWritableDatabase();

        // Se actualiza el valor del registro al nuevo.
        db.update("TASKS", v, "NAME=? AND USER=?", new String[]{task,userId});

        // Se cierra la BD
        db.close();
    }

    public int goodWork(String userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] args = new String[] {userId};

        Cursor cursor = db.rawQuery("SELECT * FROM TASKS " +
                "WHERE USER=? AND ACTIVE=0", args);

        System.out.println("Good work:" + String.valueOf(cursor.getCount()));
        return cursor.getCount();
    }
}
