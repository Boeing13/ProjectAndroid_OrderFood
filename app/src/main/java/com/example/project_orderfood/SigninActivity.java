package com.example.project_orderfood;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project_orderfood.Common.Common;
import com.example.project_orderfood.entity.User;
import com.example.project_orderfood.model.UserDAO;

import java.sql.SQLException;

public class SigninActivity extends AppCompatActivity {

    private EditText etPhone, etPassword;
    private UserDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        dao = new UserDAO();

        Intent intent = getIntent();
        String phone = intent.getStringExtra("phone");
        String password = intent.getStringExtra("password");
        etPhone.setText(phone);
        etPassword.setText(password);

    }

    public void signIn(View view) throws SQLException {
        final ProgressDialog mDialog = new ProgressDialog(SigninActivity.this);
        mDialog.setMessage("Please waiting");
        mDialog.show();
        new CountDownTimer(1000, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                mDialog.dismiss();
                String phone = etPhone.getText().toString();
                String password = etPassword.getText().toString();
                if(!validatePhone(phone)){
                    return;
                } else {
                    User user = dao.getUser(phone);
                    if(!validatePassword(password, user)){
                        return;
                    } else {
                        signInSuccess(user);
                    }
                }
            }
        }.start();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean validatePhone(String phone) {
        if (phone.isEmpty()) {
            Toast.makeText(getApplicationContext(), getString(R.string.err_msg_empty), Toast.LENGTH_LONG).show();
            requestFocus(etPhone);
            return false;
        } else if (!dao.isExistedPhone(phone)) {
            Toast.makeText(getApplicationContext(), getString(R.string.err_msg_not_exist), Toast.LENGTH_LONG).show();
            requestFocus(etPhone);
            return false;
        }
        return true;
    }

    private boolean validatePassword(String password, User user) {
        if (password.isEmpty()) {
            Toast.makeText(getApplicationContext(), getString(R.string.err_msg_empty), Toast.LENGTH_LONG).show();
            requestFocus(etPassword);
            return false;
        } else {
            if (!password.equals(user.getPassword())) {
                Toast.makeText(getApplicationContext(), getString(R.string.err_msg_wrong_password), Toast.LENGTH_LONG).show();
                requestFocus(etPassword);
                return false;
            }
        }
        return true;
    }

    private void signInSuccess(User user){
        Toast.makeText(getApplicationContext(), getString(R.string.noti_signin), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), Home.class);
        Common.currentUser = user;
        startActivity(intent);
        finish();
    }

}
