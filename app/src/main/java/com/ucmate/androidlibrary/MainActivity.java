package com.ucmate.androidlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.ucmate.socialmediasaver.FacebookVideo.facebook;
import com.ucmate.socialmediasaver.InstagramVideo.instagram;

import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String respf = facebook.getvideo("https://www.facebook.com/166087743489310/videos/755520495827973");
                Toast.makeText(MainActivity.this, "RespF -- "+respf, Toast.LENGTH_SHORT).show();

//                String resp = instagram.getvideo("https://www.instagram.com/reel/CdL4aTWpgu0/");
//                Toast.makeText(MainActivity.this, "RespI -- "+resp, Toast.LENGTH_SHORT).show();
            }
        });

    }


}