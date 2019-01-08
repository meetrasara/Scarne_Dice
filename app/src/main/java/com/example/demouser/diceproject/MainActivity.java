package com.example.demouser.diceproject;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final int MAX_DICE_NUM = 6;
    protected int userScore = 0;
    protected int userTotal = 0;
    protected int cptScore = 0;
    protected int cptTotal = 0;

    protected ImageView diceImageView;
    protected Drawable diceDrawable;
    protected TextView scoreView, turnView, computerView;
    protected Button rollBtn, holdBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        diceImageView = findViewById(R.id.diceImage);
        final Resources res = getResources();

        diceDrawable = res.getDrawable(R.drawable.dice1);

        scoreView = findViewById(R.id.score);
        turnView = findViewById(R.id.turnScore);
        computerView = findViewById(R.id.compScore);

        rollBtn = findViewById(R.id.rollButton);
        holdBtn = findViewById(R.id.holdButton);
        Button resetBtn = findViewById(R.id.resetButton);

        rollBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roll();
            }
        }
        );

        holdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hold();
                }
             }
        );

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
            }
        );
    }

    protected void roll(){
        int diceNum = rollHelper();

        if (diceNum == 1) {
            userScore = 0;
            turnView.setText("Your turn score: 0");

            computerTurn();
        }
        else {
            userScore += diceNum;
            turnView.setText("Your turn score: "+ userScore);
        }
    }

    protected int rollHelper() {
        Random rand = new Random();
        int diceNum = rand.nextInt(MAX_DICE_NUM) + 1;
        final String dice = "dice" + diceNum;

        diceImageView.setImageResource(getResources().getIdentifier(dice,"drawable", getPackageName()));

        return diceNum;
    }

    protected void hold(){
        userTotal += userScore;
        scoreView.setText("Your score: " + userTotal);
        // the turn is end
        userScore = 0;
        turnView.setText("Your turn score: "+ userScore);

        computerTurn();
    }

    protected void reset(){
        userTotal = 0;
        userScore = 0;
        cptScore = 0;
        cptTotal = 0;
        scoreView.setText("Your score: 0");
        turnView.setText("Your turn score: 0");
        computerView.setText("Computer score: 0");
    }

    //This method to hold a single roll of the computer - still working on it
    protected int computerTurnHelper() {
        rollBtn.setEnabled(false);
        holdBtn.setEnabled(false);

        int diceNum = rollHelper();
        Log.i("test", "Computer turn: " + diceNum);
        if(diceNum == 1){
            Log.i("test", "Computer turned a 1");
            cptScore = 0;
        }
        else{
            cptScore += diceNum;
        }

        cptTotal += cptScore;
        cptScore = 0;

        computerView.setText("Computer Score: " + cptTotal);

        rollBtn.setEnabled(true);
        holdBtn.setEnabled(true);

        return cptScore;
    }
    //Still incomplete
    protected void computerTurn() {
        while (cptScore < 20) {
            cptScore += computerTurnHelper();

        }
    }
}