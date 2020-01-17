package itt.dam.android.actividad02_list_task;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
;
import com.google.android.material.textfield.TextInputEditText;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import itt.dam.android.actividad02_list_task.controler.AppMessages;
import itt.dam.android.actividad02_list_task.db.DbControler;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private TextInputEditText txtUser;
    private TextInputEditText txtPass;
    private DbControler dbControler;
    private AppMessages messages;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbControler = new DbControler(this);
        messages = new AppMessages(this);

        // Se oculta la barra superior
//        getSupportActionBar().hide();

        Button btnLogin = findViewById(R.id.btnRegisterLogin);
        Button btnRegister = findViewById(R.id.btnAcceptLogin);
        // TextView tit = findViewById(R.id.loginTextViewTit);

        // Se le aplica un letterspacing al titulo en el Login
        // tit.setLetterSpacing(0.3f);


        // Eventos
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnRegisterLogin) {
            // Toast sin personalizar.
            //Toast.makeText(this "Funcionalidad no disponible", Toast.LENGTH_SHORT).show();

            // Toast personalizado sin la funcionalidad del Registro.
            //customToast("Funcionalidad no disponible");

            startActivity(new Intent(this, RegisterActivity.class));

            /*Snack Bar personalizado
            Snackbar snackbar = Snackbar.make(v, "Funcionalidad no disponible", Snackbar.LENGTH_LONG);
            View snackBarView = snackbar.getView();
            snackBarView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark, getResources().newTheme()));
            snackbar.show();
             */

        } else if  (v.getId() == R.id.btnAcceptLogin) {
            Log.i("App", "Click en userLogin");

            txtUser = findViewById(R.id.loginTextInputUser);
            txtPass = findViewById(R.id.loginTextInputPassWord);

            String user = txtUser.getText().toString();
            String pass = txtPass.getText().toString();

            if (user.isEmpty()) {
                txtUser.setError("Complete el usuario");
            } else if (pass.isEmpty()) {
                txtPass.setError("Complete la contraseña");
            } else if (dbControler.userLogin(user, pass) != null){
                userId = dbControler.userLogin(user, pass);
                startActivity(new Intent(this, MainActivity.class)
                        .putExtra("userId", userId));
                finish();
            } else {
                messages.customToast("Usuario y/o contraseña incorrecta");
            }

            /*
            // TODO Sin password ni userId
            startActivity(new Intent(this, MainActivity.class));
            finish();
            */
        }
    }

/*
    public void customToast(String texto) {
        Toast toast = new Toast(this);
        LayoutInflater i = getLayoutInflater();
        View layout = i.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.toastLayout));
        TextView txtMsg = layout.findViewById(R.id.textViewToast);
        txtMsg.setText(texto);
        toast.setView(layout);
        toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,150);
        toast.show();
    }
*/


    private final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
