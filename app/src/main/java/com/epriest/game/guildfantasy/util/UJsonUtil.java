package com.epriest.game.guildfantasy.util;

import android.content.ContentValues;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class UJsonUtil {
	private static UJsonUtil SINGLE_U;

	private UJsonUtil() {
	}

	public static WJsonArray withArray(JSONArray jsonArray) {
		if (SINGLE_U == null) {
			SINGLE_U = new UJsonUtil();
		}
		return SINGLE_U.getArray(jsonArray);
	}

	private WJsonArray getArray(JSONArray jsonArray) {
		return new WJsonArray(jsonArray);
	}

	public class WJsonArray {
		private ArrayList<ContentValues> mContentValuesList;

		private WJsonArray(JSONArray jsonArray) {
			try {
				mContentValuesList = new ArrayList<ContentValues>();
				for (int i = 0; i < jsonArray.length(); i++) {
					mContentValuesList.add(UJsonUtil.withObject(jsonArray.getJSONObject(i)).get_contentValues());
				}
			} catch (JSONException e) {
				e.printStackTrace();
				mContentValuesList = null;
			}
		}

		public ArrayList<ContentValues> getContentValuesList() {
			return mContentValuesList;
		}
	}

	public static WJsonObject withObject(String jsonString) {
		if (SINGLE_U == null) {
			SINGLE_U = new UJsonUtil();
		}
		return SINGLE_U.getString(jsonString);
	}

	private WJsonObject getString(String jsonString) {
		return new WJsonObject(jsonString);
	}

	public static WJsonObject withObject(JSONObject jsonObject) {
		if (SINGLE_U == null) {
			SINGLE_U = new UJsonUtil();
		}
		return SINGLE_U.getObject(jsonObject);
	}

	private WJsonObject getObject(JSONObject jsonObject) {
		return new WJsonObject(jsonObject);
	}

	public class WJsonObject {
		private ContentValues mContentValues;

		private WJsonObject(JSONObject jsonObject) {
			makeContentValues(jsonObject);
		}

		public void makeContentValues(JSONObject jsonObject) {
			try {
				mContentValues = new ContentValues();
				Iterator iter = jsonObject.keys();
				while (iter.hasNext()) {
					String key = (String) iter.next();
					mContentValues.put(key, jsonObject.getString(key));
				}
			} catch (JSONException e) {
				e.printStackTrace();
				mContentValues = null;
			}
		}

		private WJsonObject(String jsonString) {
			try {
				makeContentValues(new JSONObject(jsonString));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		public ContentValues get_contentValues() {
			return mContentValues;
		}
	}

	public static WJsonContentValues withContentValues(ContentValues contentValues) {
		if (SINGLE_U == null) {
			SINGLE_U = new UJsonUtil();
		}
		return SINGLE_U.getContentValues(contentValues);
	}

	private WJsonContentValues getContentValues(ContentValues contentValues) {
		return new WJsonContentValues(contentValues);
	}

	public class WJsonContentValues {
		private JSONObject mJSONObject;

		private WJsonContentValues(ContentValues contentValues) {
			try {
				mJSONObject = new JSONObject();
				Set<Entry<String, Object>> set = contentValues.valueSet();
				Iterator<Entry<String, Object>> it = set.iterator();

				while (it.hasNext()) {
					Map.Entry<String, Object> e = (Map.Entry<String, Object>) it.next();
					mJSONObject.put(e.getKey(), e.getValue());
				}
			} catch (JSONException e) {
				e.printStackTrace();
				mJSONObject = null;
			}
		}

		public JSONObject getJson() {
			return mJSONObject;
		}

		public String getString() {
			if (mJSONObject != null) {
				return mJSONObject.toString();
			}
			return null;
		}
	}

	public static ArrayList<ContentValues> getValueList(String head, Object object) throws JSONException {
		ArrayList<ContentValues> reData = new ArrayList<ContentValues>();
		if (object instanceof JSONObject) {
			ContentValues contValue = UJsonUtil.withObject((JSONObject) object).get_contentValues();
			reData.add(contValue);
		} else if (object instanceof JSONArray) {
			JSONArray jsonArray = (JSONArray) object;
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				reData.add(UJsonUtil.withObject(jsonObject).get_contentValues());
			}
		}
		return reData;
	}

	public static JSONObject addJson(JSONObject inputJson, String key, String value) throws JSONException {
		JSONObject reJson = new JSONObject();
		if (inputJson != null) {
			reJson = inputJson;
		}
		reJson.put(key, value);
		return reJson;
	}

	public static JSONObject addJson(JSONObject inputJson, String key, ContentValues contentValue) throws JSONException {
		JSONObject reJson = new JSONObject();
		if (inputJson != null) {
			reJson = inputJson;
		}
		reJson.put(key, UJsonUtil.withContentValues(contentValue).getJson());
		return reJson;
	}

	public static JSONObject addJson_Array(JSONObject inputJson, String key, ArrayList<String> arrayList) throws JSONException {
		JSONObject reJson = new JSONObject();
		if (inputJson != null) {
			reJson = inputJson;
		}
		reJson.put(key, getJson(arrayList));
		return reJson;
	}

	public static JSONObject addJson(JSONObject inputJson, String key, ArrayList<ContentValues> contentValueList) throws JSONException {
		JSONObject reJson = new JSONObject();
		if (inputJson != null) {
			reJson = inputJson;
		}
		reJson.put(key, getJsonArray(contentValueList));

		return reJson;
	}

	private static JSONArray getJsonArray(ArrayList<ContentValues> valueList) throws JSONException {
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < valueList.size(); i++) {
			JSONObject tempJson = UJsonUtil.withContentValues((ContentValues) valueList.get(i)).getJson();
			jsonArray.put(i, tempJson);
		}
		return jsonArray;
	}

	private static JSONArray getJson(ArrayList<String> arrayList) throws JSONException {
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < arrayList.size(); i++) {
			jsonArray.put(arrayList.get(i));
		}
		return jsonArray;
	}
