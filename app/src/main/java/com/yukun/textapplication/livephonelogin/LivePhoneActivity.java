package com.yukun.textapplication.livephonelogin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yukun.textapplication.MyApp;
import com.yukun.textapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LivePhoneActivity extends AppCompatActivity implements BasePresentImpl.View {

    @BindView(R.id.phoneNumber)
    EditText phoneNumber;
    @BindView(R.id.sendNum)
    Button sendNum;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.getnum)
    EditText getnum;
    @BindView(R.id.getgetuserinfo)
    Button getgetuserinfo;
    private LoginPresent loginPresent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_phone);
        ButterKnife.bind(this);


        loginPresent = new LoginPresent(this);
        setListener();
    }

    private void setListener() {
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(LivePhoneActivity.this, msg + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void doNext() {
        Log.i("----userMyApp", MyApp.getInstance().getUser().toString());
    }

    @OnClick({R.id.sendNum, R.id.login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sendNum:
                loginPresent.sendPhoneNum(phoneNumber.getText().toString());
                break;
            case R.id.login:
                loginPresent.login(phoneNumber.getText().toString(), getnum.getText().toString());
                break;
        }
    }

    @OnClick(R.id.getgetuserinfo)
    public void onClick() {
        loginPresent.getUserInfo();
    }
}
