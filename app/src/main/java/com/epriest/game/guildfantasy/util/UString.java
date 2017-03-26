package com.epriest.game.guildfantasy.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UString {
	public static String TYPE_STRING = "string";
	public static String TYPE_JSON = "json";
	public static String TYPE_ENCODING_EUCKR = "euc-kr";
	private static UString singleton;

	public static WString with(String string) {
		if (singleton == null) {
			singleton = new UString();
		}
		return singleton.getString(string);
	}

	private WString getString(String string) {
		return new WString(string);
	}

	public static WString with(InputStream inputStream) {
		if (singleton == null) {
			singleton = new UString();
		}
		return singleton.getString(inputStream);
	}

	private WString getString(InputStream inputStream) {
		return new WString(inputStream);
	}

	public static WString with(InputStream inputStream, String UString_Type) {
		if (singleton == null) {
			singleton = new UString();
		}
		return singleton.getString(inputStream, UString_Type);
	}

	private WString getString(InputStream inputStream, String UString_Type) {
		return new WString(inputStream, UString_Type);
	}

	public class WString {
		private String mString;

		private WString(String string) {
			mString = string;
		}

		private WString(InputStream inputStream) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			StringBuilder builder = new StringBuilder();
			String line;
			try {
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
				mString = builder.toString();
			} catch (Exception e) {
				e.printStackTrace();
				mString = null;
			} finally {
				try {
					inputStream.close();
				} catch (IOException e) {
				}
			}
		}

		private WString(InputStream inputStream, String UString_Type) {
			String line = null;
			StringBuilder stringBuilder = new StringBuilder();
			try {
				BufferedReader reader = null;
				if (!(UString_Type != null && UString_Type.length() != 0)) {
					reader = new BufferedReader(new InputStreamReader(inputStream));
				} else if (UString_Type.equals(TYPE_ENCODING_EUCKR)) {
					reader = new BufferedReader(new InputStreamReader(inputStream, "euc-kr"));
				}
				while ((line = reader.readLine()) != null) {
					stringBuilder.append(line + "\n");
				}
				if (stringBuilder.length() > 0) {
					mString = stringBuilder.substring(0, stringBuilder.length() - 1);
				}

			} catch (Exception e) {
				e.printStackTrace();
				mString = null;
			} finally {
				try {
					inputStream.close();
				} catch (IOException e) {
				}
			}
		}

		public String get() {
			return mString;
		}

		/**
		 * @param fileName
		 *            http://asdf/asdf.pdf
		 * @return pdf
		 */
		public String getExtenstion_url() {
			String split[] = mString.split("\\.");
			return split[split.length - 1].replace(".", "");
		}

		/**
		 * @param url
		 *            http://www.asfd.com/filename.pdf
		 * @return filename.pdf
		 */
		public String getFileName_url() {
			String split[] = mString.split("/");
			return split[split.length - 1].replace("/", "");
		}

		/**
		 * 중간에 있는 텍스트를 찾아 주는 것
		 * 
		 * @param startWord
		 *            가나
		 * @param endWord
		 *            마
		 * @return 다라
		 */
		public String getFind_inside(String startWord, String endWord) {
			String reData = null;
			try {
				int startIndex = mString.indexOf(startWord);
				int endIndex = mString.indexOf(endWord);
				if (startIndex >= 0 && endIndex >= 0) {
					reData = mString.substring(startIndex + startWord.length(), endIndex);
				}
			} catch (Exception e) {
			}
			return reData;
		}

		public int getFind_count(String wordFind) {
			int reData = 0;
			if (mString != null && mString.length() != 0) {
				mString = mString + " ";
				String[] strArry = mString.split(wordFind);
				if (strArry.length > 0) {
					reData = strArry.length - 1;
				}
			}
			return reData;
		}

		public String getMD5() {
			String redata = null;
			StringBuffer md5 = new StringBuffer();
			try {
				byte[] digest = java.security.MessageDigest.getInstance("MD5").digest(mString.getBytes());
				for (int i = 0; i < digest.length; i++) {
					md5.append(Integer.toString((digest[i] & 0xf0) >> 4, 16));
					md5.append(Integer.toString(digest[i] & 0x0f, 16));
					redata = md5.toString();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return redata;
		}

		public ArrayList<String> getList_split(String separator) {
			if (mString != null) {
				ArrayList<String> reList = new ArrayList<String>();
				String temp[] = mString.split(separator);
				if (temp.length > 0 && !(temp.length == 1 && temp[0].length() == 0)) {
					for (int i = 0; i < temp.length; i++) {
						reList.add(temp[i]);
					}
				}
				return reList;
			}
			return null;
		}

		public ArrayList<String> getList_splitTrim(String separator) {
			if (mString != null) {
				ArrayList<String> reList = new ArrayList<String>();
				String temp[] = mString.split(separator);
				if (temp.length > 0 && !(temp.length == 1 && temp[0].length() == 0)) {
					for (int i = 0; i < temp.length; i++) {
						reList.add(temp[i].trim());
					}
				}
				return reList;
			}
			return null;
		}
		
		public boolean isValid() {
			if (mString != null && mString.length() != 0) {
				return true;
			}
			return false;
		}

		public boolean isValid_not() {
			if (mString != null && mString.length() != 0) {
				return false;
			}
			return true;
		}

		public boolean isValid_email() {
			Pattern p = Pattern.compile("^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$");
			Matcher m = p.matcher(mString);
			return m.matches();
		}

		public boolean isValid_mac() {
			Pattern p = Pattern.compile("^([0-9a-fA-F][0-9a-fA-F]:){5}([0-9a-fA-F][0-9a-fA-F])$");
			Matcher m = p.matcher(mString);
			return m.matches();
		}

		public boolean isNull() {
			if (mString == null) {
				return true;
			}
			return false;
		}

		public boolean isNull_not() {
			if (mString != null) {
				return true;
			}
			return false;
		}
	}

	public static WArrayList withArrayList(ArrayList<String> arrayList) {
		if (singleton == null) {
			singleton = new UString();
		}
		return singleton.getArrayList(arrayList);
	}

	private WArrayList getArrayList(ArrayList<String> arrayList) {
		return new WArrayList(arrayList);
	}

	public class WArrayList {
		private ArrayList<String> mArrayList;

		public WArrayList(ArrayList<String> arrayList) {
			mArrayList = arrayList;
		}

		public String[] getArray() {
			try {
				if (mArrayList.size() != 0) {
					Object[] objectArray = mArrayList.toArray();
					return Arrays.asList(objectArray).toArray(new String[objectArray.length]);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		public ArrayList<String> getList_sort() {
			HashSet<String> hashSet = new HashSet<String>(mArrayList);
			ArrayList<String> afterList = new ArrayList<String>(hashSet);
			Collections.sort(afterList);
			return afterList;
		}

		public String get_empty() {
			String reData = "";
			if (mArrayList == null) {
				return null;
			}
			for (int i = 0; i < mArrayList.size(); i++) {
				reData = reData + mArrayList.get(i);
			}
			return reData;
		}
	}

	public static WArray withArray(String[] array) {
		if (singleton == null) {
			singleton = new UString();
		}
		return singleton.getArray(array);
	}

	private WArray getArray(String[] array) {
		return new WArray(array);
	}

	public class WArray {
		private String[] mArray;

		public WArray(String[] array) {
			mArray = array;
		}

		public ArrayList<String> getList() {
			ArrayList<String> reData = new ArrayList<String>();
			for (int i = 0; i < mArray.length; i++) {
				reData.add(mArray[i]);
			}
			return reData;
		}
	}

	public static ArrayList<String> getSplitSpace_Sort(String text) {
		ArrayList<String> keyTextList = new ArrayList<String>();
		ArrayList<String> keyTextNew = UString.with(text).getList_split("\n");
		for (int i = 0; i < keyTextNew.size(); i++) {
			ArrayList<String> keyTextSpace = UString.with(keyTextNew.get(i)).getList_splitTrim(" ");
			for (int j = 0; j < keyTextSpace.size(); j++) {
				keyTextList.add(keyTextSpace.get(j));
			}
		}

		return UString.withArrayList(keyTextList).getList_sort();
	}

	public static ArrayList<String> getList_sort(ArrayList<HashMap<String, String>> reHashList, String key) {
		ArrayList<String> reList = new ArrayList<String>();
		for (int i = 0; i < reHashList.size(); i++) {
			reList.add(reHashList.get(i).get(key));
		}
		reList = UString.withArrayList(reList).getList_sort();
		return reList;
	}

}
