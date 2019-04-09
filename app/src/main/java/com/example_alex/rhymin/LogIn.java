package com.example_alex.rhymin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogIn extends AppCompatActivity {

    private FirebaseAuth mAuth;
    public static final String TAG = "1";
    private EditText email;
    private EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);



        mAuth = FirebaseAuth.getInstance();

        TextView title = findViewById(R.id.Title);
        title.setTypeface(Typeface.createFromAsset(getAssets(), "Absolute.ttf"));
        LinearLayout loginLay = findViewById(R.id.linearLogIn);
        BackgroundLogInAnimation(loginLay);



        TextView login = findViewById(R.id.loginText);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               signIn();

            }
        });



        TextView register = findViewById(R.id.registerText);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void signIn() {
        EditText email = findViewById(R.id.EmailLogInText);
        EditText password = findViewById(R.id.passwordText);
        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(LogIn.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LogIn.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });



    }



    private void BackgroundLogInAnimation(LinearLayout loginLay) {
        AnimationDrawable animationDrawable = (AnimationDrawable) loginLay.getBackground();

        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);

        animationDrawable.start();
    }




}
