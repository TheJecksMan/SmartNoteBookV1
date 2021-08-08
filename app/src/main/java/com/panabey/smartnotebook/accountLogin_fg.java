package com.panabey.smartnotebook;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class accountLogin_fg extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private TextInputEditText email, password;
    private TextView InfoEmpty;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_login_fg, container, false);

        email = view.findViewById(R.id.layoutEmail);
        password = view.findViewById(R.id.layoutPassword);

        Button loginButton = view.findViewById(R.id.buttonLoginAccount);
        InfoEmpty = view.findViewById(R.id.CheckEmpty);


        TextView onClickRegister = view.findViewById(R.id.textViewRegisterFragment);
        onClickRegister.setOnClickListener(v -> {
            /*
            FragmentManager frag = getChildFragmentManager();
            frag.popBackStack();

            Fragment newFragment = new accountRegister_fg();
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

            transaction.replace(R.id.FrameLayoutAccount, newFragment);

            transaction.commit();

             */
        });

        //mAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(v -> {
            String emailID = email.getText().toString();
            String passwordID = password.getText().toString();

            if (emailID.isEmpty() || passwordID.isEmpty())
            {
               InfoEmpty.setText(R.string.checkTextEmpty);
            }
        });

        return view;
    }


}