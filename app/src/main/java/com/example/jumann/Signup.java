package com.example.jumann;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Signup extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        final EditText username=(EditText) findViewById(R.id.etname);
        final EditText password=(EditText) findViewById(R.id.etpass2);
        final EditText email=(EditText) findViewById(R.id.etmail);
        Button btn1=(Button) findViewById(R.id.btn3);
        DBHelper dbHelper = new DBHelper(getApplicationContext());



        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences preferences = getSharedPreferences("MYPREFS", MODE_PRIVATE);
                String newuser = username.getText().toString();
                String newpass = password.getText().toString();
                String newmail = email.getText().toString();
                if(!newuser.isEmpty() && !newpass.isEmpty() && !newmail.isEmpty())
                {DBHelper dbHelper = new DBHelper(getApplicationContext());
                dbHelper.addUser(newuser, newpass, newmail);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("newuser", newuser);
                editor.putString("newpass", newpass);
                editor.apply();
                Intent loginscreen = new Intent(Signup.this, MainActivity.class);
                startActivity(loginscreen);}
                else
                    Toast.makeText(Signup.this,"please insert your infromation",Toast.LENGTH_SHORT).show();

            }



        });



    }

}
