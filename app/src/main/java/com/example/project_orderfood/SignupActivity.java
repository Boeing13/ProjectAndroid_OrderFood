package com.example.project_orderfood;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.project_orderfood.Common.Common;
import com.example.project_orderfood.entity.User;
import com.example.project_orderfood.model.UserDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    private EditText etName, etPhone, etPassword;
    private UserDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        dao = new UserDAO();

    }

    //VALIDATE AND SIGNUP
    public void signup(View view) throws SQLException {
        final ProgressDialog mDialog = new ProgressDialog(SignupActivity.this);
        mDialog.setMessage("Please waiting");
        mDialog.show();
        new CountDownTimer(1000, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                mDialog.dismiss();
                String phone = etPhone.getText().toString();
                String password = etPassword.getText().toString();
                String name = etName.getText().toString();
                if(name.isEmpty()){
                    Toast.makeText(getApplicationContext(), getString(R.string.err_msg_empty), Toast.LENGTH_LONG).show();
                    requestFocus(etName);
                } else if(phone.isEmpty()){
                    Toast.makeText(getApplicationContext(), getString(R.string.err_msg_empty), Toast.LENGTH_LONG).show();
                    requestFocus(etPhone);
                } else if(dao.isExistedPhone(phone)){
                    Toast.makeText(getApplicationContext(), getString(R.string.err_msg_phone_existed), Toast.LENGTH_LONG).show();
                    requestFocus(etPhone);
                    User user = new User(phone, name, password, false);
                } else if(password.isEmpty()){
                    Toast.makeText(getApplicationContext(), getString(R.string.err_msg_empty), Toast.LENGTH_LONG).show();
                    requestFocus(etPassword);
                } else {
                    User user = new User(phone, name, password, false);
                    dao.insertUser(user);
                    Common.currentUser = user;
                    Toast.makeText(getApplicationContext(), getString(R.string.noti_signup), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), Home.class);
                    startActivity(intent);
                }
            }
        }.start();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


}
