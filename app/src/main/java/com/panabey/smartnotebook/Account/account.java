package com.panabey.smartnotebook.Account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.panabey.smartnotebook.R;

import org.json.JSONException;
import org.json.JSONObject;

public class account extends Fragment {
    private IResult ResultCallback = null;

    public account(IResult ResultCallback){
        this.ResultCallback = ResultCallback;
    }

    void initVolleyCallback(){
        ResultCallback = new IResult() {

            @Override
            public void notifySuccessPatch(JSONObject jsonObject) {
                try {
                     Username.setText(jsonObject.get("username").toString());
//                     Joined_data.setText(jsonObject.get().toString());
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notifyError(VolleyError error) {
                Log.d("Json Error", "Volley ERROR JSON post" + error.toString());
            }
        };
    }

    private TextView Username, FullName, Joined_data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        Username = view.findViewById(R.id.textViewUsername);
//        Joined_data = view.findViewById(R.id.textViewDate);
        initVolleyCallback();
        return view;
    }
}