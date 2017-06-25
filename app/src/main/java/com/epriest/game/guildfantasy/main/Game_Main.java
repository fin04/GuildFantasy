package com.epriest.game.guildfantasy.main;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;

import com.epriest.game.CanvasGL.graphics.CanvasUtil;
import com.epriest.game.CanvasGL.graphics.GLUtil;
import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.CanvasGL.util.GameUtil;
import com.epriest.game.guildfantasy.TestData;
import com.epriest.game.guildfantasy.main.enty.ButtonEnty;
import com.epriest.game.guildfantasy.main.enty.EventEnty;
import com.epriest.game.guildfantasy.main.enty.MapEnty;
import com.epriest.game.guildfantasy.main.enty.PlayerEnty;
import com.epriest.game.guildfantasy.main.enty.QuestEnty;
import com.epriest.game.guildfantasy.main.play.DataManager;
import com.epriest.game.guildfantasy.main.play.GameDbAdapter;
import com.epriest.game.guildfantasy.main.play.TurnManager;
import com.epriest.game.guildfantasy.util.PPreference;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.epriest.game.CanvasGL.graphics.CanvasUtil.drawClip;

/**
 * Created by darka on 2017-03-26.
 */

public class Game_Main {
    public final static int GAME_INTRO = 0;
    //    public final static int GAME_MAIN =1 ;
    public final static int GAME_HOME = 2;
    public final static int GAME_PARTY = 3;
    public final static int GAME_MEMBER = 4;
    public final static int GAME_TOWN = 5;
    public final static int GAME_QUEST_SELECT = 6;
    public final static int GAME_QUEST_INFO = 7;
    public final static int GAME_EVENT = 8;
    public final static int GAME_OPTION = 10;

    public final static int MODE_PARTY_SELECT = 1;
    public final static int MODE_PARTY_INFO = 0;

    public ApplicationClass appClass;
    public TurnManager turnManager;
    public GameDbAdapter dbAdapter;
//    public Scene_Main sceneMain;

    public PlayerEnty playerEnty;
    public EventEnty eventEnty;
    public ArrayList<ButtonEnty> menuButtonList;
    public QuestEnty selectQuestEnty;
    private ButtonEnty backBtn;
    private ButtonEnty memberIcon;
    private ButtonEnty partyIcon;
    private ButtonEnty goldIcon;

    private ButtonEnty alertBtn;

    public Bitmap img_mainBtn;
    private Bitmap status_bar;
    private Bitmap alertBox;

    public MapEnty.MapLayer mapLayer;

    public final String[] menuIconName = {"Home", "Member", "Party", "Quest", "Town", "Play", "Stop"};
    public final int[] menuIconNum = {1, 2, 3, 4, 5, 6, 7};
    public final static int statusBarH = 45;
    public int canvasW, canvasH;

    public Game_Main(Context context, GameDbAdapter dbAdapter) {
        appClass = (ApplicationClass) context.getApplicationContext();
//        sceneMain = new Scene_Main(this);
        this.dbAdapter = dbAdapter;
        turnManager = new TurnManager(this);
    }

    public void Init() {
//        playerEnty = checkPlayerData();
        if (playerEnty == null) {
            // 프롤로그를 실행하고 플레이어를 작성
//            startProlog1();
            playerEnty = DataManager.setStartGamePlayerData(appClass.getBaseContext(), dbAdapter);
        }

        canvasW = appClass.getGameCanvasWidth();
        canvasH = appClass.getGameCanvasHeight();

        String jsonStr = GameUtil.getAssetString(appClass.getBaseContext(), "map/stage02.json");
        Gson gson = new Gson();
        mapLayer = gson.fromJson(jsonStr, MapEnty.MapLayer.class);
        mapLayer.terrainColumnList = new ArrayList<>();
        mapLayer.buildiingColumnList = new ArrayList<>();
        int rowNum = mapLayer.getLayers().get(0).getWidth();
        int columnNum = mapLayer.getLayers().get(0).getHeight();
        for (int i = 0; i < columnNum; i++) {
            int[] mRowArray_t = new int[rowNum];
            int[] mRowArray_b = new int[rowNum];
            for (int j = 0; j < rowNum; j++) {
                int index = i * rowNum + j;
                mRowArray_t[j] = mapLayer.getLayers().get(0).getDataList().get(index);
                mRowArray_b[j] = mapLayer.getLayers().get(1).getDataList().get(index);
                if (j == rowNum - 1) {
                    mapLayer.terrainColumnList.add(mRowArray_t);
                    mapLayer.buildiingColumnList.add(mRowArray_b);
                }
            }
        }
        mapLayer.curClickTile = new Point();
        mapLayer.LeftTopTileNum = new Point();
        mapLayer.mMapAxis = new Point();
        //게임 처음 맵에서 보여지는 위치 설정 (임시로 0)
        mapLayer.mMapAxis.x = 0;
        mapLayer.mMapAxis.y = 0;
        mapLayer.getTileNum(mapLayer.LeftTopTileNum, mapLayer.mMapAxis.x, mapLayer.mMapAxis.y);


        img_mainBtn = GLUtil.loadAssetsBitmap(appClass, "main/main_btn.png", null);
        status_bar = GLUtil.loadAssetsBitmap(appClass, "main/statusbar_tile.png", null);
        alertBox = GLUtil.loadAssetsBitmap(appClass, "main/alertbox.png", null);
        menuButtonList = setMenuIcon(canvasW, canvasH);
    }

