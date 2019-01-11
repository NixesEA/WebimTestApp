package com.nixesea.webimtestapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Presenter.View {
    ImageView userPhoto;
    TextView userName;

    Button btnAuth;
    RecyclerView recyclerView;

    Activity activity;
    Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;

        recyclerView = findViewById(R.id.friendList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        userPhoto = findViewById(R.id.userPhoto);
        userName = findViewById(R.id.userName);

        btnAuth = findViewById(R.id.btnAuth);
        btnAuth.setOnClickListener(this);

        presenter = new Presenter(this);
        checkLogged();
    }

    public void checkLogged() {
        if (!VKSdk.isLoggedIn()) {
            btnAuth.setVisibility(View.VISIBLE);
            userName.setVisibility(View.GONE);
            userPhoto.setVisibility(View.GONE);
        }else {
            btnAuth.setVisibility(View.GONE);
            userName.setVisibility(View.VISIBLE);
            userPhoto.setVisibility(View.VISIBLE);

            presenter.getCurrentUserInfo();
            presenter.getFiveFriend();
        }
    }

    @Override
    public void updateFriendList(ArrayList<UserModel> friendsList) {
        mAdapter adapter = new mAdapter(activity, friendsList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void updateCurrentUserInfo(String fullName, String photoURL) {
        userName.setText(fullName);
        Glide.with(activity)
                .load(photoURL)
                .into(userPhoto);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                Toast.makeText(getApplicationContext(), R.string.successful_auth, Toast.LENGTH_LONG).show();
                checkLogged();
            }

            @Override
            public void onError(VKError error) {
                Toast.makeText(getApplicationContext(), R.string.error_auth, Toast.LENGTH_LONG).show();
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onClick(View view) {
        if (!VKSdk.isLoggedIn()) {
            VKSdk.login(this, VKScope.FRIENDS, VKScope.OFFLINE);
        }
    }
}
