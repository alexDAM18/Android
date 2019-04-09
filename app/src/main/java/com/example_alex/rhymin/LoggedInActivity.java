package com.example_alex.rhymin;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoggedInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        TextView newFileText = findViewById(R.id.textViewNewFile);
        newFileText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WriteFileActivity.class);
                startActivity(intent);
            }
        });

        TextView openFile = findViewById(R.id.textViewOpenFile);
        openFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FilesListActivity.class);
                startActivity(intent);
            }
        });

      TextView title = findViewById(R.id.TextViewTitleLoggedIn);
      title.setTypeface(Typeface.createFromAsset(getAssets(), "Absolute.ttf"));
    }
}