    private PlayerEnty checkPlayerData() {
        return new PPreference(appClass.getBaseContext()).readPlayer("player");
    }

    public void mainButtonAct(int state, int mode) {
        appClass.isGameInit = true;
        appClass.isSceneInit = true;
        appClass.gameState = state;
        appClass.gameMode = mode;

        new PPreference(appClass.getBaseContext()).writePlayer("player", playerEnty);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (appClass.gameState == GAME_HOME && playerEnty.isStartTurnAlert) {
            if (GameUtil.equalsTouch(appClass.touch, alertBtn.drawX, alertBtn.drawY, alertBtn.clipW, alertBtn.clipH)) {
                if (appClass.touch.action == MotionEvent.ACTION_UP) {
                    alertBtn.clickState = ButtonEnty.ButtonClickOff;
                    playerEnty.isStartTurnAlert = false;
//                    playerEnty = DataManager.setChangeEvent(dbAdapter, playerEnty);

                    return true;
                } else {
                    alertBtn.clickState = ButtonEnty.ButtonClickOn;
                }
            } else
                alertBtn.clickState = ButtonEnty.ButtonClickOn;
            return false;
        }

        int turnBtnNum = menuButtonList.size() - 1;
        if (GameUtil.equalsTouch(appClass.touch, menuButtonList.get(turnBtnNum).drawX, menuButtonList.get(turnBtnNum).drawY,
                menuButtonList.get(turnBtnNum).clipW, menuButtonList.get(turnBtnNum).clipH)) {
            if (appClass.touch.action == MotionEvent.ACTION_UP) {
                menuButtonList.get(turnBtnNum).clickState = ButtonEnty.ButtonClickOff;
                switch (appClass.gameState) {
                    case Game_Main.GAME_HOME:
                        turnManager.turnCycle(1);
                        break;
                }
            } else {
                menuButtonList.get(turnBtnNum).clickState = ButtonEnty.ButtonClickOn;
            }
            return true;
        } else {
            menuButtonList.get(turnBtnNum).clickState = ButtonEnty.ButtonClickOff;
        }

        if (GameUtil.equalsTouch(appClass.touch, backBtn.drawX, backBtn.drawY, backBtn.clipW, backBtn.clipH)) {
            if (appClass.touch.action == MotionEvent.ACTION_UP) {
                backBtn.clickState = ButtonEnty.ButtonClickOff;
                switch (appClass.gameState) {
                    case Game_Main.GAME_PARTY:
                        mainButtonAct(Game_Main.GAME_QUEST_SELECT, 0);
                        break;
                    default:
                        // back
                        mainButtonAct(Game_Main.GAME_HOME, 0);
                        break;
                }
            } else {
                backBtn.clickState = ButtonEnty.ButtonClickOn;
            }
            return true;
        } else {
            backBtn.clickState = ButtonEnty.ButtonClickOff;
        }


        if (appClass.gameState != GAME_HOME)
            return false;

        for (ButtonEnty mBtn : menuButtonList) {
            if (GameUtil.equalsTouch(appClass.touch, mBtn.drawX, mBtn.drawY, mBtn.clipW, mBtn.clipH)) {
                mBtn.clickState = ButtonEnty.ButtonClickOn;
                if (appClass.touch.action == MotionEvent.ACTION_UP) {
                    mBtn.clickState = ButtonEnty.ButtonClickOff;

                    if (mBtn.name.equals("Home") && appClass.gameState != GAME_HOME)
                        mainButtonAct(GAME_HOME, 0);
                    else if (mBtn.name.equals("Party") && appClass.gameState != GAME_PARTY)
                        mainButtonAct(GAME_PARTY, 0);
                    else if (mBtn.name.equals("Member") && appClass.gameState != GAME_MEMBER)
                        mainButtonAct(GAME_MEMBER, 0);
                    else if (mBtn.name.equals("Town") && appClass.gameState != GAME_TOWN)
                        mainButtonAct(GAME_TOWN, 0);
                    else if (mBtn.name.equals("Quest") && appClass.gameState != GAME_QUEST_SELECT) {
                        if (playerEnty.QUESTLIST.size() > 0)
                            mainButtonAct(GAME_QUEST_SELECT, 0);
                    } else if (mBtn.name.equals("Setting") && appClass.gameState != GAME_OPTION)
                        mainButtonAct(GAME_OPTION, 0);
                }
                return true;
            } else {
                mBtn.clickState = ButtonEnty.ButtonClickOff;
            }
        }
        return false;
    }

