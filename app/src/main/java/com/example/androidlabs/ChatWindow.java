package com.example.androidlabs;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "ChatWindowActivity";

    public static boolean mTwoPane;

    ArrayList<String> chatMessage = new ArrayList<>();
    ListView chatWindow;
    EditText chatEditText;
    ChatAdapter messageAdapter;
    Button chatSendButton;
    Long iD;
    MessageFragment messageFragment = new MessageFragment(this);

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

        if (findViewById(R.id.tabletFrame) != null) {
            Log.i("Info", "It's a tablet");
            mTwoPane = true;
        }
//
//        db = tempDBH.getWritableDatabase();
//        db.delete(tempDBH.MESSAGE_TABLE,null,null);

        db = tempDBH.getWritableDatabase();
        cursor = db.query(tempDBH.MESSAGE_TABLE, tempDBH.Column_Names,
                null, null, null, null, null, null);
//        int index = 0;
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
//                if(index != Integer.parseInt(cursor.getString(0))) {
//                    db.insert(tempDBH.MESSAGE_TABLE, null, values);
//                    cursor.getString(1);
//                }
                chatMessage.add(cursor.getString(1));
                cursor.moveToNext();
            }
            Log.i(ACTIVITY_NAME, "Cursor’s  column count =" + cursor.getColumnCount());
        }

        chatWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {


                iD = id;
                Bundle arg = new Bundle();
                arg.putString(tempDBH.Column_Names[0], "" + id);
                arg.putString(tempDBH.Column_Names[1], messageAdapter.getItem(position));

                if (mTwoPane) {

                    Log.i("Info", "Item Clicked in Tablet");
                    messageFragment.setArguments(arg);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.tabletFrame, messageFragment);
                    transaction.commit();

                } else {
                    Log.i("Info", "Show this if using phone");
                    Intent intent = new Intent(ChatWindow.this, MessageDetails.class);
                    intent.putExtras(arg);
                    startActivityForResult(intent, 999);
                }

            }
        });

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

    public void deleteMessage() {
        db.execSQL("delete from " + tempDBH.MESSAGE_TABLE + " where " + tempDBH.Column_Names[0] + " =" + Integer.parseInt(cursor.getString(0)) + ";");
        getFragmentManager().beginTransaction().remove(messageFragment).commit();
        recreate();
    }

    private class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount() {
            return chatMessage.size();
        }

        @Override
        public long getItemId(int position) {

            db = tempDBH.getWritableDatabase();
            cursor = db.query(tempDBH.MESSAGE_TABLE, tempDBH.Column_Names,
                    null, null, null, null, null, null);
            cursor.moveToPosition(position);
            Log.i("Position", "" + position);
//            cursor.getString(0);
            return Long.parseLong(cursor.getString(0));
        }

        @Override
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("Delete", "" + requestCode);
        Log.i("Delete", "Message Deleting");
        if (requestCode == 999) {
            if (mTwoPane)
                deleteMessage();
            db.execSQL("delete from " + tempDBH.MESSAGE_TABLE + " where " + tempDBH.Column_Names[0] + " ='" + resultCode + "';");

        }
        recreate();
    }

    @Override
    public void onDestroy() {
        Log.i(ACTIVITY_NAME, "In onDestroy()");
        super.onDestroy();
        db.close();
    }

}
