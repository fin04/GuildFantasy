package com.epriest.game.guildfantasy.util;

import android.content.Context;
import android.content.Intent;

import java.io.Serializable;

public class UIntent {
	private static UIntent SINGLE_U;

	private UIntent() {
	}

	public static WIntent with(Context pCon, Class<?> _class) {
		if (SINGLE_U == null) {
			SINGLE_U = new UIntent();
		}
		return SINGLE_U.get(pCon, _class);
	}

	private WIntent get(Context pCon, Class<?> _class) {
		return new WIntent(pCon, _class);
	}

	public static WIntent with(Context pCon, Class<?> _class, int id, String action) {
		if (SINGLE_U == null) {
			SINGLE_U = new UIntent();
		}
		return SINGLE_U.get(pCon, _class, id, action);
	}

	private WIntent get(Context pCon, Class<?> _class, int id, String action) {
		return new WIntent(pCon, _class, id, action);
	}

	public static WIntent with(Context pCon, Class<?> _class, int id, int idSub, String action) {
		if (SINGLE_U == null) {
			SINGLE_U = new UIntent();
		}
		return SINGLE_U.get(pCon, _class, id, idSub, action);
	}

	private WIntent get(Context pCon, Class<?> _class, int id, int idSub, String action) {
		return new WIntent(pCon, _class, id, idSub, action);
	}

	public static WIntent with(Intent intent) {
		if (SINGLE_U == null) {
			SINGLE_U = new UIntent();
		}
		return SINGLE_U.get(intent);
	}

	private WIntent get(Intent intent) {
		return new WIntent(intent);
	}

	public static final String UId = "UId";
	public static final String UIdMain = "UIdMain";
	public static final String UIdSub = "UIdSub";
	public static final String UIndex = "UIndex";
	public static final String UType = "UType";
	public static final String UState = "UState";
	public static final String UEntity = "UEntity";
	public static final String UInt1 = "UInt1";
	public static final String UInt2 = "UInt2";
	public static final String UInt3 = "UInt3";
	public static final String UInt4 = "UInt4";
	public static final String UInt5 = "UInt5";
	public static final String UInt6 = "UInt6";
	public static final String UString1 = "UString1";
	public static final String UString2 = "UString2";
	public static final String UString3 = "UString3";
	public static final String UPhoneNumber = "UPhoneNumber";
	public static final String UStateStr = "UStateStr";
	public static final String UPackageName = "UPackageName";
	public static final String ULong1 = "ULong1";
	public static final String ULong2 = "ULong2";
	public static final String ULong3 = "ULong3";
	public static final String UFloat1 = "UFloat1";
	public static final String UFloat2 = "UFloat2";
	public static final String UFloat3 = "UFloat3";
	public static final String UDouble1 = "UDouble1";
	public static final String UDouble2 = "UDouble2";
	public static final String UDouble3 = "UDouble3";
	public static final String UBoolean1 = "UBoolean1";
	public static final String UBoolean2 = "UBoolean2";
	public static final String UBoolean3 = "UBoolean3";

	public class WIntent {
		private Intent mIntent;

		private WIntent(Intent intent) {
			mIntent = intent;
		}

		private WIntent(Context pCon, Class<?> _class) {
			mIntent = new Intent(pCon, _class);
		}

		private WIntent(Context pCon, Class<?> _class, int id, String action) {
			mIntent = new Intent(pCon, _class);
			mIntent.setAction(action);
			addId(id);
		}

		private WIntent(Context pCon, Class<?> _class, int id, int idSub, String action) {
			mIntent = new Intent(pCon, _class);
			mIntent.setAction(action);
			addId(id * 10 + idSub);
			addIdMain(id);
			addIdSub(idSub);
		}

		public Intent getIntent() {
			return mIntent;
		}

		public String getAction() {
			if (mIntent != null) {
				return mIntent.getAction();
			}
			return "";
		}

		public WIntent addId(int id) {
			mIntent.putExtra(UId, id);
			return this;
		}

		/**
		 * @return default -1
		 */
		public int getId() {
			return mIntent.getIntExtra(UId, -1);
		}

		public WIntent addIdMain(int idMain) {
			mIntent.putExtra(UIdMain, idMain);
			return this;
		}

		/**
		 * @return default -1
		 */
		public int getIdMain() {
			return mIntent.getIntExtra(UIdMain, -1);
		}

		public WIntent addIdSub(int idSub) {
			mIntent.putExtra(UIdSub, idSub);
			return this;
		}

		/**
		 * @return default -1
		 */
		public int getIdSub() {
			return mIntent.getIntExtra(UIdSub, -1);
		}

		public WIntent addIndex(int index) {
			mIntent.putExtra(UIndex, index);
			return this;
		}

		/**
		 * @return default -1
		 */
		public int getIndex() {
			return mIntent.getIntExtra(UIndex, -1);
		}

		public WIntent addType(int type) {
			mIntent.putExtra(UType, type);
			return this;
		}

		/**
		 * @return default -1
		 */
		public int getType() {
			return mIntent.getIntExtra(UType, -1);
		}

		public int getType(int defaultValue) {
			return mIntent.getIntExtra(UType, defaultValue);
		}

		public WIntent addState(int state) {
			mIntent.putExtra(UState, state);
			return this;
		}

		/**
		 * @return default -1
		 */
		public int getState() {
			return mIntent.getIntExtra(UState, -1);
		}

		public int getState(int defaultValue) {
			return mIntent.getIntExtra(UState, defaultValue);
		}

		public Object getSerial() {
			return mIntent.getSerializableExtra(UEntity);
		}

		public WIntent addSerial(Serializable enty) {
			mIntent.putExtra(UEntity, enty);
			return this;
		}

		public WIntent addInt1(int int1) {
			mIntent.putExtra(UInt1, int1);
			return this;
		}

