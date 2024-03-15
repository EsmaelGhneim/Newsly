package com.example.jumann;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {

    private static final String ADMIN_USERNAME = "juman";
    private static final String ADMIN_PASSWORD = "1234";
    private static final String PREF_USERNAME = "newuser";
    private static final String PREF_PASSWORD = "newpass";

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEditText = findViewById(R.id.etnm);
        passwordEditText = findViewById(R.id.etps);
        loginButton = findViewById(R.id.log);
        signupButton = findViewById(R.id.sign);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
                    saveCredentials(username, password);
                    openAdminActivity();
                } else {
                    DBHelper dbHelper = new DBHelper(getApplicationContext());
                    boolean isUserValid = dbHelper.checkUser(username, password);
                    if (isUserValid) {
                        saveCredentials(username, password);
                        openDisplayActivity();
                    } else {
                        Toast.makeText(MainActivity.this, "Username or Password is incorrect", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signup = new Intent(MainActivity.this, Signup.class);
                startActivity(signup);
            }
        });

        checkCredentials();
    }

    private void saveCredentials(String username, String password) {
        SharedPreferences preferences = getSharedPreferences("MYPREFS", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_USERNAME, username);
        editor.putString(PREF_PASSWORD, password);
        editor.apply();
    }

    private void checkCredentials() {
        SharedPreferences preferences = getSharedPreferences("MYPREFS", MODE_PRIVATE);
        String savedUsername = preferences.getString(PREF_USERNAME, "");
        String savedPassword = preferences.getString(PREF_PASSWORD, "");

        if (!savedUsername.isEmpty() && !savedPassword.isEmpty()) {
            if (savedUsername.equals(ADMIN_USERNAME) && savedPassword.equals(ADMIN_PASSWORD)) {
                openAdminActivity();
            } else {
                DBHelper dbHelper = new DBHelper(getApplicationContext());
                boolean isUserValid = dbHelper.checkUser(savedUsername, savedPassword);
                if (isUserValid) {
                    openDisplayActivity();
                }
            }
        }
    }

    private void openAdminActivity() {
        Intent adminIntent = new Intent(MainActivity.this, AdminActivity.class);
        startActivity(adminIntent);
        finish();
    }

    private void openDisplayActivity() {
        Intent displayIntent = new Intent(MainActivity.this, DisplayActivity.class);
        startActivity(displayIntent);
        finish();
    }
}


