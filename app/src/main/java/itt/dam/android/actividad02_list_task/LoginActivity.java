package itt.dam.android.actividad02_list_task;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Se oculta la barra superior
        getSupportActionBar().hide();

        // TextView tit = findViewById(R.id.loginTextViewTit);

        // Se le aplica un letterspacing al titulo en el Login
        // tit.setLetterSpacing(0.3f);


    }
}
