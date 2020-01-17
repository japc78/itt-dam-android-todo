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

        Button btnLogin = findViewById(R.id.btnRegisterLogin);
        Button btnRegister = findViewById(R.id.btnAcceptLogin);

        // Eventos
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnRegisterLogin) {
            startActivity(new Intent(this, RegisterActivity.class));

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
        }
    }
}
