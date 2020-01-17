package itt.dam.android.actividad02_list_task.controler;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import itt.dam.android.actividad02_list_task.R;

public class AppMessages {
    private Context context;

    public AppMessages(Context context) {
        this.context = context;
    }

    /**
     * Metodo que muestra un mensaje toast personalizado, con fondo amarillo
     * e icono de información.
     *
     * @param text Del tipo String. El mensaje a mostrar.
     */

    public void customToast(String text) {
        LayoutInflater i = ((Activity) context).getLayoutInflater();
        View layout = i.inflate(R.layout.custom_toast, (ViewGroup) ((Activity) context).findViewById(R.id.toastLayout));
        TextView txtMsg = layout.findViewById(R.id.textViewToast);
        Toast toast = new Toast(context);
        txtMsg.setText(text);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,48);
        toast.show();
    }

    /**
     * Metodo que muestra una motivación al ir finalizando tareas
     *
     * @param text Del tipo String. Texto a mostrar en el mensaje.
     * @param img Del tipo int. Imagen, elemento drawable.
     */

    public void goodWork(String text, int img) {
        LayoutInflater i = ((Activity) context).getLayoutInflater();

        View layout = i.inflate(R.layout.custom_toast_good_work, (ViewGroup) ((Activity) context).findViewById(R.id.goodWorktoastLayout));
        TextView txtMsg = layout.findViewById(R.id.goodWorkTextViewToast);
        ImageView imgMsg = layout.findViewById(R.id.goodWorkImg);
        Toast toast = new Toast(context);

        imgMsg.setImageResource(img);
        txtMsg.setText(text);
        toast.setView(layout);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
}
