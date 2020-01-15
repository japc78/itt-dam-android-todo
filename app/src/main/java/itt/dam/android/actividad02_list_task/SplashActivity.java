package itt.dam.android.actividad02_list_task;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity implements Animation.AnimationListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Se oculta la barra superior
//        getSupportActionBar().hide();

        // Animacion Logo
        ImageView logo = (ImageView) findViewById(R.id.registerTit);
        Animation animLogo = AnimationUtils.loadAnimation(this, R.anim.splash_logo);
        logo.startAnimation(animLogo);
        animLogo.setAnimationListener(this);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        // Forma implementada
        startActivity(new Intent(this, LoginActivity.class));

        // Se aplica la transicion entre ambas activities.
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        // Se finaliza la ejecucion del Splash
        finish();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
