package com.example.project_orderfood;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.project_orderfood.entity.Food;
import com.example.project_orderfood.entity.OrderDetail;
import com.example.project_orderfood.entity.User;
import com.example.project_orderfood.model.UserDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class SigninActivity extends AppCompatActivity {
    private EditText etPhone, etPassword;
    private UserDAO dao;
    ArrayList<OrderDetail> orderDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        dao = new UserDAO();

    }

    //VALIDATE AND SIGNIN
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
                if(phone.isEmpty()){
                    Toast.makeText(getApplicationContext(), getString(R.string.err_msg_empty), Toast.LENGTH_LONG).show();
                    requestFocus(etPhone);
                } else if(!dao.isExistedPhone(phone)){
                    Toast.makeText(getApplicationContext(), getString(R.string.err_msg_not_exist), Toast.LENGTH_LONG).show();
                    requestFocus(etPhone);
                } else if(password.isEmpty()){
                    Toast.makeText(getApplicationContext(), getString(R.string.err_msg_empty), Toast.LENGTH_LONG).show();
                    requestFocus(etPassword);
                } else {
                    User user = dao.getUser(phone);
                    if(!password.equals(user.getPassword())){
                        Toast.makeText(getApplicationContext(), getString(R.string.err_msg_wrong_password), Toast.LENGTH_LONG).show();
                        requestFocus(etPassword);
                    } else {
                        orderDetails=new ArrayList<>();
                        Toast.makeText(getApplicationContext(), getString(R.string.noti_signin), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), Home.class);
                        Common.currentUser = user;
                        SharedPreferences.Editor editor=getApplicationContext().getSharedPreferences("MyPre",MODE_PRIVATE).edit();
                        editor.putInt("userID",user.getUserId());
                        startActivity(intent);
                        finish();
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


}
