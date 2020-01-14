package itt.dam.android.actividad02_list_task;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import itt.dam.android.actividad02_list_task.db.DbControler;

public class MainActivity extends AppCompatActivity  {

    private DbControler dbControler;
    private ArrayAdapter<String> arrayAdapter;
    private ListView listViewTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbControler = new DbControler(this);
        listViewTasks = (ListView) findViewById(R.id.listTask);

        // Se añade el toolbar y menu personalizado.
        Toolbar toolbar = findViewById(R.id.customToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.inflateMenu(R.menu.menu_bar);

        updateUI();
    }

    // Para añadir el menu al toolbar.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Para añadirle funcionalidad a los items del menu personalizado.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Si tiene mas de una opcion en el menu recoge cual es la selecionada.
        // AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        //Default Dialog
        /*
        final EditText inputText= new EditText(this);
        AlertDialog writeTask = new AlertDialog.Builder(this)
                .setTitle("Nueva tarea")
                .setView(inputText)
                .setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbControler.addTask(inputText.getText().toString());
                        updateUI();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .create();
        writeTask.show();
        */

        showDialog("Nueva tarea");
        return super.onOptionsItemSelected(item);
    }

    private void showDialog(String s) {
        // Custom Dialog
        // Se utiliza inflater para add el laytout del custom dialog al activity actual.
        LayoutInflater inflater = LayoutInflater.from(this);
        final View customDialogView = inflater.inflate(R.layout.custom_alert_dialog, null);

        // Se crea el instancia AlertDialog que se le pasa mediante setView el customdialog.
        final AlertDialog customDialog = new AlertDialog.Builder(this)
                .setView(customDialogView)
                .create();
        customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customDialog.show();

        TextView title = customDialog.findViewById(R.id.dialogTitle);
        title.setText(s);

        // Acciones
        final EditText boxTxt = (EditText)customDialog.findViewById(R.id.dialogBoxTxt);

        customDialog.findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!boxTxt.getText().toString().isEmpty()) {
                    dbControler.addTask(boxTxt.getText().toString());
                    updateUI();
                    customDialog.dismiss();
                } else {
                    boxTxt.setError("Debes de escribir algo");
                }

            }
        });

        customDialog.findViewById(R.id.btnCanel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });
    }

    private void updateUI() {
        if(dbControler.isEmptyDb()) {
            listViewTasks.setAdapter(null);
        } else {
            arrayAdapter = new ArrayAdapter<String>(this, R.layout.item_todolist, R.id.taskTxt, dbControler.getTasks());
            listViewTasks.setAdapter(arrayAdapter);
        }
    }

    public void deleteTask(View view) {
        View parentButton = (View) view.getParent();
        TextView txtTask = parentButton.findViewById(R.id.taskTxt);
        dbControler.deleteTask(txtTask.getText().toString());
        updateUI();
    }

    public void updateTask(View view) {
        View parentButton = (View) view.getParent();
        TextView txtTask = parentButton.findViewById(R.id.taskTxt);
        dbControler.udpateTask(txtTask.getText().toString());
        updateUI();
    }

}
