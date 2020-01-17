package itt.dam.android.actividad02_list_task;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import itt.dam.android.actividad02_list_task.controler.AppMessages;
import itt.dam.android.actividad02_list_task.db.DbControler;

public class HistoryActivity extends AppCompatActivity {
    private DbControler dbControler;
    private ArrayAdapter<String> arrayAdapter;
    private ListView listViewTasks;
    private LayoutInflater inflater;
    private View customDialogView;
    private AlertDialog customDialog;
    private TextView customDialogTitle;
    private EditText customDialogTxt;
    private Bundle data;
    private String userId;
    private AppMessages messages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        dbControler = new DbControler(this);
        listViewTasks = (ListView) findViewById(R.id.historyListTask);
        messages = new AppMessages(this);
        data = this.getIntent().getExtras();
        userId = data.getString("userId");

        // Custom Dialog
        // Se utiliza inflater para add el laytout del custom dialog al activity actual.
        inflater = LayoutInflater.from(this);
        customDialogView = inflater.inflate(R.layout.custom_dialog_delete_task, null);

        // Se crea el instancia AlertDialog que se le pasa mediante setView el customdialog.
        customDialog = new AlertDialog.Builder(this)
                .setView(customDialogView)
                .create();
        customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Se añade el toolbar y menu personalizado.
        Toolbar toolbar = findViewById(R.id.historyCustomToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView toolbarTitle = toolbar.findViewById(R.id.toolbarTitle);
        ImageView logoBack = toolbar.findViewById(R.id.toolbarLogo);
        logoBack.setImageResource(R.drawable.ic_logo_anagrama_back);
        toolbarTitle.setText("HISTÓRICO");

        logoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backMain();
            }
        });

        toolbar.inflateMenu(R.menu.menu_bar);

        updateUI();
    }

    // Para añadir el menu al toolbar.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar, menu);
        menu.removeItem(R.id.menuAddTask);
        menu.removeItem(R.id.menuHistory);
        return super.onCreateOptionsMenu(menu);

    }

    // Para añadirle funcionalidad a los items del menu personalizado.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Si tiene mas de una opcion en el menu recoge cual es la selecionada.

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        showDialog("¿Deseas eliminar todas la tareas?");

        customDialog.findViewById(R.id.btnDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbControler.deleteTask(userId);
                updateUI();
                customDialog.dismiss();
            }
        });

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            backMain();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private void backMain() {
        startActivity(new Intent(this, MainActivity.class)
                .putExtra("userId", userId));
    }

    /**
     * Muestra el cuadro de dialogo personalizado
     *
     * @param dialogTitle Se le pasa el titulo del cuadro de dialogo.
     */
    private void showDialog(String dialogTitle) {
        customDialog.show();
        customDialogTitle = customDialog.findViewById(R.id.dialogDeleteTitle);
        customDialogTitle.setText(dialogTitle);

        // Acciones
        customDialog.findViewById(R.id.btnCancelDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });

    }

    private void updateUI() {
        if(dbControler.isEmptyDb(userId,0)) {
            listViewTasks.setAdapter(null);
        } else {
            listViewTasks.setAdapter(null);
            arrayAdapter = new ArrayAdapter<String>(this, R.layout.item_historylist, R.id.historyTaskTxt, dbControler.getTasks(userId,0));
            listViewTasks.setAdapter(arrayAdapter);
        }
    }

    public void deleteTask(View view) {
        View parentButton = (View) view.getParent();
        final TextView txtTask = parentButton.findViewById(R.id.historyTaskTxt);

        showDialog("¿Deseas elimina la tarea?");

        customDialog.findViewById(R.id.btnDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbControler.deleteTask(txtTask.getText().toString(), userId);
                updateUI();
                customDialog.dismiss();
            }

        });
    }

    public void undoTask(View view){
        View parentButton = (View) view.getParent();
        final TextView txtTask = parentButton.findViewById(R.id.historyTaskTxt);

        showDialog("¿Deseas activar esta tarea?");

        final Button btn = customDialog.findViewById(R.id.btnDelete);
        btn.setText("Activar");

        customDialog.findViewById(R.id.btnDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbControler.undoTaks(txtTask.getText().toString(), userId);
                updateUI();
                customDialog.dismiss();
            }

        });

    }
}
