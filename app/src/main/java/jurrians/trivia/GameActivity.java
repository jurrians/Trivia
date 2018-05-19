package jurrians.trivia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity implements TriviaHelper.Callback {

    TriviaHelper triviaHelper;

    public TextView textQuestion;

    public TextView textOptionA;
    public TextView textOptionB;
    public TextView textOptionC;
    public TextView textOptionD;

    public int curScore;
    public TextView textScoreValue;

    public int lives;

    public ImageView heart1;
    public ImageView heart2;
    public ImageView heart3;

    public Question curQuestion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        triviaHelper = new TriviaHelper(getApplicationContext());
        triviaHelper.getNextQuestion(this);


        // get views for UI components
        textQuestion = findViewById(R.id.textQuestion);

        textOptionA = findViewById(R.id.textOptionA);
        textOptionB = findViewById(R.id.textOptionB);
        textOptionC = findViewById(R.id.textOptionC);
        textOptionD = findViewById(R.id.textOptionD);

//        curScore;
        textScoreValue = findViewById(R.id.textScoreValue);

        // textScore.setText(curScore);
        lives = 3;

        heart1 = findViewById(R.id.heart1);
        heart2 = findViewById(R.id.heart2);
        heart3 = findViewById(R.id.heart3);


//        //___________________________________________________________________//
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        database.setLogLevel(Logger.Level.DEBUG);
//
//        DatabaseReference myRef = database.getReference("message");
//        myRef.setValue("Hello, World!");
//
//        // Read from the database
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
//                Log.d("onDataChange", "Value is: " + value);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w("onCancelled", "Failed to read value.", error.toException());
//            }
//        });
//        //___________________________________________________________________//

    }

//    @Override
//    public void onResume(){
//        super.onResume();
//
//    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState); // always call super

        String textQuestionSave = textQuestion.getText().toString();
        outState.putString("textQuestion", textQuestionSave);

        String textOptionASave = textOptionA.getText().toString();
        outState.putString("textOptionA", textOptionASave);

        String textOptionBSave = textOptionB.getText().toString();
        outState.putString("textOptionB", textOptionBSave);

        String textOptionCSave = textOptionC.getText().toString();
        outState.putString("textOptionC", textOptionCSave);

        String textOptionDSave = textOptionD.getText().toString();
        outState.putString("textOptionD", textOptionDSave);

        int curScoreSave = curQuestion.questionValue;
        outState.putInt("curScore", curScoreSave);

//        String textScoreValueSave = textScoreValue.getText().toString();
//        outState.putString("textScore", textScoreValueSave);

//        int livesSave = lives;
//        outState.putInt("lives", livesSave);

        int heart1Save = heart1.getVisibility();
        outState.putInt("heart1",heart1Save);

        int heart2Save = heart2.getVisibility();
        outState.putInt("heart2",heart2Save);

        int heart3Save = heart3.getVisibility();
        outState.putInt("heart3",heart3Save);

//        outState.
    }

    @Override
    public void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);

        if (triviaHelper.queue != null) {
            triviaHelper.queue.cancelAll(triviaHelper.TAG);
            Log.d("onStop", triviaHelper.TAG);
        }

        String textQuestionRes = inState.getString("textQuestion");
        textQuestion.setText(textQuestionRes);


        String textOptionARes = inState.getString("textOptionA");
        textOptionA.setText(textOptionARes);

        String textOptionBRes = inState.getString("textOptionB");
        textOptionB.setText(textOptionBRes);

        String textOptionCRes = inState.getString("textOptionC");
        textOptionC.setText(textOptionCRes);

        String textOptionDRes = inState.getString("textOptionD");
        textOptionD.setText(textOptionDRes);
//
        int curScoreRes = inState.getInt("curScore");
        textScoreValue.setText(curScoreRes);
//        curScore = curScoreRes;

//        String textScoreValueRes = inState.getString("textScoreValue");
//        textScoreValue.setText(textScoreValueRes);

//        int livesRes = inState.getInt("lives");
//        lives = livesRes;

        int heart1Res = inState.getInt("heart1");
        heart1.setVisibility(heart1Res);

        int heart2Res = inState.getInt("heart2");
        heart2.setVisibility(heart2Res);

        int heart3Res = inState.getInt("heart3");
        heart3.setVisibility(heart3Res);
    }

    @Override
    public void gotNextQuestion(Question nextQuestion) {
            Log.d("Check", "gotNextQuestion");


            textQuestion.setText(nextQuestion.question);

            textOptionA.setText(nextQuestion.answers.get(0));
            textOptionB.setText(nextQuestion.answers.get(1));
            textOptionC.setText(nextQuestion.answers.get(2));
            textOptionD.setText(nextQuestion.answers.get(3));


            curQuestion = nextQuestion;
        }

