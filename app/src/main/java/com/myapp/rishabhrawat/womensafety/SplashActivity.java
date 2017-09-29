package com.myapp.rishabhrawat.womensafety;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {


    TextView text;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        text=(TextView)findViewById(R.id.tag);
        image=(ImageView)findViewById(R.id.splash);

        //set up the animation
        final Animation animationUtils=AnimationUtils.loadAnimation(this,R.anim.splashanim);

        //assign the animation to the imageview and textview
        text.setAnimation(animationUtils);
        image.setAnimation(animationUtils);

        //animation listener
        animationUtils.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                //when animation is end start a new activity
                SharedPreferenceClass sharedPreferenceClass = new SharedPreferenceClass(SplashActivity.this);
                if (!sharedPreferenceClass.getFlag()) {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                }
                else
                {
                    Intent intent=new Intent(SplashActivity.this,MainNavigationActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                    sharedPreferenceClass.setFlag(true);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