//	public void setJson(JSONObject json) {
//	JSONObject jsonObject = new JSONObject(jsonString);
//	HashMap<String, String> reHash = new HashMap<String, String>();
//	Iterator iter = jsonObject.keys();
//	while (iter.hasNext()) {
//		String key = (String) iter.next();
//		String value = jsonObject.getString(key);
//		reHash.put(key, value);
//	}
//	return reHash;
//}
//
//public static HashMap<String, String> getHashMap(String jsonString) throws JSONException {
//	JSONObject jsonObject = new JSONObject(jsonString);
//	HashMap<String, String> reHash = new HashMap<String, String>();
//	Iterator iter = jsonObject.keys();
//	while (iter.hasNext()) {
//		String key = (String) iter.next();
//		String value = jsonObject.getString(key);
//		reHash.put(key, value);
//	}
//	return reHash;
//}
//
//

//public static ArrayList<ContentValues> getContentValuesList(String head, String jsonString) throws JSONException {
//	ArrayList<ContentValues> reHashList = getContentList(head, jsonString);
//	return reHashList;
//}
//
//public static ArrayList<ContentValues> getHashMap_ContentValueList(JSONObject jsonObjectArray, String head)
//		throws JSONException {
//	ArrayList<ContentValues> reHashList = getHashMap_ContentValueList(jsonObjectArray.toString(), head);
//	return reHashList;
//}
//
//public static JSONObject getJson_ContentValues(String head, ArrayList<ContentValues> contentValuesList)
//		throws JSONException {
//	JSONArray jsonArray = getJsonArray(contentValuesList);
//	JSONObject reArrayJson = new JSONObject();
//	reArrayJson.put(head, jsonArray);
//	return reArrayJson;
//}

