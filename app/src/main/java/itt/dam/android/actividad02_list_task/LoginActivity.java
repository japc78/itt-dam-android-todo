package itt.dam.android.actividad02_list_task;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;;import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private TextInputEditText txtUser;
    private TextInputEditText txtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Se oculta la barra superior
//        getSupportActionBar().hide();

        Button btnLogin = findViewById(R.id.buttonLogin);
        Button btnRegister = findViewById(R.id.buttonRegister);
        // TextView tit = findViewById(R.id.loginTextViewTit);

        // Se le aplica un letterspacing al titulo en el Login
        // tit.setLetterSpacing(0.3f);


        // Eventos
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonRegister) {
            // Toast sin personalizar.
            //Toast.makeText(this "Funcionalidad no disponible", Toast.LENGTH_SHORT).show();

            toastMenssage("Funcionalidad no disponible");

            /*Snack Bar personalizado
            Snackbar snackbar = Snackbar.make(v, "Funcionalidad no disponible", Snackbar.LENGTH_LONG);
            View snackBarView = snackbar.getView();
            snackBarView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark, getResources().newTheme()));
            snackbar.show();
             */

        } else if  (v.getId() == R.id.buttonLogin) {
            Log.i("App", "Click en login");

            txtUser = findViewById(R.id.textInputUser);
            txtPass = findViewById(R.id.textInputPassWord);

            String user = txtUser.getText().toString();
            String pass = txtPass.getText().toString();

            if (user.isEmpty()) {
                txtUser.setError("Complete el usuario");
            } else if (pass.isEmpty()) {
                txtPass.setError("Complete la contraseña");
            } else if (user.equalsIgnoreCase("itt") && pass.equalsIgnoreCase("itt")){
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else if (!user.equalsIgnoreCase("itt")){
                //toastMenssage("Usuario incorrecto");
                txtUser.setError("Usuario incorrecto");
                txtUser.requestFocus();
            } else if (!pass.equalsIgnoreCase("itt")) {
                //toastMenssage("Contraseña incorrecta");
                txtPass.setError("Contraseña incorrecta");
                txtPass.requestFocus();
            }
        }
    }

    public void toastMenssage(String texto) {
        Toast toast = new Toast(this);
        LayoutInflater i = getLayoutInflater();
        View layout = i.inflate(R.layout.toast_msg_01, (ViewGroup) findViewById(R.id.toastLayout));
        TextView txtMsg = layout.findViewById(R.id.textViewToast);
        txtMsg.setText(texto);
        toast.setView(layout);
        toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,150);
        toast.show();
    }
}
