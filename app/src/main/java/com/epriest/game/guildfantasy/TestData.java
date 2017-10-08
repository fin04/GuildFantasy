package com.epriest.game.guildfantasy;

/**
 * Created by darka on 2017-03-30.
 */

public class TestData {

    /*public static UserEnty setTestPlayerData(Context context){
        UserEnty enty = new UserEnty();
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
                    enty1.iconid = 1;
                    enty1._class = "기사";
                    enty1.race = "인간";
                    enty1.equip = new EquipEnty();
                    enty1.equip.equip_main_arms = "목검";
                    enty1.equip.equip_sub_arms = "나무방패";
                    enty1.equip.equip_head = "두건";
                    enty1.equip.equip_body = "가죽갑옷";
                    enty1.status = new StatusEnty();
                    enty1.status.LEVEL = 1;
                    enty1.status.EXP = 0;
                    enty1.status.STR = 15;
                    enty1.status.DEX = 11;
                    enty1.status.INT = 10;
                    enty1.status.VIT = 12;
                    enty1.status.HP = 17;
                    enty1.status.MP = 10;
                    break;
                case 1:
                    enty1.id = "0001";
                    enty1.name = "터너";
                    enty1.iconid = 3;
                    enty1._class = "검사";
                    enty1.race = "인간";
                    enty1.equip = new EquipEnty();
                    enty1.equip.equip_main_arms = "목검";
                    enty1.equip.equip_sub_arms = "";
                    enty1.equip.equip_head = "";
                    enty1.equip.equip_body = "천갑옷";
                    enty1.status = new StatusEnty();
                    enty1.status.LEVEL = 1;
                    enty1.status.EXP = 0;
                    enty1.status.STR = 16;
                    enty1.status.DEX = 10;
                    enty1.status.INT = 10;
                    enty1.status.VIT = 12;
                    enty1.status.HP = 14;
                    enty1.status.MP = 10;
                    break;
                case 2:
                    enty1.id = "0002";
                    enty1.name = "크리스";
                    enty1.iconid = 5;
                    enty1._class = "마법사";
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
                    enty1.status.INT = 15;
                    enty1.status.VIT = 10;
                    enty1.status.HP = 10;
                    enty1.status.MP = 16;
                    break;
                case 3:
                    enty1.id = "0003";
                    enty1.name = "유키";
                    enty1.iconid = 7;
                    enty1._class = "도적";
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
                    enty1.status.DEX = 15;
                    enty1.status.INT = 12;
                    enty1.status.VIT = 13;
                    enty1.status.HP = 11;
                    enty1.status.MP = 11;
                    break;
                case 4:
                    enty1.id = "0004";
                    enty1.name = "미라이";
                    enty1.iconid = 9;
                    enty1._class = "사제";
                    enty1.race = "인간";
                    enty1.equip = new EquipEnty();
                    enty1.equip.equip_main_arms = "지팡이";
                    enty1.equip.equip_sub_arms = "";
                    enty1.equip.equip_head = "";
                    enty1.equip.equip_body = "가죽갑옷";
                    enty1.status = new StatusEnty();
                    enty1.status.LEVEL = 1;
                    enty1.status.EXP = 0;
                    enty1.status.STR = 11;
                    enty1.status.DEX = 10;
                    enty1.status.INT = 14;
                    enty1.status.VIT = 12;
                    enty1.status.HP = 12;
                    enty1.status.MP = 14;
                    break;
                case 5:
                    enty1.id = "0005";
                    enty1.name = "루시";
                    enty1.iconid = 11;
                    enty1._class = "검사";
                    enty1.race = "엘프";
                    enty1.equip = new EquipEnty();
                    enty1.equip.equip_main_arms = "목검";
                    enty1.equip.equip_sub_arms = "";
                    enty1.equip.equip_head = "";
                    enty1.equip.equip_body = "천갑옷";
                    enty1.status = new StatusEnty();
                    enty1.status.LEVEL = 1;
                    enty1.status.EXP = 0;
                    enty1.status.STR = 15;
                    enty1.status.DEX = 11;
                    enty1.status.INT = 10;
                    enty1.status.VIT = 12;
                    enty1.status.HP = 13;
                    enty1.status.MP = 10;
                    break;
                case 6:
                    enty1.id = "0006";
                    enty1.name = "엘렌";
                    enty1.iconid = 13;
                    enty1._class = "정찰병";
                    enty1.race = "엘프";
                    enty1.equip = new EquipEnty();
                    enty1.equip.equip_main_arms = "단검";
                    enty1.equip.equip_sub_arms = "";
                    enty1.equip.equip_head = "";
                    enty1.equip.equip_body = "망토";
                    enty1.status = new StatusEnty();
                    enty1.status.LEVEL = 1;
                    enty1.status.EXP = 0;
                    enty1.status.STR = 11;
                    enty1.status.DEX = 15;
                    enty1.status.INT = 12;
                    enty1.status.VIT = 10;
                    enty1.status.HP = 12;
                    enty1.status.MP = 12;
                    break;
                case 7:
                    enty1.id = "0007";
                    enty1.name = "테피";
                    enty1.iconid = 14;
                    enty1._class = "기사";
                    enty1.race = "드워프";
                    enty1.equip = new EquipEnty();
                    enty1.equip.equip_main_arms = "목검";
                    enty1.equip.equip_sub_arms = "";
                    enty1.equip.equip_head = "빨간모자";
                    enty1.equip.equip_body = "망토";
                    enty1.status = new StatusEnty();
                    enty1.status.LEVEL = 1;
                    enty1.status.EXP = 0;
                    enty1.status.STR = 14;
                    enty1.status.DEX = 10;
                    enty1.status.INT = 10;
                    enty1.status.VIT = 15;
                    enty1.status.HP = 17;
                    enty1.status.MP = 10;
                    break;
                case 8:
                    enty1.id = "0008";
                    enty1.name = "휴리";
                    enty1.iconid = 16;
                    enty1._class = "도적";
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
                    enty1.status.DEX = 15;
                    enty1.status.INT = 10;
                    enty1.status.VIT = 11;
                    enty1.status.HP = 13;
                    enty1.status.MP = 10;
                    break;
                case 9:
                    enty1.id = "0009";
                    enty1.name = "야옹이";
                    enty1.iconid = 18;
                    enty1._class = "마법사";
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
                    enty1.status.INT = 16;
                    enty1.status.VIT = 10;
                    enty1.status.HP = 10;
                    enty1.status.MP = 17;
                    break;
                case 10:
                    enty1.id = "0010";
                    enty1.name = "보라";
                    enty1.iconid = 19;
                    enty1._class = "사제";
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
                    enty1.status.INT = 13;
                    enty1.status.VIT = 12;
                    enty1.status.HP = 14;
                    enty1.status.MP = 15;
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
                    enty.type = "chase";
                    enty.difficult = 2;
                    enty.needAP = 1;
                    enty.maxPlayer = 3;
                    enty.pic = "mon_00.png";
                    enty.text = "마을 인근에 출몰하는 좀비들이 가축들을 잡아가고 있어요. 사례는 적지만 도와주세요.";
                    enty.tip = "마법사,사제와 함께 가면 쉽습니다.";
                    break;
                case 1:
                    enty.id = "002";
                    enty.title = "늑대 사냥";
                    enty.type = "sweep";
                    enty.difficult = 5;
                    enty.needAP = 1;
                    enty.maxPlayer = 4;
                    enty.pic = "mon_01.png";
                    enty.text = "언덕에 늑대들이 나타나 목동들을 공격하고 있습니다. 도와주세요.";
                    enty.tip = "도적,사냥꾼과 함께 가면 쉽습니다.";
                    break;
                case 2:
                    enty.id = "003";
                    enty.title = "고블린 동굴 소탕";
                    enty.type = "destruct";
                    enty.difficult = 8;
                    enty.needAP = 2;
                    enty.maxPlayer = 5;
                    enty.pic = "mon_02.png";
                    enty.text = "마을 인근을 약탈하는 고블린들의 소굴을 소탕해주십시요. 사례는 얼마 못드립니다.";
                    enty.tip = "전사,도적과 함께 가면 어렵지 않을 것입니다.";
                    break;
            }
            entyList.add(enty);
        }
        return entyList;
    }*/
}
