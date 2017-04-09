package com.epriest.game.guildfantasy;

import android.content.Context;

import com.epriest.game.guildfantasy.enty.EquipEnty;
import com.epriest.game.guildfantasy.enty.MemberEnty;
import com.epriest.game.guildfantasy.enty.PlayerEnty;
import com.epriest.game.guildfantasy.enty.QuestEnty;
import com.epriest.game.guildfantasy.enty.StatusEnty;
import com.epriest.game.guildfantasy.util.PPreference;

import java.util.ArrayList;

/**
 * Created by darka on 2017-03-30.
 */

public class TestData {

    public static PlayerEnty setTestPlayerData(Context context){
        PlayerEnty enty = new PlayerEnty();
        enty.Name = "가방짱";
        enty.LEVEL = 1;
        enty.GOLD = 100;
        enty.MEMBERLIST = new ArrayList<>();
        enty.PARTYLIST = new ArrayList<>();
        enty.QUESTLIST = new ArrayList<>();

        for(int i=0; i<11; i++){
            MemberEnty enty1 = new MemberEnty();

            switch(i){
                case 0:
                    enty1.id = "0000";
                    enty1.name = "존스미스";
                    enty1.imageId = 1;
                    enty1.play_class = "기사";
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
                    enty1.id = "0001";
                    enty1.name = "터너";
                    enty1.imageId = 3;
                    enty1.play_class = "검사";
                    enty1.race = "인간";
                    enty1.equip = new EquipEnty();
                    enty1.equip.equip_main_arms = "목검";
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
                    enty1.id = "0002";
                    enty1.name = "크리스";
                    enty1.imageId = 5;
                    enty1.play_class = "마법사";
                    enty1.race = "인간";
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
                    enty1.id = "0003";
                    enty1.name = "유키";
                    enty1.imageId = 7;
                    enty1.play_class = "도적";
                    enty1.race = "인간";
                    enty1.equip = new EquipEnty();
                    enty1.equip.equip_main_arms = "단검";
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
                case 4:
                    enty1.id = "0004";
                    enty1.name = "미라이";
                    enty1.imageId = 9;
                    enty1.play_class = "사제";
                    enty1.race = "인간";
                    enty1.equip = new EquipEnty();
                    enty1.equip.equip_main_arms = "지팡이";
                    enty1.equip.equip_sub_arms = "";
                    enty1.equip.equip_head = "";
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
                case 5:
                    enty1.id = "0005";
                    enty1.name = "루시";
                    enty1.imageId = 11;
                    enty1.play_class = "검사";
                    enty1.race = "엘프";
                    enty1.equip = new EquipEnty();
                    enty1.equip.equip_main_arms = "목검";
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
                case 6:
                    enty1.id = "0006";
                    enty1.name = "엘렌";
                    enty1.imageId = 13;
                    enty1.play_class = "정찰병";
                    enty1.race = "엘프";
                    enty1.equip = new EquipEnty();
                    enty1.equip.equip_main_arms = "단검";
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
                case 7:
                    enty1.id = "0007";
                    enty1.name = "테피";
                    enty1.imageId = 14;
                    enty1.play_class = "기사";
                    enty1.race = "드워프";
                    enty1.equip = new EquipEnty();
                    enty1.equip.equip_main_arms = "목검";
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
                case 8:
                    enty1.id = "0008";
                    enty1.name = "휴리";
                    enty1.imageId = 16;
                    enty1.play_class = "도적";
                    enty1.race = "드워프";
                    enty1.equip = new EquipEnty();
                    enty1.equip.equip_main_arms = "단검";
                    enty1.equip.equip_sub_arms = "";
                    enty1.equip.equip_head = "빨간모자";
                    enty1.equip.equip_body = "";
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
                case 9:
                    enty1.id = "0009";
                    enty1.name = "야옹이";
                    enty1.imageId = 18;
                    enty1.play_class = "마법사";
                    enty1.race = "수인";
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
                case 10:
                    enty1.id = "0010";
                    enty1.name = "보라";
                    enty1.imageId = 19;
                    enty1.play_class = "사제";
                    enty1.race = "수인";
                    enty1.equip = new EquipEnty();
                    enty1.equip.equip_main_arms = "지팡이";
                    enty1.equip.equip_sub_arms = "";
                    enty1.equip.equip_head = "빨간모자";
                    enty1.equip.equip_body = "";
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
        new PPreference(context).writePlayer("player", enty);
        return enty;
    }

    public static ArrayList<QuestEnty> testQuestList() {
        ArrayList<QuestEnty> entyList = new ArrayList<>();
        for(int i=0; i< 3; i++){
            QuestEnty enty = new QuestEnty();
            switch(i){
                case 0:
                    enty.id = "001";
                    enty.title = "유령 퇴치";
                    enty.difficult = "1";
                    enty.needAP = "1";
                    enty.pic = "mon_00.png";
                    enty.text = "마을 인근에 출몰하는 좀비들이 가축들을 잡아가고 있어요. 사례는 적지만 도와주세요.";
                    enty.tip = "마법사,사제와 함께 가면 쉽습니다.";
                    enty.btnEnty.iconImgNum = 0;
                    enty.btnEnty.x = 195;
                    enty.btnEnty.y = 120;
                    enty.btnEnty.w = 120;
                    enty.btnEnty.h = 120;
                    break;
                case 1:
                    enty.id = "002";
                    enty.title = "늑대 사냥";
                    enty.difficult = "2";
                    enty.needAP = "1";
                    enty.pic = "mon_01.png";
                    enty.text = "언덕에 늑대들이 나타나 목동들을 공격하고 있습니다. 도와주세요.";
                    enty.tip = "도적,사냥꾼과 함께 가면 쉽습니다.";
                    enty.btnEnty.iconImgNum = 1;
                    enty.btnEnty.x = 420;
                    enty.btnEnty.y = 180;
                    enty.btnEnty.w = 120;
                    enty.btnEnty.h = 120;
                    break;
                case 2:
                    enty.id = "003";
                    enty.title = "고블린 동굴 소탕";
                    enty.difficult = "3";
                    enty.needAP = "2";
                    enty.pic = "mon_02.png";
                    enty.text = "마을 인근을 약탈하는 고블린들의 소굴을 소탕해주십시요. 사례는 얼마 못드립니다.";
                    enty.tip = "전사,도적과 함께 가면 어렵지 않을 것입니다.";
                    enty.btnEnty.iconImgNum = 2;
                    enty.btnEnty.x = 360;
                    enty.btnEnty.y = 300;
                    enty.btnEnty.w = 120;
                    enty.btnEnty.h = 120;
                    break;
            }
            entyList.add(enty);
        }
        return entyList;
    }
}
