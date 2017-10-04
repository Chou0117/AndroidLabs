package com.example.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {

    protected static final String ACTIVITY_NAME = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(ACTIVITY_NAME, "In onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences defaultEmail = getSharedPreferences("Default Email",MODE_PRIVATE);
        SharedPreferences.Editor editor = defaultEmail.edit();
        defaultEmail.getString("Default Email", "email@domain.com");
        editor.commit();

        final Button lButton = findViewById(R.id.loginButton);
        lButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });
//
//        SharedPreferences sharedPref = new SharedPreferences() {
//            SharedPreferences.Editor editor = sharedPref.edit();
//           sharedPreferences.getString(“DefaultEmail”, “email@domain.com”);
//            editor.commit();
//        };
//        EditText edt = findViewById(R.id.loginText);
//        String xyz = edt.getText().toString();
//        SharedPreferences loginID = getSharedPreferences("Login ID",MODE_PRIVATE);
//        loginID.edit().putInt(xyz,1);
//        edt.setHint(loginID.getInt("",1));
//
    }

    @Override
    public void onResume(){
        Log.i(ACTIVITY_NAME, "In onResume()");
        super.onResume();
    }
    @Override
    public void onStart(){
        Log.i(ACTIVITY_NAME, "In onStart()");
        super.onStart();
    }
    @Override
    public void onPause(){
        Log.i(ACTIVITY_NAME, "In onPause()");
        super.onPause();
    }
    @Override
    public void onStop(){
        Log.i(ACTIVITY_NAME, "In onStop()");
        super.onStop();
    }
    @Override
    public void onDestroy(){
        Log.i(ACTIVITY_NAME, "In onDestroy()");
        super.onDestroy();
    }
}
