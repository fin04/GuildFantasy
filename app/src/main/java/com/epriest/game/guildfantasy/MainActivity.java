package com.epriest.game.guildfantasy;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.CanvasGL.graphics.GLActivity;
import com.epriest.game.guildfantasy.main.play.GameDbAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends GLActivity {

    private MainGLView mGLView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
    }

    @Override
    public void baseCreate() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            display.getSize(size);
        } else {
            display.getRealSize(size);
        }

        ApplicationClass applicationClass = (ApplicationClass) getApplicationContext();
        applicationClass.setGameCanvasOrientation(applicationClass.GAMECANVAS_ORIENTATION_PORTRAIT);
        applicationClass.setDeviceScreenWidth(size.x);
        applicationClass.setDeviceScreenHeight(size.y);

        setContentView(R.layout.activity_main);

        if (hasGLES20()) {
            mGLView = new MainGLView(this);
            FrameLayout fl = (FrameLayout) findViewById(R.id.surfaceFrame);
            fl.addView(mGLView);
        } else {
            finish();
        }

//        AdView adView = new AdView(this);
        MobileAds.initialize(getApplicationContext(), getString(R.string.banner_ad_unit_id));
        AdView adView = (AdView) findViewById(R.id.adView);
//        adView.setAdUnitId(getString(R.string.banner_ad_unit_id));
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)        // All emulators
                .addTestDevice("5A92EEA5FFFE4715F8EBD04418F47C10")
                .addTestDevice("C6BBAF77674324608544CB6B78DF28ED")
                .build();
        adView.loadAd(adRequest);

/*        RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        adParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

        FrameLayout layout = (FrameLayout) findViewById(R.id.surfaceFrame);
        layout.addView(mGLView);
        layout.addView(adView, adParams);*/
    }

    @Override
    public void baseResume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void basePause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void basOnBackPressed() {
        mGLView.onBackPressed(this);

    }

}
