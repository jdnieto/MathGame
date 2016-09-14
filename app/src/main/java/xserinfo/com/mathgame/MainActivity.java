package xserinfo.com.mathgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonObjectPlay = (Button)findViewById(R.id.buttonPlay);
        Button buttonObjectQuit = (Button)findViewById(R.id.buttonQuit);
        buttonObjectPlay.setOnClickListener(this);
        buttonObjectQuit.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonPlay:
                Intent i;
                i = new Intent(this, GameActivity.class);
                startActivity(i);
                break;
            case R.id.buttonQuit:
                finish();
        }

    }
}