//        triviaHelper.queue.cancelAll(triviaHelper.TAG);
//        Log.d("onStop", triviaHelper.TAG);


    @Override
    public void gotNextQuestionError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void onOptionClick(View view) {
        Log.d("Check", String.valueOf(view.getId()));

        int id = view.getId();

            // button 1
        if (id == 2131165328) {
            if (textOptionA.getText().toString().equals(curQuestion.correctAnswer)) {
                Toast.makeText(this, "CORRECT ANSWER!", Toast.LENGTH_LONG).show();
                Log.d("answer", "CORRECT!");

                Log.d("Value: ", String.valueOf(curQuestion.questionValue));


                int curScore = Integer.valueOf(textScoreValue.getText().toString());
                curScore += curQuestion.questionValue;

                textScoreValue.setText(String.valueOf(curScore));

                triviaHelper.getNextQuestion(this);

            }

            else {
                Toast.makeText(this, "WRONG ANSWER!", Toast.LENGTH_SHORT).show();
                lives -= 1;

                switch (lives) {
                    case 2:
                        heart1.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        heart2.setVisibility(View.INVISIBLE);
                        break;
                    case 0:
                        heart3.setVisibility(View.INVISIBLE);
                        Toast.makeText(this, "GAME OVER!", Toast.LENGTH_LONG).show();
                        // intent naar highscoreLijst.
                }

            }
        }

        else if (id == 2131165329) {
            if (textOptionB.getText().toString().equals(curQuestion.correctAnswer)) {
                Toast.makeText(this, "CORRECT ANSWER!", Toast.LENGTH_LONG).show();
                Log.d("answer", "CORRECT!");

                Log.d("Value: ", String.valueOf(curQuestion.questionValue));


                int curScore = Integer.valueOf(textScoreValue.getText().toString());
                curScore += curQuestion.questionValue;

                textScoreValue.setText(String.valueOf(curScore));

                triviaHelper.getNextQuestion(this);
            }

            else {
                Toast.makeText(this, "WRONG ANSWER!", Toast.LENGTH_SHORT).show();
                lives -= 1;

                switch (lives) {
                    case 2:
                        heart1.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        heart2.setVisibility(View.INVISIBLE);
                        break;
                    case 0:
                        heart3.setVisibility(View.INVISIBLE);
                        Toast.makeText(this, "GAME OVER!", Toast.LENGTH_LONG).show();
                        // intent naar
                }

            }
        }

        else if (id == 2131165330) {
            if (textOptionC.getText().toString().equals(curQuestion.correctAnswer)) {
                Toast.makeText(this, "CORRECT ANSWER!", Toast.LENGTH_LONG).show();
                Log.d("answer", "CORRECT!");

                Log.d("Value: ", String.valueOf(curQuestion.questionValue));


                int curScore = Integer.valueOf(textScoreValue.getText().toString());
                curScore += curQuestion.questionValue;

                textScoreValue.setText(String.valueOf(curScore));

                triviaHelper.getNextQuestion(this);
            }

            else {
                Toast.makeText(this, "WRONG ANSWER!", Toast.LENGTH_SHORT).show();
                lives -= 1;

                switch (lives) {
                    case 2:
                        heart1.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        heart2.setVisibility(View.INVISIBLE);
                        break;
                    case 0:
                        heart3.setVisibility(View.INVISIBLE);
                        Toast.makeText(this, "GAME OVER!", Toast.LENGTH_LONG).show();
                        // intent naar
                }

            }
        }

        else if (id == 2131165331) {
            if (textOptionD.getText().toString().equals(curQuestion.correctAnswer)) {
                Toast.makeText(this, "CORRECT ANSWER!", Toast.LENGTH_LONG).show();
                Log.d("answer", "CORRECT!");

                Log.d("Value: ", String.valueOf(curQuestion.questionValue));


                int curScore = Integer.valueOf(textScoreValue.getText().toString());
                curScore += curQuestion.questionValue;

                textScoreValue.setText(String.valueOf(curScore));

                triviaHelper.getNextQuestion(this);
            }

            else {
                Toast.makeText(this, "WRONG ANSWER!", Toast.LENGTH_SHORT).show();
                lives -= 1;

                switch (lives) {
                    case 2:
                        heart1.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        heart2.setVisibility(View.INVISIBLE);
                        break;
                    case 0:
                        heart3.setVisibility(View.INVISIBLE);
                        Toast.makeText(this, "GAME OVER!", Toast.LENGTH_LONG).show();
                        // intent naar

                }

            }
        }





    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (triviaHelper.queue != null) {
//            triviaHelper.queue.cancelAll(triviaHelper.TAG);
//            Log.d("onStop", triviaHelper.TAG);
//        }
//    }

}
