package com.example_alex.rhymin;

import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
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

public class Register extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private FirebaseAuth mAuth;
    public static final String TAG = "1";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView titleRegister = findViewById(R.id.textCrearCuenta);
        titleRegister.setTypeface(Typeface.createFromAsset(getAssets(), "basictitlefont.ttf"));

        mAuth = FirebaseAuth.getInstance();

        TextView buttonRegister = findViewById(R.id.RegisterTextButton);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });



    }

    private void signUp() {
        email = findViewById(R.id.emailRegister);
        password = findViewById(R.id.registerPassw);
        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            verify();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Register.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

        private void verify(){
            final FirebaseUser user = mAuth.getCurrentUser();
            user.sendEmailVerification().addOnCompleteListener(Register.this, new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(Register.this,
                                "Verification email sent to " + user.getEmail(),
                                Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Log.e(TAG, "sendEmailVerification", task.getException());
                        Toast.makeText(Register.this,
                                "Failed to send verification email.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


}
