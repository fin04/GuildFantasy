package com.epriest.game.guildfantasy.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.epriest.game.CanvasGL.graphics.CanvasUtil;
import com.epriest.game.CanvasGL.graphics.GLUtil;
import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.CanvasGL.util.Game;
import com.epriest.game.CanvasGL.util.Scene;
import com.epriest.game.guildfantasy.enty.ButtonEnty;

import static com.epriest.game.CanvasGL.graphics.CanvasUtil.drawClip;

/**
 * Created by darka on 2017-04-07.
 */

public class Scene_Party extends Scene{

    private Scene_Main sceneMain;
    private Game_Party gameParty;
    private Context context;
    private int canvasW, canvasH;

    private Bitmap bg;
    private Bitmap questPaper;
    private Bitmap mos_detail;

    public Scene_Party(Game_Party gameParty, Scene_Main sceneMain) {
        this.gameParty = gameParty;
        this.sceneMain = sceneMain;
    }

    @Override
    public void initScene(ApplicationClass appClass) {
        this.context = appClass.getBaseContext();
        canvasW = appClass.getGameCanvasWidth();
        canvasH = appClass.getGameCanvasHeight();

        bg = GLUtil.loadAssetsBitmap(context, "main/quest.png", null);

        mos_detail = GLUtil.loadAssetsBitmap(context, "mon/" + gameParty.gameMain.QuestEnty.pic, null);
        questPaper = GLUtil.loadAssetsBitmap(context, "main/guildpaper.png", null);

        gameParty.viewMember.initScene();
    }

    @Override
    public void recycleScene() {
        CanvasUtil.recycleBitmap(mos_detail);
        CanvasUtil.recycleBitmap(questPaper);
        gameParty.viewMember.recycleScene();
    }

    @Override
    public void drawLoading(Canvas mCanvas, Bitmap loading) {

    }

    @Override
    public void draw(Canvas mCanvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        mCanvas.drawBitmap(bg, 0, 0, null);

        if(gameParty.isViewMember)
            gameParty.viewMember.draw(mCanvas);
        else{
            drawQuestDetail(mCanvas, paint);
            drawPartyGroup(mCanvas, paint);
        }

        sceneMain.drawMain(mCanvas, paint, false);
    }

    private void drawPartyGroup(Canvas mCanvas, Paint paint) {
        for(int i=0; i<4; i++) {
            ButtonEnty btn = gameParty.PartyButtonList.get(i);
            int color = Color.argb(150, 180, 195, 145);
            if(btn.clickState == ButtonEnty.ButtonClickOn)
                color = Color.argb(150, 80, 95, 45);
            CanvasUtil.drawBox(mCanvas, paint, color, true, btn.x, btn.y, btn.w, btn.h);
            if(btn.iconImgNum > 0) {
                int imgNum = btn.iconImgNum;
                drawClip(gameParty.viewMember.char_01, mCanvas, null,
                        (imgNum % 10) * gameParty.viewMember.charW, (imgNum / 10) * gameParty.viewMember.charH,
                        gameParty.viewMember.charW, gameParty.viewMember.charH,
                        btn.x, btn.y);
            }
        }
    }

    private void drawQuestDetail(Canvas mCanvas, Paint paint) {

        CanvasUtil.drawBitmap(questPaper, mCanvas, null, 30, 30);
        int widthCenter = 30 + (questPaper.getWidth() / 2);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(35);
        CanvasUtil.drawString(mCanvas, gameParty.gameMain.QuestEnty.title, paint, widthCenter, 40);
        CanvasUtil.drawBitmap(mos_detail, mCanvas, null, widthCenter - mos_detail.getWidth() / 2, 80);
        paint.setTextSize(20);
        paint.setARGB(200, 255, 10, 10);
        CanvasUtil.drawString(mCanvas, gameParty.gameMain.QuestEnty.tip, paint, widthCenter, 90 + mos_detail.getHeight());
        paint.setTextSize(24);
        paint.setARGB(200, 40, 30, 10);
        CanvasUtil.drawString(mCanvas, gameParty.gameMain.QuestEnty.text, paint, widthCenter, 130 + mos_detail.getHeight());
        paint.setTextAlign(Paint.Align.LEFT);

    }
}
