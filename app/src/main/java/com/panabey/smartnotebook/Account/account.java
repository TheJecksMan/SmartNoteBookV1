package com.panabey.smartnotebook.Account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.panabey.smartnotebook.R;

import java.text.SimpleDateFormat;
import java.util.Date;


public class account extends Fragment {
    private IResult ResultCallback = null;

    private TextView Username, FullName, Joined_data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        Username = view.findViewById(R.id.textViewUsername);
        FullName = view.findViewById(R.id.textViewFullName);
        Joined_data = view.findViewById(R.id.textViewDate);

        Username.setText("Привет! " + User.Username);
        FullName.setText(User.FirstName + " " + User.LastName);
        Joined_data.setText("Дата регистрации: " + User.DateJoin);
        return view;
    }
}