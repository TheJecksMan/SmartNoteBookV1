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
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.panabey.smartnotebook.Account.account;

public class accountLogin_fg extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private TextInputEditText email, password;
    private TextView InfoEmpty;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_account_login_fg, container, false);

        email = view.findViewById(R.id.layoutEmail);
        password = view.findViewById(R.id.layoutPassword);

        Button loginButton = view.findViewById(R.id.buttonLoginAccount);
        InfoEmpty = view.findViewById(R.id.CheckEmpty);

        loginButton.setOnClickListener(v -> {
            InfoEmpty.setText("");

            String emailID = email.getText().toString();
            String passwordID = password.getText().toString();

            if (emailID.isEmpty() || passwordID.isEmpty())
            {
               InfoEmpty.setText(R.string.checkTextEmpty);
            }
            else{
                loginUsers(emailID, passwordID);
            }
            loginUsers(emailID, passwordID);
        });

        TextView onClickRegister = view.findViewById(R.id.textViewRegisterFragment);
        onClickRegister.setOnClickListener(v -> {

            //Переход между фрагментами регистрации
            Fragment newFragment = new accountRegister_fg();
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragmentPager, newFragment)
                    .addToBackStack(null);
            transaction.commit();
        });
        return view;
    }
    private void loginUsers(String Email, String Password){
        mAuth.signInWithEmailAndPassword(Email, Password).addOnSuccessListener(authResult -> {
            Toast.makeText(getContext(), "Вход выполнен успешно!", Toast.LENGTH_SHORT).show();

            Fragment newFragment = new account();
            FragmentTransaction transaction =
                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.fragmentPager, newFragment)
                            .addToBackStack(null);
            transaction.commit();
        });
    }
}