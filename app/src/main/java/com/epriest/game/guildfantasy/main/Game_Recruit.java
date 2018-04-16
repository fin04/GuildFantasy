package com.epriest.game.guildfantasy.main;

import android.graphics.Bitmap;
import android.view.MotionEvent;
import android.widget.Toast;

import com.epriest.game.CanvasGL.graphics.GLUtil;
import com.epriest.game.CanvasGL.util.ButtonSprite;
import com.epriest.game.CanvasGL.util.Game;
import com.epriest.game.CanvasGL.util.GameUtil;
import com.epriest.game.CanvasGL.util.TouchData;
import com.epriest.game.guildfantasy.main.enty.ButtonEnty;
import com.epriest.game.guildfantasy.main.enty.MemberEnty;
import com.epriest.game.guildfantasy.main.play.AlertManager;
import com.epriest.game.guildfantasy.main.play.DataManager;
import com.epriest.game.guildfantasy.main.play.GameDialog;
import com.epriest.game.guildfantasy.util.INN;

import java.util.ArrayList;

/**
 * Created by darka on 2017-11-06.
 */

public class Game_Recruit extends Game {

    public Bitmap img_value;
    public Bitmap bg;

    public Game_Main gameMain;

    public ButtonEnty summonBtn;
    public ButtonEnty bondageBtn;
    public ButtonEnty covenantBtn;

    public int maxMember = 100;

    public MemberEnty recruitEnty;
    public Bitmap recruitImg;

    public GameDialog summonDialog;
    public GameDialog covenantDialog;
    public GameDialog bondageDialog;


    public Game_Recruit(Game_Main gameMain) {
        this.gameMain = gameMain;
    }

    @Override
    public void gStart() {
        bg = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/bg_tavern.jpg", null);
        img_value = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/banner_value.jpg", null);
        summonBtn = new ButtonEnty();
        summonBtn.bitmap = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/banner_summon.png", null);
        bondageBtn = new ButtonEnty();
        bondageBtn.bitmap = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/banner_bondage.png", null);
        covenantBtn = new ButtonEnty();
        covenantBtn.bitmap = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/banner_covenant.png", null);
        setButton();

        summonDialog = new GameDialog(gameMain.appClass);
        summonDialog.setOnButtonListener(new GameDialog.onOneClickListener() {
            @Override
            public void onClick() {
                gameMain.showAlertType = GameDialog.ALERT_TYPE_NONE;
            }
        });
        summonDialog.setTitle("소환 완료");
        summonDialog.setText("");
        summonDialog.setButtonTitle("확인");

        covenantDialog = new GameDialog(gameMain.appClass);
        covenantDialog.setOnButtonListener(new GameDialog.onOneClickListener() {
            @Override
            public void onClick() {
                gameMain.showAlertType = GameDialog.ALERT_TYPE_NONE;
            }
        });
        covenantDialog.setTitle("계약 완료");
        covenantDialog.setText("");
        covenantDialog.setButtonTitle("확인");

        bondageDialog = new GameDialog(gameMain.appClass);
        bondageDialog.setOnButtonListener(new GameDialog.onOneClickListener() {
            @Override
            public void onClick() {
                gameMain.showAlertType = GameDialog.ALERT_TYPE_NONE;
            }
        });
        bondageDialog.setTitle("속박 완료");
        bondageDialog.setText("");
        bondageDialog.setButtonTitle("확인");
    }

    private void setButton() {
        int btnW = summonBtn.bitmap.getWidth();
        int btnH = summonBtn.bitmap.getHeight();
        int canvasW = gameMain.canvasW;
        int canvasH = gameMain.canvasH;
        int btnX = canvasW / 2 - btnW / 2;

        summonBtn.drawX = btnX;
        summonBtn.drawY = (canvasH-btnH*3)/2-40;
        summonBtn.clipW = btnW;
        summonBtn.clipH = btnH;
        summonBtn.name = "summon";

        bondageBtn.drawX = btnX;
        bondageBtn.drawY = (canvasH-btnH*3)/2+btnH;
        bondageBtn.clipW = btnW;
        bondageBtn.clipH = btnH;
        bondageBtn.name = "bondage";

        covenantBtn.drawX = btnX;
        covenantBtn.drawY = (canvasH-btnH*3)/2+btnH*2+40;
        covenantBtn.clipW = btnW;
        covenantBtn.clipH = btnH;
        covenantBtn.name = "covenant";
    }

    @Override
    public void gStop() {

    }

    @Override
    public void gUpdate() {

    }

