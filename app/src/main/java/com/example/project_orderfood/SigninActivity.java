package com.example.project_orderfood;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project_orderfood.entity.User;
import com.example.project_orderfood.model.UserDAO;

import java.sql.SQLException;

public class SigninActivity extends AppCompatActivity {

    private Button btnSignin;
    private EditText etPhone, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        btnSignin = findViewById(R.id.btnSignin);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);

    }

    public void signIn(View view) throws SQLException {
        ProgressDialog mDialog = new ProgressDialog(SigninActivity.this);
        mDialog.setMessage("Please waiting");
        mDialog.show();
        String phone = etPhone.getText().toString();
        String password = etPassword.getText().toString();


    }
}
