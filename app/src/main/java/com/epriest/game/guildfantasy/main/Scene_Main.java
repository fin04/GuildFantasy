package com.epriest.game.guildfantasy.main;

import android.graphics.Bitmap;

import com.epriest.game.CanvasGL.graphics.GLUtil;

/**
 * Created by darka on 2017-03-26.
 */

public class Scene_Main {

    /*private Game_Main gameMain;

    public Bitmap img_mainBtn;
    private Bitmap status_bar;
    private Bitmap alertBox;
    private Bitmap alertCard;

    public Scene_Main(Game_Main gameMain){
        this.gameMain = gameMain;
    }

    public void initScene() {
        img_mainBtn = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/main_btn.png", null);
        status_bar = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/statusbar_tile.png", null);
        alertBox = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/alertbox.png", null);
        alertCard = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/alertcard.png", null);
        gameMain.appClass.isSceneInit = false;
    }

    private ArrayList<ButtonEnty> setMenuIcon(int canvasW, int canvasH) {
        ArrayList<ButtonEnty> menuButtonList = new ArrayList<>();
        switch(gameMain.appClass.gameFlag) {
            default:
                for(int i=0; i< gameMain.menuIconName.length-2; i++) {
                    ButtonEnty mBtn = new ButtonEnty();
                    mBtn.num = i;
                    mBtn.name = gameMain.menuIconName[i];
                    mBtn.iconImgNum = gameMain.menuIconNum[i];
                    mBtn.clipW = btnW;
                    mBtn.clipH = btnH;
                    mBtn.clipX = ((mBtn.iconImgNum-1)%4)*mBtn.clipW;
                    mBtn.clipY = ((mBtn.iconImgNum-1)/4)*mBtn.clipH;
                    mBtn.drawX = canvasW - 110 - (4-i)*(mBtn.clipW+10);
                    mBtn.drawY = canvasH - mBtn.clipH - 10;
                    menuButtonList.add(mBtn);
                }
                break;

            case Game_Main.GAME_PARTY:
                ButtonEnty mBtn1 = new ButtonEnty();
                mBtn1.num = 0;
                mBtn1.name = "start";
                mBtn1.clipW = 117;
                mBtn1.clipH = 117;
                mBtn1.clipX = 1;
                mBtn1.clipY = 172;
                mBtn1.drawX = canvasW- mBtn1.clipW-20;
                mBtn1.drawY = canvasH-mBtn1.clipH-10;
                menuButtonList.add(mBtn1);

                ButtonEnty mBtn2 = new ButtonEnty();
                mBtn2.num = 1;
                mBtn2.name = "back";
                mBtn2.clipW = 90;
                mBtn2.clipH = 90;
                mBtn2.clipX = 230;
                mBtn2.clipY = 172;
                mBtn2.drawX = canvasW- mBtn1.clipW-20;
                mBtn2.drawY = 90;
                menuButtonList.add(mBtn2);
                break;
        }

        return  menuButtonList;
    }

    public void drawMain(Canvas mCanvas, Paint paint, boolean viewMenuButton){
        //menu button
        if(viewMenuButton)
            drawMenuButton(mCanvas, paint);

        // status bar
        drawStatusBar(mCanvas, paint);
    }

    public void drawStatusBar(Canvas mCanvas, Paint paint) {
        CanvasUtil.drawBitmap(status_bar, mCanvas, null, 0, 0);
        paint.setColor(Color.argb(255, 50, 50, 50));
        paint.setTextSize(25);
        CanvasUtil.drawString(mCanvas, gameMain.playerEnty.Name, paint, 30, 3);
        CanvasUtil.drawString(mCanvas, "Level "+ gameMain.playerEnty.LEVEL, paint, 150, 3);
        CanvasUtil.drawString(mCanvas, "quest "+ gameMain.playerEnty.QUESTLIST.size(), paint, 300, 3);
        CanvasUtil.drawString(mCanvas, "party "+ gameMain.playerEnty.PARTYLIST.size(), paint, 450, 3);
        CanvasUtil.drawString(mCanvas, "member "+ gameMain.playerEnty.MEMBERLIST.size(), paint, 600, 3);
    }

    private void drawPartyMenuButton(Canvas mCanvas, Paint paint) {
        for(ButtonEnty mBtn : gameMain.menuButtonList){
            int clipY = mBtn.clipY;
            if(mBtn.clickState == ButtonEnty.ButtonClickOn){
                clipY += mBtn.clipH;
            }
            CanvasUtil.drawClip(menu_icon, mCanvas, null, mBtn.clipX, clipY,
                    mBtn.clipW, mBtn.clipH, mBtn.drawX, mBtn.drawY);
//            CanvasUtil.drawClip(menu_icon, mCanvas, null, (iconNum%5)*mBtn.w, (iconNum/5)*mBtn.h,
//                    mBtn.w, mBtn.h, mBtn.x+(btnArea-mBtn.w)/2, mBtn.y);

        }
    }

    public void drawMenuButton(Canvas mCanvas, Paint paint) {
//        int btnArea = (canvasH - statusBarH)/ gameMain.menuButtonList.size();
        for(ButtonEnty mBtn : gameMain.menuButtonList){
            int clipY = mBtn.clipY;
            if(mBtn.clickState == ButtonEnty.ButtonClickOn){
                clipY += mBtn.clipH;
            }
            CanvasUtil.drawClip(menu_icon, mCanvas, null, mBtn.clipX, clipY,
                    mBtn.clipW, mBtn.clipH, mBtn.drawX, mBtn.drawY);
//            CanvasUtil.drawClip(menu_icon, mCanvas, null, (iconNum%5)*mBtn.w, (iconNum/5)*mBtn.h,
//                    mBtn.w, mBtn.h, mBtn.x+(btnArea-mBtn.w)/2, mBtn.y);

        }
    }*/

}
