package itt.dam.android.actividad02_list_task;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import java.util.Random;

import itt.dam.android.actividad02_list_task.controler.AppMessages;
import itt.dam.android.actividad02_list_task.db.DbControler;

public class MainActivity extends AppCompatActivity  {

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
        setContentView(R.layout.activity_main);

        dbControler = new DbControler(this);
        listViewTasks = (ListView) findViewById(R.id.listTask);

        messages = new AppMessages(this);
        data = this.getIntent().getExtras();
        userId = data.getString("userId");

        System.out.println("MainActivity: userId-" + userId);

        // Custom Dialog
        // Se utiliza inflater para add el laytout del custom dialog al activity actual.
        inflater = LayoutInflater.from(this);
        customDialogView = inflater.inflate(R.layout.custom_dialog_add_task, null);

        // Se crea el instancia AlertDialog que se le pasa mediante setView el customdialog.
        customDialog = new AlertDialog.Builder(this)
                .setView(customDialogView)
                .create();
        customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Se añade el toolbar y menu personalizado.
        Toolbar toolbar = findViewById(R.id.customToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView toolbarTitle = toolbar.findViewById(R.id.toolbarTitle);
        toolbarTitle.setText("TAREAS");
        toolbar.inflateMenu(R.menu.menu_bar);
        updateUI();
    }

    // Para añadir el menu al toolbar.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar, menu);
        menu.removeItem(R.id.menuTrash);
        return super.onCreateOptionsMenu(menu);
    }

    // Para añadirle funcionalidad a los items del menu personalizado.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Si tiene mas de una opcion no visible en el menu recoge cual es la selecionada.
        //AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.menuHistory :
                startActivity(new Intent(this, HistoryActivity.class)
                        .putExtra("userId", userId));
                break;

            case R.id.menuAddTask:
                showDialog("Nueva tarea");
                customDialog.findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String task = customDialogTxt.getText().toString();

                        if(!task.isEmpty()) {
                            if (!dbControler.isTaskExist(task, userId)) {
                                dbControler.addTask(task, userId);
                                customDialogTxt.setText("");
                                updateUI();
                                customDialog.dismiss();
                                messages.customToast("Tarea creada");

                            } else if(dbControler.isTaskExist(task, userId,0)) {
                                dbControler.udpateTask(task,userId);
                                customDialogTxt.setText("");
                                updateUI();
                                customDialog.dismiss();
                                messages.customToast("Tarea creada");

                            } else if (dbControler.isTaskExist(task, userId,1)) {
                                messages.customToast("Tarea duplicada");
                            }
                        } else {
                            customDialogTxt.setError("Debes de escribir algo");
                        }

                    }
                });

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // Para al presionar en el boton atras/volver salga de la aplicación.
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    /**
     * Muestra el cuadro de dialogo personalizado.
     *
     * @param dialogTitle Se le pasa el titulo del cuadro de dialogo.
     */
    private void showDialog(String dialogTitle) {
        customDialog.show();
        customDialogTitle = customDialog.findViewById(R.id.dialogTitle);
        customDialogTitle.setText(dialogTitle);
        // Acciones
        customDialogTxt = (EditText)customDialog.findViewById(R.id.dialogBoxTxt);

        customDialog.findViewById(R.id.btnCanel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialogTxt.setText("");
                customDialog.dismiss();
            }
        });

    }

    /**
     * Actualiza la lista de tareas en el ListView.
     */
    private void updateUI() {
        if(dbControler.isEmptyDb(userId,1)) {
            listViewTasks.setAdapter(null);
        } else {
            arrayAdapter = new ArrayAdapter<String>(this, R.layout.item_todolist, R.id.taskTxt, dbControler.getTasks(userId,1));
            listViewTasks.setAdapter(arrayAdapter);
        }
    }

    /**
     * Metodo para finalizar una tarea y pasar al historico. Se implementa sobre
     * el evento onclick del item en el xml.
     *
     * Se crea dos arrays para mostrar mensajes de animo aleatorios.
     * Uno para los mensajes aleatorios y otro para las imagenes.
     *
     * @param view
     */
    public void disableTask(View view) {
        View parentButton = (View) view.getParent();
        TextView txtTask = parentButton.findViewById(R.id.taskTxt);
        //dbControler.disableTask(txtTask.getText().toString(), userId);
        dbControler.disableTask(txtTask.getText().toString(), userId);
        updateUI();

        System.out.println("GOODWORK MOD: " + dbControler.goodWork(userId));

        if ((dbControler.goodWork(userId)%3 == 0) && (dbControler.goodWork(userId) > 1)) {
            String[] textgoodWork = new String[6];
            textgoodWork[0] = "¡¡Ouhhh yeah!!";
            textgoodWork[1] = "¡¡Magníficoo!!";
            textgoodWork[2] = "¡¡Estupendeibolsss!!";
            textgoodWork[3] = "¡¡Good Work!!";
            textgoodWork[4] = "¡¡A topeeEEE!!";
            textgoodWork[5] = "¡¡Genial, sigue así!!";

            int[] img = new int[9];
            img[0] = R.drawable.ic_img_goodwork_01;
            img[1] = R.drawable.ic_img_goodwork_02;
            img[2] = R.drawable.ic_img_goodwork_03;
            img[3] = R.drawable.ic_img_goodwork_04;
            img[4] = R.drawable.ic_img_goodwork_05;
            img[5] = R.drawable.ic_img_goodwork_06;
            img[6] = R.drawable.ic_img_goodwork_07;
            img[7] = R.drawable.ic_img_goodwork_08;
            img[8] = R.drawable.ic_img_goodwork_09;

            messages.goodWork(textgoodWork[ramdomNum(5)], img[ramdomNum(9)]);
        } else {
            messages.customToast("Tarea realizada");
        }
    }

    /**
     * Metogo privado para genera numeros enteros aletatorios entre dos numeros.
     *
     * @param numbers
     * @return
     */
    private int ramdomNum(int numbers) {
        int num = new Random().nextInt(numbers);

        System.out.println("Num: " + num);
        return num;
    }

    /**
     * Método para editar una tarea. Se implementa sobre el evento onclick del item en el xml.
     * @param view
     */
    public void editTask(View view) {
        View parentButton = (View) view.getParent();
        final TextView txtTask = parentButton.findViewById(R.id.taskTxt);
        showDialog("Editar tarea");
        customDialogTxt.setText(txtTask.getText().toString());

        customDialog.findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!customDialogTxt.getText().toString().isEmpty()) {
                    dbControler.udpateTask(txtTask.getText().toString(), customDialogTxt.getText().toString(),userId);
                    updateUI();
                    customDialog.dismiss();
                    messages.customToast("Tarea editada");

                } else {
                    customDialogTxt.setError("Debes de escribir algo");
                }

            }
        });

    }

}
