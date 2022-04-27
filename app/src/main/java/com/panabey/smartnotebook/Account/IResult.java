package com.panabey.smartnotebook.Account;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface IResult {
    void notifySuccessPatch(JSONObject jsonObject);
    void notifyError(VolleyError error);
}
