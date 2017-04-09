package com.epriest.game.guildfantasy.main;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.Toast;

import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.CanvasGL.util.GameUtil;
import com.epriest.game.guildfantasy.TestData;
import com.epriest.game.guildfantasy.enty.ButtonEnty;
import com.epriest.game.guildfantasy.enty.EquipEnty;
import com.epriest.game.guildfantasy.enty.MemberEnty;
import com.epriest.game.guildfantasy.enty.PlayerEnty;
import com.epriest.game.guildfantasy.enty.QuestEnty;
import com.epriest.game.guildfantasy.enty.StatusEnty;
import com.epriest.game.guildfantasy.util.PPreference;

import java.util.ArrayList;

/**
 * Created by darka on 2017-03-26.
 */

public class Game_Main {
    public final static int GAME_INTRO = 0;
    public final static int GAME_MAIN =1 ;
    public final static int GAME_HOME = 2;
    public final static int GAME_PARTY = 3;
    public final static int GAME_MEMBER = 4;
    public final static int GAME_TOWN = 5;
    public final static int GAME_QUEST = 6;
    public final static int GAME_OPTION = 10;

    public final static int MODE_PARTY_SELECT = 1;
    public final static int MODE_PARTY_INFO = 0;

    public PlayerEnty playerEnty;
    public ArrayList<ButtonEnty> menuButtonList;
    public ApplicationClass appClass;
    public QuestEnty QuestEnty;

    public String[] menuIconName = {"Home" , "Member", "Quest", "Town", "Party","Setting"};
    public int[] menuIconNum = {1 , 2, 3, 4, 5, 20};

    public Game_Main(Context context){
        appClass = (ApplicationClass) context.getApplicationContext();
    }

    public void Init(){
//        playerEnty = checkPlayerData();
        if(playerEnty == null){
            // 프롤로그를 실행하고 플레이어를 작성
//            startProlog1();
            playerEnty = TestData.setTestPlayerData(appClass.getBaseContext());
        }
//        menuButtonList = new ArrayList();
    }

    private PlayerEnty checkPlayerData(){
        return new PPreference(appClass.getBaseContext()).readPlayer("player");
    }

    private void MainButtonAct(int flag){
        appClass.isGameInit = true;
        appClass.isSceneInit = true;
        QuestEnty = null;
        appClass.gameFlag = flag;
    }


    public boolean onTouchEvent(MotionEvent event){
        for(ButtonEnty mBtn : menuButtonList){
            if(GameUtil.equalsTouch(appClass.touch, mBtn.x, mBtn.y, mBtn.w, mBtn.h)){
                mBtn.clickState = ButtonEnty.ButtonClickOn;
                if(appClass.touch.Action == MotionEvent.ACTION_UP) {
                    mBtn.clickState = ButtonEnty.ButtonClickOff;

                    if(mBtn.name.equals("Home") && appClass.gameFlag != GAME_HOME)
                        MainButtonAct(GAME_HOME);
                    else if(mBtn.name.equals("Party") && appClass.gameFlag != GAME_PARTY)
                        MainButtonAct(GAME_PARTY);
                    else if(mBtn.name.equals("Member") && appClass.gameFlag != GAME_MEMBER)
                        MainButtonAct(GAME_MEMBER);
                    else if(mBtn.name.equals("Town") && appClass.gameFlag != GAME_TOWN)
                        MainButtonAct(GAME_TOWN);
                    else if(mBtn.name.equals("Quest") && appClass.gameFlag != GAME_QUEST)
                        MainButtonAct(GAME_QUEST);
                    else if(mBtn.name.equals("Setting") && appClass.gameFlag != GAME_OPTION)
                        MainButtonAct(GAME_OPTION);
                }
                return true;
            }else{
                mBtn.clickState = ButtonEnty.ButtonClickOff;
            }
        }
        return false;
    }


}
