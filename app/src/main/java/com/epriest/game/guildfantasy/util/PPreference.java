package com.epriest.game.guildfantasy.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.epriest.game.guildfantasy.enty.MemberEnty;
import com.epriest.game.guildfantasy.enty.PlayerEnty;
import com.google.gson.Gson;

import java.util.ArrayList;

public class PPreference {
    public Context pCon;
    public SharedPreferences pSharedPref;

    public PPreference(Context pCon) {
        this.pCon = pCon;
        pSharedPref = PreferenceManager.getDefaultSharedPreferences(pCon);
    }

    public PPreference(Context pCon, String name) {
        this.pCon = pCon;
        pSharedPref = pCon.getSharedPreferences(name, 0);
    }

    public String read(String key) {
        return pSharedPref.getString(key, "");
    }

    public String read(String key, String defaultString) {
        return pSharedPref.getString(key, defaultString);
    }

    public void write(String key, String value) {
        SharedPreferences.Editor sharedEditor = pSharedPref.edit();
        sharedEditor.putString(key, value);
        sharedEditor.commit();
    }

    public ArrayList<String> read_list(String key) {
        String str = read(key, null);
        if (str != null) {
            return UString.with(str).getList_split("####");
        }
        return null;
    }

    public void write_list(String key, ArrayList<String> strList) {
        String value = null;
        if (strList != null) {
            value = "";
            for (int i = 0; i < strList.size(); i++) {
                if (i == strList.size() - 1) {
                    value = value + strList.get(i);
                } else {
                    value = value + strList.get(i) + "####";
                }
            }
        }
        write(key, value);
    }

    public int read(String key, int initInt) {
        return pSharedPref.getInt(key, initInt);
    }

    public void write(String key, int value) {
        SharedPreferences.Editor sharedEditor = pSharedPref.edit();
        sharedEditor.putInt(key, value);
        sharedEditor.commit();
    }

    public boolean read(String key, boolean flag) {
        return pSharedPref.getBoolean(key, flag);
    }

    public void write(String key, boolean flag) {
        SharedPreferences.Editor sharedEditor = pSharedPref.edit();
        sharedEditor.putBoolean(key, flag);
        sharedEditor.commit();
    }

    public float read(String key, float value) {
        return pSharedPref.getFloat(key, value);
    }

    public void write(String key, float value) {
        SharedPreferences.Editor sharedEditor = pSharedPref.edit();
        sharedEditor.putFloat(key, value);
        sharedEditor.commit();
    }

    public double read(String key, double value) {
        return pSharedPref.getFloat(key, (float) value);
    }

    public void write(String key, double value) {
        SharedPreferences.Editor sharedEditor = pSharedPref.edit();
        sharedEditor.putFloat(key, (float) value);
        sharedEditor.commit();
    }

    public long read(String key, long value) {
        return pSharedPref.getLong(key, value);
    }

    public void write(String key, long value) {
        SharedPreferences.Editor sharedEditor = pSharedPref.edit();
        sharedEditor.putLong(key, value);
        sharedEditor.commit();
    }

    public void reverseBoolean(String key, boolean defaultBool) {
        if (read(key, defaultBool)) {
            write(key, false);
        } else {
            write(key, true);
        }
    }

    /*public ArrayList<MemberEnty> readPlayList(String key) {
        ArrayList<MemberEnty> entyList = new ArrayList();
        Gson gson = new Gson();
        String str = read(key, null);
        if (str != null) {
            ArrayList<String> reList = new ArrayList<String>();
            String temp[] = str.split("####");
            if (temp.length > 0 && !(temp.length == 1 && temp[0].length() == 0)) {
                for (int i = 0; i < temp.length; i++) {
                    entyList.add(gson.fromJson(temp[i], MemberEnty.class));
                }
            }
            return entyList;
        }
        return null;
    }*/

    /*public void writePlayList(String key, ArrayList<MemberEnty> playerList) {
        Gson gson = new Gson();
        String value = null;
        if (playerList != null) {
            value = "";
            for (int i = 0; i < playerList.size(); i++) {
                if (i == playerList.size() - 1) {
                    value += gson.toJson(playerList.get(i));
                } else {
                    value += gson.toJson(playerList.get(i)) + "####";
                }
            }
        }
        write(key, value);
    }*/

    public PlayerEnty readPlayer(String key) {
        Gson gson = new Gson();
        String str = read(key, null);
        PlayerEnty enty = gson.fromJson(str, PlayerEnty.class);
        return enty;
    }

    public void writePlayer(String key, PlayerEnty enty) {
        Gson gson = new Gson();
        String value = gson.toJson(enty);
        write(key, value);
    }
}
