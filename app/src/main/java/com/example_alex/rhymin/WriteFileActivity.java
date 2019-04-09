package com.example_alex.rhymin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.TestLooperManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.VectorEnabledTintResources;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.FileOutputStream;

public class WriteFileActivity extends AppCompatActivity {

    EditText textWrite;
    private String m_Text = "";
    private String filename = "";
    private String fileContents = "";
    FileOutputStream outputStream;
    private String getString = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_file);

        ImageView book = findViewById(R.id.imageViewBook);
        book.bringToFront();

        textWrite = findViewById(R.id.editTextWrite);
        textWrite.bringToFront();

        ScrollView scrollView = findViewById(R.id.scroll);
        scrollView.bringToFront();

        TextView saveFileText = findViewById(R.id.textSaveFile);
        saveFileText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveDialogue();
            }
        });

        TextView textGoToRhymeSearch = findViewById(R.id.textGoToRhymeSearch);
        textGoToRhymeSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RhymeSearchActivity.class);
                startActivity(intent);
            }
        });

        TextView goBack = findViewById(R.id.textGoBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoggedInActivity.class);
                startActivity(intent);
            }
        });
    }

    //Dialogue for saving the file
    private void SaveDialogue() {
        //Dialogo
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ponle un titulo a tu fichero");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();
                filename = m_Text;
                fileContents = textWrite.getText().toString();
                try {
                    outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                    outputStream.write(fileContents.getBytes());
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
}