    private ArrayList<ButtonEnty> setMenuIcon(int canvasW, int canvasH) {
        ArrayList<ButtonEnty> menuButtonList = new ArrayList<>();
        switch (appClass.gameState) {
            default:
                for (int i = 0; i < menuIconName.length - 2; i++) {
                    ButtonEnty mBtn = new ButtonEnty();
                    mBtn.num = i;
                    mBtn.name = menuIconName[i];
                    mBtn.iconImgNum = menuIconNum[i];
                    mBtn.clipW = 96;
                    mBtn.clipH = 84;
                    mBtn.clipX = 0;
                    mBtn.clipY = 0;
//                    mBtn.drawX = canvasW - 110 - (4 - i) * (mBtn.clipW + 10);
//                    mBtn.drawY = canvasH - mBtn.clipH - 10;

                    mBtn.drawX = 150 + (mBtn.clipW + 5) * i;
                    mBtn.drawY = canvasH - (mBtn.clipH + 5);
                    menuButtonList.add(mBtn);
                }

                //play button
                ButtonEnty mBtn = new ButtonEnty();
                mBtn.num = menuIconName.length - 2;
                mBtn.name = menuIconName[mBtn.num];
                mBtn.iconImgNum = menuIconNum[mBtn.num];
                mBtn.clipW = 116;
                mBtn.clipH = 116;
                mBtn.clipX = 0;
                mBtn.clipY = 172;
                mBtn.drawX = canvasW - (mBtn.clipW + 5);
                mBtn.drawY = canvasH - (mBtn.clipH + 5);
                menuButtonList.add(mBtn);

                backBtn = new ButtonEnty();
                backBtn.name = "Back";
                backBtn.drawX = canvasW - 110;
                backBtn.drawY = 2;
                backBtn.clipW = 104;
                backBtn.clipH = 40;
                backBtn.clipX = 97;
                backBtn.clipY = 0;

                memberIcon = new ButtonEnty();
                memberIcon.name = "Member";
                memberIcon.drawX = (appClass.getGameCanvasWidth() - 120) / 5;
                memberIcon.drawY = 5;
                memberIcon.clipW = 24;
                memberIcon.clipH = 34;
                memberIcon.clipX = 136;
                memberIcon.clipY = 92;

                partyIcon = new ButtonEnty();
                partyIcon.name = "Party";
                partyIcon.drawX = (appClass.getGameCanvasWidth() - 120) / 5 + 80;
                partyIcon.drawY = 5;
                partyIcon.clipW = 32;
                partyIcon.clipH = 32;
                partyIcon.clipX = 100;
                partyIcon.clipY = 92;

                goldIcon = new ButtonEnty();
                goldIcon.name = "Gold";
                goldIcon.drawX = (appClass.getGameCanvasWidth() - 120) / 5 + 160;
                goldIcon.drawY = 5;
                goldIcon.clipW = 36;
                goldIcon.clipH = 27;
                goldIcon.clipX = 101;
                goldIcon.clipY = 126;

                alertBtn = new ButtonEnty();
                alertBtn.name = "Alert";
                alertBtn.drawX = (appClass.getGameCanvasWidth() - alertBox.getWidth()) / 2 + alertBox.getWidth() - 100;
                alertBtn.drawY = (appClass.getGameCanvasHeight() - alertBox.getHeight()) / 2 + alertBox.getHeight() - 100;
                alertBtn.clipW = 90;
                alertBtn.clipH = 90;
                alertBtn.clipX = 231;
                alertBtn.clipY = 173;
                break;

            case Game_Main.GAME_PARTY:
                ButtonEnty mBtn1 = new ButtonEnty();
                mBtn1.num = 0;
                mBtn1.name = "start";
                mBtn1.clipW = 117;
                mBtn1.clipH = 117;
                mBtn1.clipX = 1;
                mBtn1.clipY = 172;
                mBtn1.drawX = canvasW - mBtn1.clipW - 20;
                mBtn1.drawY = canvasH - mBtn1.clipH - 10;
                menuButtonList.add(mBtn1);

                ButtonEnty mBtn2 = new ButtonEnty();
                mBtn2.num = 1;
                mBtn2.name = "back";
                mBtn2.clipW = 90;
                mBtn2.clipH = 90;
                mBtn2.clipX = 230;
                mBtn2.clipY = 172;
                mBtn2.drawX = canvasW - mBtn1.clipW - 20;
                mBtn2.drawY = 90;
                menuButtonList.add(mBtn2);
                break;
        }

        return menuButtonList;
    }

