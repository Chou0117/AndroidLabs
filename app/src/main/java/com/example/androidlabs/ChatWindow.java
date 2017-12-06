package com.example.androidlabs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "ChatWindowActivity";

    ArrayList<String> chatMessage = new ArrayList<>();
    ListView chatWindow;
    EditText chatEditText;
    ChatAdapter messageAdapter;
    Button chatSendButton;

    ChatDatabaseHelper tempDBH;
    SQLiteDatabase db;
    ContentValues values = new ContentValues();
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        chatWindow = findViewById(R.id.chatWindow);
        chatEditText = findViewById(R.id.chatEditText);
        chatSendButton = findViewById(R.id.chatSendButton);
        messageAdapter = new ChatAdapter(this);
        tempDBH = new ChatDatabaseHelper(getApplicationContext());

//        db = tempDBH.getWritableDatabase();
//        db.delete(tempDBH.MESSAGE_TABLE,null,null);

        db = tempDBH.getReadableDatabase();
        cursor = db.query(tempDBH.MESSAGE_TABLE, tempDBH.Column_Names,
                null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                chatMessage.add(cursor.getString(1));
                Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(tempDBH.KEY_MESSAGE)));
                cursor.moveToNext();
            }
            Log.i(ACTIVITY_NAME, "Cursor’s  column count =" + cursor.getColumnCount());
        }

        db = tempDBH.getWritableDatabase();
        chatSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputMSG = chatEditText.getText().toString();
                chatMessage.add(inputMSG);

                values.put(tempDBH.Column_Names[1], inputMSG);
                db.insert(tempDBH.MESSAGE_TABLE, null, values);

                messageAdapter.notifyDataSetChanged();
                chatEditText.setText("");
            }
        });

        chatWindow.setAdapter(messageAdapter);
    }

    private class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount() {
            return chatMessage.size();
        }

        public String getItem(int position) {
            return chatMessage.get(position);
        }

        public View getView(int position, View convertView, ViewGroup Parent) {
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result;
            if (position % 2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);

            TextView message = result.findViewById(R.id.message_text);
            message.setText(getItem(position));
            return result;
        }
    }

    @Override
    public void onResume() {
        Log.i(ACTIVITY_NAME, "In onResume()");
        super.onResume();
    }

    @Override
    public void onStart() {
        Log.i(ACTIVITY_NAME, "In onStart()");
        super.onStart();
    }

    @Override
    public void onPause() {
        Log.i(ACTIVITY_NAME, "In onPause()");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i(ACTIVITY_NAME, "In onStop()");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.i(ACTIVITY_NAME, "In onDestroy()");
        super.onDestroy();
        db.close();
    }

}
