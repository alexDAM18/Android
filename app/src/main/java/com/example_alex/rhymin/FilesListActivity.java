package com.example_alex.rhymin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilesListActivity extends AppCompatActivity {

    private String dirPath = "/data/data/com.example_alex.rhymin/files";
    private ArrayList<ListItem> list;
    private ListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_files_list);

        list = new ArrayList<>();


        File dir = new File(dirPath);
        File[] filelist = dir.listFiles();
        String[] theNamesOfFiles = new String[filelist.length];

        //Put the files into the recView
        for (int i = 0; i < theNamesOfFiles.length; i++) {
            theNamesOfFiles[i] = filelist[i].getName();
            list.add(new ListItem(theNamesOfFiles[i]));
        }

        RecyclerView recView = findViewById(R.id.recView);
        adapter = new ListAdapter(getApplicationContext(), list);
        recView.setAdapter(adapter);
        recView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false));
    }
}
