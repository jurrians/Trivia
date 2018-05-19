package jurrians.trivia;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Question {

    // question
    // answers[]
    // correctAnswer

    public String question;
    public String correctAnswer;
    public int questionValue;

    public ArrayList<String> answers;

    public Question(String question, String correctAnswer, int questionValue, ArrayList<String> answers) {
        Log.d("Check", "Question constructor operational");
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.questionValue = questionValue;

        this.answers = answers;
    }


}
