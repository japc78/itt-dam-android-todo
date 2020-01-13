package itt.dam.android.actividad02_list_task;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.widget.Toolbar;

import itt.dam.android.actividad02_list_task.db.DbControler;

public class MainActivity extends AppCompatActivity {

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
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

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
        return super.onOptionsItemSelected(item);
    }

    private void updateUI() {
        if(dbControler.isEmptyDb()) {
            listViewTasks.setAdapter(null);
        } else {
            arrayAdapter = new ArrayAdapter<String>(this, R.layout.item_todolist, R.id.taskTxt, dbControler.getTasks());
            listViewTasks.setAdapter(arrayAdapter);
        }
    }
}
