package com.epriest.game.guildfantasy;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.FrameLayout;
import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.CanvasGL.graphics.GLActivity;

public class MainActivity extends GLActivity {

    MainGLView mGLView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
    }

    @Override
    public void baseCreate() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        ApplicationClass applicationClass = (ApplicationClass) getApplicationContext();
        applicationClass.setGamecanvasOrientation(ApplicationClass.GAMECANVAS_ORIENTATION_LANDSCAPE);
//        applicationClass.setGameCanvasWidth(ApplicationClass.GAMECANVAS_WIDTH);
//        applicationClass.setGameCanvasHeight(ApplicationClass.GAMECANVAS_HEIGHT);
//		applicationClass.setScreenWidth(displayMetrics.widthPixels);
//		applicationClass.setScreenHeight(displayMetrics.heightPixels);

        setContentView(R.layout.surface_main);

        if (hasGLES20()) {
            mGLView = new MainGLView(this);
            FrameLayout fl = (FrameLayout) findViewById(R.id.surfaceFrame);
            fl.addView(mGLView);
        } else {
            finish();
        }
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
