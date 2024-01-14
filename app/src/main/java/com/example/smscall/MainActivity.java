package com.example.smscall;




import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MainActivity extends AppCompatActivity {
    private EditText editTextNumber;
    FloatingActionButton callbutton;
    static int PERMISSION_CODE=100;
    private EditText editTextMessage;
    private Matcher matcher;
    private Pattern pattern;
    private static final String NUMBER_PATTERN = "^[0-9]{10}$";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS, Manifest.permission.CALL_PHONE}, PERMISSION_CODE);
        //  ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);
        editTextMessage = findViewById(R.id.editTextTextMultiLine);
        editTextNumber = findViewById(R.id.editTextPhone);
        callbutton=findViewById(R.id.callbutton);
        pattern = Pattern.compile(NUMBER_PATTERN);
        if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CALL_PHONE},PERMISSION_CODE);
        }
        callbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = editTextNumber.getText().toString();
                if (number.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a phone number", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + number));
                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Call permission not granted.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

    public void sendSMS(View view) {
        try {


            String message = editTextMessage.getText().toString();
            String number = editTextNumber.getText().toString();
            matcher = pattern.matcher(number);
            if (number.isEmpty()) {
                editTextNumber.setError("Enter the number");
                Toast.makeText(MainActivity.this, "Sending Failed", Toast.LENGTH_SHORT).show();
            } else if (number.length() != 10) {

                Toast.makeText(MainActivity.this, "Please enter 10 digit number ", Toast.LENGTH_SHORT).show();
                editTextNumber.setError(" Enter 10 digit number ");
                {
                    if (!matcher.matches()) {
                        Toast.makeText(MainActivity.this, "Please enter number only", Toast.LENGTH_SHORT).show();
                        editTextNumber.setError("Please enter number only");

                    }
                }
            } else {
                // editTextNumber.setError(null);

                // if (!editTextNumber.matches("\\d{10}")) {
                //  editTextNumber.setError("Please enter a 10-digit number");
                // } else {
                // numberInput.setError(null);

                // if (number.length() != 10) {
                //   Toast.makeText(MainActivity.this, "Please enter a 10-digit number", Toast.LENGTH_SHORT).show();
                // editTextNumber.setError("Please enter a 10-digit number");
                // } else {
                // editTextNumber.setError(null);

                try {


                    SmsManager mySmsManager = SmsManager.getDefault();
                    mySmsManager.sendTextMessage(number, null, message, null, null);
                    Toast.makeText(getApplicationContext(), "Message Sent successfully!", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Sending Failed", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "Sending Failed", Toast.LENGTH_SHORT).show();
        }
    }
}
//}};