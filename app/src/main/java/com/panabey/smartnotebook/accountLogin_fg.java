package com.panabey.smartnotebook;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.android.material.textfield.TextInputEditText;
import com.panabey.smartnotebook.Account.AccountManager.WebRequest;
import com.panabey.smartnotebook.Account.IResult;
import com.panabey.smartnotebook.Account.User;
import com.panabey.smartnotebook.Account.account;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Класс авторазиции в аккаунт.
 * Использует Firebase.
 */
public class accountLogin_fg extends Fragment {

    private TextInputEditText username, password;
    private TextView InfoEmpty;

    private IResult ResultCallback = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    void initVolleyCallback(){
        ResultCallback = new IResult() {

            @Override
            public void notifySuccessPatch(JSONObject jsonObject) {
                try {
                    if ((int)jsonObject.get("ErrorCode") == 0) {
                        User.Username = (String)jsonObject.get("username");
                        User.FirstName = (String)jsonObject.get("firstname");
                        User.LastName = (String)jsonObject.get("lastname");
                        User.DateJoin = (String)jsonObject.get("date_joined");
                        User.isLogin = true;
                        ReplaceFragment(new account());
                    }
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_account_login_fg, container, false);

        username = view.findViewById(R.id.layoutEmail);
        password = view.findViewById(R.id.layoutPassword);

        Button loginButton = view.findViewById(R.id.buttonLoginAccount);
        InfoEmpty = view.findViewById(R.id.CheckEmpty);

        loginButton.setOnClickListener(v -> {
            InfoEmpty.setText("");

            String usernameID = username.getText().toString();
            String passwordID = password.getText().toString();

            if (usernameID.isEmpty() || passwordID.isEmpty()){
                InfoEmpty.setText(R.string.checkTextEmpty);
            }
            else{
                LoginIn(usernameID, passwordID);
            }
        });

        TextView onClickRegister = view.findViewById(R.id.textViewRegisterFragment);
        onClickRegister.setOnClickListener(v -> {
            ReplaceFragment(new accountRegister_fg());
        });
        return view;
    }

    private void LoginIn(String username, String password){
        JSONObject json = new JSONObject();
        try{
            json.put("username", username);
            json.put("password_user", password);

        }
        catch (JSONException e){
            e.printStackTrace();
        }
        WebRequest webRequest = new WebRequest(ResultCallback, getContext());
        webRequest.LoginIn(json);
        initVolleyCallback();
    }

    private void ReplaceFragment(Fragment newFragment ){
        FragmentTransaction transaction =
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragmentPager, newFragment)
                        .addToBackStack(null);
        transaction.commit();
    }
}