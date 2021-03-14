package com.example.project;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class splashScreen extends Activity {

    Handler handler;
    //Animation variables
    Animation leftanim,rightanim;
    TextView title,first_name,sec_name,third_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //Animations
        leftanim = AnimationUtils.loadAnimation(this,R.anim.left_animation);
        rightanim = AnimationUtils.loadAnimation(this,R.anim.right_animation);
        //Hooks
        title = findViewById(R.id.text_name);
        first_name = findViewById(R.id.text_person1);
        sec_name = findViewById(R.id.text_person2);
        third_name = findViewById(R.id.text_person3);

        title.setAnimation(leftanim);
        first_name.setAnimation(rightanim);
        sec_name.setAnimation(rightanim);
        third_name.setAnimation(rightanim);

        handler=new Handler();


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(splashScreen.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);

    }

}
