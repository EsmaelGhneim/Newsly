package com.example.jumann;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.jumann.R;

public class ProfileFragment extends Fragment {

    private TextView usernameTextView;
    private TextView emailTextView;
    private Button logoutButton;

    private SharedPreferences sharedPreferences;

    private DBHelper dbHelper;



    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        usernameTextView = view.findViewById(R.id.usernameTextView);
        emailTextView = view.findViewById(R.id.emailTextView);
        logoutButton = view.findViewById(R.id.logoutButton);

        sharedPreferences = getActivity().getSharedPreferences("MYPREFS", Context.MODE_PRIVATE);
        dbHelper = new DBHelper(getActivity());
        String username = sharedPreferences.getString("newuser", "");
        String email = dbHelper.getUserEmail(username);

        usernameTextView.setText("Hello " + username);
        emailTextView.setText("E-mail : " + email);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear the shared preferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                Intent display = new Intent(getContext() , MainActivity.class);
                startActivity(display);
                // Finish the activity
                getActivity().finish();

            }
        });

        return view;
    }
}
