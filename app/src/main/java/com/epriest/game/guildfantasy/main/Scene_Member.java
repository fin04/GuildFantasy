package com.epriest.game.guildfantasy.main;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.epriest.game.CanvasGL.graphics.CanvasUtil;
import com.epriest.game.CanvasGL.graphics.GLUtil;
import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.CanvasGL.util.Scene;
import com.epriest.game.guildfantasy.main.enty.ImageEnty;

import static com.epriest.game.CanvasGL.graphics.CanvasUtil.*;

/**
 * Created by darka on 2017-03-26.
 */

public class Scene_Member extends Scene {

    private Game_Member gameMember;
    private Bitmap bg;

    public Scene_Member(Game_Member gameMember, Scene_Main sceneMain) {
        this.gameMember = gameMember;
    }

    @Override
    public void initScene(ApplicationClass appClass) {
        bg = GLUtil.loadAssetsBitmap(appClass, "main/member.jpg", null);
        gameMember.viewMember.initScene();
    }

    @Override
    public void recycleScene() {
        recycleBitmap(bg);
//        recycleBitmap(char_01);
//        recycleBitmap(classesMark);
        gameMember.viewMember.recycleScene();
    }

    @Override
    public void drawLoading(Canvas mCanvas, Bitmap loading) {

    }

    @Override
    public void draw(Canvas mCanvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
//        mCanvas.drawColor(Color.BLACK);
        CanvasUtil.drawBgBitmap(bg, mCanvas);

        gameMember.viewMember.draw(mCanvas);
        gameMember.gameMain.drawMain(mCanvas, false);

    }


}
