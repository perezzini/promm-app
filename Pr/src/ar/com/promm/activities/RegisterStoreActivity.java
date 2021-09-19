package ar.com.promm.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ar.com.promm.R;

public class RegisterStoreActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_store);
    }

    public void registerStore(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://prommapp.com/register")));
    }

    public void toNextActivity(View view) {
        Intent intent = new Intent(view.getContext(), OtraTabsActivity.class);
        startActivity(intent);
        finish();
    }
}
