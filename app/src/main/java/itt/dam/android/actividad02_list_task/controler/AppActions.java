package itt.dam.android.actividad02_list_task.controler;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import itt.dam.android.actividad02_list_task.R;
import itt.dam.android.actividad02_list_task.db.DbControler;

public class AppActions {
    private DbControler dbControler;
    private ArrayAdapter<String> arrayAdapter;
    private ListView listViewTasks;
    private View v;



    public AppActions () {
        dbControler = new DbControler(v.getContext());
        listViewTasks = (ListView) v.findViewById(R.id.listTask);
    }

    private void updateUI() {
        if(dbControler.isEmptyDb()) {
            listViewTasks.setAdapter(null);
        } else {
            arrayAdapter = new ArrayAdapter<String>(v.getContext(), R.layout.item_todolist, R.id.taskTxt, dbControler.getTasks());
            listViewTasks.setAdapter(arrayAdapter);
        }
    }

    private void showDialog(String s) {
        // Custom Dialog
        // Se utiliza inflater para add el laytout del custom dialog al activity actual.
        LayoutInflater inflater = LayoutInflater.from(v.getContext());
        final View customDialogView = inflater.inflate(R.layout.custom_alert_dialog, null);

        // Se crea el instancia AlertDialog que se le pasa mediante setView el customdialog.
        final AlertDialog customDialog = new AlertDialog.Builder(v.getContext())
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
}
