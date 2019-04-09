package com.example_alex.rhymin;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView logIn;
    private TextView signUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textTitle = findViewById(R.id.TextViewTitle);
        textTitle.setTypeface(Typeface.createFromAsset(getAssets(), "Absolute.ttf"));

        logIn = findViewById(R.id.TextViewLogIn);
        signUp = findViewById(R.id.textViewSignUp);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LogIn.class);
                startActivity(intent);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });



    }

    //Check if user is logged or no
    @Override
    public void onStart() {
        super.onStart();
        mAuth=FirebaseAuth.getInstance();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser(); //usuario validado en este momento
        if (currentUser == null){
            Intent intent = new Intent(MainActivity.this, LogIn.class);
            startActivity(intent);
        }else{
            if (currentUser.isEmailVerified()) {
                String s = currentUser.getDisplayName() + "\n";
                s = s + currentUser.getEmail() + "\n";
                Toast.makeText(getApplicationContext(), "You are currently logged as " + s, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, LoggedInActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(MainActivity.this,
                        "Failed to verify.",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, LogIn.class);
                startActivity(intent);
            }

        }
  }

}
