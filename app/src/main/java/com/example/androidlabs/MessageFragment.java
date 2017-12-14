package com.example.androidlabs;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static com.example.androidlabs.ChatWindow.mTwoPane;

/**
 * Created by 周星丞 on 12/9/2017.
 */

public class MessageFragment extends Fragment {
    private ChatWindow chatactivity;
    Bundle arg;

    public MessageFragment(){

    }

    public MessageFragment( ChatWindow chatactivity){

        this.chatactivity = chatactivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        arg = getArguments();
        View v = inflater.inflate(R.layout.activity_message_fragment, container, false);
        String s = "No Message";
        String i = "No ID";
        if(arg != null) {
            i = arg.getString("_id");
            s = arg.getString("Message");
        }
        TextView idView = v.findViewById(R.id.messageID);
        idView.setText(i);
        TextView messageView = v.findViewById(R.id.messageShow);
        messageView.setText(s);

        Button delete = v.findViewById(R.id.messageDeleteButton);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChatWindow.class);
                getActivity().setResult(Integer.parseInt((arg.getString("_id"))), intent);
                if(!mTwoPane)
                getActivity().finish();
                else{
                    chatactivity.deleteMessage();
                }
            }
        });

        return v;
    }



}
