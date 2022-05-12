package mason.example.jigneshjc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import mason.example.jigneshjc.utils.UtilMini;
import mason.photo.onvideo.R;


public class PopupActivity extends AppCompatActivity {

    UtilMini helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        helper = new UtilMini(this);
    }

    public void onBack(View v) {
        finish();
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    public void openMenu(View v) {
        finish();
    }

    public void rate(View v) {
        helper.rate();
        finish();
    }

    public void share(View v) {
        helper.share();
        finish();
    }

    public void privacy(View v) {
        startActivity(new Intent(this,
                PrivacyPolicyActivity.class));
        finish();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
