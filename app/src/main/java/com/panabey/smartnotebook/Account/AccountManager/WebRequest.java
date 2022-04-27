package com.panabey.smartnotebook.Account.AccountManager;


import android.content.Context;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.panabey.smartnotebook.Account.IResult;

import org.json.JSONObject;


public class WebRequest{

    IResult ResultCallback = null;

    private final String URL = "http://localhost:8000/api/";
    // private final String URL = "http://192.168.130.36:8000/api/";
//    private final String URL = "http://192.168.43.81:8000/api/";
    private Context context;

    public WebRequest(IResult ResultCallback,Context context){
        this.ResultCallback = ResultCallback;
        this.context = context;
    }

    /**
     * Первоночальный вход в аккаунта
     * @param jsonObject - объект json с входящими параметрами
     */
    public void LoginIn(JSONObject jsonObject) {
        String url = URL + "auth/login";
        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, response -> {
            Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT);
            if(ResultCallback != null)
                ResultCallback.notifySuccessPatch(response);

        }, error -> {
            Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT);
            if(ResultCallback != null)
                ResultCallback.notifyError(error);
        });
//        jsonObjectRequest.setShouldCache(false);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1.0f));
        queue.add(jsonObjectRequest);
    }

    /**
     * Выход из аккаунта
     * @param context - context activity
     */
//    public static void Logout(Context context){
//        String url = URL + "auth/login";
//
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
//            Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();
//        }, error -> {
//            Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
//        });
//    }

}
