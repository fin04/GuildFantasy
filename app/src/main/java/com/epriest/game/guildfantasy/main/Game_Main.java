package com.epriest.game.guildfantasy.main;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.Toast;

import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.CanvasGL.util.GameUtil;
import com.epriest.game.guildfantasy.enty.ButtonEnty;
import com.epriest.game.guildfantasy.enty.EquipEnty;
import com.epriest.game.guildfantasy.enty.MemberEnty;
import com.epriest.game.guildfantasy.enty.PlayerEnty;
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
    public final static int GAME_MEMEBER = 4;
    public final static int GAME_TOWN = 5;
    public final static int GAME_QUEST = 6;
    public final static int GAME_OPTION = 10;

    public PlayerEnty playerEnty;
    public ArrayList<ButtonEnty> menuButtonList;
    public ApplicationClass appClass;

    public Game_Main(Context context){
        appClass = (ApplicationClass) context.getApplicationContext();
    }

    public void Init(){
        playerEnty = checkPlayerData();
        if(playerEnty == null){
            // 프롤로그를 실행하고 플레이어를 작성
//            startProlog1();
            playerEnty = setTestPlayerData();
        }
        menuButtonList = new ArrayList();

    }

    private PlayerEnty checkPlayerData(){
        return new PPreference(appClass.getBaseContext()).readPlayer("player");
    }

    public void onTouchEvent(MotionEvent event){
        for(ButtonEnty mBtn : menuButtonList){
            if(GameUtil.equalsTouch(appClass.touch, mBtn.x, mBtn.y, mBtn.w, mBtn.h)){
                mBtn.clickState = ButtonEnty.ButtonClickOn;
                if(appClass.touch.Action == MotionEvent.ACTION_UP) {
                    mBtn.clickState = ButtonEnty.ButtonClickOff;

                    if(mBtn.name.equals("Home"))
                        appClass.gameFlag = GAME_HOME;
                    else if(mBtn.name.equals("Party"))
                        appClass.gameFlag = GAME_PARTY;
                    else if(mBtn.name.equals("Member"))
                        appClass.gameFlag = GAME_MEMEBER;
                    else if(mBtn.name.equals("Town"))
                        appClass.gameFlag = GAME_TOWN;
                    else if(mBtn.name.equals("Quest"))
                        appClass.gameFlag = GAME_QUEST;
                    else if(mBtn.name.equals("Setting"))
                        appClass.gameFlag = GAME_OPTION;

                    appClass.isGameInit = true;
                    appClass.isSceneInit = true;
//                    Toast.makeText(appClass.getBaseContext(), mBtn.name, Toast.LENGTH_SHORT).show();
                }
            }else{
                mBtn.clickState = ButtonEnty.ButtonClickOff;
            }
        }
    }

    private PlayerEnty setTestPlayerData(){
        PlayerEnty enty = new PlayerEnty();
        enty.Name = "가방짱";
        enty.LEVEL = 1;
        enty.GOLD = 100;
        enty.MEMBERLIST = new ArrayList<>();
        enty.PARTYLIST = new ArrayList<>();
        enty.QUESTLIST = new ArrayList<>();

        for(int i=0; i<4; i++){
            MemberEnty enty1 = new MemberEnty();

            switch(i){
                case 0:
                    enty1.id = "0001";
                    enty1.name = "홍길동";
                    enty1.play_class = "전사";
                    enty1.race = "인간";
                    enty1.equip = new EquipEnty();
                    enty1.equip.equip_main_arms = "목검";
                    enty1.equip.equip_sub_arms = "나무방패";
                    enty1.equip.equip_head = "두건";
                    enty1.equip.equip_body = "가죽갑옷";
                    enty1.status = new StatusEnty();
                    enty1.status.LEVEL = 1;
                    enty1.status.EXP = 0;
                    enty1.status.STR = 10;
                    enty1.status.DEX = 10;
                    enty1.status.INT = 10;
                    enty1.status.VIT = 10;
                    enty1.status.HP = 10;
                    enty1.status.MP = 10;
                    break;
                case 1:
                    enty1.id = "0002";
                    enty1.name = "타이거";
                    enty1.play_class = "사냥꾼";
                    enty1.race = "드워프";
                    enty1.equip = new EquipEnty();
                    enty1.equip.equip_main_arms = "활";
                    enty1.equip.equip_sub_arms = "";
                    enty1.equip.equip_head = "";
                    enty1.equip.equip_body = "천갑옷";
                    enty1.status = new StatusEnty();
                    enty1.status.LEVEL = 1;
                    enty1.status.EXP = 0;
                    enty1.status.STR = 10;
                    enty1.status.DEX = 10;
                    enty1.status.INT = 10;
                    enty1.status.VIT = 10;
                    enty1.status.HP = 10;
                    enty1.status.MP = 10;
                    break;
                case 2:
                    enty1.id = "0003";
                    enty1.name = "로리";
                    enty1.play_class = "마법사";
                    enty1.race = "엘프";
                    enty1.equip = new EquipEnty();
                    enty1.equip.equip_main_arms = "지팡이";
                    enty1.equip.equip_sub_arms = "";
                    enty1.equip.equip_head = "";
                    enty1.equip.equip_body = "망토";
                    enty1.status = new StatusEnty();
                    enty1.status.LEVEL = 1;
                    enty1.status.EXP = 0;
                    enty1.status.STR = 10;
                    enty1.status.DEX = 10;
                    enty1.status.INT = 10;
                    enty1.status.VIT = 10;
                    enty1.status.HP = 10;
                    enty1.status.MP = 10;
                    break;
                case 3:
                    enty1.id = "0004";
                    enty1.name = "야옹이";
                    enty1.play_class = "도적";
                    enty1.race = "수인";
                    enty1.equip = new EquipEnty();
                    enty1.equip.equip_main_arms = "단검";
                    enty1.equip.equip_sub_arms = "";
                    enty1.equip.equip_head = "빨간모자";
                    enty1.equip.equip_body = "망토";
                    enty1.status = new StatusEnty();
                    enty1.status.LEVEL = 1;
                    enty1.status.EXP = 0;
                    enty1.status.STR = 10;
                    enty1.status.DEX = 10;
                    enty1.status.INT = 10;
                    enty1.status.VIT = 10;
                    enty1.status.HP = 10;
                    enty1.status.MP = 10;
                    break;
            }
            enty.MEMBERLIST.add(enty1);
        }
        new PPreference(appClass.getBaseContext()).writePlayer("player", enty);
        return enty;
    }
}
