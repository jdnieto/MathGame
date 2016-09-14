package xserinfo.com.mathgame;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    // Defining gloval variables
    int correctAnswer;
    int currentScore = 0;
    int currentLevel = 1;

    // Defining textObjects
    TextView textObjectPartA;
    TextView textObjectPartB;
    TextView textObjectLevel;
    TextView textObjectScore;
    TextView textObjectOperation;

    // Defining buttonObjects
    Button buttonObjectChoice1;
    Button buttonObjectChoice2;
    Button buttonObjectChoice3;

    // Soundpool creation
    SoundPool soundPool;
    int winSound = -1;
    int loseSound = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        textObjectPartA = (TextView)findViewById(R.id.textPartA);
        textObjectPartB = (TextView)findViewById(R.id.textPartB);
        textObjectLevel = (TextView)findViewById(R.id.textLevel);
        textObjectScore = (TextView)findViewById(R.id.textScore);
        textObjectOperation = (TextView)findViewById(R.id.textOperator);

        buttonObjectChoice1 = (Button)findViewById(R.id.buttonOption1);
        buttonObjectChoice2 = (Button)findViewById(R.id.buttonOption2);
        buttonObjectChoice3 = (Button)findViewById(R.id.buttonOption3);

        buttonObjectChoice1.setOnClickListener(this);
        buttonObjectChoice2.setOnClickListener(this);
        buttonObjectChoice3.setOnClickListener(this);

        textObjectScore.setText("Score: "+currentScore);
        textObjectLevel.setText("Level: "+currentLevel);

        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
        try{
            AssetManager assetManager = getAssets();
            AssetFileDescriptor descriptor;
            //create our three fx in memory ready for use
            descriptor = assetManager.openFd("pass.wav");
            winSound = soundPool.load(descriptor, 0);
            descriptor = assetManager.openFd("ow.wav");
            loseSound = soundPool.load(descriptor, 0);
        }catch (IOException e){

        }

        setQuestion();
    }

    @Override
    public void onClick(View view) {
        int answerGiven=0;
        switch (view.getId()){
            case R.id.buttonOption1:
                answerGiven = Integer.parseInt("" +
                        buttonObjectChoice1.getText());
                break;
            case R.id.buttonOption2:
                answerGiven = Integer.parseInt("" +
                        buttonObjectChoice2.getText());
                break;
            case R.id.buttonOption3:
                answerGiven = Integer.parseInt("" +
                        buttonObjectChoice3.getText());
                break;
        }
        updateScoreAndLevel(answerGiven);
        setQuestion();
    }

    void setQuestion(){
        int numberRange = currentLevel * 3;
        Random randInt = new Random();
        int partA = randInt.nextInt(numberRange);
        partA++;

        int partB = randInt.nextInt(numberRange);
        partB++;

        int operationSelection = randInt.nextInt(4);
        switch (operationSelection){
            case 0:
                textObjectOperation.setText("+");
                correctAnswer = partA + partB;
                break;
            case 1:
                textObjectOperation.setText("-");
                correctAnswer = partA - partB;
                break;
            case 2:
                textObjectOperation.setText("*");
                correctAnswer = partA * partB;
                break;
            case 3:
                textObjectOperation.setText("/");
                while (partA < partB || partA % partB !=0){
                    partA = randInt.nextInt(numberRange);
                    partA++;
                    partB = randInt.nextInt(numberRange);
                    partB++;
                }
                correctAnswer = partA / partB;

                break;

        }
        int wrongAnswer1= correctAnswer + 2;
        int wrongAnswer2= correctAnswer - 2;

        textObjectPartA.setText(""+partA);
        textObjectPartB.setText(""+partB);

        int buttonLayout = randInt.nextInt(3);

        switch (buttonLayout){
            case 0:
                buttonObjectChoice1.setText(""+correctAnswer);
                buttonObjectChoice2.setText(""+wrongAnswer1);
                buttonObjectChoice3.setText(""+wrongAnswer2);
                break;
            case 1:
                buttonObjectChoice2.setText(""+correctAnswer);
                buttonObjectChoice3.setText(""+wrongAnswer1);
                buttonObjectChoice1.setText(""+wrongAnswer2);
                break;
            case 2:
                buttonObjectChoice3.setText(""+correctAnswer);
                buttonObjectChoice1.setText(""+wrongAnswer1);
                buttonObjectChoice2.setText(""+wrongAnswer2);
                break;
        }
    }

    void updateScoreAndLevel(int givenAnswer){
        if(isCorrect(givenAnswer)){
            for(int i = 1; i <= currentLevel; i++){
                currentScore = currentScore + i;
            }
            currentLevel++;
        }else{
            currentScore = 0;
            currentLevel = 1;
        }
        textObjectScore.setText("Score: "+currentScore);
        textObjectLevel.setText("Level: "+currentLevel);
    }

    boolean isCorrect(int answerGiven){
        boolean correctTrueOrFalse;
        if (answerGiven == correctAnswer){
            Toast.makeText(getApplicationContext(),"Well Done!",Toast.LENGTH_SHORT).show();
            soundPool.play(winSound, 1, 1, 0, 0, 1);
            correctTrueOrFalse=true;
        }else{
            Toast.makeText(getApplicationContext(),"Wrong, wrong!",Toast.LENGTH_SHORT).show();
            soundPool.play(loseSound, 1, 1, 0, 0, 1);
            correctTrueOrFalse=false;
        }
        return correctTrueOrFalse;
    }
}
