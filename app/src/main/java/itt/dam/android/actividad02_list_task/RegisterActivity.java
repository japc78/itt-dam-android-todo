package itt.dam.android.actividad02_list_task;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import itt.dam.android.actividad02_list_task.controler.AppMessages;
import itt.dam.android.actividad02_list_task.db.DbControler;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private TextInputEditText user, pass, passRepeat;
    private DbControler dbControler;
    private AppMessages messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbControler = new DbControler(this);
        messages = new AppMessages(this);

        Button btnAcept = findViewById(R.id.btnAcceptRegister);
        Button btnCancel = findViewById(R.id.btnCancelRegister);

        // Eventos
        btnAcept.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case  R.id.btnAcceptRegister:
                this.user = findViewById(R.id.registerTextInputUser);
                this.pass = findViewById(R.id.registerTextInputPassWord);
                this.passRepeat = findViewById(R.id.registerTextInputPassWordRepeat);

                String user = this.user.getText().toString();
                String pass = this.pass.getText().toString();
                String passRepeat = this.passRepeat.getText().toString();

                System.out.println("Usuario: " + user);

                if (user.isEmpty()) {
                    this.user.setError("Complete el usuario");

                } else if (pass.isEmpty()) {
                    this.pass.setError("Complete la contraseña");

                } else if (dbControler.isUserExist(user)){
                    messages.customToast("El usuario ya existe");

                } else if (pass.equals(passRepeat)) {
                    dbControler.userRegister(user, pass);
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                } else {
                    messages.customToast("Las contraseñas deben de ser iguales");
                }

                break;

            case R.id.btnCancelRegister:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }

    }
}
