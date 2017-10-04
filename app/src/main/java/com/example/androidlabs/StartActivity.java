package com.example.androidlabs;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StartActivity extends Activity {

    protected static final String ACTIVITY_NAME = "StartActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(ACTIVITY_NAME, "In onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        final Button lIButton = findViewById(R.id.listButton);
        lIButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, ListItemsActivity.class);
                startActivityForResult(intent,10);
            }
           });

    }

    public void onActivityResult(int requestCode, int responseCode, Intent data){
        if(requestCode==10)Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult");
        if(responseCode==Activity.RESULT_OK){
            String messagePassed = data.getStringExtra("Response");
            Log.i(ACTIVITY_NAME, messagePassed);
            CharSequence text = "ListItemsActivity passed: My information to share";
            Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT); //this is the ListActivity
            toast.show(); //display your message box
        }
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