//public static HashMap<String, String> getHashMap(JSONObject jsonObject) throws JSONException {
//	HashMap<String, String> reHash = new HashMap<String, String>();
//	Iterator iter = jsonObject.keys();
//	while (iter.hasNext()) {
//		String key = (String) iter.next();
//		String value = jsonObject.getString(key);
//		reHash.put(key, value);
//	}
//	return reHash;
//}
//
//public static HashMap<String, String> getHashMap(JSONObject jsonObjectArray, String head) throws JSONException {
//	JSONObject json = new JSONObject(jsonObjectArray.toString());
//	JSONObject jsonObject = json.getJSONObject(head);
//
//	HashMap<String, String> reHash = new HashMap<String, String>();
//	Iterator iter = jsonObject.keys();
//	while (iter.hasNext()) {
//		String key = (String) iter.next();
//		String value = jsonObject.getString(key);
//		reHash.put(key, value);
//	}
//	return reHash;
//}
//
//public static ArrayList<HashMap<String, String>> getHashMap_ArrayList(JSONObject jsonObjectArray, String head)
//		throws JSONException {
//	ArrayList<HashMap<String, String>> reHashList = getHashMap_ArrayList(jsonObjectArray.toString(), head);
//	return reHashList;
//}
//
//public static ArrayList<HashMap<String, String>> getHashMap_ArrayList(String jsonString, String head)
//		throws JSONException {
//	ArrayList<HashMap<String, String>> reHashList = new ArrayList<HashMap<String, String>>();
//	JSONObject json = new JSONObject(jsonString);
//	JSONArray jsonArray = json.getJSONArray(head);
//
//	for (int i = 0; i < jsonArray.length(); i++) {
//		JSONObject jsonObject = jsonArray.getJSONObject(i);
//		HashMap<String, String> hashList = new HashMap<String, String>();
//		Iterator iter = jsonObject.keys();
//		while (iter.hasNext()) {
//			String key = (String) iter.next();
//			String value = jsonObject.getString(key);
//			hashList.put(key, value);
//		}
//		reHashList.add(hashList);
//	}
//	return reHashList;
//}
//
//public static HashMap<String, String> getHashMapKey(String key, JSONObject jsonObject) throws JSONException {
//	JSONObject reJson = jsonObject.getJSONObject(key);
//	return getHashMap(reJson);
//}
//
//public static ArrayList<HashMap<String, String>> getHashMapKey_ArrayList(String key, JSONObject jsonInput)
//		throws JSONException {
//	JSONArray jsonArray = jsonInput.getJSONArray(key);
//	ArrayList<HashMap<String, String>> reHashList = new ArrayList<HashMap<String, String>>();
//
//	for (int i = 0; i < jsonArray.length(); i++) {
//		JSONObject jsonObject = jsonArray.getJSONObject(i);
//		HashMap<String, String> hashList = new HashMap<String, String>();
//		Iterator iter = jsonObject.keys();
//		while (iter.hasNext()) {
//			String reKey = (String) iter.next();
//			String value = jsonObject.getString(reKey);
//			hashList.put(reKey, value);
//		}
//		reHashList.add(hashList);
//	}
//	return reHashList;
//}
//
//public static JSONObject getJson(HashMap<String, String> hashIn) throws JSONException {
//	JSONObject reJson = new JSONObject();
//	Set<Entry<String, String>> set = hashIn.entrySet();
//	Iterator<Entry<String, String>> it = set.iterator();
//
//	while (it.hasNext()) {
//		Map.Entry<String, String> e = (Map.Entry<String, String>) it.next();
//		reJson.put(e.getKey(), e.getValue());
//	}
//	return reJson;
//}
//
//public static JSONObject getJson(String head, ArrayList<HashMap<String, String>> hashList) throws JSONException {
//	JSONArray jsonArray = new JSONArray();
//	for (int i = 0; i < hashList.size(); i++) {
//		JSONObject tempJson = new JSONObject();
//		Set<Entry<String, String>> set = hashList.get(i).entrySet();
//		Iterator<Entry<String, String>> it = set.iterator();
//
//		while (it.hasNext()) {
//			Map.Entry<String, String> e = (Map.Entry<String, String>) it.next();
//			tempJson.put(e.getKey(), e.getValue());
//		}
//		jsonArray.put(i, tempJson);
//	}
//	JSONObject reArrayJson = new JSONObject();
//	reArrayJson.put(head, jsonArray);
//	return reArrayJson;
//}
//
//public static JSONObject getJson(String headerKey, HashMap<String, String> header, String paramSetKey,
//		HashMap<String, String> paramSet, String dataSetKey, ArrayList<HashMap<String, String>> dataSetList)
//		throws JSONException {
//	JSONObject reJson = new JSONObject();
//	reJson.put(headerKey, getJson(header));
//	reJson.put(paramSetKey, getJson(paramSet));
//
//	JSONArray jsonArrayData = new JSONArray();
//	for (int i = 0; i < dataSetList.size(); i++) {
//		JSONObject tempJson = new JSONObject();
//		Set<Entry<String, String>> set = dataSetList.get(i).entrySet();
//		Iterator<Entry<String, String>> it = set.iterator();
//
//		while (it.hasNext()) {
//			Map.Entry<String, String> e = (Map.Entry<String, String>) it.next();
//			tempJson.put(e.getKey(), e.getValue());
//		}
//		jsonArrayData.put(i, tempJson);
//	}
//
//	reJson.put(dataSetKey, jsonArrayData);
//	return reJson;
//}

//public static String getJson_String(HashMap<String, String> hashIn) throws JSONException {
//	JSONObject reJson = new JSONObject();
//	Set<Entry<String, String>> set = hashIn.entrySet();
//	Iterator<Entry<String, String>> it = set.iterator();
//
//	while (it.hasNext()) {
//		Map.Entry<String, String> e = (Map.Entry<String, String>) it.next();
//		reJson.put(e.getKey(), e.getValue());
//	}
//	return reJson.toString();
//}
}