    public void drawMain(Canvas mCanvas, boolean viewMenuButton) {

        //menu button
        if (viewMenuButton) {
            drawMenuButton(mCanvas);
//            drawHomeCard(mCanvas);
        }

        // status bar
        drawStatusBar(mCanvas);

        // top button
        drawTopButton(mCanvas);

        // turn alert dialog
        if (appClass.gameState == GAME_HOME && playerEnty.isStartTurnAlert)
            drawTurnStartAlert(mCanvas);
    }

    private void drawTurnStartAlert(Canvas mCanvas) {
        CanvasUtil.drawBitmap(alertBox, mCanvas, (appClass.getGameCanvasWidth() - alertBox.getWidth()) / 2
                , (appClass.getGameCanvasHeight() - alertBox.getHeight()) / 2);
        int alertBtnClipX = alertBtn.clipX;
        if (alertBtn.clickState == ButtonEnty.ButtonClickOn) {
            alertBtnClipX += alertBtn.clipW;
        }
        drawClip(img_mainBtn, mCanvas,
                alertBtnClipX, alertBtn.clipY,
                alertBtn.clipW, alertBtn.clipH,
                alertBtn.drawX, alertBtn.drawY);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
//        paint.setAntiAlias(true);
        paint.setTextSize(20);
        int strX = (appClass.getGameCanvasWidth() - 400) / 2 + 30;
        int strY = (appClass.getGameCanvasHeight() - 300) / 2;
        CanvasUtil.drawString(mCanvas, playerEnty.TURN + " 턴", paint, strX, strY);
        CanvasUtil.drawString(mCanvas, "완료된 퀘스트 수 - ", paint, strX, strY + 30);
        CanvasUtil.drawString(mCanvas, "길드 수입 - " + playerEnty.eventEnty.Gold + "Gold", paint, strX, strY + 80);
//        CanvasUtil.drawString(mCanvas, "충전된 AP - " + turnManager.turnEnty.AP + "point", paint, strX, strY + 130);
        CanvasUtil.drawString(mCanvas, "새로운 퀘스트 수 - " + playerEnty.eventEnty.QuestIDList.size(), paint, strX, strY + 180);
    }

    private void drawTopButton(Canvas mCanvas) {
        int clipY = backBtn.clipY;
        if (backBtn.clickState == ButtonEnty.ButtonClickOn) {
            clipY = 41;
        }
        CanvasUtil.drawClip(img_mainBtn, mCanvas, backBtn.clipX, clipY,
                backBtn.clipW, backBtn.clipH, backBtn.drawX, backBtn.drawY);

        switch (appClass.gameState) {
            case GAME_HOME:
                CanvasUtil.drawClip(img_mainBtn, mCanvas, 270, 0,
                        backBtn.clipW, 18, backBtn.drawX, backBtn.drawY + 12);
                break;
            default:
                CanvasUtil.drawClip(img_mainBtn, mCanvas, 270, 18,
                        backBtn.clipW, 18, backBtn.drawX, backBtn.drawY + 12);
                break;
        }
    }

