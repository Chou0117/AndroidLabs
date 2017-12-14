package com.example.androidlabs;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class MessageDetails extends Activity {
    Bundle arg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        arg = getIntent().getExtras();

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        MessageFragment mf = new MessageFragment();

        mf.setArguments(arg);
        transaction.replace(R.id.phoneFrame, mf);
        transaction.commit();

    }


}
