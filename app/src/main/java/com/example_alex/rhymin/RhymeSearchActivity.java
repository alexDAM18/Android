package com.example_alex.rhymin;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RhymeSearchActivity extends AppCompatActivity {


    FileInputStream is;
    BufferedReader reader;
    Context context;
    private String line;
    private String fileName = "example";
    private String wordSearched;
    private String dictionaryWord;
    private int lineNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rhyme_search);

        Button rhymeButton = findViewById(R.id.ButtonSearchWord);


        final TextView textOneSyllWords = findViewById(R.id.textOneSyllableWord);
        final TextView textTwoSyllWord = findViewById(R.id.textTwoSyllableWord);
        final TextView textThreeSyllWord = findViewById(R.id.textThreeSyllableWord);
        final TextView textFourSyllWord = findViewById(R.id.textFourSyllableWord);
        final SearchView searchWord = findViewById(R.id.SearchViewWord);
        final CharSequence query = searchWord.getQuery();
        dictionaryWord = readRawTextFile(getApplicationContext(), R.raw.example);
        rhymeSearch(rhymeButton, textOneSyllWords, textTwoSyllWord, textThreeSyllWord, textFourSyllWord, query);
    }

    private void rhymeSearch(Button rhymeButton, final TextView textOneSyllWords,
                             final TextView textTwoSyllWord, final TextView textThreeSyllWord,
                             final TextView textFourSyllWord,  final CharSequence query) {
        rhymeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final TextView searchForWord  = findViewById(R.id.searchedWordText);
                final TextView textViewPhrase = findViewById(R.id.textViewSearchWord);
                wordSearched = query.toString();
                String lasSyllableWord = returnLastSyllable(wordSearched);
                searchForWord.setText(wordSearched);
                textViewPhrase.setVisibility(View.VISIBLE);

                //Hacemos split en las palabras y cogemos la ultima silaba
                textOneSyllWords.setText("");
                try {
                    InputStream in   = getResources().openRawResource(R.raw.dictionary);
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    String line;
                    while ((line = br.readLine()) != null) {
                       final String lastSyllableDict  = returnLastSyllable(line);
                       int numSyllables = countSyllables(line);
                        if (lasSyllableWord.equals(lastSyllableDict)){
                            switch (numSyllables){
                                case 1:
                                    textOneSyllWords.append(line + ", ");
                                    break;
                                case 2:
                                    textTwoSyllWord.append(line + ", " );
                                    break;
                                case 3:
                                    textThreeSyllWord.append(line + ", ");
                                    break;
                                case 4:
                                    textFourSyllWord.append(line + ", ");
                                    break;
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    //Leer el fichero
    public static String readRawTextFile(Context ctx, int resId) {
        InputStream inputStream = ctx.getResources().openRawResource(resId);

        InputStreamReader inputreader = new InputStreamReader(inputStream);
        BufferedReader buffreader = new BufferedReader(inputreader);
        String line;
        StringBuilder text = new StringBuilder();

        try {
            while (( line = buffreader.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
        } catch (IOException e) {
            return null;
        }
        return text.toString();
    }


    public static int countSyllables(String word)
    {
        char[] vowels = { 'a', 'e', 'i', 'o', 'u', 'y' };
        char[] currentWord = word.toCharArray();
        int numVowels = 0;
        boolean lastWasVowel = false;
        for (char wc : currentWord) {
            boolean foundVowel = false;
            for (char v : vowels)
            {
                //don't count diphthongs
                if ((v == wc) && lastWasVowel)
                {
                    foundVowel = true;
                    lastWasVowel = true;
                    break;
                }
                else if (v == wc && !lastWasVowel)
                {
                    numVowels++;
                    foundVowel = true;
                    lastWasVowel = true;
                    break;
                }
            }
            // If full cycle and no vowel found, set lastWasVowel to false;
            if (!foundVowel)
                lastWasVowel = false;
        }
        // Remove es, it's _usually? silent
        if (word.length() > 2 &&
                word.substring(word.length() - 2).equals("es"))
            numVowels--;
            // remove silent e
        else if (word.length() > 1 &&
                word.substring(word.length() - 1).equals("e"))
            numVowels--;
        return numVowels;
    }


    //Metodo para sacar la ultima silaba de las palabras
    public static String returnLastSyllable(String word)
    {

        char[] vowels = { 'a', 'e', 'i', 'o', 'u', 'y' };
        char[] currentWord = word.toCharArray();
        int numVowels = 0;
        int posVowel = 0;
        int saveVowel = 0;
        int saveVowelAnt = 0;
        boolean lastWasVowel = false;
        for (char wc : currentWord) {
            boolean foundVowel = false;
            posVowel++;
            for (char v : vowels)
            {
                //don't count diphthongs
                if ((v == wc) && lastWasVowel)
                {
                    foundVowel = true;
                    lastWasVowel = true;
                    break;
                }
                else if (v == wc && !lastWasVowel)
                {
                    numVowels++;
                    saveVowelAnt = saveVowel;
                    saveVowel = posVowel;
                    foundVowel = true;
                    lastWasVowel = true;
                    break;
                }
            }
            // If full cycle and no vowel found, set lastWasVowel to false;
            if (!foundVowel)
                lastWasVowel = false;
        }
        // Remove es, it's _usually? silent
        if (word.length() > 2 &&
                word.substring(word.length() - 2).equals("es")) {
            numVowels--;
            saveVowel = saveVowelAnt;
        }
            // remove silent e
        else if (word.length() > 1 &&
                word.substring(word.length() - 1).equals("e")) {
            numVowels--;
            saveVowel = saveVowelAnt;
        }
        if (saveVowel == 0){
            saveVowel = 1;
        }
        return word.substring(saveVowel - 1);
    }


}