    public void drawStatusBar(Canvas mCanvas) {
        Paint paint = new Paint();
//        paint.setAntiAlias(true);

        int barNum = appClass.getGameCanvasWidth() / status_bar.getWidth();
        for (int i = 0; i <= barNum; i++) {
            CanvasUtil.drawBitmap(status_bar, mCanvas, status_bar.getWidth() * i, 0);
        }

        int drawY = 6;
        int drawX = (appClass.getGameCanvasWidth() - 120) / 5;
        paint.setColor(Color.argb(255, 50, 50, 50));
        paint.setTextSize(20);
        CanvasUtil.drawString(mCanvas, playerEnty.Name + "(Lv " + playerEnty.LEVEL + ")", paint, 10, drawY);
//        CanvasUtil.drawClip(img_mainBtn, mCanvas, mBtn.clipX, clipY,
//                mBtn.clipW, mBtn.clipH, drawX * 1, drawY);
        CanvasUtil.drawClip(img_mainBtn, mCanvas, memberIcon.clipX, memberIcon.clipY,
                memberIcon.clipW, memberIcon.clipH, memberIcon.drawX, memberIcon.drawY);
        CanvasUtil.drawString(mCanvas, Integer.toString(playerEnty.MEMBERLIST.size()), paint,
                memberIcon.drawX + memberIcon.clipW + 5, memberIcon.drawY + 3);
        CanvasUtil.drawClip(img_mainBtn, mCanvas, partyIcon.clipX, partyIcon.clipY,
                partyIcon.clipW, partyIcon.clipH, partyIcon.drawX, partyIcon.drawY);
        CanvasUtil.drawString(mCanvas, Integer.toString(playerEnty.PARTYLIST.size()), paint,
                partyIcon.drawX + partyIcon.clipW + 5, partyIcon.drawY + 3);
        CanvasUtil.drawClip(img_mainBtn, mCanvas, goldIcon.clipX, goldIcon.clipY,
                goldIcon.clipW, goldIcon.clipH, goldIcon.drawX, goldIcon.drawY);
        CanvasUtil.drawString(mCanvas, Integer.toString(playerEnty.GOLD), paint,
                goldIcon.drawX + goldIcon.clipW + 5, goldIcon.drawY + 3);
        CanvasUtil.drawString(mCanvas, "AP " + playerEnty.AP, paint, drawX * 4, drawY);
        CanvasUtil.drawString(mCanvas, "Turn " + playerEnty.TURN, paint, appClass.getGameCanvasWidth() - 180, drawY);

    }
/*
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
    }*/

    public void drawHomeCard(Canvas mCanvas) {
        for (ButtonEnty mBtn : menuButtonList) {
            if (mBtn.clickState == ButtonEnty.ButtonClickOn) {

            }
            CanvasUtil.drawBitmap(img_mainBtn, mCanvas, mBtn.drawX, mBtn.drawY);
            CanvasUtil.drawClip(img_mainBtn, mCanvas, mBtn.clipW + 1, mBtn.num * 18,
                    64, 18, mBtn.drawX + (mBtn.clipW - 64) / 2, mBtn.drawY + 56);
//            CanvasUtil.drawClip(menu_icon, mCanvas, null, (iconNum%5)*mBtn.w, (iconNum/5)*mBtn.h,
//                    mBtn.w, mBtn.h, mBtn.x+(btnArea-mBtn.w)/2, mBtn.y);

        }
    }

    public void drawMenuButton(Canvas mCanvas) {
//        int btnArea = (canvasH - statusBarH)/ gameMain.menuButtonList.size();
        for (ButtonEnty mBtn : menuButtonList) {
            int clipX = mBtn.clipX;
            int clipY = mBtn.clipY;
            if (mBtn.clickState == ButtonEnty.ButtonClickOn) {
                if (mBtn.num == menuButtonList.size() - 1)
                    clipX += mBtn.clipW;
                else
                    clipY += mBtn.clipH;
            }
            CanvasUtil.drawClip(img_mainBtn, mCanvas, clipX, clipY,
                    mBtn.clipW, mBtn.clipH, mBtn.drawX, mBtn.drawY);
            CanvasUtil.drawClip(img_mainBtn, mCanvas, 200, mBtn.iconImgNum * 18,
                    64, 18, mBtn.drawX + (mBtn.clipW - 64) / 2, mBtn.drawY + 56);
//            CanvasUtil.drawClip(menu_icon, mCanvas, null, (iconNum%5)*mBtn.w, (iconNum/5)*mBtn.h,
//                    mBtn.w, mBtn.h, mBtn.x+(btnArea-mBtn.w)/2, mBtn.y);

        }
    }


}