		public int getInt1() {
			return mIntent.getIntExtra(UInt1, 0);
		}

		public int getInt1(int defaultValue) {
			return mIntent.getIntExtra(UInt1, defaultValue);
		}

		public WIntent addInt2(int int2) {
			mIntent.putExtra(UInt2, int2);
			return this;
		}

		public int getInt2() {
			return mIntent.getIntExtra(UInt2, 0);
		}

		public int getInt2(int defaultValue) {
			return mIntent.getIntExtra(UInt2, defaultValue);
		}

		public WIntent addInt3(int int3) {
			mIntent.putExtra(UInt3, int3);
			return this;
		}

		public int getInt3() {
			return mIntent.getIntExtra(UInt3, 0);
		}

		public int getInt3(int defaultValue) {
			return mIntent.getIntExtra(UInt3, defaultValue);
		}

		public WIntent addInt4(int int4) {
			mIntent.putExtra(UInt4, int4);
			return this;
		}

		public int getInt4() {
			return mIntent.getIntExtra(UInt4, 0);
		}

		public int getInt4(int defaultValue) {
			return mIntent.getIntExtra(UInt4, defaultValue);
		}

		public WIntent addInt5(int int5) {
			mIntent.putExtra(UInt5, int5);
			return this;
		}

		public int getInt5() {
			return mIntent.getIntExtra(UInt5, 0);
		}

		public int getInt5(int defaultValue) {
			return mIntent.getIntExtra(UInt5, defaultValue);
		}

		public WIntent addInt6(int int6) {
			mIntent.putExtra(UInt6, int6);
			return this;
		}

		public int getInt6() {
			return mIntent.getIntExtra(UInt6, 0);
		}

		public int getInt6(int defaultValue) {
			return mIntent.getIntExtra(UInt6, defaultValue);
		}

		public WIntent addString1(String string1) {
			mIntent.putExtra(UString1, string1);
			return this;
		}

		public String getString1() {
			return mIntent.getStringExtra(UString1);
		}

		public WIntent addString2(String string2) {
			mIntent.putExtra(UString2, string2);
			return this;
		}

		public String getString2() {
			return mIntent.getStringExtra(UString2);
		}

		public WIntent addString3(String string3) {
			mIntent.putExtra(UString3, string3);
			return this;
		}

		public String getString3() {
			return mIntent.getStringExtra(UString3);
		}

		public WIntent addPhoneNumber(String phoneNumber) {
			mIntent.putExtra(UPhoneNumber, phoneNumber);
			return this;
		}

		public String getPhoneNumber() {
			return mIntent.getStringExtra(UPhoneNumber);
		}

		public WIntent addStateStr(String stateStr) {
			mIntent.putExtra(UStateStr, stateStr);
			return this;
		}

		public String getStateStr() {
			return mIntent.getStringExtra(UStateStr);
		}
		
		public WIntent addPackageName(String stateStr) {
			mIntent.putExtra(UPackageName, stateStr);
			return this;
		}

		public String getPackageName() {
			return mIntent.getStringExtra(UPackageName);
		}
		
		public WIntent addLong1(long long1) {
			mIntent.putExtra(ULong1, long1);
			return this;
		}

		public long getLong1() {
			return mIntent.getLongExtra(ULong1, 0);
		}

		public WIntent addLong2(long long2) {
			mIntent.putExtra(ULong2, long2);
			return this;
		}

		public long getLong2() {
			return mIntent.getLongExtra(ULong2, 0);
		}

		public WIntent addLong3(long long3) {
			mIntent.putExtra(ULong3, long3);
			return this;
		}

		public long getLong3() {
			return mIntent.getLongExtra(ULong3, 0);
		}

		public WIntent addFloat1(Float float1) {
			mIntent.putExtra(UFloat1, float1);
			return this;
		}

		public float getFloat1() {
			return mIntent.getFloatExtra(UFloat1, 0);
		}

		public WIntent addFloat2(Float float2) {
			mIntent.putExtra(UFloat2, float2);
			return this;
		}

		public float getFloat() {
			return mIntent.getFloatExtra(UFloat2, 0);
		}

		public WIntent addFloat3(Float float3) {
			mIntent.putExtra(UFloat3, float3);
			return this;
		}

		public float getFloat3() {
			return mIntent.getFloatExtra(UFloat3, 0);
		}

		public WIntent addDouble1(Double double1) {
			mIntent.putExtra(UDouble1, double1);
			return this;
		}

		public double getDouble1() {
			return mIntent.getDoubleExtra(UDouble1, 0);
		}

		public WIntent addDouble2(Double double2) {
			mIntent.putExtra(UDouble2, double2);
			return this;
		}

		public double getDouble() {
			return mIntent.getDoubleExtra(UDouble2, 0);
		}

		public WIntent addDouble3(Double double3) {
			mIntent.putExtra(UDouble3, double3);
			return this;
		}

		public double getDouble3() {
			return mIntent.getDoubleExtra(UDouble3, 0);
		}

		public WIntent addBoolean1(Boolean boolean1) {
			mIntent.putExtra(UBoolean1, boolean1);
			return this;
		}

		public boolean getBoolean1() {
			return mIntent.getBooleanExtra(UBoolean1, false);
		}

		public WIntent addBoolean2(Boolean boolean2) {
			mIntent.putExtra(UBoolean2, boolean2);
			return this;
		}

		public boolean getBoolean2() {
			return mIntent.getBooleanExtra(UBoolean2, false);
		}

		public WIntent addBoolean3(Boolean boolean3) {
			mIntent.putExtra(UBoolean3, boolean3);
			return this;
		}

		public boolean getBoolean3() {
			return mIntent.getBooleanExtra(UBoolean3, false);
		}
	}
}
