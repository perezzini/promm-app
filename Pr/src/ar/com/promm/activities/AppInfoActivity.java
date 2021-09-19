package ar.com.promm.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import ar.com.promm.R;

public class AppInfoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);
    }

    public void goBack(View view) {
        AppInfoActivity.this.finish();
    }

    public void registerStore(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://prommapp.com/register")));
    }

    public void shareApp(View view) {
        String shareText="¡Descubre Promm! Ahora disponible en Google Play. Visita https://prommapp.com";        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }
}
