package com.example.comp304_003_finalproject;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SMSActivity extends Fragment {

    private EditText txtMobile;
    private EditText txtMessage;
    private Button btnSms;
    View viewSMSActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewSMSActivity = inflater.inflate(R.layout.activity_sms, container, false);

        txtMobile = (EditText)viewSMSActivity.findViewById(R.id.mblTxt);
        txtMessage = (EditText)viewSMSActivity.findViewById(R.id.msgTxt);
        btnSms = (Button)viewSMSActivity.findViewById(R.id.btnSend);
        btnSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    SmsManager smgr = SmsManager.getDefault();
                    smgr.sendTextMessage(txtMobile.getText().toString(),null,txtMessage.getText().toString(),null,null);
                    Toast.makeText(getContext(), "SMS Sent Successfully", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Toast.makeText(getContext(), "SMS Failed to Send, Please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return viewSMSActivity;
    }
}
