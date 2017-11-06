package com.epriest.game.guildfantasy.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.guildfantasy.R;

/**
 * Created by User on 2017-10-20.
 */

public class DialogActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_user);
//        WindowManager.LayoutParams  layoutParams = new WindowManager.LayoutParams();
//        layoutParams.flags  = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
//        layoutParams.dimAmount  = 0.7f;

        TextView tv = (TextView)findViewById(R.id.text_popuptitle);
        tv.setText("유저네임?");

        this.setFinishOnTouchOutside(false);
        findViewById(R.id.button_ok).setOnClickListener(this);
        findViewById(R.id.button_no).setOnClickListener(this);


//        EditText et_name = (EditText)findViewById(R.id.editText_username);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button_ok:
                EditText et_name = (EditText)findViewById(R.id.editText_username);
                String name = et_name.getText().toString();
                if (name.length() < 3) {
                    Toast.makeText(this, "이름을 세글자 이상으로 적어주세요.", Toast.LENGTH_SHORT).show();
                    return;
                } else
                    ((ApplicationClass) getApplicationContext()).newName = name;

                break;
            case R.id.button_no:
                break;
        }
        this.finish();
    }
}
