package com.gavhane.b.vivek.simpleanswerquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    AdView mAdview;                    //creating variables for Add view

    Button b_continue;
    TextView tv_question;
    EditText et_answer;

    List<Item> questions;           // We r creating List of type Item   .we created the Item class to get questions and answers  constructor   to fetch all the questions and corresponding answers here
    int curQuestion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this,"ca-app-pub-6556746493468246~4757739913");       // This is the real App id // so by adding that dependancy (implementation 'com.google.android.gms:play-services-ads:11.0.0')  in build gradle module app   and   initialising here ,  we have added AddMob to our android SDK project

        mAdview = (AdView) findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder().build();            //create new variable for an Add Request
        mAdview.loadAd(adRequest);   //This will load add on Banner

        b_continue = (Button) findViewById(R.id.b_continue);
        tv_question = (TextView) findViewById(R.id.tv_question);
        et_answer = (EditText) findViewById(R.id.et_answer);

        b_continue.setVisibility(View.INVISIBLE);               //this is for to set Button grayed out option  (INVISIBLE  for graded out,  VISIBLE for graded in)

        questions = new ArrayList<>();

        //Add all questions and answers to ArrayList  questions
        for (int i = 0; i < Database.questions.length;i++){
            questions.add(new Item(Database.questions[i], Database.answers[i]));       // Here 2 constructor parameters of Item class are : (Database.questions[i], Database.answers[i])    // note down here 1st questions  is List of questions created   and    2nd questions means: Database.questions[i] : questions from Database class
        }
                                                                    //we did not do  -> questions.addAll(Arrays.asList(Database.questions));     but we used for loop and another class Item bcz we want to fetch questions and corresponding answers
        //shuffle all the questions after adding
        Collections.shuffle(questions);

        tv_question.setText(questions.get(curQuestion).getQuestion());


        et_answer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //check if the entered answer is right i.e. whether it matches the answer stored in questions ArrayList
                if(et_answer.getText().toString().equalsIgnoreCase(questions.get(curQuestion).getAnswer())){
                    b_continue.setVisibility(View.VISIBLE);                     //i think this is for to set Button grayed out option
                } else {
                    b_continue.setVisibility(View.INVISIBLE);               //i think this is for to set Button grayed out option
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        b_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (curQuestion < Database.questions.length - 1){
                    // Next question
                    curQuestion++;
                    tv_question.setText(questions.get(curQuestion).getQuestion());
                    b_continue.setVisibility(View.INVISIBLE);
                    et_answer.setText("");
                } else {
                    // No more questions - game over
                    Toast.makeText(MainActivity.this, "You won the Game!!!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
