package com.example.androidlabs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

public class ChatRoomActivity extends AppCompatActivity implements View.OnClickListener{
    // private ArrayList<Message> messages;
    private EditText editText;
    private MyListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);

        // messages = new ArrayList<>();
        editText = findViewById(R.id.editTextChatMsg);
        ListView listConv = findViewById(R.id.listConversation);
        adapter = new MyListAdapter(this, R.id.listConversation);
        listConv.setAdapter(adapter);

        Button buttonSend = findViewById(R.id.buttonSend);
        buttonSend.setOnClickListener(this);

        Button buttonReceived = findViewById(R.id.buttonReceive);
        buttonReceived.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String input = editText.getText().toString();

        if (input.length() == 0)
            return;

        switch (v.getId()) {
            case R.id.buttonSend:
                adapter.add(new Message(input, MessageType.SENT));
                break;
            case R.id.buttonReceive:
                adapter.add(new Message(input, MessageType.RECEIVED));
                break;
            default:
                assert false;
        }
        adapter.notifyDataSetChanged();
        editText.setText("");
    }



    private enum MessageType { SENT, RECEIVED }

    private class Message {
        private String message;
        private MessageType type;

        public Message(String message, MessageType type) {
            this.message = message;
            this.type = type;
        }

        public String getMessage() {
            return message;
        }

        public MessageType getType() {
            return type;
        }

        @Override
        public String toString() {
            return "Message{" +
                    "message='" + message + '\'' +
                    ", type=" + type +
                    '}';
        }
    }

    private class MyListAdapter extends ArrayAdapter<Message> {
        private LayoutInflater inflater;


        public MyListAdapter(Context context, int resource) {
            super(context, resource);
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Message message = getItem(position);

            View view = null;
            TextView textView = null;

            if (message.getType() == MessageType.SENT) {
                view = inflater.inflate(R.layout.chat_message_sent, null);
                textView = view.findViewById(R.id.textViewSent);

            } else if (message.getType() == MessageType.RECEIVED) {
                view = inflater.inflate(R.layout.chat_message_received, null);
                textView = view.findViewById(R.id.textViewReceived);
            }
            textView.setText(message.getMessage());

            return view;
        }
    }
}
