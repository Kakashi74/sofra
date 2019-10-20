package com.shanaptech.sofra.Utils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.shanaptech.sofra.Ui.activity.MainActivity;
import com.shanaptech.sofra.R;

public class Splash extends AppCompatActivity implements View.OnClickListener {

    private Button sellbtn , orderbtn ;
    private final int SPLASH_DISPLAY_LENGTH = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);

//        new Handler().postDelayed(new Runnable(){
//            @Override
//            public void run() {
//                Intent mainIntent = new Intent(Splash.this, MainActivity.class);
//                Splash.this.startActivity(mainIntent);
//                Splash.this.finish();
//            }
//        }, SPLASH_DISPLAY_LENGTH);

        sellbtn = findViewById(R.id.SellButton);
        orderbtn = findViewById(R.id.OrderButton);
        sellbtn.setOnClickListener(this);
        orderbtn.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.SellButton :


        Intent intent = new Intent(this , MainActivity.class);
        startActivity(intent);
           return;
            case R.id.OrderButton :
                Intent intent1 = new Intent(this , MainActivity.class);
                startActivity(intent1);
                return;
        }
    }
}


