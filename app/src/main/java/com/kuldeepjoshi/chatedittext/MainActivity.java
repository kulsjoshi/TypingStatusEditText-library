package com.kuldeepjoshi.chatedittext;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.kuldeepjoshi.chatedittextlibrary.TypingStatusEditText;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private TypingStatusEditText etTypingStatusEditText;
    private TextView txtTypingStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialization();
    }

    private void initialization(){

        etTypingStatusEditText = findViewById(R.id.etChatEditText);
        txtTypingStatus = findViewById(R.id.txtTypingStatusData);

        etTypingStatusEditText.setOnTypingModified(new TypingStatusEditText.OnTypingModified() {
            @Override
            public void onIsTypingModified(EditText view, boolean isTyping) {

                if (isTyping) {
                    Log.i(TAG, "onIsTypingModified: User started typing.");
                    txtTypingStatus.setText("Typing....");
                } else {
                    Log.i(TAG, "onIsTypingModified: User stopped typing");
                    txtTypingStatus.setText("Stopped Typing....");
                }

            }
        });

    }
}