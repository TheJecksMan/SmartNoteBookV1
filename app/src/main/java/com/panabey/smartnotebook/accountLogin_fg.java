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

    private Button LoginButton;
    private TextInputEditText email, password;
    private TextView InfoEmpty;
    private TextView OnClickRegister;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_login_fg, container, false);

        email = (TextInputEditText) view.findViewById(R.id.layoutEmail);
        password = (TextInputEditText) view.findViewById(R.id.layoutPassword);

        LoginButton = view.findViewById(R.id.buttonLoginAccount);
        InfoEmpty = view.findViewById(R.id.CheckEmpty);


        OnClickRegister = view.findViewById(R.id.textViewRegisterFragment);
        OnClickRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                FragmentManager frag = getChildFragmentManager();
                frag.popBackStack();

                Fragment newFragment = new accountRegister_fg();
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

                transaction.replace(R.id.FrameLayoutAccount, newFragment);

                transaction.commit();

                 */
            }
        });

        mAuth = FirebaseAuth.getInstance();

        LoginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String emailID = email.getText().toString();
                String passwordID = password.getText().toString();

                if (emailID.isEmpty() || passwordID.isEmpty())
                {
                   InfoEmpty.setText(R.string.checkTextEmpty);
                }
            }
        });

        return view;
    }


}