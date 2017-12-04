package com.epriest.game.guildfantasy.main;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.epriest.game.CanvasGL.graphics.CanvasUtil;
import com.epriest.game.CanvasGL.graphics.GLUtil;
import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.CanvasGL.util.Scene;
import com.epriest.game.guildfantasy.main.enty.ImageEnty;
import com.epriest.game.guildfantasy.main.enty.MemberEnty;

import static com.epriest.game.CanvasGL.graphics.CanvasUtil.*;

/**
 * Created by darka on 2017-03-26.
 */

public class Scene_Member extends Scene {

    private Game_Member gameMember;


    public Scene_Member(Game_Member gameMember, Scene_Main sceneMain) {
        this.gameMember = gameMember;
    }

    @Override
    public void initScene(ApplicationClass appClass) {
    }

    @Override
    public void recycleScene() {
        recycleBitmap(gameMember.bg);
        recycleBitmap(gameMember.img_memberSheet);
        recycleBitmap(gameMember.img_membercard);
        for (int i = 0; i < gameMember.img_member.size(); i++) {
            recycleBitmap(gameMember.img_member.get(i));
        }
//        recycleBitmap(classesMark);
//        gameMember.viewMember.recycleScene();
    }

    @Override
    public void drawLoading(Canvas mCanvas, Bitmap loading) {

    }

    @Override
    public void draw(Canvas mCanvas) {
        CanvasUtil.drawBitmap(gameMember.bg, mCanvas, 0, 0);

        if (gameMember.img_member.size() == 0)
            return;

        int partyNum = gameMember.gameMain.getSelectPartyNum();
        CanvasUtil.drawString(mCanvas, Integer.toString(partyNum), 20,
                Color.argb(255,255,255,255), Paint.Align.LEFT, 200, 50);

        int chrImgX = (200 - gameMember.img_member.get(0).getWidth()) / 2;
        int chrImgY = 30;// + (180 - gameMember.img_member.get(0).getHeight()) / 2;
        int cardTextBoxY = gameMember.cardH - gameMember.cardTextBoxH - 15;

        for (int i = 0; i < gameMember.memberList.size(); i++) {
            int cx = 30 + i % gameMember.cardRowNum * (gameMember.cardW + 10);
            int cy = 300 + i / gameMember.cardRowNum * (gameMember.cardH + 30);

            drawMemberCard(mCanvas, i, cx, cy, chrImgX, chrImgY, cardTextBoxY);
        }
    }

    private void drawMemberCard(Canvas mCanvas, int i, int cx, int cy, int chrImgX, int chrImgY, int cardTextBoxY) {
        MemberEnty enty = gameMember.memberList.get(i);

        //draw cardBG
        CanvasUtil.drawClip(gameMember.img_membercard, mCanvas, 0, 0,
                gameMember.cardW, gameMember.cardH, cx, cy);
        //draw Character
        CanvasUtil.drawClip(gameMember.img_member.get(i), mCanvas, (gameMember.img_member.get(i).getWidth()-gameMember.cardW)/2, 80,
                gameMember.cardW-10, gameMember.cardH-10,  cx+5, cy+5);
        //draw cardNameBG
        CanvasUtil.drawClip(gameMember.img_membercard, mCanvas, 176, 283,
                127, 26, cx+(gameMember.cardW-127)/2, cy+10 );
        //draw cardTextBox
        CanvasUtil.drawClip(gameMember.img_membercard, mCanvas, 35, 283,
                gameMember.cardTextBoxW, gameMember.cardTextBoxH, cx+35, cy+cardTextBoxY );

        CanvasUtil.drawString(mCanvas, enty.name, 20, Color.WHITE, Paint.Align.CENTER, cx + gameMember.cardW / 2, cy + 7);
        CanvasUtil.drawString(mCanvas, "LV." + Integer.toString(enty.status.LEVEL), 20, Color.DKGRAY, Paint.Align.LEFT, cx + 15, cy + 3);

//        int cardY = gameMember.img_title_bg.getHeight()+300+30;
//        for (ButtonEnty mBtn : gameMember.CardButtonList) {
//            CanvasUtil.drawClip(gameMember.img_membercard, mCanvas, mBtn.clipX, mBtn.clipY,
//                    mBtn.clipW, mBtn.clipH, mBtn.drawX, mBtn.drawY);
//
//            clipY = 39;
//            if (mBtn.clickState == ButtonEnty.ButtonClickOn) {
//                clipY += 47;
//            }
//            CanvasUtil.drawClip(gameMember.img_membercard, mCanvas, 163, clipY,
//                    47, 47, mBtn.drawX+(163-47)/2,  cardY+(253-47-25)/2);
//        }
    }

    private void drawMemberSheet(Canvas mCanvas) {
//        CanvasUtil.drawBitmap(gameMember.img_memberSheet, mCanvas, cx, cy);
//        CanvasUtil.drawBitmap(gameMember.img_member.get(i), mCanvas, cx+chrImgX, cy+chrImgY);
//
//        CanvasUtil.drawString(mCanvas, enty.name, 25, Color.WHITE, Paint.Align.CENTER, cx+gameMember.sheetW/2, cy+7);
//        CanvasUtil.drawString(mCanvas, "LV."+Integer.toString(enty.status.LEVEL), 30, Color.YELLOW, Paint.Align.LEFT, cx+15, cy+3);
//
//        CanvasUtil.drawString(mCanvas, enty.race, 20, Color.WHITE, Paint.Align.LEFT, cx+215, cy+60);
//        CanvasUtil.drawString(mCanvas, enty._class, 20, Color.WHITE, Paint.Align.LEFT, cx+215, cy+100);
//        CanvasUtil.drawString(mCanvas, enty.sex, 20, Color.WHITE, Paint.Align.LEFT, cx+215, cy+140);
//        CanvasUtil.drawString(mCanvas, "age", 20, Color.WHITE, Paint.Align.LEFT, cx+215, cy+180);
//        CanvasUtil.drawString(mCanvas, enty.age, 20, Color.WHITE, Paint.Align.LEFT, cx+295, cy+180);
//        CanvasUtil.drawString(mCanvas, "exp", 20, Color.WHITE, Paint.Align.LEFT, cx+215, cy+220);
//        CanvasUtil.drawString(mCanvas, Integer.toString(enty.status.EXP), 20, Color.WHITE, Paint.Align.LEFT, cx+295, cy+220);
    }


}
