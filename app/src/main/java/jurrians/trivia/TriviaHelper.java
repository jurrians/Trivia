package jurrians.trivia;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class TriviaHelper implements Response.Listener<JSONObject>, Response.ErrorListener {

    private Context context;
    private Callback activity;

    public String TAG = "TAG";

    public RequestQueue queue;

    public interface Callback {
        void gotNextQuestion(Question nextQuestion);
        void gotNextQuestionError(String message);
    }

    public TriviaHelper(Context context) {
        this.context = context;
    }



    @Override
    public void onResponse(JSONObject response) {
        try {
            // get arraylist with all questions in category
            JSONArray questions = response.getJSONArray("clues");
            // get size of arraylist
            int size = response.getInt("clues_count");


            // get random question from category
            int max = size - 1;
            Log.d("maxNumber ", String.valueOf(max));
            int min = 0;

            int numberMain = (int) (Math.random() * (max - min) + 1);

            JSONObject mainQuestionObject = questions.getJSONObject(numberMain);

            max = max - 1;
            Log.d("maxNumber - 1 ", String.valueOf(max));

            questions.remove(numberMain);

            String mainQuestion = mainQuestionObject.getString("question");
            String correctAnswer = mainQuestionObject.getString("answer");
            int questionValue = Integer.parseInt(mainQuestionObject.getString("value")) ;

            // get random answers from category

            ArrayList<String> answers = new ArrayList<>();

            answers.add(correctAnswer);

            int randomNumber1 = (int) (Math.random() * (max - min) + 1);
            Log.d("RandomNumber1: ", String.valueOf(randomNumber1) );
            max = max - 1;
            Log.d("maxNumber - 1 ", String.valueOf(max));

            JSONObject answersObject1 = questions.getJSONObject(randomNumber1);
            String answerString1 = answersObject1.getString("answer");
            answers.add(answerString1);
            questions.remove(randomNumber1);


            int randomNumber2 = (int) (Math.random() * (max - min) + 1);
            Log.d("RandomNumber2: ", String.valueOf(randomNumber2) );
            max = max - 1;
            Log.d("maxNumber - 1 ", String.valueOf(max));

            JSONObject answersObject2 = questions.getJSONObject(randomNumber2);
            String answerString2 = answersObject2.getString("answer");
            answers.add(answerString2);
            questions.remove(randomNumber2);

            int randomNumber3 = (int) (Math.random() * (max - min) + 1);
            Log.d("RandomNumber3: ", String.valueOf(randomNumber3) );

            JSONObject answersObject3 = questions.getJSONObject(randomNumber3);
            String answerString3 = answersObject3.getString("answer");
            answers.add(answerString3);
            questions.remove(randomNumber3);


            Collections.shuffle(answers);

            Question nextQuestion = new Question(mainQuestion, correctAnswer, questionValue, answers);

            activity.gotNextQuestion(nextQuestion);




        } catch (JSONException e) {
            e.printStackTrace();
        }





    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("gotNextQuestionError", error.getMessage());
        activity.gotNextQuestionError(error.getMessage());
    }

    public void getNextQuestion(final Callback activity) {
        this.activity = activity;


        int max = 18418;
        int min = 1;

        String randomCategory = String.valueOf((int)(Math.random() * (max - min) + 1));
        Log.d("Random number main: ", randomCategory);

//        String urlCategoriesRandom = ("http://jservice.io/api/category?id=").concat(randomCategory);
//        String urlCategoriesRandom = new StringBuilder().append("http://jservice.io/api/category?id=").append(randomCategory);
//                buffer.toString();
        String baseUrl = "http://jservice.io";
        String url = baseUrl + "/api/category?id=" + randomCategory;


        // get new random category
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);


        RequestQueue queue = Volley.newRequestQueue(this.context);

        jsonObjectRequest.setTag(TAG);
        queue.add(jsonObjectRequest);

    }




}