    private void activeNewCard(String type) {
        if(gameMain.userEnty.MEMBERLIST.size() >= maxMember){
            gameMain.showAlertType = GameDialog.ALERT_TYPE_MAXMEMBER;
            return;
        }
        if (type.equals("covenant")) {
            if (gameMain.userEnty.GOLD < 10) {
                gameMain.showAlertType = GameDialog.ALERT_TYPE_EMPTYGOLD;
            } else {
                gameMain.userEnty.GOLD -= 10;
                ArrayList<MemberEnty> entyList = DataManager.getGradeMemberDataList(gameMain.dbAdapter, "1");
                getRecruitMember(entyList);
            }
        }else if (type.equals("summon")) {
            if(gameMain.userEnty.GEM_RED == 0 || gameMain.userEnty.GEM_GREEN == 0 || gameMain.userEnty.GEM_BLUE == 0){
                gameMain.showAlertType = GameDialog.ALERT_TYPE_GEMNOTENOUGH;
            }else{
                gameMain.userEnty.GEM_RED--;
                gameMain.userEnty.GEM_GREEN--;
                gameMain.userEnty.GEM_BLUE--;
                ArrayList<MemberEnty> entyList = DataManager.getGradeMemberDataList(gameMain.dbAdapter, "2");
                getRecruitMember(entyList);
            }
        }
    }

    /**
     * entyList안에서 random으로 하나를 뽑아 update.
     * @param entyList
     */
    private void getRecruitMember(ArrayList<MemberEnty> entyList){
        int num = (int)(Math.random()*entyList.size());
        recruitEnty = entyList.get(num);
        gameMain.userEnty.MEMBERLIST.add(recruitEnty);

        DataManager.updateUserInfo(gameMain.dbAdapter, gameMain.userEnty);
        DataManager.insertUserMember(gameMain.dbAdapter, recruitEnty, gameMain.userEnty.Name);

        recruitImg = GLUtil.loadAssetsBitmap(gameMain.appClass, "member/"+recruitEnty.image, null, 2);
//                Toast.makeText(gameMain.appClass, enty.name, Toast.LENGTH_SHORT).show();
//        gameMain.alertManager.showAlertType = AlertManager.ALERT_TYPE_GETNEWMEMBER;
    }

    @Override
    public void gOnTouchEvent(MotionEvent event) {
        if (gameMain.onStatusTouch())
            return;

        //소환버튼
        if (GameUtil.equalsTouch(gameMain.appClass.touch,
                summonBtn.drawX, summonBtn.drawY, summonBtn.clipW, summonBtn.clipH)) {
            if (gameMain.appClass.touch.action == MotionEvent.ACTION_UP) {
                summonBtn.clickState = ButtonEnty.ButtonClickOff;
                summonDialog.setOnButtonListener(new GameDialog.onOneClickListener() {
                    @Override
                    public void onClick() {
                        activeNewCard(summonBtn.name);
                        summonDialog.setImage(recruitImg);
                        gameMain.showAlertType = GameDialog.ALERT_TYPE_NONE;
                    }
                });
                gameMain.showAlertType = GameDialog.ALERT_TYPE_RECRUIT_SUMMON;
                return;
            } else {
                summonBtn.clickState = ButtonEnty.ButtonClickOn;
                return;
            }
        } else {
            summonBtn.clickState = ButtonEnty.ButtonClickOff;
        }

        //속박버튼
        if (GameUtil.equalsTouch(gameMain.appClass.touch,
                bondageBtn.drawX, bondageBtn.drawY, bondageBtn.clipW, bondageBtn.clipH)) {
            if (gameMain.appClass.touch.action == MotionEvent.ACTION_UP) {
                bondageBtn.clickState = ButtonEnty.ButtonClickOff;
                bondageDialog.setOnButtonListener(new GameDialog.onOneClickListener() {
                    @Override
                    public void onClick() {
                        activeNewCard(bondageBtn.name);
                        bondageDialog.setImage(recruitImg);
                        gameMain.showAlertType = GameDialog.ALERT_TYPE_NONE;
                    }
                });
                gameMain.showAlertType = GameDialog.ALERT_TYPE_RECRUIT_BONDAGE;
                return;
            } else {
                bondageBtn.clickState = ButtonEnty.ButtonClickOn;
                return;
            }
        } else {
            bondageBtn.clickState = ButtonEnty.ButtonClickOff;
        }

        //계약버튼
        if (GameUtil.equalsTouch(gameMain.appClass.touch,
                covenantBtn.drawX, covenantBtn.drawY, covenantBtn.clipW, covenantBtn.clipH)) {
            if (gameMain.appClass.touch.action == MotionEvent.ACTION_UP) {
                covenantBtn.clickState = ButtonEnty.ButtonClickOff;
                covenantDialog.setOnButtonListener(new GameDialog.onOneClickListener() {
                    @Override
                    public void onClick() {
                        activeNewCard(covenantBtn.name);
                        covenantDialog.setImage(recruitImg);
                        gameMain.showAlertType = GameDialog.ALERT_TYPE_NONE;
                    }
                });
                gameMain.showAlertType = GameDialog.ALERT_TYPE_RECRUIT_COVENANT;
                return;
            } else {
                covenantBtn.clickState = ButtonEnty.ButtonClickOn;
                return;
            }
        } else {
            covenantBtn.clickState = ButtonEnty.ButtonClickOff;
        }
    }
}


