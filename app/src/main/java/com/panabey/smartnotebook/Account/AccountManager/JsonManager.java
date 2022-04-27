package com.panabey.smartnotebook.Account.AccountManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class JsonManager {
    private Map<String, String> Json;
    private StringBuilder JsonString;

    private  JSONObject jsonObject;

    public JsonManager (Map<String, String> Json){
        this.Json = Json;
    }

    public JsonManager(){
        this.Json = null;
    }

    public String EncodeJsonObject(){
        JsonString = new StringBuilder("{");
        for (String key: Json.keySet())
        {
            JsonString.append(key + ":" + Json.get(key) + ", ");
        }
        JsonString.delete(JsonString.length()-2, JsonString.length()).append("}");
        return JsonString.toString();
    }

    public  String DecodeJsonObject(){
        jsonObject = new JSONObject();
        return jsonObject.toString();
    }

    public String getStringJson(String key){
        try{
            return jsonObject.get("key").toString();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return  null;
    }
}
