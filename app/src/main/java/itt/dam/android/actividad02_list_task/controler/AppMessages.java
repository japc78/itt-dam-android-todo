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

    public void customToast(String text) {
        LayoutInflater i = ((Activity) context).getLayoutInflater();

        View layout = i.inflate(R.layout.custom_toast, (ViewGroup) ((Activity) context).findViewById(R.id.toastLayout));
        TextView txtMsg = layout.findViewById(R.id.textViewToast);
        Toast toast = new Toast(context);
        txtMsg.setText(text);
        toast.setView(layout);
        toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,150);
        toast.show();
    }

    public void goodWork() {
        LayoutInflater i = ((Activity) context).getLayoutInflater();

        View layout = i.inflate(R.layout.custom_toast_good_work, (ViewGroup) ((Activity) context).findViewById(R.id.toastLayout));
        TextView txtMsg = layout.findViewById(R.id.textViewToast);
        Toast toast = new Toast(context);
        toast.setView(layout);
        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL,0,150);
        toast.show();
    }

    public void goodWork(String text) {
        LayoutInflater i = ((Activity) context).getLayoutInflater();

        View layout = i.inflate(R.layout.custom_toast_good_work, (ViewGroup) ((Activity) context).findViewById(R.id.toastLayout));
        TextView txtMsg = layout.findViewById(R.id.textViewToast);
        Toast toast = new Toast(context);
        txtMsg.setText(text);
        toast.setView(layout);
        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL,0,150);
        toast.show();
    }

    public void goodWork(String text, int img) {
        LayoutInflater i = ((Activity) context).getLayoutInflater();

        View layout = i.inflate(R.layout.custom_toast_good_work, (ViewGroup) ((Activity) context).findViewById(R.id.goodWorktoastLayout));
        TextView txtMsg = layout.findViewById(R.id.goodWorkTextViewToast);
        ImageView imgMsg = layout.findViewById(R.id.goodWorkImg);
        Toast toast = new Toast(context);

        imgMsg.setImageResource(img);
        txtMsg.setText(text);
        toast.setView(layout);
        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL,0,150);
        toast.show();
    }
}
