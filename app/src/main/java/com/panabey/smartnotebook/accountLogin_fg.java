package com.panabey.smartnotebook;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Класс авторазиции в аккаунт.
 * Использует Firebase.
 */
public class accountLogin_fg extends Fragment {

//    private FirebaseAuth mAuth;

    private TextInputEditText username, password;
    private TextView InfoEmpty;

    @Override
    public void onCreate(Bundle savedInstanceState) {
//        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
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

//    private void loginUsers(String Email, String Password){
//        mAuth.signInWithEmailAndPassword(Email, Password).addOnSuccessListener(authResult -> {
//            Toast.makeText(getContext(), "Вход выполнен успешно!", Toast.LENGTH_SHORT).show();
//            ReplaceFragment(new account());
//        });
//    }

    private JSONObject LoginIn(String username, String password){
        JSONObject json = new JSONObject();
        try{
            json.put("username", username);
            json.put("password", password);
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return json;
    }

    private void ReplaceFragment(Fragment newFragment ){
        FragmentTransaction transaction =
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragmentPager, newFragment)
                        .addToBackStack(null);
        transaction.commit();
    }
}