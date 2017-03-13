package com.demo.mob.utils;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HashUtils
{
  public <T> HashMap<String, T> fromJson(String jsonStr)
  {
    if (TextUtils.isEmpty(jsonStr)) {
      return new HashMap();
    }
    try
    {
      if ((jsonStr.startsWith("[")) && (jsonStr.endsWith("]"))) {
        jsonStr = "{\"fakelist\":" + jsonStr + "}";
      }
      JSONObject json = new JSONObject(jsonStr);
      return fromJson(json);
    }
    catch (Throwable t)
    {
      
    }
    return new HashMap();
  }
  
  private <T> HashMap<String, T> fromJson(JSONObject json)
    throws JSONException
  {
    HashMap<String, T> map = new HashMap();
    Iterator<String> iKey = json.keys();
    while (iKey.hasNext())
    {
      String key = (String)iKey.next();
      Object value = json.opt(key);
      if (JSONObject.NULL.equals(value)) {
        value = null;
      }
      if (value != null)
      {
        if ((value instanceof JSONObject)) {
          value = fromJson((JSONObject)value);
        } else if ((value instanceof JSONArray)) {
          value = fromJson((JSONArray)value);
        }
        map.put(key, (T) value);
      }
    }
    return map;
  }
  
  private ArrayList<Object> fromJson(JSONArray array)
    throws JSONException
  {
    ArrayList<Object> list = new ArrayList();
    int i = 0;
    for (int size = array.length(); i < size; i++)
    {
      Object value = array.opt(i);
      if ((value instanceof JSONObject)) {
        value = fromJson((JSONObject)value);
      } else if ((value instanceof JSONArray)) {
        value = fromJson((JSONArray)value);
      }
      list.add(value);
    }
    return list;
  }
  
  public <T> String fromHashMap(HashMap<String, T> map)
  {
    try
    {
      JSONObject obj = getJSONObject(map);
      if (obj == null) {
        return "";
      }
      return obj.toString();
    }
    catch (Throwable t)
    {
    }
    return "";
  }
  
  private <T> JSONObject getJSONObject(HashMap<String, T> map)
    throws JSONException
  {
    JSONObject json = new JSONObject();
    for (Entry<String, T> entry : map.entrySet())
    {
      Object value = entry.getValue();
      if ((value instanceof HashMap)) {
        value = getJSONObject((HashMap)value);
      } else if ((value instanceof ArrayList)) {
        value = getJSONArray((ArrayList)value);
      } else if (isBasicArray(value)) {
        value = getJSONArray(arrayToList(value));
      }
      json.put((String)entry.getKey(), value);
    }
    return json;
  }
  
  private boolean isBasicArray(Object value)
  {
    return ((value instanceof byte[])) || ((value instanceof short[])) || ((value instanceof int[])) || ((value instanceof long[])) || ((value instanceof float[])) || ((value instanceof double[])) || ((value instanceof char[])) || ((value instanceof boolean[])) || ((value instanceof String[]));
  }
  
  private ArrayList<?> arrayToList(Object value)
  {
    if ((value instanceof byte[]))
    {
      ArrayList<Byte> list = new ArrayList();
      for (byte item : (byte[])value) {
        list.add(Byte.valueOf(item));
      }
      return list;
    }
    if ((value instanceof short[]))
    {
      ArrayList<Short> list = new ArrayList();
      for (short item : (short[])value) {
        list.add(Short.valueOf(item));
      }
      return list;
    }
    if ((value instanceof int[]))
    {
      ArrayList<Integer> list = new ArrayList();
      for (int item : (int[])value) {
        list.add(Integer.valueOf(item));
      }
      return list;
    }
    if ((value instanceof long[]))
    {
      ArrayList<Long> list = new ArrayList();
      for (long item : (long[])value) {
        list.add(Long.valueOf(item));
      }
      return list;
    }
    if ((value instanceof float[]))
    {
      ArrayList<Float> list = new ArrayList();
      for (float item : (float[])value) {
        list.add(Float.valueOf(item));
      }
      return list;
    }
    if ((value instanceof double[]))
    {
      ArrayList<Double> list = new ArrayList();
      for (double item : (double[])value) {
        list.add(Double.valueOf(item));
      }
      return list;
    }
    if ((value instanceof char[]))
    {
      ArrayList<Character> list = new ArrayList();
      for (char item : (char[])value) {
        list.add(Character.valueOf(item));
      }
      return list;
    }
    if ((value instanceof boolean[]))
    {
      ArrayList<Boolean> list = new ArrayList();
      for (boolean item : (boolean[])value) {
        list.add(Boolean.valueOf(item));
      }
      return list;
    }
    if ((value instanceof String[]))
    {
      ArrayList<String> list = new ArrayList();
      for (String item : (String[])value) {
        list.add(item);
      }
      return list;
    }
    return null;
  }
  
  private JSONArray getJSONArray(ArrayList<?> arrayList)
    throws JSONException
  {
    JSONArray array = new JSONArray();
    for (Object value : arrayList)
    {
      if ((value instanceof HashMap)) {
        value = getJSONObject((HashMap)value);
      } else if ((value instanceof ArrayList)) {
        value = getJSONArray((ArrayList)value);
      }
      array.put(value);
    }
    return array;
  }
  
  public String format(String jsonStr)
  {
    try
    {
      return format("", fromJson(jsonStr));
    }
    catch (Throwable t)
    {
    }
    return "";
  }
  
  private String format(String sepStr, HashMap<String, Object> map)
  {
    StringBuffer sb = new StringBuffer();
    sb.append("{\n");
    String mySepStr = sepStr + "\t";
    int i = 0;
    for (Entry<String, Object> entry : map.entrySet())
    {
      if (i > 0) {
        sb.append(",\n");
      }
      sb.append(mySepStr).append('"').append((String)entry.getKey()).append("\":");
      Object value = entry.getValue();
      if ((value instanceof HashMap)) {
        sb.append(format(mySepStr, (HashMap)value));
      } else if ((value instanceof ArrayList)) {
        sb.append(format(mySepStr, (ArrayList)value));
      } else if ((value instanceof String)) {
        sb.append('"').append(value).append('"');
      } else {
        sb.append(value);
      }
      i++;
    }
    sb.append('\n').append(sepStr).append('}');
    return sb.toString();
  }
  
  private String format(String sepStr, ArrayList<Object> list)
  {
    StringBuffer sb = new StringBuffer();
    sb.append("[\n");
    String mySepStr = sepStr + "\t";
    int i = 0;
    for (Object value : list)
    {
      if (i > 0) {
        sb.append(",\n");
      }
      sb.append(mySepStr);
      if ((value instanceof HashMap)) {
        sb.append(format(mySepStr, (HashMap)value));
      } else if ((value instanceof ArrayList)) {
        sb.append(format(mySepStr, (ArrayList)value));
      } else if ((value instanceof String)) {
        sb.append('"').append(value).append('"');
      } else {
        sb.append(value);
      }
      i++;
    }
    sb.append('\n').append(sepStr).append(']');
    return sb.toString();
  }
}
